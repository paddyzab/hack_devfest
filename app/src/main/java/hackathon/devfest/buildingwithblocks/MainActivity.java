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
    private BuildingShowView showView;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        view = (BuildingGameView) findViewById(R.id.building_game_view);
        showView= (BuildingShowView) findViewById(R.id.show_view);

        view.setListener(new BuildingGameView.GameUpdateListener() {
            @Override
            public void onHouseCompleted() {
                view.freeze();

                new AlertDialog.Builder(MainActivity.this).setMessage("yaay next level")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                App.setSpec(new HouseSpec());
                                gameStats.incrementPoints();
                                showView.init();
                                showView.refreshSizes();
                                showView.invalidate();

                                view.unfreeze();


                            }
                        })
                        .show();


            }

            @Override
            public void onGameEnded() {

                mediaPlayer.stop();
                try {
                    prepareMediaPlayerAndPlay("lose.mp3", false);
                    playMusic();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                new AlertDialog.Builder(MainActivity.this).setMessage("you lost")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                App.setSpec(new MockHouseSpec());

                                showView.init();
                                showView.refreshSizes();
                                showView.invalidate();

                                view.reset();
                                gameStats.resetPoints();
                                gameStats.resetTime();

                                prepareMainTrack();
                            }
                        })
                        .show();

                view.reset();

                gameStats.resetPoints();
                gameStats.resetTime();
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

        prepareMainTrack();
    }

    private void prepareMainTrack() {
        try {
            prepareMediaPlayerAndPlay("skipping_through_orchestra_pit.mp3", true);
            playMusic();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playMusic() throws IOException {
        mediaPlayer.start();
    }

    private void prepareMediaPlayerAndPlay(String fileName, boolean loop) throws IOException {
        mediaPlayer = new MediaPlayer();
        AssetFileDescriptor descriptor = getBaseContext().getAssets().openFd(fileName);
        long start = descriptor.getStartOffset();
        long end = descriptor.getLength();

        mediaPlayer.setDataSource(descriptor.getFileDescriptor(), start, end);
        mediaPlayer.prepare();
        mediaPlayer.setLooping(loop);
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
