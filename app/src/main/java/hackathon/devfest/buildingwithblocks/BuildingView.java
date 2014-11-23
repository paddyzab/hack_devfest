package hackathon.devfest.buildingwithblocks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

public class BuildingView extends View {

    private int act_cursor=0;

    private final static int BLOCKS_X = 20;
    private final static int BLOCKS_Y = 100;

    private final int[][] backingArray = new int[BLOCKS_X][BLOCKS_Y];

    private final Bitmap[] blockImages = new Bitmap[2];
    private float blockSize;
    private final Paint paint;

    public BuildingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        HouseSpec spec=new HouseSpec();

        for (int x=0;x<HouseSpec.SIZE;x++) {
            for (int y=0;y<HouseSpec.SIZE;y++) {
                backingArray[x][y]=spec.getPlan()[y][x];
            }

        }
        paint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        blockSize = (float) w / BLOCKS_X;
        blockImages[0]=calcBitmap(blockSize,R.drawable.bg_castle);
        blockImages[1]=calcBitmap(blockSize,R.drawable.brick_wall);

    }

    private Bitmap calcBitmap(float size, int resourceId) {
        final Bitmap unscaled_bitmap = BitmapFactory.decodeResource(this.getResources(), resourceId);
        return Bitmap.createScaledBitmap(unscaled_bitmap, (int) size, (int) size, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int x=0;x<BLOCKS_X;x++) {
            for (int y=0;y<BLOCKS_X;y++) {
                canvas.drawBitmap(blockImages[backingArray[x][y]],x*blockSize,y*blockSize,paint);
            }
        }
    }

    private Point find() {
        final HouseSpec spec = new HouseSpec();
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

}
