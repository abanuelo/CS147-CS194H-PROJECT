package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
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
    private ImageButton noHat, yellowHat, pinkHat, blueHat, orangeHat;
    private ImageButton noShirt, greenShirt, pinkShirt, yellowShirt, brownShirt;
    Pair currHatShirt = new Pair(0, 0);

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
        noHat = findViewById(R.id.zero_item);
        yellowHat = findViewById(R.id.fourth_item);
        pinkHat = findViewById(R.id.third_item);
        blueHat = findViewById(R.id.first_item);
        orangeHat = findViewById(R.id.second_item);

        //Find the price tags for each hat
        noShirt = findViewById(R.id.zero_item_shirt);
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

        avatarDefaultLoad();

        //Set the hats to clickable

        noHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rewardType.setVisibility(View.GONE);
                description.setVisibility(View.GONE);
                buy.setVisibility(View.VISIBLE);
                buy.setText("Unequip hat");
                changeToDefault();
                top.setImageResource(R.drawable.undressedtoptrans);
                items="NoHat";
            }
        });

        yellowHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yellowHatText.getText().equals("")){
                    description.setText("Purchased item");
                    buy.setVisibility(View.VISIBLE);
                    buy.setText("Equip Hat");
                    rewardType.setVisibility(View.GONE);
                } else {
                    description.setText("Press 'Buy Item' to purchase item");
                    buy.setVisibility(View.VISIBLE);
                    buy.setText("Buy Item");
                    rewardType.setVisibility(View.VISIBLE);
                }
                description.setVisibility(View.VISIBLE);
                rewardType.setImageResource(R.drawable.money_bag);
                rewardType.getLayoutParams().height = 300;
                rewardType.getLayoutParams().width = 300;
                price = 20;
                items = "YellowHat";
                changeToDefault();
                //avatarDefault();
                top.setImageResource(R.drawable.yellowtoptrans);
            }
        });

        pinkHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pinkHatText.getText().equals("")){
                    description.setText("Purchased item");
                    buy.setVisibility(View.VISIBLE);
                    buy.setText("Equip Hat");
                    rewardType.setVisibility(View.GONE);
                } else {
                    description.setText("Press 'Buy Item' to purchase item");
                    buy.setVisibility(View.VISIBLE);
                    buy.setText("Buy Item");
                    rewardType.setVisibility(View.VISIBLE);
                }
                description.setVisibility(View.VISIBLE);
                rewardType.setImageResource(R.drawable.money_bag);
                rewardType.getLayoutParams().height = 300;
                rewardType.getLayoutParams().width = 300;
                price = 15;
                items = "PinkHat";
                changeToDefault();
                top.setImageResource(R.drawable.pinktoptrans);
            }
        });

        blueHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (blueHatText.getText().equals("")){
                    description.setText("Purchased item");
                    buy.setVisibility(View.VISIBLE);
                    buy.setText("Equip Hat");
                    rewardType.setVisibility(View.GONE);
                } else {
                    description.setText("Press 'Buy Item' to purchase item");
                    buy.setVisibility(View.VISIBLE);
                    buy.setText("Buy Item");
                    rewardType.setVisibility(View.VISIBLE);
                }
                description.setVisibility(View.VISIBLE);
                rewardType.setImageResource(R.drawable.money_bag);
                rewardType.getLayoutParams().height = 300;
                rewardType.getLayoutParams().width = 300;
                price = 5;
                items = "BlueHat";
                changeToDefault();
                top.setImageResource(R.drawable.bluetoptrans);
            }
        });

        orangeHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orangeHatText.getText().equals("")){
                    description.setText("Purchased item");
                    buy.setVisibility(View.VISIBLE);
                    buy.setText("Equip Hat");
                    rewardType.setVisibility(View.GONE);
                } else {
                    description.setText("Press 'Buy Item' to purchase item");
                    buy.setVisibility(View.VISIBLE);
                    buy.setText("Buy Item");
                    rewardType.setVisibility(View.VISIBLE);
                }
                description.setVisibility(View.VISIBLE);
                rewardType.setImageResource(R.drawable.money_bag);
                rewardType.getLayoutParams().height = 300;
                rewardType.getLayoutParams().width = 300;
                price = 10;
                items = "OrangeHat";
                changeToDefault();
                top.setImageResource(R.drawable.orangetoptrans);
            }
        });

        //Set the shirts to clickable
        noShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rewardType.setVisibility(View.GONE);
                description.setVisibility(View.GONE);
                buy.setVisibility(View.VISIBLE);
                buy.setText("Unequip shirt");
                items = "NoShirt";
                changeToDefault();
                bottom.setImageResource(R.drawable.undressedbottomtrans);

            }
        });

        greenShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Some odd: " + FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("FirstTutorial"));

                /*if (FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("FirstTutorial") == null){
                    description.setText("Complete one practice session!");
                    buy.setVisibility(View.GONE);
                } else {
                    description.setText("Congrats! You completed one practice session!");
                    buy.setVisibility(View.VISIBLE);
                    buy.setText("Equip Shirt");

                }*/
                description.setVisibility(View.VISIBLE);
                rewardType.setVisibility(View.VISIBLE);
                rewardType.setImageResource(R.drawable.star_trophy);
                items = "GreenShirt";
                changeToDefault();
                checkFirstTutorialCompleted();

                //bottom.setImageResource(R.drawable.greenbottomtrans);

            }
        });

        pinkShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description.setVisibility(View.VISIBLE);
                rewardType.setVisibility(View.VISIBLE);
                changeToDefault();
                checkSecondTutorialCompleted();
            }
        });

        yellowShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description.setVisibility(View.VISIBLE);
                rewardType.setVisibility(View.VISIBLE);
                changeToDefault();
                checkThirdTutorialCompleted();
            }
        });

        brownShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description.setVisibility(View.VISIBLE);
                rewardType.setVisibility(View.VISIBLE);
                description.setText("Practice for a total of 3 hours!");
                rewardType.setImageResource(R.drawable.clock_trophy);
                items = "BrownShirt";
                changeToDefault();
                bottom.setImageResource(R.drawable.brownbottomtrans);
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
                if (buy.getText().equals("Buy Item")) {
                    if (Integer.parseInt(pifePoints) >= price) {
                        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Store");
                        HashMap map = new HashMap();
                        map.put(items, true);
                        matchDb.updateChildren(map);
                        DatabaseReference userCoinsData = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("xp");
                        userCoinsData.setValue(Integer.parseInt(pifePoints) - price);
                        pifePoints = Integer.toString(Integer.parseInt(pifePoints) - price);
                        userCoins.setText(pifePoints);

                        changePriceTag();
                        description.setText("Purchased item!");
                        buy.setVisibility(View.GONE);

                        Toast.makeText(Store.this, "Successfully bought!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Store.this, "Insufficient funds! Practice some more!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Where we equip and make changes to the DataSingleton/Firebase
                    int enumer = 0;
                    DataSingleton ds = DataSingleton.getInstance();
                    if (buy.getText().equals("Equip Hat") || buy.getText().equals("Unequip hat") ) {
                        System.out.print("Maybe: hats?");
                        //We got hats here
                        if (items.equals("YellowHat")) {
                            enumer = 4;
                        } else if (items.equals("BlueHat")) {
                            enumer = 1;
                        } else if (items.equals("OrangeHat")) {
                            enumer = 2;
                        } else if (items.equals("PinkHat")) {
                            enumer = 3;
                        }
                        changeHat(enumer);
                        ds.setAvatarClothes(new Pair(enumer, currHatShirt.second));
                    } else {
                        System.out.print("Maybe: shirts?");
                        if (items.equals("YellowShirt")) {
                            enumer = 3;
                        } else if (items.equals("GreenShirt")) {
                            enumer = 1;
                        } else if (items.equals("BrownShirt")) {
                            enumer = 4;
                        } else if (items.equals("PinkShirt")) {
                            enumer = 2;
                        }
                        changeShirt(enumer);
                        ds.setAvatarClothes(new Pair(currHatShirt.first, enumer));

                    }

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
                        buy.setVisibility(View.VISIBLE);
                        buy.setText("Equip Shirt");
                    } else {
                        description.setText("Comment on a total of three videos!");
                        buy.setVisibility(View.GONE);
                    }
                    rewardType.setImageResource(R.drawable.sun_trophy);
                    bottom.setImageResource(R.drawable.yellowbottomtrans);
                    items = "YellowShirt";
                } else {
                    buy.setVisibility(View.GONE);
                    description.setText("Comment on a total of three videos!");
                    rewardType.setImageResource(R.drawable.sun_trophy);
                    bottom.setImageResource(R.drawable.yellowbottomtrans);
                    items = "YellowShirt";
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
                    buy.setVisibility(View.VISIBLE);
                    buy.setText("Equip Shirt");
                } else {
                    description.setText("Make a total of five friends!");
                    buy.setVisibility(View.GONE);
                }
                rewardType.setImageResource(R.drawable.smiley_trophy);
                bottom.setImageResource(R.drawable.pinkbottomtrans);
                items = "PinkShirt";

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
                if (dataSnapshot.getValue() != null) {


                    System.out.println("Some odd reason..." + dataSnapshot.getValue().toString());
                    greenShirt.setImageResource(R.drawable.jemi_shirt_green);
                    description.setText("Congrats! You completed one practice session!");
                    buy.setVisibility(View.VISIBLE);
                    buy.setText("Equip Shirt");
                    //bottom.setImageResource(R.drawable.greenbottomtrans);
                    //rewardType.setImageResource(R.drawable.star_trophy);
                } else {
                    description.setText("Complete one practice session!");
                    buy.setVisibility(View.GONE);
                }
                System.out.println("Some odd weird reason...");

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

    private void avatarDefaultLoad(){
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference avatarDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("AvatarClothes");
        avatarDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    System.out.println("Datasnapshot:" + dataSnapshot.getValue().toString());

                    Long hatL = (Long) dataSnapshot.child("hat").getValue();
                    int hat = hatL.intValue();
                    System.out.println("What do we have here: hat OG: " + hat);
                    Long shirtL = (Long) dataSnapshot.child("shirt").getValue();
                    int shirt = shirtL.intValue();
                    System.out.println("What do we have here: shirt OG: " + shirt);

                    DataSingleton ds = DataSingleton.getInstance();
                    ds.setAvatarClothes(new Pair(hat, shirt));

                    currHatShirt = new Pair(hat, shirt);

                    changeHat(hat);
                    changeShirt(shirt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void changeToDefault() {
        changeHat((int) currHatShirt.first);
        changeShirt((int) currHatShirt.second);
    }

    private void changeHat(int hat){
        System.out.println("What do we have here: hat: " + hat);
        // Table of contents: 0 - no hat, 1 - blue, 2 - orange, 3 - pink, 4 - yellow
        ImageView hatImg = (ImageView) findViewById(R.id.avatarTop);
        Drawable myDraw = getResources().getDrawable(R.drawable.yellowtoptrans);

        if (hat == 0) {
            myDraw = getResources().getDrawable(R.drawable.undressedtoptrans);
        } else if (hat == 1) {
            myDraw = getResources().getDrawable(R.drawable.bluetoptrans);
        } else if (hat == 2) {
            myDraw = getResources().getDrawable(R.drawable.orangetoptrans);
        } else if (hat == 3) {
            myDraw = getResources().getDrawable(R.drawable.pinktoptrans);
        }
        hatImg.setImageDrawable(myDraw);
    }

    private void changeShirt(int shirt) {
        System.out.println("What do we have here: shirt: " + shirt);
        // Table of contents: 0 - no shirt, 1 - green, 2 - pink, 3 - yellow, 4 - brown
        ImageView shirtImg = (ImageView) findViewById(R.id.avatarBottom);

        Drawable myDraw = getResources().getDrawable(R.drawable.brownbottomtrans);

        if (shirt == 0) {
            myDraw = getResources().getDrawable(R.drawable.undressedbottomtrans);
        } else if (shirt == 1) {
            myDraw = getResources().getDrawable(R.drawable.greenbottomtrans);
        } else if (shirt == 2) {
            myDraw = getResources().getDrawable(R.drawable.pinkbottomtrans);
        } else if (shirt == 3) {
            myDraw = getResources().getDrawable(R.drawable.yellowbottomtrans);
        }
        shirtImg.setImageDrawable(myDraw);

    }
}



