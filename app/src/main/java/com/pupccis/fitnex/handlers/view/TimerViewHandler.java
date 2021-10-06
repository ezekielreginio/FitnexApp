package com.pupccis.fitnex.handlers.view;

import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.activities.routine.RoutinePage;
import com.pupccis.fitnex.activities.routine.RoutineTracker;
import com.pupccis.fitnex.utilities.Constants.ProgramConstants;

import java.util.concurrent.Callable;

public class TimerViewHandler {
    private long millisLeftInMillis;
    private long initialTime;
    private CountDownTimer countDownTimer;
    private ProgressBar timerProgressBar;
    private TextView textViewTimer;
    private Callable<Void> executeOnFinish;

    public TimerViewHandler(long millisLeftInMillis, ProgressBar timerProgressBar, TextView textViewTimer, Callable<Void> executeOnFinish) {
        this.millisLeftInMillis = millisLeftInMillis;
        this.initialTime = millisLeftInMillis/1000;
        this.timerProgressBar = timerProgressBar;
        this.textViewTimer = textViewTimer;
        this.executeOnFinish = executeOnFinish;
    }

    public void startTimer(boolean reset) {
        if(reset)
            millisLeftInMillis = initialTime * 1000;
        countDownTimer = new CountDownTimer(millisLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                millisLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                try{
                    executeOnFinish.call();
                }
                catch (Exception e){

                }
                //startRoutineTracker();
            }
        }.start();
    }

    public void stopTimer(){
        countDownTimer.cancel();
        millisLeftInMillis = initialTime * 1000;
    }

    private void updateCountDownText() {
        int seconds = (int) millisLeftInMillis/1000;
        textViewTimer.setText(seconds+"");
        Log.d("Seconds",""+seconds);
        float progress = ((float)seconds/initialTime) * 100;
        Log.d("Progress", progress+"");
        timerProgressBar.setProgress((int)progress);
    }
}

