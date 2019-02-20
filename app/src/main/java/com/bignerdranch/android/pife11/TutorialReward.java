package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TutorialReward extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_reward);
    }

    public void returnToProfile(View view){
        Intent practice_intent = new Intent(this, Profile.class);
        startActivity(practice_intent);
    }
}
