package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.os.Bundle;
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
                Intent practice_intent = new Intent(peform.this, StartPerformance.class);
                startActivity(practice_intent);
            }
        });

    }
}
