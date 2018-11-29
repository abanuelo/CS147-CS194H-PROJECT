package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Dashboard extends AppCompatActivity {
    private Button bPractice;
    private Button bPerform;
    private Button bCollab;
    private ImageView rewardShop;
    private ImageView userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bPractice = findViewById(R.id.practice);
        bCollab = findViewById(R.id.collab);
        userProfile = findViewById(R.id.user_profile);

        bPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent practice_intent = new Intent(Dashboard.this, PracticePlaying.class);
                startActivity(practice_intent);
            }
        });

        bCollab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent collab_intent = new Intent(Dashboard.this, CollabMain.class);
                startActivity(collab_intent);
            }
        });

        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent user_profile_intent = new Intent(Dashboard.this, Profile.class);
                startActivity(user_profile_intent);
            }
        });
    }
}
