package hackathon.devfest.buildingwithblocks;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

public class GameStateView extends FrameLayout {

    private int pointsCounter = 0;
    private TextView points;
    private int elapsedTimeCounter = 0;
    private TextView elapsedTime;

    public GameStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_game_stats, this);

        elapsedTime = (TextView) findViewById(R.id.textViewElapsedTime);
        points = (TextView) findViewById(R.id.textViewPoints);
    }

    public void incrementTime() {
        elapsedTimeCounter++;
        elapsedTime.setText("" + elapsedTimeCounter);
    }

    public void resetTime() {
        elapsedTimeCounter = 0;
        elapsedTime.setText("" + elapsedTimeCounter);
    }

    public void incrementPoints() {
        pointsCounter++;
        points.setText("" + pointsCounter);

    }

    public void resetPoints() {
        pointsCounter = 0;
        points.setText("" + pointsCounter);
    }
}
