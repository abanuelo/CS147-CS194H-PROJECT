package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PracticeHiFi2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView tasks = (ListView) findViewById(R.id.tasks);
        String routineName = getIntent().getExtras().getString("ROUTINE_NAME");

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

        if (!routineName.contentEquals("Open Practice")) {
            ListView lv = (ListView) findViewById(R.id.tasks);
            lv.setAdapter(tasksAdapter);
        } else {
            TextView musicGoalsLabel = (TextView) findViewById(R.id.musicGoalsLabel);
            musicGoalsLabel.setVisibility(View.GONE);
        }
    }

    public void backHome(View view) {
        Intent practice_intent = new Intent(this, Dashboard.class);
        startActivity(practice_intent);
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
