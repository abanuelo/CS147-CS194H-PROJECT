package com.bignerdranch.android.pife11;

import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class watch_performance extends AppCompatActivity {
    private Button searchPerform1, searchPerform2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_performance);

        searchPerform1 = findViewById(R.id.button1);
        searchPerform2 = findViewById(R.id.button2);

        //Search Perform 2 for making it pop up forever
        searchPerform1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://appear.in/piferoom1";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(watch_performance.this, Uri.parse(url));
            }
        });

        //Search Perform 2 for making it pop up forever
        searchPerform2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://appear.in/piferoom1";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(watch_performance.this, Uri.parse(url));
            }
        });
    }
}
