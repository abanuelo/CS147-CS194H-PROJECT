package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Store extends AppCompatActivity {

    private TextView description, userCoins;
    private TextView blueHatText, orangeHatText, pinkHatText, yellowHatText;
    private ImageView top, bottom, rewardType;
    private String currentUserId, pifePoints, items;
    private int price;
    private boolean result;
    private Handler handler;
    private Button buy;
    private DatabaseReference userDb;
    private ImageButton yellowHat, pinkHat, blueHat, orangeHat;
    private ImageButton greenShirt, pinkShirt, yellowShirt, brownShirt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("dressed");
        handler = new Handler();

        //Button
        buy = findViewById(R.id.buy);

        //Current Coin Levels to Buy Objects
        userCoins = findViewById(R.id.user_coins);
        pifePoints = userCoins.getText().toString();

        //Description
        description = findViewById(R.id.rewardDescription);

        //Reward Type ImageView
        rewardType = findViewById(R.id.type_unlock);

        //Find the Avatar Portions to Change
        top = findViewById(R.id.avatarTop);
        bottom = findViewById(R.id.avatarBottom);

        //Find the hats in the XML File
        yellowHat = findViewById(R.id.fourth_item);
        pinkHat = findViewById(R.id.third_item);
        blueHat = findViewById(R.id.first_item);
        orangeHat = findViewById(R.id.second_item);

        //Find the price tags for each hat
        blueHatText = findViewById(R.id.first_item_text);
        orangeHatText = findViewById(R.id.second_item_text);
        pinkHatText = findViewById(R.id.third_item_text);
        yellowHatText = findViewById(R.id.fourth_item_text);

        //Method to update from firebase based on if you bought an item or not
        checkIfItemBought();

        //Find the shirts in the XML File
        greenShirt = findViewById(R.id.first_item_shirt);
        pinkShirt = findViewById(R.id.second_item_shirt);
        yellowShirt = findViewById(R.id.third_item_shirt);
        brownShirt = findViewById(R.id.fourth_item_shirt);

        checkFirstTutorialCompleted();
        checkSecondTutorialCompleted();
        checkThirdTutorialCompleted();

        //Set the hats to clickable
        yellowHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yellowHatText.getText().equals("")){
                    description.setText("Purchased item");
                    buy.setVisibility(View.GONE);
                } else {
                    description.setText("Press 'Buy Item' to purchase item");
                    buy.setVisibility(View.VISIBLE);
                }
                rewardType.setImageResource(R.drawable.money_bag);
                rewardType.getLayoutParams().height = 300;
                rewardType.getLayoutParams().width = 300;
                price = 20;
                items = "YellowHat";
                top.setImageResource(R.drawable.yellowtoptrans);
            }
        });

        pinkHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pinkHatText.getText().equals("")){
                    description.setText("Purchased item");
                    buy.setVisibility(View.GONE);
                } else {
                    description.setText("Press 'Buy Item' to purchase item");
                    buy.setVisibility(View.VISIBLE);
                }
                rewardType.setImageResource(R.drawable.money_bag);
                rewardType.getLayoutParams().height = 300;
                rewardType.getLayoutParams().width = 300;
                price = 15;
                items = "PinkHat";
                top.setImageResource(R.drawable.pinktoptrans);
            }
        });

        blueHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (blueHatText.getText().equals("")){
                    description.setText("Purchased item");
                    buy.setVisibility(View.GONE);
                } else {
                    description.setText("Press 'Buy Item' to purchase item");
                    buy.setVisibility(View.VISIBLE);
                }

                rewardType.setImageResource(R.drawable.money_bag);
                rewardType.getLayoutParams().height = 300;
                rewardType.getLayoutParams().width = 300;
                price = 5;
                items = "BlueHat";
                top.setImageResource(R.drawable.bluetoptrans);
            }
        });

        orangeHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orangeHatText.getText().equals("")){
                    description.setText("Purchased item");
                    buy.setVisibility(View.GONE);
                } else {
                    description.setText("Press 'Buy Item' to purchase item");
                    buy.setVisibility(View.VISIBLE);
                }

                rewardType.setImageResource(R.drawable.money_bag);
                rewardType.getLayoutParams().height = 300;
                rewardType.getLayoutParams().width = 300;
                price = 10;
                items = "OrangeHat";
                top.setImageResource(R.drawable.orangetoptrans);
            }
        });

        //Set the shirts to clickable
        greenShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("FirstTutorial").getKey() == null){
                    description.setText("Complete one practice session!");
                }
                else {
                    description.setText("Congrats! You completed one practice session!");

                }
                bottom.setImageResource(R.drawable.greenbottomtrans);
                rewardType.setImageResource(R.drawable.star_trophy);
                items = "GreenShirt";
                buy.setVisibility(View.GONE);

            }
        });

        pinkShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSecondTutorialCompleted();
            }
        });

        yellowShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkThirdTutorialCompleted();
            }
        });

        brownShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description.setText("Practice for a total of 3 hours!");
                rewardType.setImageResource(R.drawable.clock_trophy);
                bottom.setImageResource(R.drawable.brownbottomtrans);
                items = "BrownShirt";
                buy.setVisibility(View.GONE);
            }
        });

        //initializeFireBase();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationViewPerform);
        bottomNavigationView.setSelectedItemId(R.id.user_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                                                     @Override
                                                                     public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                                                                         switch (menuItem.getItemId()){
                                                                             case R.id.friends_nav:
                                                                                 Intent collab = new Intent(Store.this, CollabHiFi2.class);
                                                                                 finish();
                                                                                 startActivity(collab);
                                                                                 break;
                                                                             case R.id.practice_nav:
                                                                                 Intent practice_intent = new Intent(Store.this, ChooseRoutineActivity.class);
                                                                                 finish();
                                                                                 startActivity(practice_intent);
                                                                                 break;
                                                                             case R.id.perform_nav:
                                                                                 Intent perform_intent = new Intent(Store.this, MyPerform.class);
                                                                                 finish();
                                                                                 startActivity(perform_intent);
                                                                                 break;
                                                                             case R.id.user_nav:
                                                                                 Intent profile_intent = new Intent(Store.this, Profile.class);
                                                                                 finish();
                                                                                 startActivity(profile_intent);
                                                                                 break;
                                                                         }
                                                                         return true;
                                                                     }
                                                                 }
        );

        getUserPifePoints();

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(pifePoints) >= price){
                    DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Store");
                    HashMap map = new HashMap();
                    map.put(items, true);
                    matchDb.updateChildren(map);
                    DatabaseReference userCoinsData = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("xp");
                    userCoinsData.setValue(Integer.parseInt(pifePoints)-price);
                    pifePoints = Integer.toString(Integer.parseInt(pifePoints)-price);
                    userCoins.setText(pifePoints);

                    changePriceTag();
                    description.setText("Purchased item!");
                    buy.setVisibility(View.GONE);

                    Toast.makeText(Store.this, "Successfully bought!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Store.this,"Insufficient funds! Practice some more!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void checkIfItemBought(){
        final DatabaseReference itemDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Store");
        itemDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    String purchased_item = item.getKey();
                    items = purchased_item;
                    changePriceTag();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void changePriceTag(){
        if (items.equals("BlueHat")){
            blueHatText.setText("");
        } else if (items.equals("OrangeHat")){
            orangeHatText.setText("");
        } else if (items.equals("PinkHat")){
            pinkHatText.setText("");
        } else {
            yellowHatText.setText("");
        }
    }

    private void checkThirdTutorialCompleted(){
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("TutorialThree");
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    if (((Long) dataSnapshot.getValue()).intValue() >= 3){
                        yellowShirt.setImageResource(R.drawable.yellowshirttrans);
                        description.setText("Congrats! You have commented on a total of three videos!");
                    } else {
                        description.setText("Comment on a total of three videos!");
                    }
                    rewardType.setImageResource(R.drawable.sun_trophy);
                    bottom.setImageResource(R.drawable.yellowbottomtrans);
                    items = "YellowShirt";
                    buy.setVisibility(View.GONE);
                } else {
                    description.setText("Comment on a total of three videos!");
                    rewardType.setImageResource(R.drawable.sun_trophy);
                    bottom.setImageResource(R.drawable.yellowbottomtrans);
                    items = "YellowShirt";
                    buy.setVisibility(View.GONE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkSecondTutorialCompleted(){
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Collaborations").child("Matches");
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int num_friends = (int) dataSnapshot.getChildrenCount();
                if (num_friends >= 5){
                    pinkShirt.setImageResource(R.drawable.pinkshirttrans);
                    description.setText("Congrats! You have made at least 5 friends!");
                } else {
                    description.setText("Make a total of five friends!");
                }
                rewardType.setImageResource(R.drawable.smiley_trophy);
                bottom.setImageResource(R.drawable.pinkbottomtrans);
                items = "PinkShirt";
                buy.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkFirstTutorialCompleted(){
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("FirstTutorial");
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    greenShirt.setImageResource(R.drawable.jemi_shirt_green);
                    description.setText("Congrats! You completed one practice session!");
                    bottom.setImageResource(R.drawable.greenbottomtrans);
                    rewardType.setImageResource(R.drawable.star_trophy);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUserPifePoints() {
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("xp");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    pifePoints = dataSnapshot.getValue().toString().trim();
                    userCoins.setText(pifePoints);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    private void initializeFireBase(){
//        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Store");
//        HashMap map = new HashMap();
//        map.put("BlueHat", false);
//        map.put("OrangeHat", false);
//        map.put("PinkHat", false);
//        map.put("YellowHat", false);
//        map.put("GreenShirt", false);
//        map.put("PinkShirt", false);
//        map.put("YellowShirt", false);
//        map.put("BrownShirt", false);
//        matchDb.updateChildren(map);
//    }
}



