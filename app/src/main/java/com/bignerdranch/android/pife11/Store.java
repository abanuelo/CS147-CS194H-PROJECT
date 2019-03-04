package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

public class Store extends AppCompatActivity {

    private TextView description, userCoins;
    private ImageView top, bottom, rewardType;
    private String currentUserId, pifePoints;
    private Handler handler;
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

        //Find the shirts in the XML File
        greenShirt = findViewById(R.id.first_item_shirt);
        pinkShirt = findViewById(R.id.second_item_shirt);
        yellowShirt = findViewById(R.id.third_item_shirt);
        brownShirt = findViewById(R.id.fourth_item_shirt);

        //Set the hats to clickable
        yellowHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                top.setImageResource(R.drawable.yellowtoptrans);
            }
        });

        pinkHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                top.setImageResource(R.drawable.pinktoptrans);
            }
        });

        blueHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                top.setImageResource(R.drawable.bluetoptrans);
            }
        });

        orangeHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                top.setImageResource(R.drawable.orangetoptrans);
            }
        });

        //Set the shirts to clickable
        greenShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottom.setImageResource(R.drawable.greenbottomtrans);
            }
        });

        pinkShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottom.setImageResource(R.drawable.pinkbottomtrans);
            }
        });

        yellowShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottom.setImageResource(R.drawable.yellowbottomtrans);
            }
        });

        brownShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottom.setImageResource(R.drawable.brownbottomtrans);
            }
        });

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
}

//        pifePointsLocation = findViewById(R.id.pifepoints);

