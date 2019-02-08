package com.bignerdranch.android.pife11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PracticeHiFi2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView tasks = (ListView) findViewById(R.id.tasks);
        //ArrayAdapter<String> tasksAdapter = new ArrayAdapter<String>(this, );
        setContentView(R.layout.activity_practice_hi_fi2);
    }


}
