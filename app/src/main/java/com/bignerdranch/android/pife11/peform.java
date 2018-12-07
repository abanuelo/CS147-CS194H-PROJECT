package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class peform extends AppCompatActivity {
    private Button watch;
    private Button perform;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peform);

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
                String url = "https://youtu.be/MZ5dz7BDbNw";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(peform.this, Uri.parse(url));
            }
        });

    }
}
