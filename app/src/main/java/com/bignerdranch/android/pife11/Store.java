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

    private static int SCROLLCOUNT = 217;
    private static int SCROLLCOUNTSHIRT = 223;

    private HorizontalScrollView hatScrollView, shirtScrollView;
    private TextView description, userCoins, itemDescription, shirtItemDescription;
    private TextView blueHatText, orangeHatText, pinkHatText, yellowHatText;
    private ImageView top, bottom, rewardType, leftArrowHat, rightArrowHat, leftArrowShirt, rightArrowShirt, hatsInDescription, shirtsInDescription;
    private String currentUserId, pifePoints, items;
    private int price, currXHat, currXShirt, currItem;
    private boolean result;
    private Handler handler;
    private Button buy, purchaseItem;
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

        //Importing the side buttons for functionality of the store
        leftArrowHat = findViewById(R.id.left_arrow_hat);
        rightArrowHat = findViewById(R.id.right_arrow_hat);
        leftArrowShirt = findViewById(R.id.left_arrow_shirt);
        rightArrowShirt = findViewById(R.id.right_arrow_shirt);

        //Import the Hat Image in the Description Section of the Store
        hatsInDescription = findViewById(R.id.hat_description);
        shirtsInDescription = findViewById(R.id.shirt_description);
        itemDescription = findViewById(R.id.description);
        shirtItemDescription = findViewById(R.id.shirt_des);
        purchaseItem = findViewById(R.id.itemPurchaseButton);
        currItem = 0;
        checkIfItemBought(); //autopopulates the view for the first time;
        checkFirstTutorialCompleted(); //autopopulates the view for the first time;

        //Importing the horizontal scroll views
        hatScrollView = findViewById(R.id.hatScrollView);
        shirtScrollView = findViewById(R.id.shirtScrollView);

        //This will implement functionality when trying to iterate through the horizontal scroll view
        //As a test we will first be attempting to move around the horizontal scroll view via rightArrow for Hats
        rightArrowHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hatScrollView.scrollTo(currXHat + SCROLLCOUNT, 0);
                if(currXHat < SCROLLCOUNT*4){
                    currXHat += SCROLLCOUNT;
                }

                //This next part will implement the fact that after you click the right arrow to look at another item
                //Jemi's attire will chage
                changeJemisHat();

            }
        });

        //testing to return back to previous item using similar code to above
        leftArrowHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hatScrollView.scrollTo(currXHat - SCROLLCOUNT, 0);
                if (currXHat > 0) {
                    currXHat -= SCROLLCOUNT;
                }

                changeJemisHat();
            }
        });

        //We will now begin the implementation of the right and left arrows for the shirt in our Store
        rightArrowShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shirtScrollView.scrollTo(currXShirt + SCROLLCOUNTSHIRT, 0);
                if (currXShirt < SCROLLCOUNTSHIRT*4){
                    currXShirt += SCROLLCOUNTSHIRT;
                }

                changeJemisShirt();
            }
        });

        leftArrowShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shirtScrollView.scrollTo(currXShirt - SCROLLCOUNTSHIRT, 0);
                if (currXShirt > 0){
                    currXShirt -= SCROLLCOUNTSHIRT;
                }

                changeJemisShirt();
            }
        });


        //Current Coin Levels to Buy Objects
        userCoins = findViewById(R.id.user_coins);
        pifePoints = userCoins.getText().toString();


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
        //checkIfItemBought();

        //Find the shirts in the XML File
        greenShirt = findViewById(R.id.first_item_shirt);
        pinkShirt = findViewById(R.id.second_item_shirt);
        yellowShirt = findViewById(R.id.third_item_shirt);
        brownShirt = findViewById(R.id.fourth_item_shirt);


        avatarDefaultLoad();
        changeToDefault();



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

