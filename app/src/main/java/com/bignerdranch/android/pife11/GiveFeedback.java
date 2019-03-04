package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bignerdranch.android.pife11.SelectVideoOnProfile.SelectVideoOnProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

public class GiveFeedback extends AppCompatActivity {

    private Button sendFeedback;
    private TextInputLayout ilike;
    private TextInputLayout iWish;
    private DatabaseReference userdb;
    private FirebaseAuth auth;
    private String currentUserId;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_feedback);

        Intent intent = getIntent();
        final String videoId = intent.getStringExtra("videoId");
        currentUserId = intent.getStringExtra("userId");

        final DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username = dataSnapshot.child("username").getValue().toString().trim();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



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

                Boolean errorFree = true;
                if (TextUtils.isEmpty(ilikeItem)) {
                    iliketext.setError("I like comment is required");
                    errorFree = false;
                }
                if (TextUtils.isEmpty(iwishItem)) {
                    iwishtext.setError("I wish comment is required");
                    errorFree = false;
                }
                if (errorFree) {

                    String currentTimeFormatted = Calendar.getInstance().getTime().toString();
                    String currentTime = currentTimeFormatted.replaceAll(":","");
                    currentTime = currentTime.replaceAll("\\s","");
                    currentTime = currentTime.toLowerCase();
                    currentTime = currentTime.replaceAll("\\p{Punct}","");

                    storeInFirebase(ilikeItem, iwishItem, videoId, currentTime, currentTimeFormatted, username);
                    Log.d("TEXT", iliketext.getText().toString()); //returns text from textinput;


                    Intent sign_out_intent = new Intent(GiveFeedback.this, Profile.class);
                    finish();
                    startActivity(sign_out_intent);
                }


            }
        });

    }

    public void storeInFirebase(String ilike, String iwish, String videoId, String dateTim, String datetimeFormatted, String username){

        //store userid
        //store date
        //store comments
        videoId = videoId.replaceAll("\\p{Punct}","");

        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Comments").child(videoId).child(dateTim);
        matchDb.child("user").setValue(currentUserId);
        matchDb.child("ilike").setValue(ilike);
        matchDb.child("iwish").setValue(iwish);
        matchDb.child("date").setValue(datetimeFormatted);
        matchDb.child("username").setValue(username);
    }
}
