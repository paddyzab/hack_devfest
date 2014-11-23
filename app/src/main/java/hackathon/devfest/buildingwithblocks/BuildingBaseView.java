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

abstract class BuildingBaseView extends View {

    protected final static int BLOCKS_X = 23;
    protected final static int BLOCKS_Y = 23;

    protected final int[][] backingArray = new int[BLOCKS_X][BLOCKS_Y];

    protected final Bitmap[] blockImages = new Bitmap[2];
    protected int blockSize;
    protected final Paint paint;

    private IHouseSpec spec;

    public BuildingBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);


        spec = new MockHouseSpec();

        /*for (int x = 0; x < HouseSpec.SIZE; x++) {
            for (int y = 0; y < HouseSpec.SIZE; y++) {
                backingArray[x][y] = spec.getPlan()[y][x];
            }

        }
        */
        paint = new Paint();

    }

    public void reset() {
        for (int x = 0; x < BLOCKS_X; x++) {
            for (int y = 0; y < BLOCKS_Y; y++) {
                backingArray[x][y] = 0;
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

    }

}
