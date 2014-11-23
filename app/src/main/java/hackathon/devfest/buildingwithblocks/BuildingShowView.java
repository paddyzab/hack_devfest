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
        return spec.getSize();
    }

    @Override
    protected int getBlocksY() {
        return spec.getSize();
    }

    public BuildingShowView(Context context, AttributeSet attrs) {
        super(context, attrs);

        for (int x = 0; x < spec.getSize(); x++) {
            for (int y = 0; y < spec.getSize(); y++) {
                backingArray[x][y] = spec.getPlan()[y][x];
            }

        }

    }

}
