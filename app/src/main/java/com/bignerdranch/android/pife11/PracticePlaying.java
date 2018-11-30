package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.os.SystemClock;

import com.bignerdranch.android.pife11.Matches.MatchesObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                    } else {
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
                        if (match.getKey() == "heartlevel") {
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
                Intent homeScreen = new Intent(PracticePlaying.this, Dashboard.class);
                startActivity(homeScreen);
                break;
            case R.id.PracticePlayPause:
//                See if Goal has been Met
                EditText goalMet = (EditText) findViewById(R.id.PracticeGoalMet);
                EditText todaysGoal = (EditText) findViewById(R.id.PracticeTodaysGoal);
                //spinner also exists

                if (playing) {
//                    It is now paused
                    timeWhenStopped = stopwatch.getBase() - SystemClock.elapsedRealtime();
                    stopwatch.stop();
                    playPause.setBackgroundResource(R.drawable.play);

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
