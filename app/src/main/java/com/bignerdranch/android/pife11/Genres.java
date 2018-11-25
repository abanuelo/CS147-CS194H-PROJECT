package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Genres extends AppCompatActivity {
    private Button proceed;
    private CardView bluesCard;
    private CardView classicalCard;
    private CardView countryCard;
    private CardView edmCard;
    private CardView hiphopCard;
    private CardView jazzCard;
    private CardView popCard;
    private CardView rockCard;
    private String blues;
    private String classical;
    private String country;
    private String edm;
    private String hiphop;
    private String jazz;
    private String pop;
    private String rock;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);

        proceed = findViewById(R.id.Proceed);
        bluesCard = findViewById(R.id.bluesCard);
        classicalCard = findViewById(R.id.classicalCard);
        countryCard = findViewById(R.id.countryCard);
        edmCard = findViewById(R.id.EDMCard);
        hiphopCard = findViewById(R.id.hiphopCard);
        jazzCard = findViewById(R.id.jazzCard);
        popCard = findViewById(R.id.popCard);
        rockCard = findViewById(R.id.rockCard);

        blues = "False";
        classical = "False";
        country = "False";
        edm = "False";
        hiphop = "False";
        jazz = "False";
        pop = "False";
        rock = "False";

        auth = FirebaseAuth.getInstance();

        bluesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the color is white, then we have not selected the cardView, otherwise we have\
                ColorStateList stateList = bluesCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    bluesCard.setCardBackgroundColor(Color.BLUE);
                    blues = "True";
                } else {
                    bluesCard.setCardBackgroundColor(Color.WHITE);
                    blues = "False";
                }

            }
        });

        classicalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = classicalCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    classicalCard.setCardBackgroundColor(Color.BLUE);
                    classical = "True";
                } else {
                    classicalCard.setCardBackgroundColor(Color.WHITE);
                    classical = "False";
                }
            }
        });

        countryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = countryCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    countryCard.setCardBackgroundColor(Color.BLUE);
                    country = "True";
                } else {
                    countryCard.setCardBackgroundColor(Color.WHITE);
                    country = "False";
                }
            }
        });

        edmCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = edmCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    edmCard.setCardBackgroundColor(Color.BLUE);
                    edm = "True";
                } else {
                    edmCard.setCardBackgroundColor(Color.WHITE);
                    edm = "False";
                }
            }
        });

        hiphopCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = hiphopCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    hiphopCard.setCardBackgroundColor(Color.BLUE);
                    hiphop = "True";
                } else {
                    hiphopCard.setCardBackgroundColor(Color.WHITE);
                    hiphop = "False";
                }
            }
        });

        jazzCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = jazzCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    jazzCard.setCardBackgroundColor(Color.BLUE);
                    jazz = "True";
                } else {
                    jazzCard.setCardBackgroundColor(Color.WHITE);
                    jazz = "False";
                }
            }
        });

        popCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = popCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    popCard.setCardBackgroundColor(Color.BLUE);
                    pop = "True";
                } else {
                    popCard.setCardBackgroundColor(Color.WHITE);
                    pop = "False";
                }
            }
        });

        rockCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = rockCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    rockCard.setCardBackgroundColor(Color.BLUE);
                    rock = "True";
                } else {
                    rockCard.setCardBackgroundColor(Color.WHITE);
                    rock = "False";
                }
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (blues == "False" && classical == "False" && country == "False" && edm == "False" && hiphop == "False" && jazz == "False" && pop == "False" && rock == "False"){
                    Toast.makeText(Genres.this, "Please select at least one genre!", Toast.LENGTH_SHORT).show();
                } else {
                    UserGenres genres = new UserGenres(blues, classical, country, edm, hiphop, jazz, pop, rock);
                    String userId = auth.getCurrentUser().getUid();
                    DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Genres");
                    currentUserDb.setValue(genres);
                    Intent proceed_intent = new Intent(Genres.this, Dashboard.class);
                    startActivity(proceed_intent);
                }
            }
        });

    }
}