//        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("xp");
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//                    pifePointsLocation.setText(dataSnapshot.getValue().toString());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//
//        //gets the hat and shirt
//        hat = findViewById(R.id.button2);
////        shirt = findViewById(R.id.button4);
//
//        hat_placement = findViewById(R.id.hat);
//        shirt_placement = findViewById(R.id.shirt);
//
//        //Gets the button for the constraints
//        constraint = findViewById(R.id.shapeLayout);
//        scrollViewHats = findViewById(R.id.horizontalscrollview);
//        scrollViewHats2 = findViewById(R.id.horizontalscrollview2);
//        button1 = findViewById(R.id.button1);
//        button2 = findViewById(R.id.button2);
//        button3 = findViewById(R.id.button3);
////        button4 = findViewById(R.id.button4);
//
//        //Sets the visibility true if we are fully dressed
//
//        userDb.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    String value = dataSnapshot.getValue().toString().trim();
//                    if (value.equals("true")) {
//                        hat.setVisibility(View.VISIBLE);
////                        shirt.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        scrollViewHats.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        constrainedX = (int) scrollViewHats.getScrollX();
//                        constrainedY = (int) scrollViewHats.getScrollY();
//
//                        button1X = (int) button1.getX();
//                        button1Y = (int) button1.getY();
//
//                        button2X = (int) button2.getX();
//                        button2Y = (int) button2.getY();
//
//                        if (Math.abs(constrainedX - button1X) < Math.abs(constrainedX - button2X) && Math.abs(constrainedX - button1X) < Math.abs(constrainedX - button3X)) {
//                            scrollViewHats.smoothScrollTo(button1X, button1Y);
//                            hat_placement.setImageResource(0);
//                            //CHANGE THE PRICE TAG HERE
//                        } else if (Math.abs(constrainedX - button2X) < Math.abs(constrainedX - button1X) && Math.abs(constrainedX - button2X) < Math.abs(constrainedX - button3X)) {
//                            scrollViewHats.smoothScrollTo(button2X, button2Y);
//
//                            hat_placement.setImageResource(R.drawable.ic_jemi_orange_hat);
//
//                        } else {
//                            hat_placement.setImageResource(0);
//                        }
//                    }
//                }, 600);
//
//            }
//        });
//
//
//        scrollViewHats2.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        constrainedX = (int) scrollViewHats2.getScrollX();
//                        constrainedY = (int) scrollViewHats2.getScrollY();
//
//                        button1X = (int) button3.getX();
//                        button1Y = (int) button3.getY();
//
////                        button2X = (int) button4.getX();
////                        button2Y = (int) button4.getY();
//
//                        TextView description = (TextView) findViewById(R.id.accessDescription);
//
//                        if (Math.abs(constrainedX - button1X) < Math.abs(constrainedX - button2X) && Math.abs(constrainedX - button1X) < Math.abs(constrainedX - button3X)) {
//                            //This is for the orange shirt
//                            scrollViewHats2.smoothScrollTo(button1X, button1Y);
//                            shirt_placement.setImageResource(0);
//
//                            System.out.println("WTF is this?");
//
//                            description.setText("Proof of practicing for 1 hour on Pife.");
//
//                            //CHANGE THE PRICE TAG HERE
//                        } else if (Math.abs(constrainedX - button2X) < Math.abs(constrainedX - button1X) && Math.abs(constrainedX - button2X) < Math.abs(constrainedX - button3X)) {
//                            //This is for the tutorial shirt!
//
//                            scrollViewHats2.smoothScrollTo(button2X, button2Y);
//                            shirt_placement.setImageResource(R.drawable.ic_jemi_green_shirt);
//                            System.out.println("WTF is this? 2");
//
//                            description.setText("Proof of practicing for 15 seconds on Pife.");
//
//                        } else {
//
//                            // THis is the no shirt sign
//                            shirt_placement.setImageResource(0);
//                            System.out.println("WTF is this? 3");
//                        }
//                    }
//                }, 600);
//
//            }
//        });
//
//    }
//
//
//    public void buyHats(View view) {
//        Toast.makeText(Store.this, "Sorry, this accessory hasn't been unlocked. Please purchase the green tutorial T-shirt.", Toast.LENGTH_LONG).show();
//    }
//
//    public void buyShirt(View view){
//        TextView pointsDisplay = (TextView) findViewById(R.id.pifepoints);
//        int coinsAvailable = Integer.parseInt(pointsDisplay.getText().toString());
//
//        if (Math.abs(constrainedX - button2X) < Math.abs(constrainedX - button1X) && Math.abs(constrainedX - button2X) < Math.abs(constrainedX - button3X)) {
//            if (coinsAvailable >= 15) {
//                DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("xp");
//                matchDb.setValue(0);
//                Toast.makeText(Store.this, "Successfully obtained tutorial T-shirt from store!", Toast.LENGTH_LONG).show();
//            }
//            Intent practice_intent = new Intent(this, Profile.class);
//            startActivity(practice_intent);
//        } else if (Math.abs(constrainedX - button1X) < Math.abs(constrainedX - button2X) && Math.abs(constrainedX - button1X) < Math.abs(constrainedX - button3X)) {
//            Toast.makeText(Store.this, "Sorry, this accessory hasn't been unlocked. Please purchase the green tutorial T-shirt.", Toast.LENGTH_LONG).show();
//        }
//
//
//    }
//
//
//    private void getUserAvatar() {
//        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Avatar");
//        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                if (dataSnapshot.exists()){
////                    myAvatar = dataSnapshot.getValue().toString().trim();
////                    ImageView avatarDisplay = (ImageView) findViewById(R.id.avatarMyRewards);
////                    if(myAvatar.equals("{avatar=Jemi}")) {
////                        avatarDisplay.setImageResource(R.drawable.ic_monster_baby);
////                    } else if (myAvatar.equals("toddler")) {
////                        //always make this the monster baby
////                        avatarDisplay.setImageResource(R.drawable.ic_jemi_toddler_arms_down);
////                    } else if (myAvatar.equals("dressed")){
////                        avatarDisplay.setImageResource(R.drawable.ic_jemi_toddler_arms_down);
////                    } else {
////                        avatarDisplay.setImageResource(R.drawable.ic_monster_baby);
////                    }
////
////                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//    }
//
//