//        buy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (buy.getText().equals("Buy Item")) {
//                    if (Integer.parseInt(pifePoints) >= price) {
//                        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Store");
//                        HashMap map = new HashMap();
//                        map.put(items, true);
//                        matchDb.updateChildren(map);
//                        DatabaseReference userCoinsData = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("xp");
//                        userCoinsData.setValue(Integer.parseInt(pifePoints) - price);
//                        pifePoints = Integer.toString(Integer.parseInt(pifePoints) - price);
//                        userCoins.setText(pifePoints);
//
//                        changePriceTag();
//                        description.setText("Purchased item!");
//                        buy.setVisibility(View.GONE);
//
//                        Toast.makeText(Store.this, "Successfully bought!", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(Store.this, "Insufficient funds! Practice some more!", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    //Where we equip and make changes to the DataSingleton/Firebase
//                    int enumer = 0;
//                    DataSingleton ds = DataSingleton.getInstance();
//                    if (buy.getText().equals("Equip Hat") || buy.getText().equals("Unequip hat") ) {
//                        System.out.println("Arrived at Buying Hats");
//
//                        //We got hats here
//                        if (items.equals("YellowHat")) {
//                            enumer = 4;
//                        } else if (items.equals("BlueHat")) {
//                            enumer = 1;
//                        } else if (items.equals("OrangeHat")) {
//                            enumer = 2;
//                        } else if (items.equals("PinkHat")) {
//                            enumer = 3;
//                        }
//                        changeHat(enumer);
//                        System.out.println("We currently have " + Integer.toString(enumer) + " as hat num.");
//                        System.out.println("Output is " + currHatShirt.second);
//                        String tuple = currHatShirt.toString();
//                        System.out.println(tuple);
//                        boolean startExtraction = false;
//                        String result = "";
//                        for (int i = 0; i < tuple.length(); i++){
//                            char c = tuple.charAt(i);
//                            if (c == '}'){
//                                break;
//                            }
//                            if (c == ' '){
//                                startExtraction = true;
//                            } else {
//                                if (startExtraction){
//                                    result += c;
//                                }
//                            }
//                        }
//                        int shirtIndex = Integer.parseInt(result);
//                        currHatShirt = new Pair(enumer, shirtIndex);
//                        ds.setAvatarClothes(new Pair(enumer, shirtIndex));
//                    } else {
//                        System.out.println("Arriving at Buying Shirts");
//                        if (items.equals("YellowShirt")) {
//                            enumer = 3;
//                        } else if (items.equals("GreenShirt")) {
//                            enumer = 1;
//                        } else if (items.equals("BrownShirt")) {
//                            enumer = 4;
//                        } else if (items.equals("PinkShirt")) {
//                            enumer = 2;
//                        }
//                        changeShirt(enumer);
//                        String tuple = currHatShirt.toString();
//                        System.out.println(tuple);
//                        boolean startExtraction = false;
//                        String result = "";
//                        for (int i = 0; i < tuple.length(); i++){
//                            char c = tuple.charAt(i);
//                            if (c == ' '){
//                                break;
//                            }
//                            if (c == '{'){
//                                startExtraction = true;
//                            } else {
//                                if (startExtraction){
//                                    result += c;
//                                }
//                            }
//                        }
//                        int hatIndex = Integer.parseInt(result);
//                        currHatShirt = new Pair(hatIndex, enumer);
//                        ds.setAvatarClothes(new Pair(hatIndex, enumer));
//
//                    }
//                    Toast.makeText(getApplicationContext(), "Clothing change successful!", Toast.LENGTH_SHORT).show();
//                    buy.setVisibility(View.GONE);
//
//
//                }
//            }
//        });

    }


    private void checkIfItemBought(){
        final DatabaseReference itemDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Store");
        itemDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean updatedTextField = false;
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    String purchased_item = item.getKey();
                    items = purchased_item;
                    int item_index = getCurrItemIndex();

                    //Special case when you scroll to the view where Jemi has no shirt or hat
                    if(currItem == 4 && updatedTextField == false){
                        itemDescription.setText("Be naked!");
                        purchaseItem.setVisibility(View.GONE);
                        updatedTextField = true;
                    }

                    if (currItem == item_index && updatedTextField == false){
                        itemDescription.setText("Item purchased!");
                        purchaseItem.setVisibility(View.GONE);
                        updatedTextField = true;
                    }
                }
                if (updatedTextField == false){
                    itemDescription.setText("Buy item?");
                    purchaseItem.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //This method will change Jemis Shirt Across All parts of the screen
    private void changeJemisShirt(){
        if(currXShirt == SCROLLCOUNTSHIRT*0){
            bottom.setImageResource(R.drawable.greenbottomtrans);
            checkFirstTutorialCompleted();
        } else if (currXShirt == SCROLLCOUNTSHIRT*1){
            bottom.setImageResource(R.drawable.pinkbottomtrans);
            checkSecondTutorialCompleted();
        } else if (currXShirt == SCROLLCOUNTSHIRT*2){
            bottom.setImageResource(R.drawable.yellowbottomtrans);
            checkThirdTutorialCompleted();
        } else if (currXShirt == SCROLLCOUNTSHIRT*3){
            bottom.setImageResource(R.drawable.brownbottomtrans);
            shirtsInDescription.setImageResource(R.drawable.brownshirtlockedtrans);
            shirtItemDescription.setText("You must practice for 2 hours to unlock this item");
        } else {
            bottom.setImageResource(R.drawable.undressedbottomtrans);
            shirtsInDescription.setImageResource(R.drawable.none_icon);
            shirtItemDescription.setText("Be naked!");
        }
    }

    //This will change Jemis Hat Across All Parts of the Screen
    private void changeJemisHat(){
        if (currXHat == SCROLLCOUNT*0){
            top.setImageResource(R.drawable.bluetoptrans);
            hatsInDescription.setImageResource(R.drawable.jemi_tiny_blue_hat);
            currItem = 0;
        } else if (currXHat == SCROLLCOUNT*1){
            top.setImageResource(R.drawable.orangetoptrans);
            hatsInDescription.setImageResource(R.drawable.jemi_tiny_orange_hat);
            currItem = 1;
        } else if (currXHat == SCROLLCOUNT*2){
            top.setImageResource(R.drawable.pinktoptrans);
            hatsInDescription.setImageResource(R.drawable.pinkhattrans);
            currItem = 2;
        } else if (currXHat == SCROLLCOUNT*3){
            top.setImageResource(R.drawable.yellowtoptrans);
            hatsInDescription.setImageResource(R.drawable.yellowhattrans);
            currItem = 3;
        } else {
            top.setImageResource(R.drawable.undressedtoptrans);
            hatsInDescription.setImageResource(R.drawable.none_icon);
            currItem = 4;
        }
        checkIfItemBought();
    }

    private int getCurrItemIndex(){
        int item_index = 0;
        if (items.equals("BlueHat")){
            item_index = 0;
        } else if (items.equals("OrangeHat")){
           item_index = 1;
        } else if (items.equals("PinkHat")){
            item_index = 2;
        } else if (items.equals("YellowHat")){
           item_index = 3;
        } else {
            item_index = 4;
        }
        return item_index;
    }

    private void checkThirdTutorialCompleted(){
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("TutorialThree");
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    if (((Long) dataSnapshot.getValue()).intValue() >= 3){
                        shirtItemDescription.setText("Congrats! You have commented on a total of three videos!");
                        yellowShirt.setImageResource(R.drawable.yellowshirttrans);
                        shirtsInDescription.setImageResource(R.drawable.yellowshirttrans);
                    } else {
                        shirtItemDescription.setText("Comment on a total of three videos!");
                        yellowShirt.setImageResource(R.drawable.yellowshirtlockedtrans);
                        shirtsInDescription.setImageResource(R.drawable.yellowshirtlockedtrans);
                    }

                } else {
                    shirtItemDescription.setText("Comment on a total of three videos!");
                    yellowShirt.setImageResource(R.drawable.yellowshirtlockedtrans);
                    shirtsInDescription.setImageResource(R.drawable.yellowshirtlockedtrans);
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
                    shirtItemDescription.setText("Congrats! You have made at least 5 friends!");
                    pinkShirt.setImageResource(R.drawable.pinkshirttrans);
                    shirtsInDescription.setImageResource(R.drawable.pinkshirttrans);
                } else {
                    shirtItemDescription.setText("Make a total of five friends!");
                    pinkShirt.setImageResource(R.drawable.pinkshirtlockedtrans);
                    shirtsInDescription.setImageResource(R.drawable.pinkshirtlockedtrans);
                }

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
                    shirtsInDescription.setImageResource(R.drawable.jemi_shirt_green);
                    shirtItemDescription.setText("Congrats! You completed one practice session!");

                } else {
                    greenShirt.setImageResource(R.drawable.greenshirtlockedtrans);
                    shirtItemDescription.setText("Complete one practice session!");
                    shirtsInDescription.setImageResource(R.drawable.greenshirtlockedtrans);
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



