package com.bignerdranch.android.pife11;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class GiveFeedback extends AppCompatActivity {

    private Button sendFeedback;
    private TextInputLayout ilike;
    private TextInputLayout iWish;
    private DatabaseReference userdb;
    private FirebaseAuth auth;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_feedback);


        ilike = findViewById(R.id.ilike);
        iWish = findViewById(R.id.iwish);

        sendFeedback = findViewById(R.id.feedback);

        sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText iliketext = ilike.getEditText();
                EditText iwishtext = iWish.getEditText();
                String ilikeItem = iliketext.getText().toString();
                String iwishItem = iwishtext.getText().toString();
                storeInFirebase(ilikeItem, iwishItem);
                Log.d("TEXT", iliketext.getText().toString()); //returns text from textinput;
            }
        });
    }

    public void storeInFirebase(String ilike, String iwish){

    }
}
