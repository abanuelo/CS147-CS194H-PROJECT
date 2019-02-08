package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class peform extends AppCompatActivity {
    private Button watch;
    private Button perform;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peform);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationViewPerform);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                                                     @Override
                                                                     public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                                                                         switch (menuItem.getItemId()){
                                                                             case R.id.practice_nav:
                                                                                 Intent practice_intent = new Intent(peform.this, PracticePlaying.class);
                                                                                 startActivity(practice_intent);
                                                                             case R.id.perform_nav:
                                                                                 Intent perform_intent = new Intent(peform.this, peform.class);
                                                                                 startActivity(perform_intent);
                                                                             case R.id.friends_nav:
                                                                                 Intent collab_intent = new Intent(peform.this, CollabHiFi2.class);
                                                                                 startActivity(collab_intent);
                                                                             case R.id.user_nav:
                                                                                 Intent profile_intent = new Intent(peform.this, CollabHiFi2.class);
                                                                                 startActivity(profile_intent);
                                                                         }
                                                                         return true;
                                                                     }
                                                                 }

        );

        watch = findViewById(R.id.search);
        perform = findViewById(R.id.perform);

        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent practice_intent = new Intent(peform.this, watch_performance.class);
                startActivity(practice_intent);
            }
        });

        perform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //UPDATE THIS URL FOR THE FINAL PRESENTATION OF THE PROJECT
                String url = "https://appear.in/piferoom2";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(peform.this, Uri.parse(url));
            }
        });

    }
}