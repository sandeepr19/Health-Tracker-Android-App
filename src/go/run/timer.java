package go.run;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.TextView;


public class timer extends Activity {
    TextView textGoesHere;
    long startTime;
    long countUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);

        Chronometer stopWatch = (Chronometer) findViewById(R.id.chrono);
        stopWatch.start();
        startTime = SystemClock.elapsedRealtime();
      //  stopWatch.setBase(elapsedTimeBeforePause);
        textGoesHere = (TextView) findViewById(R.id.textGoesHere);
        stopWatch.setOnChronometerTickListener(new OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer arg0) {
                countUp = (SystemClock.elapsedRealtime() - arg0.getBase()) / 1000;
                String asText = (countUp / 60) + ":" + (countUp % 60); 
                textGoesHere.setText(asText);
            }
        });
        stopWatch.start();
    }
	
}