package com.bignerdranch.android.pife11;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ChooseRoutineActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_routine);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationViewPerform);
        bottomNavigationView.setSelectedItemId(R.id.practice_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                                                     @Override
                                                                     public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                                                                         switch (menuItem.getItemId()){
                                                                             case R.id.perform_nav:
                                                                                 Intent practice_intent = new Intent(ChooseRoutineActivity.this, peform.class);
                                                                                 startActivity(practice_intent);
                                                                                 break;
                                                                             case R.id.friends_nav:
                                                                                 Intent collab_intent = new Intent(ChooseRoutineActivity.this, CollabHiFi2.class);
                                                                                 startActivity(collab_intent);
                                                                                 break;
                                                                             case R.id.user_nav:
                                                                                 Intent profile_intent = new Intent(ChooseRoutineActivity.this, Profile.class);
                                                                                 startActivity(profile_intent);
                                                                                 break;
                                                                         }
                                                                         return true;
                                                                     }
                                                                 }
        );

        DataSingleton ds = DataSingleton.getInstance();
        ArrayList<ArrayList<String>> dsRoutines = ds.getRoutinesList();

        ArrayList<String> items = new ArrayList<>();

        //Populate the list of routines with the names of the routines (the first string in each routine list)
        for (ArrayList<String> curr: dsRoutines){
            items.add(curr.get(0));
        }

        //TODO: Get clicking on routine to change the intent

        ArrayAdapter<String> routines = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        ListView routinesList = (ListView) findViewById(R.id.routinesList);
        routinesList.setAdapter(routines);
    }

    public void addRoutine(View view){
        Intent practice_intent = new Intent(ChooseRoutineActivity.this, AddNewRoutine.class);
        startActivity(practice_intent);
    }

    public void goToOpenPractice(View view) {
        Intent practice_intent = new Intent(ChooseRoutineActivity.this, PracticeHiFi2.class);
        practice_intent.putExtra("SOURCE", "CHOOSE");
        practice_intent.putExtra("ROUTINE_NAME", "Open Practice");
        startActivity(practice_intent);
    }
}
