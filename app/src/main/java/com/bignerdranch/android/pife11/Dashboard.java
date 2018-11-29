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
    private ImageView userShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        bPractice = findViewById(R.id.practice);
        bPerform = findViewById(R.id.perform);
        bPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, PracticeMain.class);
                startActivity(intent);
            }
        });
        bPerform.setOnClickListener( new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                Intent in = new Intent(Dashboard.this,peform.class);
                startActivity(in);
            }
        });
    }
}
