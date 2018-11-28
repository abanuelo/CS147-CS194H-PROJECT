package com.bignerdranch.android.pife11.Scheduler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bignerdranch.android.pife11.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    //String related to user input to the Edit Texts
    private String mon_start;
    private String tues_start;
    private String wed_start;
    private String thurs_start;
    private String fri_start;
    private String sat_start;
    private String sun_start;

    private String mon_end;
    private String tues_end;
    private String wed_end;
    private String thurs_end;
    private String fri_end;
    private String sat_end;
    private String sun_end;

    private String[] items;

    private Boolean result = false;

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
                //Once the button has been clicked we are going to make sure that it aligns with inputs
                //Check which entries are ready to be inserted
                //Here is going to be the Strings of each of the Collaborations
                mon_start = startTimeMon.getText().toString();
                tues_start = startTimeTues.getText().toString();
                wed_start = startTimeWed.getText().toString();
                thurs_start = startTimeThurs.getText().toString();
                fri_start = startTimeFri.getText().toString();
                sat_start = startTimeSat.getText().toString();
                sun_start = startTimeSun.getText().toString();

                mon_end = endTimeMon.getText().toString();
                tues_end = endTimeTues.getText().toString();
                wed_end = endTimeWed.getText().toString();
                thurs_end = endTimeThurs.getText().toString();
                fri_end = endTimeFri.getText().toString();
                sat_end = endTimeSat.getText().toString();
                sun_end = endTimeSun.getText().toString();

                //Check if the entries being inserted comply with regex
                Boolean areTimes = entriesComplyTimeRegex();
                if (!areTimes){
                    Toast.makeText(ScheduleMain.this, "Please fix time format to HH:MM", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private Boolean entriesComplyTimeRegex() {
        Pattern time_pattern =  Pattern.compile("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$");
        result = false;
        //Strings that will hold the checks for the remainder of the collaboration schedule
        String time_check = "";

        //CHECKS FOR MONDAY
        Matcher matcher = time_pattern.matcher(mon_start);
        while (matcher.find()){
            time_check = matcher.group(1);
        }
        if (time_check.isEmpty()){
            return result;
        }

        time_check = "";

        Matcher matcher2 = time_pattern.matcher(mon_end);
        while (matcher2.find()){
            time_check = matcher2.group(1);
        }
        if (time_check.isEmpty()){
            return result;
        }


        time_check = "";

        Matcher matcher3 = time_pattern.matcher(tues_start);
        while (matcher3.find()){
            time_check = matcher3.group(1);
        }
        if (time_check.isEmpty()){
            return result;
        }

        time_check = "";

        Matcher matcher4 = time_pattern.matcher(tues_end);
        while (matcher4.find()){
            time_check = matcher4.group(1);
        }
        if (time_check.isEmpty()){
            return result;
        }

        time_check = "";

        Matcher matcher5 = time_pattern.matcher(wed_start);
        while (matcher5.find()){
            time_check = matcher5.group(1);
        }
        if (time_check.isEmpty()){
            return result;
        }

        time_check = "";

        Matcher matcher6 = time_pattern.matcher(wed_end);
        while (matcher6.find()){
            time_check = matcher6.group(1);
        }
        if (time_check.isEmpty()){
            return result;
        }

        time_check = "";

        Matcher matcher7 = time_pattern.matcher(thurs_start);
        while (matcher7.find()){
            time_check = matcher7.group(1);
        }
        if (time_check.isEmpty()){
            return result;
        }

        time_check = "";

        Matcher matcher8 = time_pattern.matcher(thurs_end);
        while (matcher8.find()){
            time_check = matcher8.group(1);
        }
        if (time_check.isEmpty()){
            return result;
        }

        time_check = "";

        Matcher matcher9 = time_pattern.matcher(fri_start);
        while (matcher9.find()){
            time_check = matcher9.group(1);
        }
        if (time_check.isEmpty()){
            return result;
        }

        time_check = "";

        Matcher matcher10 = time_pattern.matcher(fri_end);
        while (matcher10.find()){
            time_check = matcher10.group(1);
        }
        if (time_check.isEmpty()){
            return result;
        }

        time_check = "";

        Matcher matcher11 = time_pattern.matcher(sat_start);
        while (matcher11.find()){
            time_check = matcher11.group(1);
        }
        if (time_check.isEmpty()){
            return result;
        }

        time_check = "";

        Matcher matcher12 = time_pattern.matcher(sat_end);
        while (matcher12.find()){
            time_check = matcher12.group(1);
        }
        if (time_check.isEmpty()){
            return result;
        }

        time_check = "";

        Matcher matcher13 = time_pattern.matcher(sun_start);
        while (matcher13.find()){
            time_check = matcher13.group(1);
        }
        if (time_check.isEmpty()){
            return result;
        }

        time_check = "";

        Matcher matcher14 = time_pattern.matcher(sun_end);
        while (matcher14.find()){
            time_check = matcher14.group(1);
        }
        if (time_check.isEmpty()){
            return result;
        }

        result = true;
        return result;
    }
}
