package com.bignerdranch.android.pife11.Scheduler;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.bignerdranch.android.pife11.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MondaySchedule extends AppCompatActivity {

    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monday_schedule);
        //Set Schedule from Database
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //GrabSchedule
    }

    public void GetScheduleFromDatabase(){
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child("Schedule");

        //Idkwhatever you did

    }

    public void onClick(View v){
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child("Schedule");
        switch (v.getId()){
            case R.id.twelveambutton:
                //Checks the color to yellow if grey and vice versa
                ToggleButton twelveam = (ToggleButton) findViewById(R.id.twelveambutton);

                //this is what you do to Database
                if (twelveam.isChecked()) {
                    //manipulate map? of Times? ie (array of 0-23?) ->adv feature bitmap!
                }
                else {

                }



                break;

            case R.id.oneambutton:
                break;

            case R.id.twoambutton:
                break;

            case R.id.threeambutton:
                break;

            case R.id.fourambutton:
                break;

            case R.id.fiveambutton:
                break;

            case R.id.sixambutton:
                break;

            case R.id.sevenambutton:
                break;

            case R.id.eightambutton:
                break;

            case R.id.nineambutton:
                break;

            case R.id.tenambutton:
                break;

            case R.id.elevenambutton:
                break;

            case R.id.twelvepmbutton:
                break;

            case R.id.onepmbutton:
                break;

            case R.id.twopmbutton:
                break;

            case R.id.threepmbutton:
                break;

            case R.id.fourpmbutton:
                break;

            case R.id.fivepmbutton:
                break;

            case R.id.sixpmbutton:
                break;

            case R.id.sevenpmbutton:
                break;

            case R.id.eightpmbutton:
                break;

            case R.id.ninepmbutton:
                break;

            case R.id.tenpmbutton:
                break;

            case R.id.elevenpmbutton:
                break;

        }

    }

}
