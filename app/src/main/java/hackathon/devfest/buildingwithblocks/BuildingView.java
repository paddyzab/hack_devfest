package hackathon.devfest.buildingwithblocks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class BuildingView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, SensorEventListener {

    private int act_cursor_y = 0;
    private int act_cursor_x = BLOCKS_X / 2 + 1;

    private final static int BLOCKS_X = 23;
    private final static int BLOCKS_Y = 23;
    private final static float ACCELERATION_TRESHOLD = 3.0f;

    private final int[][] backingArray = new int[BLOCKS_X][BLOCKS_Y];

    private final Bitmap[] blockImages = new Bitmap[2];
    private int blockSize;
    private final Paint paint;

    private GestureDetectorCompat detector;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private IHouseSpec spec;

    private GameUpdateListener listener;

    public void setListener(GameUpdateListener listener) {
        this.listener = listener;
    }


    public interface GameUpdateListener {
        void onHouseCompleted();

        void onGameEnded();
    }

    public BuildingView(Context context, AttributeSet attrs) {
        super(context, attrs);


        spec = new MockHouseSpec();

        /*for (int x = 0; x < HouseSpec.SIZE; x++) {
            for (int y = 0; y < HouseSpec.SIZE; y++) {
                backingArray[x][y] = spec.getPlan()[y][x];
            }

        }
        */
        paint = new Paint();

        detector = new GestureDetectorCompat(context, this);
        detector.setOnDoubleTapListener(this);
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // Sorry, there are no accelerometers on your device.
            // You can't play this game.
            Log.d("LOG_TAG", "device has no ACCELEROMETER, use pad or fingers!");
        }
    }

    public void reset() {
        for (int x = 0; x < BLOCKS_X; x++) {
            for (int y = 0; y < BLOCKS_Y; y++) {
                backingArray[x][y] = 0;
            }
        }
        invalidate();
    }

    public void update() {

        act_cursor_y++;

        if (act_cursor_y == BLOCKS_Y - 1 || backingArray[act_cursor_x][act_cursor_y + 1] > 0) {
            backingArray[act_cursor_x][act_cursor_y] = 1;
            act_cursor_y = 0;

            final Point point = find();

            if (point != null) {

                if (point.x == 0) {
                    listener.onGameEnded();
                }

                listener.onHouseCompleted();


                for (int x = 0; x < spec.getSize(); x++) {
                    for (int y = 0; y < spec.getSize(); y++) {
                        backingArray[point.x + x][point.y + y] = 0;
                    }

                }


            }
        }
        invalidate();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        blockSize = w / BLOCKS_X;
        blockImages[0] = calcBitmap(blockSize, R.drawable.bg_castle);
        blockImages[1] = calcBitmap(blockSize, R.drawable.brick_wall);

    }

    private Bitmap calcBitmap(float size, int resourceId) {
        final Bitmap unscaled_bitmap = BitmapFactory.decodeResource(this.getResources(), resourceId);
        return Bitmap.createScaledBitmap(unscaled_bitmap, (int) size, (int) size, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int x = 0; x < BLOCKS_X; x++) {
            for (int y = 0; y < BLOCKS_X; y++) {
                canvas.drawBitmap(blockImages[backingArray[x][y]], x * blockSize, y * blockSize, paint);
            }
        }

        canvas.drawBitmap(blockImages[1], act_cursor_x * blockSize, act_cursor_y * blockSize, paint);


    }

    private Point find() {

        for (int x = 0; x <= BLOCKS_X - spec.getSize(); x++) {
            for (int y = 0; y <= BLOCKS_Y - spec.getSize(); y++) {
                boolean maching = true;
                exit:
                if (maching) {

                    for (int xx = 0; xx < spec.getSize(); xx++) {
                        for (int yy = 0; yy < spec.getSize(); yy++) {

                            maching &= backingArray[x + xx][y + yy] == spec.getPlan()[xx][yy];
                            if (!maching) {
                                break exit;
                            }

                        }
                    }
                }


                if (maching) {
                    return new Point(x, y);
                }

            }
        }


        return null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.detector.onTouchEvent(event);
        return true;
    }

    public void moveLeft() {
        if (act_cursor_x >= 0) {
            act_cursor_x--;
        }
    }

    public void moveRight() {
        if (act_cursor_x <= BLOCKS_X) {
            act_cursor_x++;
        }
    }

    private void rotate() {
        Log.d("LOG_TAG", "Mr ROTATOR do the flip!");
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        rotate();
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {

        float eventX = motionEvent.getX();


        if (eventX < getWidth() / 2) {
            // LEFT SIDE OF VIEW
            moveLeft();
        } else {
            // RIGHT SIDE OF VIEW
            moveRight();
        }

        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        // nop
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        // nop
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        // nop
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        // nop
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        // nop
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        // nop
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        // nop
        return false;
    }

    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {
        final Sensor sensor = sensorEvent.sensor;

        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float y = sensorEvent.values[1];

            if (y < -ACCELERATION_TRESHOLD) {
                moveLeft();
            } else if (y > ACCELERATION_TRESHOLD) {
                moveRight();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // do nothing
    }
}
