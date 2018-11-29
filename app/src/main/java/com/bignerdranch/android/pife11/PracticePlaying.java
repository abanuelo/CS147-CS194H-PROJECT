package com.bignerdranch.android.pife11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Spinner;


public class PracticePlaying extends AppCompatActivity {


    Chronometer stopwatch;
    Button playPause;
    Button done;
    Spinner spinner;
    Boolean playing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_playing);

        stopwatch = (Chronometer) findViewById(R.id.PracticeStopwatch);
        playPause = (Button) findViewById(R.id.PracticePlayPause);
        done = (Button) findViewById(R.id.PracticeDone);
//        start time
        stopwatch.start();
//        set heart level

        spinner = (Spinner) findViewById(R.id.PracticeTodaysGoalMinutes);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.PracticeMinGoalArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.PracticeDone:
//
//                check if time = goal
                        //if so, say congrats, show XP points gained, etc
//                if else message
                    //confirm hasn't hit target goal yet. Ask to stay
                        //
                        //transition to Home Screen
                break;
            case R.id.PracticePlayPause:
//                See if Goal has been Met
                EditText goalMet = (EditText) findViewById(R.id.PracticeGoalMet);
                EditText todaysGoal = (EditText) findViewById(R.id.PracticeTodaysGoal);
                //spinner also exists

                if (playing) {
//                    It is now paused
                    stopwatch.stop();
                    playPause.setBackgroundResource(R.drawable.play);

                    goalMet.setVisibility(View.VISIBLE);
                    todaysGoal.setVisibility(View.INVISIBLE);
                    spinner.setVisibility(View.INVISIBLE);

                    playing = !playing;
                }
                else{
//                    It is now playing
                    stopwatch.start();
                    playPause.setBackgroundResource(R.drawable.pause);

                    goalMet.setVisibility(View.INVISIBLE);
                    todaysGoal.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.VISIBLE);

                    playing = !playing;

                }


        }
    }

}
