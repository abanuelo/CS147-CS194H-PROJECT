package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class practice_goal_met extends AppCompatActivity {

    private String currentUserId;
    private String myAvatar;
    private String message;
    private GifDrawable drawable;
    private GifImageView animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_goal_met);
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getUserAvatar();
        message = getIntent().getExtras().getString("message");

        EditText messageView = findViewById(R.id.PracticeGoalMet);
        messageView.setText(message);

        TextView talking = findViewById(R.id.PracticeGoalMetMessage);
        talking.setText(message);
    }

    @Override
    protected void onResume(){
        getUserAvatar();
        message = getIntent().getExtras().getString("message");

        EditText messageView = findViewById(R.id.PracticeGoalMet);
        messageView.setText(message);

        TextView talking = findViewById(R.id.PracticeGoalMetMessage);
        talking.setText(message);
        super.onResume();
    }

    private void getUserAvatar() {
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Avatar");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    myAvatar = dataSnapshot.getValue().toString().trim();
                    ImageView avatarDisplay = (ImageView) findViewById(R.id.PracticeGoalAvatar);
                    if(myAvatar.equals("{avatar=Jemi}")) {
                        avatarDisplay.setImageResource(R.drawable.ic_monster_baby);
                        avatarDisplay.setVisibility(View.INVISIBLE);


                        try {
                            drawable = new GifDrawable(getResources(), R.drawable.jemi_talking);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        drawable.setLoopCount(0);
                        animation = (GifImageView) findViewById(R.id.PracticeGoalAvatarGIF);
                        animation.setBackground(drawable);
                        animation.setVisibility(View.VISIBLE);
                    } else {
                        avatarDisplay.setImageResource(R.drawable.ic_nerdy_monster_baby);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void onClick(View v) {
        switch (v.getId()){

            case R.id.PracticeGoalDone:
                Intent homeScreen = new Intent(practice_goal_met.this, Dashboard.class);
                startActivity(homeScreen);
                break;
        }
    }

}
