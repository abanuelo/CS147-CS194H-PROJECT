package com.bignerdranch.android.pife11.Scheduler;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bignerdranch.android.pife11.MatchesChoice;
import com.bignerdranch.android.pife11.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleMain extends AppCompatActivity {
    private String currUser;
    private String matchUser;
    private Button schedule;
    private ToggleButton mon_option_1;
    private ToggleButton mon_option_2;
    private ToggleButton thur_option_1;
    private ToggleButton thur_option_2;
    private ToggleButton sat_option_1;

    //For this algorithm we are going to iterate through all the times free within the match and the intended user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_main);

        schedule = (Button) findViewById(R.id.schedule);
        mon_option_1 = (ToggleButton) findViewById(R.id.mon_option_1);
        mon_option_2 = (ToggleButton) findViewById(R.id.mon_option_2);
        thur_option_1 = (ToggleButton) findViewById(R.id.thursday_option_1);
        thur_option_2 = (ToggleButton) findViewById(R.id.thursday_option_2);
        sat_option_1 = (ToggleButton) findViewById(R.id.sat_option_1);


        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBackToOptions = new Intent(ScheduleMain.this, MatchesChoice.class);
                startActivity(goBackToOptions);
            }
        });
    }

    public void onClick(View v){
        //Monday Option #1
        if(mon_option_1.isChecked()){
            mon_option_1.setChecked(true);
        } else {
            mon_option_1.setChecked(false);
        }

        //Monday Option #2
        if(mon_option_2.isChecked()){
            mon_option_2.setChecked(true);
        } else {
            mon_option_2.setChecked(false);
        }

        //Thursday Option #1
        if(thur_option_1.isChecked()){
            thur_option_1.setChecked(true);
        } else {
            thur_option_1.setChecked(false);
        }

        //Thursday Option #2
        if(thur_option_2.isChecked()){
            thur_option_2.setChecked(true);
        } else {
            thur_option_2.setChecked(false);
        }

        //Saturday Option #1
        if(sat_option_1.isChecked()){
            sat_option_1.setChecked(true);
        } else {
            sat_option_1.setChecked(false);
        }

    }
}
