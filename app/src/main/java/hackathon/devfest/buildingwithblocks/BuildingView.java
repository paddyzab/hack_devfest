package hackathon.devfest.buildingwithblocks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class BuildingView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private int act_cursor_y=0;
    private int act_cursor_x=BLOCKS_X/2+1;

    private final static int BLOCKS_X = 23;
    private final static int BLOCKS_Y = 23;

    private final int[][] backingArray = new int[BLOCKS_X][BLOCKS_Y];

    private final Bitmap[] blockImages = new Bitmap[2];
    private int blockSize;
    private final Paint paint;

    private GestureDetectorCompat detector;

    private IHouseSpec spec;

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
    }


    public void update() {

        invalidate();
        act_cursor_y++;

        if (act_cursor_y==BLOCKS_Y-1 || backingArray[act_cursor_x][act_cursor_y+1]>0){
            backingArray[act_cursor_x][act_cursor_y]=1;
            act_cursor_y=0;
        }


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
//        final HouseSpec spec = new HouseSpec();
        for(int x = 0; x<BLOCKS_X - HouseSpec.SIZE; x++) {
            for(int y = 0; y<BLOCKS_Y - HouseSpec.SIZE; x++) {
                boolean maching = true;
                exit:
                if (maching) {

                    for(int xx = 0; xx<HouseSpec.SIZE; xx++) {
                        for(int yy = 0; yy<HouseSpec.SIZE; yy++) {
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
        if (act_cursor_x>=0){
            act_cursor_x--;
        }
    }

    public void moveRight() {
        if (act_cursor_x<=BLOCKS_X) {
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
}
