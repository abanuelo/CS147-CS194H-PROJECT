package com.bignerdranch.android.pife11.Scheduler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.bignerdranch.android.pife11.R;

public class ScheduleMain extends AppCompatActivity {
    //Declaration of all the variables for the time collab
    private EditText startTimeMon;
    private EditText startTimeTues;
    private EditText startTimeWed;
    private EditText startTimeThurs;
    private EditText startTimeFri;
    private EditText startTimeSat;
    private EditText startTimeSun;

    //All the end times related
    private EditText endTimeMon;
    private EditText endTimeTues;
    private EditText endTimeWed;
    private EditText endTimeThurs;
    private EditText endTimeFri;
    private EditText endTimeSat;
    private EditText endTimeSun;

    //Get all Spinners from the Scheduler Main Activity Page
    private Spinner spinMon;
    private Spinner spinTues;
    private Spinner spinWed;
    private Spinner spinThurs;
    private Spinner spinFri;
    private Spinner spinSat;
    private Spinner spinSun;

    private String[] items;

    private Button schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_main);

        //Declaring the variables for the schedule collaborator
        startTimeMon = findViewById(R.id.start_time_mon);
        startTimeTues = findViewById(R.id.start_time_tues);
        startTimeWed = findViewById(R.id.start_time_wed);
        startTimeThurs = findViewById(R.id.start_time_thur);
        startTimeFri = findViewById(R.id.start_time_fri);
        startTimeSat = findViewById(R.id.start_time_sat);
        startTimeSun = findViewById(R.id.start_time_sun);

        //Declaring the vairables for the end times for the schedule collaborator
        endTimeMon = findViewById(R.id.end_time_mon);
        endTimeTues = findViewById(R.id.end_time_tues);
        endTimeWed = findViewById(R.id.end_time_wed);
        endTimeThurs = findViewById(R.id.end_time_thur);
        endTimeFri = findViewById(R.id.start_time_fri);
        endTimeSat = findViewById(R.id.start_time_sat);
        endTimeSun = findViewById(R.id.start_time_sun);

        //finding the Button
        schedule = findViewById(R.id.schedule);

        //Initializes the spinners for each of the days of the week
        items = new String[]{"AM", "PM"}; //only declare this once

        spinMon = findViewById(R.id.am_pm_spinner_mon);
        ArrayAdapter<String> adapterMon = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinMon.setAdapter(adapterMon);

        spinTues = findViewById(R.id.am_pm_spinner_tues);
        ArrayAdapter<String> adapterTues = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinTues.setAdapter(adapterTues);

        spinWed = findViewById(R.id.am_pm_spinner_wed);
        ArrayAdapter<String> adapterWed = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinWed.setAdapter(adapterWed);

        spinThurs = findViewById(R.id.am_pm_spinner_thur);
        ArrayAdapter<String> adapterThurs = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinThurs.setAdapter(adapterThurs);

        spinFri = findViewById(R.id.am_pm_spinner_fri);
        ArrayAdapter<String> adapterFri = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinFri.setAdapter(adapterFri);

        spinSat = findViewById(R.id.am_pm_spinner_sat);
        ArrayAdapter<String> adapterSat = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinSat.setAdapter(adapterSat);

        spinSun = findViewById(R.id.am_pm_spinner_sun);
        ArrayAdapter<String> adapterSun = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinSun.setAdapter(adapterSun);


        //Click Listener For the Button
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}
