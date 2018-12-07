package com.bignerdranch.android.pife11;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.os.SystemClock;
import android.widget.TextView;
import android.widget.Toast;


import com.bignerdranch.android.pife11.Matches.MatchesObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import static java.lang.Integer.min;
import static java.lang.Integer.parseInt;


public class PracticePlaying extends AppCompatActivity {

    private String myAvatar;
    private Chronometer stopwatch;
    private Button playPause;
    private Button done;
    private Spinner spinner;
    private Boolean playing = false;
    private ProgressBar heartlevel;
    long timeWhenStopped = 0;
    private String currentUserId;
    private GifDrawable drawable;
    private GifImageView animation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_playing);

        stopwatch = (Chronometer) findViewById(R.id.PracticeStopwatch);
        playPause = (Button) findViewById(R.id.PracticePlayPause);
        done = (Button) findViewById(R.id.PracticeDone);
//        start time
        stopwatch.stop();
//        set heart level

        spinner = (Spinner) findViewById(R.id.PracticeTodaysGoalMinutes);
        spinner.setEnabled(true);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.PracticeMinGoalArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getUserAvatar();
        FetchHeartLevel();

    }

    private void getUserAvatar() {
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Avatar");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    myAvatar = dataSnapshot.getValue().toString().trim();
                    ImageView avatarDisplay = (ImageView) findViewById(R.id.PracticeAvatar);
                    if(myAvatar.equals("{avatar=Jemi}")) {
                        avatarDisplay.setImageResource(R.drawable.ic_monster_baby);
                        avatarDisplay.setVisibility(View.INVISIBLE);

                        try {
                            drawable = new GifDrawable(getResources(), R.drawable.jemi_talking);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        drawable.setLoopCount(0);
                        animation = (GifImageView) findViewById(R.id.PracticeAvatarGIF);
                        animation.setBackground(drawable);
                        animation.setVisibility(View.VISIBLE);
                        //make him talking?
                    }
                    else if (myAvatar.equals("dressed")) { //have armando put this in
                        avatarDisplay.setImageResource(R.drawable.ic_monster_baby);
                        avatarDisplay.setVisibility(View.INVISIBLE);

                        try {
                            drawable = new GifDrawable(getResources(), R.drawable.jemi_dressed_toddler);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        drawable.setLoopCount(0);
                        animation = (GifImageView) findViewById(R.id.PracticeAvatarGIF);
                        animation.setBackground(drawable);
                        animation.setVisibility(View.VISIBLE);
                    }
                    else if (myAvatar.equals("toddler")) { //have armando put this in
                        Log.d("Avatar Toddler Detected", "Here");
                        avatarDisplay.setImageResource(R.drawable.ic_monster_baby);
                        avatarDisplay.setVisibility(View.INVISIBLE);

                        try {
                            drawable = new GifDrawable(getResources(), R.drawable.jemi_plain_toddler);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        drawable.setLoopCount(0);
                        animation = (GifImageView) findViewById(R.id.PracticeAvatarGIF);
                        animation.setBackground(drawable);
                        animation.setVisibility(View.VISIBLE);
                    }
                    else {
                        avatarDisplay.setImageResource(R.drawable.ic_nerdy_monster_baby);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void FetchHeartLevel(){
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats");

        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot match: dataSnapshot.getChildren()){
                        Log.i("Ab-matchkey", match.getKey());
                        Log.i("Ab-matchVal", match.getValue().toString().trim());
                        if (match.getKey().equals("heartlevel")) {
                            int hl = Integer.parseInt(match.getValue().toString().trim());
                            heartlevel = findViewById(R.id.PracticeHeartLevel);
                            heartlevel.setProgress(hl);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void updateHeartLevel(){
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats");
        String goal = spinner.getSelectedItem().toString().trim();
        String min = goal.substring(0,2).trim();
        final int minutes = Integer.parseInt(min);

        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot match: dataSnapshot.getChildren()){
                        if (match.getKey().equals("heartlevel")) {
                            int hl = Integer.parseInt(match.getValue().toString().trim());
                            hl += minutes;
                            if (hl >100) hl = 100;
                            heartlevel = findViewById(R.id.PracticeHeartLevel);
                            heartlevel.setProgress(hl);
                            FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("heartlevel").setValue(Integer.toString((hl)));
                        }
                        if (match.getKey().equals("xp")) {
                            int xp = Integer.parseInt(match.getValue().toString().trim());
                            xp += minutes;
                            FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("xp").setValue(Integer.toString((xp)));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean checkIfGoalMet(){

        String goal = spinner.getSelectedItem().toString().trim();
        goal = goal.substring(0,2).trim();
//        int minutes = Integer.parseInt(goal);
        int minutes = Integer.parseInt("1");
        if (-timeWhenStopped >= minutes * 10 * 1000) return true;
        return false;
    }

    private void confirmExit(){
        View pop = (LayoutInflater.from(PracticePlaying.this)).inflate(R.layout.practice_goal_not_met, null);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(PracticePlaying.this);
        alertBuilder.setView(pop);
        final EditText userInput = pop.findViewById(R.id.userinput);

        alertBuilder.setNegativeButton("Practice More",null);
        alertBuilder.setCancelable(true);
        alertBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent homeScreen = new Intent(PracticePlaying.this, Dashboard.class);
                        startActivity(homeScreen);
                    }
                }
        );
        Dialog dialog = alertBuilder.create();
        dialog.show();
    }

    public void onClick(View v) {

        if (playing) {timeWhenStopped = stopwatch.getBase() - SystemClock.elapsedRealtime(); }
        boolean goalMetBool = checkIfGoalMet();

        switch (v.getId()){
            case R.id.PracticeDone:
                if (goalMetBool) {
                    updateHeartLevel();
                    Intent goalMet = new Intent(PracticePlaying.this, practice_goal_met.class).putExtra("message","You Have Met Your Goal!!!");
                    startActivity(goalMet);
                }
                else {
                    confirmExit();
                }

            case R.id.PracticePlayPause:
//                See if Goal has been Met
                TextView goalMet = (TextView) findViewById(R.id.PracticeGoalMet);
                TextView todaysGoal = (TextView) findViewById(R.id.PracticeTodaysGoal);
                //spinner also exists

                if (playing) {
//                    It is now paused
                    timeWhenStopped = stopwatch.getBase() - SystemClock.elapsedRealtime();
                    stopwatch.stop();
                    playPause.setBackgroundResource(R.drawable.play);

                    if(goalMetBool) {goalMet.setText("Goal Has Been Met!");}
                    else{goalMet.setText("Goal Not Met Yet!!!");}
                    goalMet.setVisibility(View.VISIBLE);
                    todaysGoal.setVisibility(View.INVISIBLE);
                    spinner.setVisibility(View.INVISIBLE);

                    playing = !playing;
                }
                else{
//                    It is now playing
                    stopwatch.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
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
