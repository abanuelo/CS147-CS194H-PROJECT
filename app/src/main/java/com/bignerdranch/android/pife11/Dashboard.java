package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieImageAsset;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class Dashboard extends AppCompatActivity {
    private Button bPractice;
    private Button bPerform;
    private Button bCollab;
    private ImageView rewardShop;
    private ImageView userProfile;
    private String currentUserId;
    private GifImageView animation;
    private GifDrawable drawable;
    private int heartLevelPoints = 0;
    private ProgressBar heartlevel;
    private ProgressBar streaklevel;

    private final String collab = "collab";
    private final String practice = "practice";
    private final String perform = "perform";

    private boolean practiceBool = false;
    private boolean performBool = false;
    private boolean collabBool = false;


    //private LottieAnimationView animation;
    //private String myAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        FetchHeartStreakLevel();
//        checkIfTaskComplete();


        bPractice = findViewById(R.id.practice);
        bCollab = findViewById(R.id.collab);
        bPerform = findViewById(R.id.perform);
        userProfile = findViewById(R.id.user_profile);
        rewardShop = findViewById(R.id.reward);

        bPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateButton(1);
                Intent practice_intent = new Intent(Dashboard.this, PracticePlaying.class);
                startActivity(practice_intent);
            }
        });

        bPerform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateButton(2);
                Intent practice_intent = new Intent(Dashboard.this, peform.class);
                startActivity(practice_intent);
            }
        });

        bCollab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateButton(3);
                Intent collab_intent = new Intent(Dashboard.this, CollabMain.class);
                startActivity(collab_intent);
            }
        });

        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent user_profile_intent = new Intent(Dashboard.this, Profile.class);
                startActivity(user_profile_intent);
            }
        });

        rewardShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rewards_intent = new Intent(Dashboard.this, RewardsBoth.class);
                startActivity(rewards_intent);
            }
        });

    }

    //Method to call when a button is clicked -> provides change in bool
    // 1=practice 2=perform 3=collab

    @Override
    protected void onResume(){
        FetchHeartStreakLevel();
        checkIfTaskComplete();
        super.onResume();
    }

    private void updateButton(final int task){
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats");

        String taskflow = "";
        switch (task) {
            case 1:
                taskflow = practice;
                practiceBool = true;
                break;
            case 2:
                taskflow = perform;
                performBool = true;
                break;
            case 3:
                taskflow = collab;
                collabBool = true;
                break;
            default:
                break;
        }
        FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child(taskflow).setValue(Integer.toString((1)));

    }

    private void checkIfTaskComplete(){

        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot match: dataSnapshot.getChildren()){
                        Log.i("Stats:", match.getKey() + ": " + match.getValue().toString());
                        if (match.getKey().equals(practice)) {
                            int hl = Integer.parseInt(match.getValue().toString().trim());
                            if(hl == 1) practiceBool = true;
                        }
                        if (match.getKey().equals(perform)) {
                            int hl = Integer.parseInt(match.getValue().toString().trim());
                            if(hl == 1) performBool = true;
                        }
                        if (match.getKey().equals(collab)) {
                            int hl = Integer.parseInt(match.getValue().toString().trim());
                            if(hl == 1) collabBool = true;
                        }
                    }
                    updateAnimation();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateAnimation(){
        Log.i("Stats:", "Truths: " + practiceBool + performBool + collabBool);
        if (practiceBool && performBool && collabBool) {
            //Evolve the creature
            try{
                //do the dialog
                drawable = new GifDrawable(getResources(), R.drawable.jemi_plain_toddler);
                FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("xp").setValue(Integer.toString((10)));

            }
            catch (IOException ie) {
                Toast.makeText(getApplicationContext(),"Error with Gif",Toast.LENGTH_LONG).show();
            }
        }
        else {

            //Adding Gifs into the Code Content
            try {
                Log.i("heartLevelPoints", "Points" + Integer.toString(heartLevelPoints));
                if (heartLevelPoints > 51) {
                    drawable = new GifDrawable(getResources(), R.drawable.jemi_happy);
                }
                else {
                    drawable = new GifDrawable(getResources(), R.drawable.jemi_sad);
                }


            } catch (IOException ie) {
                Toast.makeText(getApplicationContext(),"Error with Gif",Toast.LENGTH_LONG).show();
                //Catch the IO Exception in case of getting an hour
            }
        }

        drawable.setLoopCount(0);
        animation = (GifImageView) findViewById(R.id.animation);
        animation.setBackground(drawable);
    }

    private void FetchHeartStreakLevel(){
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats");

        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot match: dataSnapshot.getChildren()){
                        if (match.getKey().equals("heartlevel")) {
                            int hl = Integer.parseInt(match.getValue().toString().trim());
                            heartlevel = findViewById(R.id.DashboardHeartLevel);
                            Log.i("heartlevel-hl", Integer.toString(hl) );
                            heartLevelPoints = hl;
                            heartlevel.setProgress(hl);
                        }
                        if (match.getKey().equals("streak")) {
                            int hl = Integer.parseInt(match.getValue().toString().trim());
                            streaklevel = findViewById(R.id.StreakLevel);
                            streaklevel.setProgress(hl);
                        }
                    }
                }
                checkIfTaskComplete();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //getUserAvatar();
    //animation = (LottieAnimationView) findViewById(R.id.avatarDisplay);
    //animation.setImageAssetsFolder("images");
    //animation.playAnimation();

    //        animation = (GifImageView) findViewById(R.id.animation);
//        animation.setImageResource(R.drawable.jemi_happy);


//        animation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                animation.setImageResource(R.drawable.jemi_happy);
//            }
//        });

//    private void getUserAvatar() {
//        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Avatar");
//        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//                    myAvatar = dataSnapshot.getValue().toString().trim();
//                    ImageView avatarDisplay = (ImageView) findViewById(R.id.avatarDisplay);
//                    if(myAvatar.equals("{avatar=Jemi}")) {
//                        avatarDisplay.setImageResource(R.drawable.ic_monster_baby);
//                        TextView name = (TextView) findViewById(R.id.avatar_name);
//                        name.setText("Jemi");
//                    } else {
//                        avatarDisplay.setImageResource(R.drawable.ic_nerdy_monster_baby);
//                        TextView name = (TextView) findViewById(R.id.avatar_name);
//                        name.setText("Ronald");
//
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    //}
}
