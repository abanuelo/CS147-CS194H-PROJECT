package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class peform extends AppCompatActivity {
    private Button bperform1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peform);
        Button btn = (Button) findViewById(R.id.activity_final_perform);

        //.. set what happens when user clicks

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent ine = new Intent(Dashboard.this,peform.class);
                startActivity(ine);

                // check dashboard.java for how to click button and transfer to another page
                // check out online for how to incoporate video but this is something for later
            }
        }
    }
}
