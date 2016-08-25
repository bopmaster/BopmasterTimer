package com.tuanm.timer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controllerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        timerTextView.setText("00:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        controllerButton.setText("Start");
        timerSeekBar.setEnabled(true);
        counterIsActive = false;
    }
    public void updateTimer(int secondsLeft){

        int minutes = (int) secondsLeft /60;
        int seconds = secondsLeft - minutes*60;
        String secondsString = Integer.toString(seconds);
        if (seconds < 10){

            secondsString = "0" + secondsString;
        }
        String minutesString = Integer.toString(minutes);
        if (minutes < 10){

            minutesString = "0" + minutesString;
        }

        timerTextView.setText(minutesString + ":" + secondsString);
    }
    public void controlTimer(View view){
        if (counterIsActive == false) {

            counterIsActive = true;
            controllerButton.setText("Stop");

            timerSeekBar.setEnabled(false);
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long l) {

                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    timerTextView.setText("00:30");
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mPlayer.start();
                }
            }.start();
        } else {

            resetTimer();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controllerButton = (Button) findViewById(R.id.controllerButton);

        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerTextView = (TextView) findViewById(R.id.timerTextView);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
