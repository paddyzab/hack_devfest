package building_with_blocks.hackathon.devfest.buildingwithblocks;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class BuildingView extends View {

    private final static int BLOCKS_X=20;
    private final static int BLOCKS_Y=100;

    public BuildingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
