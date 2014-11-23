package hackathon.devfest.buildingwithblocks;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;


public class MainActivity extends ActionBarActivity {

    public static final int DELAY_MILLIS = 150;
    public static final int DELAY_TIME_MILIS = 1000;
    private BuildingView view;
    private GameStateView gameStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        view = (BuildingView) findViewById(R.id.building_view);
        gameStats = (GameStateView) findViewById(R.id.game_stats);

        final Handler gameLoopHandler = new Handler();
        final Handler timeHandler = new Handler();

        final Runnable updated = new Runnable() {
            @Override
            public void run() {
                view.update();
                gameLoopHandler.postDelayed(this, DELAY_MILLIS);
            }
        };

        final Runnable updateTime = new Runnable() {
            @Override
            public void run() {
                gameStats.incrementTime();
                timeHandler.postDelayed(this, DELAY_TIME_MILIS);

            }
        };

        timeHandler.postDelayed(updateTime, DELAY_TIME_MILIS);
        gameLoopHandler.postDelayed(updated,DELAY_MILLIS);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {

        // Check if this event if from a D-pad and process accordingly.
        if (Dpad.isDpadDevice(event)) {

            Dpad mDpad = new Dpad();
            int press = mDpad.getDirectionPressed(event);
            switch (press) {
                case Dpad.LEFT:
                    view.moveLeft();
                    return true;
                case Dpad.RIGHT:
                    view.moveRight();
                    return true;
                case Dpad.UP:
                    return true;
            }
        }

        return false;
    }
}
