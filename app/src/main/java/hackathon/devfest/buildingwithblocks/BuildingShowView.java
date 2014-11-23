package hackathon.devfest.buildingwithblocks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BuildingShowView extends BuildingBaseView {

    @Override
    protected int getBlocksX() {
        return App.getSpec().getSize();
    }

    @Override
    protected int getBlocksY() {
        return App.getSpec().getSize();
    }

    public BuildingShowView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    @Override
    public void init() {
        super.init();
        for (int x = 0; x < App.getSpec().getSize(); x++) {
            for (int y = 0; y < App.getSpec().getSize(); y++) {
                backingArray[x][y] = App.getSpec().getPlan()[y][x];
            }

        }
    }
}
