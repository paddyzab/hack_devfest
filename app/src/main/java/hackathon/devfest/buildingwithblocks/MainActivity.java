package hackathon.devfest.buildingwithblocks;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.WindowManager;
import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    public static final int DELAY_MILLIS = 50;
    public static final int DELAY_TIME_MILIS = 1000;
    private BuildingGameView view;
    private GameStateView gameStats;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        view = (BuildingGameView) findViewById(R.id.building_game_view);

        view.setListener(new BuildingGameView.GameUpdateListener() {
            @Override
            public void onHouseCompleted() {
                gameStats.incrementPoints();
            }

            @Override
            public void onGameEnded() {
                new AlertDialog.Builder(MainActivity.this).setMessage("you lost")
                        .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                view.reset();
                            }
                        })
                        .show();


            }
        });

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

        mediaPlayer = new MediaPlayer();

        try {
            playMusic();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playMusic() throws IOException {
        prepareMusic();
        mediaPlayer.start();
    }

    private void prepareMusic() throws IOException {
        AssetFileDescriptor descriptor = getBaseContext().getAssets().openFd("skipping_through_orchestra_pit.mp3");
        long start = descriptor.getStartOffset();
        long end = descriptor.getLength();

        mediaPlayer.setDataSource(descriptor.getFileDescriptor(), start, end);
        mediaPlayer.prepare();
        mediaPlayer.setLooping(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
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
