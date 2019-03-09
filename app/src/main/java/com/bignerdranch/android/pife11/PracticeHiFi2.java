package com.bignerdranch.android.pife11;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PracticeHiFi2 extends AppCompatActivity {

    private Chronometer stopwatch;
    long timeWhenStopped = 0;
    private DatabaseReference matchDb;
    private ValueEventListener listener;
    private Button done, firstTutorialButton;
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private boolean finishedFirstTutorial = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView tasks = (ListView) findViewById(R.id.tasks);
        String routineName = getIntent().getExtras().getString("ROUTINE_NAME");

        DataSingleton ds = DataSingleton.getInstance();
        ArrayList<ArrayList<String>> routineLists = ds.getRoutinesList();
        done = findViewById(R.id.done);

        //ArrayAdapter<String> tasksAdapter = null;

        ArrayList<String> justTasks = null;

        if (!routineName.contentEquals("Free Practice")) {

            //Yes, I understand that the routineName is the ID....
            int currRoutineIndex = getRoutineIndex(routineLists, routineName);
            if (currRoutineIndex == -1) {
                System.out.println("This routine doesn't exist :(");
                return;
            }
            justTasks = routineLists.get(currRoutineIndex);

            //Remove the title from the tasks...
            justTasks.remove(0);

            //Yes, I should have a checklist here, but let's do a simple one first...
            //tasksAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, justTasks);
        }
        //You need to set the content view before you can set the listview.... yeah....
        setContentView(R.layout.activity_practice_hi_fi2);

        changeCoins();

        //Setting the title in the UI
        TextView routineTitle = (TextView) findViewById(R.id.routineTitle);
        routineTitle.setText(routineName);

        stopwatch = (Chronometer) findViewById(R.id.PracticeStopwatch);
        stopwatch.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        stopwatch.start();


        if (!routineName.contentEquals("Free Practice")) {
            //ListView lv = (ListView) findViewById(R.id.tasks);
            //lv.setAdapter(tasksAdapter);
            System.out.println("MOOOOO");
            createTaskList(justTasks);
            System.out.println(justTasks.toString());

        } else {
            TextView musicGoalsLabel = (TextView) findViewById(R.id.musicGoalsLabel);
            musicGoalsLabel.setVisibility(View.GONE);
        }

        checkIfTaskComplete();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (matchDb != null && listener != null) {
            Log.d("Clear", "clearing userdatabase!");
            matchDb.removeEventListener(listener);
        }
    }

    private void checkIfTaskComplete(){
        final String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats");

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot match: dataSnapshot.getChildren()){
                        if (match.getKey().equals("practice")) {
                            int hl = Integer.parseInt(match.getValue().toString().trim());
                            if(hl == 0) {
                                //run tutorial program
                                FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("practice").setValue(Integer.toString((1)));

                            }
                        }
                    } }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        matchDb.addValueEventListener(listener);
    }

    private void createTaskList(ArrayList<String> justTasks) {

        TaskList adapter = new TaskList(this, justTasks, false);
        ListView tasksView = (ListView) findViewById(R.id.tasks);

        tasksView.setAdapter(adapter);

    }

    public void backHome(View view) {
        timeWhenStopped = SystemClock.elapsedRealtime() - stopwatch.getBase();
        stopwatch.stop();

        Toast t = Toast.makeText(getApplicationContext(), formToastText(timeWhenStopped), Toast.LENGTH_SHORT);
        t.show();

        //Add stuff to the DataSingleton
        DataSingleton ds = DataSingleton.getInstance();
        long currentTotal = ds.getPracticeLength() + timeWhenStopped;
        ds.setPracticeLength(currentTotal);
        System.out.println("Total practiced time is: " + Long.toString(currentTotal));


        if (currentTotal > 2*1000) {
            final Context context = getApplicationContext();
            final DatabaseReference firstTutorial = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("FirstTutorial");
            firstTutorial.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue() != null){
                        Intent practice_intent = new Intent(context, Profile.class);
                        practice_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(practice_intent);
                    } else {
                        //Show the gamification
                        Intent practice_intent = new Intent(context, TutorialReward.class);
                        practice_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(practice_intent);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            Intent practice_intent = new Intent(this, Profile.class);
            startActivity(practice_intent);
        }
    }

    private String formToastText(long timeWhenStopped){
        int hrs = 0;
        int min = 0;
        int sec = 0;
        String res = "You have played for ";
        System.out.println("hrs:" + timeWhenStopped);
        hrs = (int)(timeWhenStopped / (60*60*1000));
        timeWhenStopped -= hrs * (60*60*1000);
        System.out.println("hrs1:" + timeWhenStopped);
        min = (int)(timeWhenStopped / (60*1000));
        timeWhenStopped -= hrs * (60*1000);
        System.out.println("hrs2:" + timeWhenStopped);
        sec = (int)(timeWhenStopped / (1000));
        System.out.println("hrs:" + hrs + "hrs:" + min + "hrs:" + sec);
        if (hrs != 0) {
            res = res + Integer.toString(hrs) + " hrs";
        }
        if (min != 0) {
            res = res + ifAnythingBeforeMin(hrs) + Integer.toString(min) + " min";
        }
        if (sec != 0) {
            res = res + ifAnythingBeforeSec(hrs, min) + Integer.toString(sec) + " s";
        }
        res = res + "!";
        return res;
    }

    private String ifAnythingBeforeMin(int before) {
        if (before != 0) {
            return " ";
        }
        return "";
    }

    private String ifAnythingBeforeSec(int hrs, int min) {
        if (hrs != 0 || min != 0) {
            return " ";
        }
        return "";
    }

    private int getRoutineIndex(ArrayList<ArrayList<String>> arr, String routineName) {
        for (int i = 0; i < arr.size(); i++) {
            ArrayList<String> curr = arr.get(i);
            if (routineName.equals(curr.get(0))) {
                return i;
            }
        }
        return -1;
    }


    public void changeCoins(){
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("xp");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String pifePoints = dataSnapshot.getValue().toString().trim();
                    TextView pointsDisplay = (TextView) findViewById(R.id.coins);
                    pointsDisplay.setText(pifePoints);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        //Do nothing
    }

    public void goToStore(View view) {
        Intent practice_intent = new Intent(this, Store.class);
        startActivity(practice_intent);
    }

}
