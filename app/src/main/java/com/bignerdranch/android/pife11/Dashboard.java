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
import android.widget.TextClock;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieImageAsset;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    //private LottieAnimationView animation;
    //private String myAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //getUserAvatar();

        //animation = (LottieAnimationView) findViewById(R.id.avatarDisplay);
        //animation.setImageAssetsFolder("images");
        //animation.setAnimation("jemi_happy_homescreen.xml");
        //animation.playAnimation();

        //Adding Gifs into the Code Content
        //GifDrawable drawable = new GifDrawable(Bitmap.Config.R.drawable.jemi_happy);
        animation = (GifImageView) findViewById(R.id.animation);
        animation.setImageResource(R.drawable.jemi_happy);


        animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.setImageResource(R.drawable.jemi_happy);
            }
        });


        bPractice = findViewById(R.id.practice);
        bCollab = findViewById(R.id.collab);
        userProfile = findViewById(R.id.user_profile);
        rewardShop = findViewById(R.id.reward);


        bPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent practice_intent = new Intent(Dashboard.this, PracticePlaying.class);
                startActivity(practice_intent);
            }
        });

        bCollab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
