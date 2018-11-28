package com.bignerdranch.android.pife11;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class PracticeMain extends AppCompatActivity {
    private long START_TIME_IN_MILLIS = 900000;
    private TextView mTextViewCountDown;
    private Spinner dropdown;
    private ArrayAdapter<String> adapter;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    String[] items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_main);

        /*
            Creates links to activity_practice_main.xml
         */
        mTextViewCountDown = findViewById(R.id.countdown);
        mButtonStartPause = findViewById(R.id.bstart);
        mButtonReset = findViewById(R.id.reset);

        /*
            Initalizes the Dropdown Choosing Time to Conduct Practice Session
         */
        dropdown = findViewById(R.id.spinner1);
        items = new String[]{"15 minutes", "30 minutes", "45 minutes", "60 minutes"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        /*
            Next part of method defines the START_TIME_IN_MILLIS based on the dropdown above;
            According to android documentation there needs to be onItemSelectedListener
         */
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (items[i] == "15 minutes"){
                    START_TIME_IN_MILLIS = 900000;
                } else if (items[i] == "30 minutes"){
                    START_TIME_IN_MILLIS = 900000 * 2;
                } else if (items[i] == "45 minutes"){
                    START_TIME_IN_MILLIS = 900000 * 3;
                } else{
                    START_TIME_IN_MILLIS = 900000 * 4;
                }
                mTimeLeftInMillis = START_TIME_IN_MILLIS;
                updateCountDownText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*
            Create SetOnClickListeners if you click on the start button to initiate practice
         */
        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTimerRunning){
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        /*
            Create SetOnClickListeners if you decide to click reset to reset practice
         */
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        updateCountDownText();

    }

    /*
        Method 0: Handles the initiation of the timer; basically ticks and finishing
     */
    private void startTimer(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("Pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    /*
        Method 1: Pauses timer and sets the reset button to visible. Thus upon pausing,
        users have the option to reset timer to original value
     */
    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
    }

    /*
        Method 2: Resets Timer to Original Starting Time Values
     */
    private void resetTimer(){
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    /*
        Method 3: Updates the countdownText continuously on the screen; cast of int is required
        since we are dealing with long type
     */
    private void updateCountDownText(){
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }


}
