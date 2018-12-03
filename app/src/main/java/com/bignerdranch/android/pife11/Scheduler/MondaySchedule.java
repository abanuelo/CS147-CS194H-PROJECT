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

import com.bignerdranch.android.pife11.R;

public class MondaySchedule extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monday_schedule);
    }


    public void OnClick(View v){
        switch (v.getId()){
            case R.id.twelveambutton:
                //Checks the color to yellow if grey and vice versa
                Button twelveam = (Button) findViewById(R.id.twelveambutton);
                Drawable buttonBackground = twelveam.getBackground();
                Drawable greyButtonBackground = ContextCompat.getDrawable(this, R.drawable.grey_buttonoval);
                Drawable yellowButtonBackground = ContextCompat.getDrawable(this, R.drawable.buttonoval);

                Bitmap curr = ((BitmapDrawable)buttonBackground).getBitmap();
                Bitmap yellow = ((BitmapDrawable)yellowButtonBackground).getBitmap();
                Bitmap grey = ((BitmapDrawable)greyButtonBackground).getBitmap();

                if (curr == grey){
                    //change the background to yellow and register
                    twelveam.setBackground(yellowButtonBackground);
                    Log.d("CONVERT YELLOW", "TRUE");
                } else {
                    twelveam.setBackground(greyButtonBackground);
                    Log.d("CONVERT GREY", "TRUE");
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
