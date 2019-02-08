package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PracticeHiFi2 extends AppCompatActivity {

    private Chronometer stopwatch;
    long timeWhenStopped = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView tasks = (ListView) findViewById(R.id.tasks);
        String routineName = getIntent().getExtras().getString("ROUTINE_NAME");

//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationViewPerform);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//                                                                     @Override
//                                                                     public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                                                                         switch (menuItem.getItemId()){
//                                                                             case R.id.practice_nav:
//                                                                                 Intent practice_intent = new Intent(peform.this, PracticeHiFi2.class);
//                                                                                 startActivity(practice_intent);
//                                                                                 break;
//                                                                             case R.id.perform_nav:
//                                                                                 Intent perform_intent = new Intent(peform.this, peform.class);
//                                                                                 startActivity(perform_intent);
//                                                                                 break;
//                                                                             case R.id.friends_nav:
//                                                                                 Intent collab_intent = new Intent(peform.this, CollabHiFi2.class);
//                                                                                 startActivity(collab_intent);
//                                                                                 break;
//                                                                             case R.id.user_nav:
//                                                                                 Intent profile_intent = new Intent(peform.this, Profile.class);
//                                                                                 startActivity(profile_intent);
//                                                                                 break;
//                                                                         }
//                                                                         return true;
//                                                                     }
//                                                                 }
//        );

        DataSingleton ds = DataSingleton.getInstance();
        ArrayList<ArrayList<String>> routineLists = ds.getRoutinesList();

        ArrayAdapter<String> tasksAdapter = null;

        if (!routineName.contentEquals("Open Practice")) {

            //Yes, I understand that the routineName is the ID....
            int currRoutineIndex = getRoutineIndex(routineLists, routineName);
            if (currRoutineIndex == -1) {
                System.out.println("This routine doesn't exist :(");
                return;
            }
            ArrayList<String> justTasks = routineLists.get(currRoutineIndex);

            //Remove the title from the tasks...
            justTasks.remove(0);

            //Yes, I should have a checklist here, but let's do a simple one first...
            tasksAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, justTasks);
        }
        //You need to set the content view before you can set the listview.... yeah....
        setContentView(R.layout.activity_practice_hi_fi2);

        //Setting the title in the UI
        TextView routineTitle = (TextView) findViewById(R.id.routineTitle);
        routineTitle.setText(routineName);

        stopwatch = (Chronometer) findViewById(R.id.PracticeStopwatch);
        stopwatch.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        stopwatch.start();


        if (!routineName.contentEquals("Open Practice")) {
            ListView lv = (ListView) findViewById(R.id.tasks);
            lv.setAdapter(tasksAdapter);
        } else {
            TextView musicGoalsLabel = (TextView) findViewById(R.id.musicGoalsLabel);
            musicGoalsLabel.setVisibility(View.GONE);
        }
    }

    public void backHome(View view) {
        timeWhenStopped = SystemClock.elapsedRealtime() - stopwatch.getBase();
        stopwatch.stop();

        Toast t = Toast.makeText(getApplicationContext(), formToastText(timeWhenStopped), Toast.LENGTH_SHORT);
        t.show();

        Intent practice_intent = new Intent(this, Dashboard.class);
        startActivity(practice_intent);
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


}
