package building_with_blocks.hackathon.devfest.buildingwithblocks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class BuildingView extends View {

    private final static int BLOCKS_X = 20;
    private final static int BLOCKS_Y = 100;

    private final int[][] backingArray = new int[BLOCKS_X][BLOCKS_Y];

    private final Bitmap[] blockImages = new Bitmap[2];

    public BuildingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        float blockSize = (float) getWidth() / BLOCKS_X;
    }

    private Bitmap calcBitmap(float size, int resourceId) {
        final Bitmap unscaled_bitmap = BitmapFactory.decodeResource(this.getResources(), resourceId);
        return Bitmap.createScaledBitmap(unscaled_bitmap, (int) size, (int) size, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
