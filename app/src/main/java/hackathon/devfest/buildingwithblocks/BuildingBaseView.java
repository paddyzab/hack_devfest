package hackathon.devfest.buildingwithblocks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

abstract class BuildingBaseView extends View {

    protected int getBlocksX(){
        return 23;
    }

    protected int getBlocksY(){
        return 23;
    }

    protected int[][] backingArray;

    protected final Bitmap[] blockImages = new Bitmap[2];
    protected int blockSize;
    protected final Paint paint;

    protected IHouseSpec spec;

    public BuildingBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);

        spec = new HouseSpec();
        backingArray = new int[getBlocksX()][getBlocksY()];

        paint = new Paint();

    }

    public void reset() {
        for (int x = 0; x < getBlocksX(); x++) {
            for (int y = 0; y < getBlocksY(); y++) {
                backingArray[x][y] = 0;
            }
        }
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        blockSize = w / getBlocksX();
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
        for (int x = 0; x < getBlocksX(); x++) {
            for (int y = 0; y < getBlocksY(); y++) {
                canvas.drawBitmap(blockImages[backingArray[x][y]], x * blockSize, y * blockSize, paint);
            }
        }

    }

}
