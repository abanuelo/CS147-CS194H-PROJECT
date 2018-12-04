package com.bignerdranch.android.pife11.Scheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.bignerdranch.android.pife11.Profile;
import com.bignerdranch.android.pife11.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThursdaySchedule extends AppCompatActivity {
    private String currentUserId;
    private DatabaseReference userDb;
    private ArrayList<Integer> times;
    private ImageView wednesday,friday;
    private Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thursday_schedule);
        //Set Schedule from Database
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Schedule");
        times = new ArrayList<Integer>();
        //Populates times for 23 locations
        for (int i = 0; i < 24; i++){
            times.add(0);
        }

        finish = (Button) findViewById(R.id.finish);
        wednesday = (ImageView) findViewById(R.id.wednesday);
        friday = (ImageView) findViewById(R.id.friday);


        //We are going to add a method that revolves around making sure that we already have entries
        //and populating the view accordingly
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("THURS").exists()){
                    int index = 0;
                    for (DataSnapshot time_slot : dataSnapshot.child("THURS").getChildren()){
                        String value = time_slot.getValue().toString();
                        if (Integer.parseInt(value) == 1){
                            if (index == 0){
                                ToggleButton twelveam = (ToggleButton) findViewById(R.id.twelveambutton);
                                twelveam.setChecked(true);
                            } else if (index == 1){
                                ToggleButton oneam = (ToggleButton) findViewById(R.id.oneambutton);
                                oneam.setChecked(true);
                            } else if (index ==2){
                                ToggleButton twoam = (ToggleButton) findViewById(R.id.twoambutton);
                                twoam.setChecked(true);
                            } else if (index == 3){
                                ToggleButton threeam = (ToggleButton) findViewById(R.id.threeambutton);
                                threeam.setChecked(true);
                            } else if (index == 4){
                                ToggleButton fouram = (ToggleButton) findViewById(R.id.fourambutton);
                                fouram.setChecked(true);
                            } else if (index == 5){
                                ToggleButton fiveam = (ToggleButton) findViewById(R.id.fiveambutton);
                                fiveam.setChecked(true);
                            } else if (index == 6){
                                ToggleButton sixam = (ToggleButton) findViewById(R.id.sixambutton);
                                sixam.setChecked(true);
                            } else if (index == 7){
                                ToggleButton sevenam = (ToggleButton) findViewById(R.id.sevenambutton);
                                sevenam.setChecked(true);
                            } else if (index == 8){
                                ToggleButton eightam = (ToggleButton) findViewById(R.id.eightambutton);
                                eightam.setChecked(true);
                            } else if (index == 9){
                                ToggleButton nineam = (ToggleButton) findViewById(R.id.nineambutton);
                                nineam.setChecked(true);
                            } else if (index == 10){
                                ToggleButton tenam = (ToggleButton) findViewById(R.id.tenambutton);
                                tenam.setChecked(true);
                            } else if (index == 11){
                                ToggleButton elevenam = (ToggleButton) findViewById(R.id.elevenambutton);
                                elevenam.setChecked(true);
                            } else if (index == 12){
                                ToggleButton twelvepm = (ToggleButton) findViewById(R.id.twelvepmbutton);
                                twelvepm.setChecked(true);
                            } else if (index == 13){
                                ToggleButton onepm = (ToggleButton) findViewById(R.id.onepmbutton);
                                onepm.setChecked(true);
                            } else if (index == 14){
                                ToggleButton twopm = (ToggleButton) findViewById(R.id.twopmbutton);
                                twopm.setChecked(true);
                            } else if (index == 15){
                                ToggleButton threepm = (ToggleButton) findViewById(R.id.threepmbutton);
                                threepm.setChecked(true);
                            } else if (index == 16){
                                ToggleButton fourpm = (ToggleButton) findViewById(R.id.fourpmbutton);
                                fourpm.setChecked(true);
                            } else if (index == 17){
                                ToggleButton fivepm = (ToggleButton) findViewById(R.id.fivepmbutton);
                                fivepm.setChecked(true);
                            } else if (index == 18){
                                ToggleButton sixpm = (ToggleButton) findViewById(R.id.sixpmbutton);
                                sixpm.setChecked(true);
                            } else if (index == 19){
                                ToggleButton sevenpm = (ToggleButton) findViewById(R.id.sevenpmbutton);
                                sevenpm.setChecked(true);
                            } else if (index == 20){
                                ToggleButton eightpm = (ToggleButton) findViewById(R.id.eightpmbutton);
                                eightpm.setChecked(true);
                            } else if (index == 21){
                                ToggleButton ninepm = (ToggleButton) findViewById(R.id.ninepmbutton);
                                ninepm.setChecked(true);
                            } else if (index == 22){
                                ToggleButton tenpm = (ToggleButton) findViewById(R.id.tenpmbutton);
                                tenpm.setChecked(true);
                            } else if (index == 23){
                                ToggleButton elevenpm = (ToggleButton) findViewById(R.id.elevenpmbutton);
                                elevenpm.setChecked(true);
                            }
                        }
                        index += 1;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToProfile = new Intent(ThursdaySchedule.this, Profile.class);
                storeData();
                startActivity(goToProfile);
            }
        });

        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToWednesday = new Intent(ThursdaySchedule.this, WednesdaySchedule.class);
                storeData();
                startActivity(goToWednesday);
            }
        });

        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToFriday = new Intent(ThursdaySchedule.this, ThursdaySchedule.class);
                storeData();
                startActivity(goToFriday);
            }
        });
    }

    public void storeData(){
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("THURS").exists()){
                    //the case when the values are all zero
                    //Values already exist and we need to update those values in the database
                    ArrayList<Integer> new_times = new ArrayList<Integer>();
                    for (DataSnapshot time_slots : dataSnapshot.child("THURS").getChildren()){
                        String index_string = time_slots.getKey().trim();
                        String time_slot_val_string = time_slots.getValue().toString().trim();
                        Integer time_slot_val = Integer.parseInt(time_slot_val_string);
                        Integer index = Integer.parseInt(index_string);
                        if (index == 0){
                            ToggleButton twelveam = (ToggleButton) findViewById(R.id.twelveambutton);
                            if (twelveam.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!twelveam.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 1){
                            ToggleButton oneam = (ToggleButton) findViewById(R.id.oneambutton);
                            if (oneam.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!oneam.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index ==2){
                            ToggleButton twoam = (ToggleButton) findViewById(R.id.twoambutton);
                            if (twoam.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!twoam.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 3){
                            ToggleButton threeam = (ToggleButton) findViewById(R.id.threeambutton);
                            if (threeam.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!threeam.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 4){
                            ToggleButton fouram = (ToggleButton) findViewById(R.id.fourambutton);
                            if (fouram.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!fouram.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 5){
                            ToggleButton fiveam = (ToggleButton) findViewById(R.id.fiveambutton);
                            if (fiveam.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!fiveam.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 6){
                            ToggleButton sixam = (ToggleButton) findViewById(R.id.sixambutton);
                            if (sixam.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!sixam.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 7){
                            ToggleButton sevenam = (ToggleButton) findViewById(R.id.sevenambutton);
                            if (sevenam.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!sevenam.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 8){
                            ToggleButton eightam = (ToggleButton) findViewById(R.id.eightambutton);
                            if (eightam.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!eightam.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 9){
                            ToggleButton nineam = (ToggleButton) findViewById(R.id.nineambutton);
                            if (nineam.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!nineam.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 10){
                            ToggleButton tenam = (ToggleButton) findViewById(R.id.tenambutton);
                            if (tenam.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!tenam.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 11){
                            ToggleButton elevenam = (ToggleButton) findViewById(R.id.elevenambutton);
                            if (elevenam.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!elevenam.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 12){
                            ToggleButton twelvepm = (ToggleButton) findViewById(R.id.twelvepmbutton);
                            if (twelvepm.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!twelvepm.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 13){
                            ToggleButton onepm = (ToggleButton) findViewById(R.id.onepmbutton);
                            if (onepm.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!onepm.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 14){
                            ToggleButton twopm = (ToggleButton) findViewById(R.id.twopmbutton);
                            if (twopm.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!twopm.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 15){
                            ToggleButton threepm = (ToggleButton) findViewById(R.id.threepmbutton);
                            if (threepm.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!threepm.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 16){
                            ToggleButton fourpm = (ToggleButton) findViewById(R.id.fourpmbutton);
                            if (fourpm.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!fourpm.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 17){
                            ToggleButton fivepm = (ToggleButton) findViewById(R.id.fivepmbutton);
                            if (fivepm.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!fivepm.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 18){
                            ToggleButton sixpm = (ToggleButton) findViewById(R.id.sixpmbutton);
                            if (sixpm.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!sixpm.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 19){
                            ToggleButton sevenpm = (ToggleButton) findViewById(R.id.sevenpmbutton);
                            if (sevenpm.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!sevenpm.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 20){
                            ToggleButton eightpm = (ToggleButton) findViewById(R.id.eightpmbutton);
                            if (eightpm.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!eightpm.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 21){
                            ToggleButton ninepm = (ToggleButton) findViewById(R.id.ninepmbutton);
                            if (ninepm.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!ninepm.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 22){
                            ToggleButton tenpm = (ToggleButton) findViewById(R.id.tenpmbutton);
                            if (tenpm.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!tenpm.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        } else if (index == 23){
                            ToggleButton elevenpm = (ToggleButton) findViewById(R.id.elevenpmbutton);
                            if (elevenpm.isChecked() && time_slot_val == 0){
                                new_times.add(1);
                            } else if (!elevenpm.isChecked() && time_slot_val == 1){
                                new_times.add(0);
                            } else {
                                new_times.add(time_slot_val);
                            }
                        }
                    }
                    Map new_mon_times = new HashMap();
                    new_mon_times.put("THURS", new_times);
                    userDb.updateChildren(new_mon_times);

                } else {
                    Map mon_times = new HashMap();
                    mon_times.put("THURS", times);
                    userDb.updateChildren(mon_times);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void updateBitArray(final Boolean isChecked, final Integer index){
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (isChecked){
                    times.set(index, 1);
                } else {
                    times.set(index, 0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.twelveambutton:
                int index_0 = 0;
                Boolean isChecked_0 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton twelveam = (ToggleButton) findViewById(R.id.twelveambutton);

                //Toggle it to see if we have selected the button for the specific element
                if (twelveam.isChecked()) {
                    isChecked_0 = true;
                }
                else {
                    isChecked_0 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_0, index_0);
                break;

            case R.id.oneambutton:
                int index_1 = 1;
                Boolean isChecked_1 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton oneam = (ToggleButton) findViewById(R.id.oneambutton);

                //Toggle it to see if we have selected the button for the specific element
                if (oneam.isChecked()) {
                    isChecked_1 = true;
                }
                else {
                    isChecked_1 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_1, index_1);
                break;

            case R.id.twoambutton:
                int index_2 = 2;
                Boolean isChecked_2 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton twoam = (ToggleButton) findViewById(R.id.twoambutton);

                //Toggle it to see if we have selected the button for the specific element
                if (twoam.isChecked()) {
                    isChecked_2 = true;
                }
                else {
                    isChecked_2 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_2, index_2);

                break;

            case R.id.threeambutton:
                int index_3 = 3;
                Boolean isChecked_3 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton threeam = (ToggleButton) findViewById(R.id.threeambutton);

                //Toggle it to see if we have selected the button for the specific element
                if (threeam.isChecked()) {
                    isChecked_3 = true;
                }
                else {
                    isChecked_3 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_3, index_3);
                break;

            case R.id.fourambutton:
                int index_4 = 4;
                Boolean isChecked_4 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton fouram = (ToggleButton) findViewById(R.id.fourambutton);

                //Toggle it to see if we have selected the button for the specific element
                if (fouram.isChecked()) {
                    isChecked_4 = true;
                }
                else {
                    isChecked_4 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_4, index_4);
                break;

            case R.id.fiveambutton:
                int index_5 = 5;
                Boolean isChecked_5 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton fiveam = (ToggleButton) findViewById(R.id.fiveambutton);

                //Toggle it to see if we have selected the button for the specific element
                if (fiveam.isChecked()) {
                    isChecked_5 = true;
                }
                else {
                    isChecked_5 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_5, index_5);
                break;

            case R.id.sixambutton:
                int index_6 = 6;
                Boolean isChecked_6 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton sixam = (ToggleButton) findViewById(R.id.sixambutton);

                //Toggle it to see if we have selected the button for the specific element
                if (sixam.isChecked()) {
                    isChecked_6 = true;
                }
                else {
                    isChecked_6 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_6, index_6);
                break;

            case R.id.sevenambutton:
                int index_7 = 7;
                Boolean isChecked_7 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton sevenam = (ToggleButton) findViewById(R.id.sevenambutton);

                //Toggle it to see if we have selected the button for the specific element
                if (sevenam.isChecked()) {
                    isChecked_7 = true;
                }
                else {
                    isChecked_7 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_7, index_7);
                break;

            case R.id.eightambutton:
                int index_8 = 8;
                Boolean isChecked_8 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton eightam = (ToggleButton) findViewById(R.id.eightambutton);

                //Toggle it to see if we have selected the button for the specific element
                if (eightam.isChecked()) {
                    isChecked_8 = true;
                }
                else {
                    isChecked_8 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_8, index_8);
                break;

            case R.id.nineambutton:
                int index_9 = 9;
                Boolean isChecked_9 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton nineam = (ToggleButton) findViewById(R.id.nineambutton);

                //Toggle it to see if we have selected the button for the specific element
                if (nineam.isChecked()) {
                    isChecked_9 = true;
                }
                else {
                    isChecked_9 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_9, index_9);
                break;

            case R.id.tenambutton:
                int index_10 = 8;
                Boolean isChecked_10 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton tenam = (ToggleButton) findViewById(R.id.tenambutton);

                //Toggle it to see if we have selected the button for the specific element
                if (tenam.isChecked()) {
                    isChecked_10 = true;
                }
                else {
                    isChecked_10 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_10, index_10);
                break;

            case R.id.elevenambutton:
                int index_11 = 11;
                Boolean isChecked_11 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton elevenam = (ToggleButton) findViewById(R.id.elevenambutton);

                //Toggle it to see if we have selected the button for the specific element
                if (elevenam.isChecked()) {
                    isChecked_11 = true;
                }
                else {
                    isChecked_11 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_11, index_11);
                break;

            case R.id.twelvepmbutton:
                int index_12 = 12;
                Boolean isChecked_12 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton twelvepm = (ToggleButton) findViewById(R.id.twelvepmbutton);

                //Toggle it to see if we have selected the button for the specific element
                if (twelvepm.isChecked()) {
                    isChecked_12 = true;
                }
                else {
                    isChecked_12 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_12, index_12);
                break;

            case R.id.onepmbutton:
                int index_13 = 13;
                Boolean isChecked_13 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton onepm = (ToggleButton) findViewById(R.id.onepmbutton);

                //Toggle it to see if we have selected the button for the specific element
                if (onepm.isChecked()) {
                    isChecked_13 = true;
                }
                else {
                    isChecked_13 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_13, index_13);
                break;

            case R.id.twopmbutton:
                int index_14 = 14;
                Boolean isChecked_14 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton twopm = (ToggleButton) findViewById(R.id.twopmbutton);

                //Toggle it to see if we have selected the button for the specific element
                if (twopm.isChecked()) {
                    isChecked_14 = true;
                }
                else {
                    isChecked_14 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_14, index_14);
                break;

            case R.id.threepmbutton:
                int index_15 = 15;
                Boolean isChecked_15 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton threepm = (ToggleButton) findViewById(R.id.threepmbutton);

                //Toggle it to see if we have selected the button for the specific element
                if (threepm.isChecked()) {
                    isChecked_15 = true;
                }
                else {
                    isChecked_15 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_15, index_15);
                break;

            case R.id.fourpmbutton:
                int index_16 = 16;
                Boolean isChecked_16 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton fourpm = (ToggleButton) findViewById(R.id.fourpmbutton);

                //Toggle it to see if we have selected the button for the specific element
                if (fourpm.isChecked()) {
                    isChecked_16 = true;
                }
                else {
                    isChecked_16 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_16, index_16);
                break;

            case R.id.fivepmbutton:
                int index_17 = 17;
                Boolean isChecked_17 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton fivepm = (ToggleButton) findViewById(R.id.fivepmbutton);

                //Toggle it to see if we have selected the button for the specific element
                if (fivepm.isChecked()) {
                    isChecked_17 = true;
                }
                else {
                    isChecked_17 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_17, index_17);
                break;

            case R.id.sixpmbutton:
                int index_18 = 18;
                Boolean isChecked_18 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton sixpm = (ToggleButton) findViewById(R.id.sixpmbutton);

                //Toggle it to see if we have selected the button for the specific element
                if (sixpm.isChecked()) {
                    isChecked_18 = true;
                }
                else {
                    isChecked_18 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_18, index_18);
                break;

            case R.id.sevenpmbutton:
                int index_19 = 19;
                Boolean isChecked_19 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton sevenpm = (ToggleButton) findViewById(R.id.sevenpmbutton);

                //Toggle it to see if we have selected the button for the specific element
                if (sevenpm.isChecked()) {
                    isChecked_19 = true;
                }
                else {
                    isChecked_19 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_19, index_19);
                break;

            case R.id.eightpmbutton:
                int index_20 = 20;
                Boolean isChecked_20 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton eightpm = (ToggleButton) findViewById(R.id.eightpmbutton);

                //Toggle it to see if we have selected the button for the specific element
                if (eightpm.isChecked()) {
                    isChecked_20 = true;
                }
                else {
                    isChecked_20 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_20, index_20);
                break;

            case R.id.ninepmbutton:
                int index_21 = 21;
                Boolean isChecked_21 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton ninepm = (ToggleButton) findViewById(R.id.ninepmbutton);

                //Toggle it to see if we have selected the button for the specific element
                if (ninepm.isChecked()) {
                    isChecked_21 = true;
                }
                else {
                    isChecked_21 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_21, index_21);
                break;

            case R.id.tenpmbutton:
                int index_22 = 22;
                Boolean isChecked_22 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton tenpm = (ToggleButton) findViewById(R.id.tenpmbutton);

                //Toggle it to see if we have selected the button for the specific element
                if (tenpm.isChecked()) {
                    isChecked_22 = true;
                }
                else {
                    isChecked_22 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_22, index_22);
                break;

            case R.id.elevenpmbutton:
                int index_23 = 23;
                Boolean isChecked_23 = false;
                //Checks the color to yellow if grey and vice versa
                ToggleButton elevenpm = (ToggleButton) findViewById(R.id.elevenpmbutton);

                //Toggle it to see if we have selected the button for the specific element
                if (elevenpm.isChecked()) {
                    isChecked_23 = true;
                }
                else {
                    isChecked_23 = false;
                }

                //manipulate map? map will be bits with 1 or 0 and will be indicating time
                updateBitArray(isChecked_23, index_23);
                break;
        }

    }

}
