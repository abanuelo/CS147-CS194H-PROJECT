package com.bignerdranch.android.pife11;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ChooseRoutineActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_choose_routine);

        DataSingleton ds = DataSingleton.getInstance();
        ArrayList<ArrayList<String>> dsRoutines = ds.getRoutinesList();

        ArrayList<String> items = new ArrayList<>();

        //Populate the list of routines with the names of the routines (the first string in each routine list)
        for (ArrayList<String> curr: dsRoutines){
            items.add(curr.get(0));
        }

        /*items.add("15 minute practice");
        items.add("1 minute practice");
        items.add("Favorite Things Jam-out");*/
        ArrayAdapter<String> routines = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        ListView routinesList = (ListView) findViewById(R.id.routinesList);
        routinesList.setAdapter(routines);
    }

    public void addRoutine(View view){
        Intent practice_intent = new Intent(ChooseRoutineActivity.this, AddNewRoutine.class);
        startActivity(practice_intent);
    }
}
