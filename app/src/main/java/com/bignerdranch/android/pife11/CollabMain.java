package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CollabMain extends AppCompatActivity {
    private Button bApplyFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collab_main);

        bApplyFilter = findViewById(R.id.apply_filter);

        bApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent applyfilter_intent = new Intent(CollabMain.this, SearchCollab.class);
                startActivity(applyfilter_intent);
            }
        });
    }
}
