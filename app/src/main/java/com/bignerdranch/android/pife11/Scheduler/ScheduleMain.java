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
    private Boolean resultForTimeCheck = false;

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
                } else{
                    Boolean areTimesInOrder = checkTimeOrders();
                    if (!areTimesInOrder){
                        Toast.makeText(ScheduleMain.this, "Correct times in the right order", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }

    private Boolean checkTimeOrders() {
        //MONDAY
        //tokenize the entries in each of the results to get results
        String[] token_mon_start = mon_start.split(":");
        Integer hour_mon_start = Integer.parseInt(token_mon_start[0]);
        Integer min_mon_start = Integer.parseInt(token_mon_start[1]);
        String[] token_mon_end = mon_end.split(":");
        Integer hour_mon_end = Integer.parseInt(token_mon_end[0]);
        Integer min_mon_end = Integer.parseInt(token_mon_end[1]);

        Log.d("HOUR MONDAY START", hour_mon_start.toString());
        Log.d("HOUR MONDAY END", hour_mon_end.toString());
        Log.d("MIN MONDAY START", min_mon_start.toString());
        Log.d("MIN MONDAY END", min_mon_end.toString());

        //Checks to see if the hours are similar to each other
        if (hour_mon_end == hour_mon_start){
            //now we are going to check the minutes to see if they are in the correct order
            if (min_mon_start >= min_mon_end){
                return resultForTimeCheck;
            }
        } else {
            if(hour_mon_start >= hour_mon_end){
                return resultForTimeCheck;
            }
        }

//        //TUESDAY
//        //tokenize the entries in each of the results to get results
//        String[] token_tues_start = tues_start.split(":");
//        Integer hour_tues_start = Integer.parseInt(token_tues_start[0]);
//        Integer min_tues_start = Integer.parseInt(token_tues_start[1]);
//        String[] token_tues_end = tues_end.split(":");
//        Integer hour_tues_end = Integer.parseInt(token_tues_end[0]);
//        Integer min_tues_end = Integer.parseInt(token_tues_end[1]);
//
//        //Checks to see if the hours are similar to each other
//        if (hour_tues_end == hour_tues_start){
//            //now we are going to check the minutes to see if they are in the correct order
//            if (min_tues_start >= min_tues_end){
//                return resultForTimeCheck;
//            }
//        } else {
//            if(hour_tues_start >= hour_tues_end){
//                return resultForTimeCheck;
//            }
//        }
//
//        //WEDNESDAY
//        //tokenize the entries in each of the results to get results
//        String[] token_wed_start = wed_start.split(":");
//        Integer hour_wed_start = Integer.parseInt(token_wed_start[0]);
//        Integer min_wed_start = Integer.parseInt(token_wed_start[1]);
//        String[] token_wed_end = wed_end.split(":");
//        Integer hour_wed_end = Integer.parseInt(token_wed_end[0]);
//        Integer min_wed_end = Integer.parseInt(token_wed_end[1]);
//
//        //Checks to see if the hours are similar to each other
//        if (hour_wed_end == hour_wed_start){
//            //now we are going to check the minutes to see if they are in the correct order
//            if (min_wed_start >= min_wed_end){
//                return resultForTimeCheck;
//            }
//        } else {
//            if(hour_wed_start >= hour_wed_end){
//                return resultForTimeCheck;
//            }
//        }
//
//
//        //THURSDAY
//        //tokenize the entries in each of the results to get results
//        String[] token_thurs_start = thurs_start.split(":");
//        Integer hour_thurs_start = Integer.parseInt(token_thurs_start[0]);
//        Integer min_thurs_start = Integer.parseInt(token_thurs_start[1]);
//        String[] token_thurs_end = thurs_end.split(":");
//        Integer hour_thurs_end = Integer.parseInt(token_thurs_end[0]);
//        Integer min_thurs_end = Integer.parseInt(token_thurs_end[1]);
//
//        //Checks to see if the hours are similar to each other
//        if (hour_thurs_end == hour_thurs_start){
//            //now we are going to check the minutes to see if they are in the correct order
//            if (min_thurs_start >= min_thurs_end){
//                return resultForTimeCheck;
//            }
//        } else {
//            if(hour_thurs_start >= hour_thurs_end){
//                return resultForTimeCheck;
//            }
//        }
//
//
//        //FRIDAY
//        //tokenize the entries in each of the results to get results
//        String[] token_fri_start = fri_start.split(":");
//        Integer hour_fri_start = Integer.parseInt(token_fri_start[0]);
//        Integer min_fri_start = Integer.parseInt(token_fri_start[1]);
//        String[] token_fri_end = fri_end.split(":");
//        Integer hour_fri_end = Integer.parseInt(token_fri_end[0]);
//        Integer min_fri_end = Integer.parseInt(token_fri_end[1]);
//
//        //Checks to see if the hours are similar to each other
//        if (hour_fri_end == hour_fri_start){
//            //now we are going to check the minutes to see if they are in the correct order
//            if (min_fri_start >= min_fri_end){
//                return resultForTimeCheck;
//            }
//        } else {
//            if(hour_fri_start >= hour_fri_end){
//                return resultForTimeCheck;
//            }
//        }
//
//        //SATURDAY
//        //tokenize the entries in each of the results to get results
//        String[] token_sat_start = sat_start.split(":");
//        Integer hour_sat_start = Integer.parseInt(token_sat_start[0]);
//        Integer min_sat_start = Integer.parseInt(token_sat_start[1]);
//        String[] token_sat_end = sat_end.split(":");
//        Integer hour_sat_end = Integer.parseInt(token_sat_end[0]);
//        Integer min_sat_end = Integer.parseInt(token_sat_end[1]);
//
//        //Checks to see if the hours are similar to each other
//        if (hour_sat_end == hour_sat_start){
//            //now we are going to check the minutes to see if they are in the correct order
//            if (min_sat_start >= min_sat_end){
//                return resultForTimeCheck;
//            }
//        } else {
//            if(hour_sat_start >= hour_sat_end){
//                return resultForTimeCheck;
//            }
//        }
//
//        //SUNDAY
//        //tokenize the entries in each of the results to get results
//        String[] token_sun_start = sun_start.split(":");
//        Integer hour_sun_start = Integer.parseInt(token_sun_start[0]);
//        Integer min_sun_start = Integer.parseInt(token_sun_start[1]);
//        String[] token_sun_end = sun_end.split(":");
//        Integer hour_sun_end = Integer.parseInt(token_sun_end[0]);
//        Integer min_sun_end = Integer.parseInt(token_sun_end[1]);
//
//        //Checks to see if the hours are similar to each other
//        if (hour_sun_end == hour_sun_start){
//            //now we are going to check the minutes to see if they are in the correct order
//            if (min_sun_start >= min_sun_end){
//                return resultForTimeCheck;
//            }
//        } else {
//            if(hour_sun_start >= hour_sun_end){
//                return resultForTimeCheck;
//            }
//        }

        resultForTimeCheck = true;
        return resultForTimeCheck;

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
