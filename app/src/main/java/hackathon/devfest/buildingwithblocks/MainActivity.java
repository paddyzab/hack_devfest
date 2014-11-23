package hackathon.devfest.buildingwithblocks;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {

    private BuildingView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (BuildingView) findViewById(R.id.building_view);

        final Handler gameLoopHandler = new Handler();

        final Runnable updated = new Runnable() {
            @Override
            public void run() {
                view.update();
                gameLoopHandler.postDelayed(this,500);
            }
        };


        gameLoopHandler.postDelayed(updated, 500);
    }
}
