package com.bignerdranch.android.pife11;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class GiveFeedback extends AppCompatActivity {

    private Button sendFeedback;
    private TextInputLayout inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_feedback);
    }
}
