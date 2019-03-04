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

import java.util.HashMap;
import java.util.Map;

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
    private FirebaseAuth auth;
    private Map userGenreInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);

        //Initialize Card Views from the XML File
        proceed = findViewById(R.id.Proceed);
        bluesCard = findViewById(R.id.bluesCard);
        classicalCard = findViewById(R.id.classicalCard);
        countryCard = findViewById(R.id.countryCard);
        edmCard = findViewById(R.id.EDMCard);
        hiphopCard = findViewById(R.id.hiphopCard);
        jazzCard = findViewById(R.id.jazzCard);
        popCard = findViewById(R.id.popCard);
        rockCard = findViewById(R.id.rockCard);

        auth = FirebaseAuth.getInstance();

        //Create the HashMap that will supply data to Firebase Database
        userGenreInfo = new HashMap();
        initUserGenreInfo();

        bluesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the color is white, then we have not selected the cardView, otherwise we have
                String genre = "blues";
                ColorStateList stateList = bluesCard.getCardBackgroundColor();
                boolean result;

                if (stateList.getDefaultColor() == Color.WHITE){
                    bluesCard.setCardBackgroundColor(Color.LTGRAY);
                    result = true;
                } else {
                    bluesCard.setCardBackgroundColor(Color.WHITE);
                    result = false;
                }

                userGenreInfo.replace(genre, result);
            }
        });

        classicalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genre = "classical";
                ColorStateList stateList = classicalCard.getCardBackgroundColor();
                boolean result;

                if (stateList.getDefaultColor() == Color.WHITE){
                    classicalCard.setCardBackgroundColor(Color.LTGRAY);
                    result = true;
                } else {
                    classicalCard.setCardBackgroundColor(Color.WHITE);
                    result = false;
                }

                userGenreInfo.replace(genre, result);
            }
        });

        countryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genre = "country";
                ColorStateList stateList = countryCard.getCardBackgroundColor();
                boolean result;

                if (stateList.getDefaultColor() == Color.WHITE){
                    countryCard.setCardBackgroundColor(Color.LTGRAY);
                    result = true;
                } else {
                    countryCard.setCardBackgroundColor(Color.WHITE);
                    result = false;
                }

                userGenreInfo.replace(genre, result);
            }
        });

        edmCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genre = "edm";
                ColorStateList stateList = edmCard.getCardBackgroundColor();
                boolean result;

                if (stateList.getDefaultColor() == Color.WHITE){
                    edmCard.setCardBackgroundColor(Color.LTGRAY);
                    result = true;
                } else {
                    edmCard.setCardBackgroundColor(Color.WHITE);
                    result = false;
                }

                userGenreInfo.replace(genre, result);
            }
        });

        hiphopCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genre = "hiphop";
                ColorStateList stateList = hiphopCard.getCardBackgroundColor();
                boolean result;

                if (stateList.getDefaultColor() == Color.WHITE){
                    hiphopCard.setCardBackgroundColor(Color.LTGRAY);
                    result = true;
                } else {
                    hiphopCard.setCardBackgroundColor(Color.WHITE);
                    result = false;
                }

                userGenreInfo.replace(genre, result);
            }
        });

        jazzCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genre = "jazz";
                ColorStateList stateList = jazzCard.getCardBackgroundColor();
                boolean result;

                if (stateList.getDefaultColor() == Color.WHITE){
                    jazzCard.setCardBackgroundColor(Color.LTGRAY);
                    result = true;
                } else {
                    jazzCard.setCardBackgroundColor(Color.WHITE);
                    result = false;
                }

                userGenreInfo.replace(genre, result);
            }
        });

        popCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genre = "pop";
                ColorStateList stateList = popCard.getCardBackgroundColor();
                boolean result;

                if (stateList.getDefaultColor() == Color.WHITE){
                    popCard.setCardBackgroundColor(Color.LTGRAY);
                    result = true;
                } else {
                    popCard.setCardBackgroundColor(Color.WHITE);
                    result = false;
                }

                userGenreInfo.replace(genre, result);
            }
        });

        rockCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genre = "rock";
                ColorStateList stateList = rockCard.getCardBackgroundColor();
                boolean result;

                if (stateList.getDefaultColor() == Color.WHITE){
                    rockCard.setCardBackgroundColor(Color.LTGRAY);
                    result = true;
                } else {
                    rockCard.setCardBackgroundColor(Color.WHITE);
                    result = false;
                }

                userGenreInfo.replace(genre, result);
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((boolean) userGenreInfo.get("blues") || (boolean) userGenreInfo.get("classical") || (boolean) userGenreInfo.get("country") || (boolean) userGenreInfo.get("edm") || (boolean) userGenreInfo.get("hiphop") || (boolean) userGenreInfo.get("jazz")  || (boolean) userGenreInfo.get("pop") || (boolean) userGenreInfo.get("rock")){
                    Toast.makeText(Genres.this, "Please select at least one genre!", Toast.LENGTH_SHORT).show();
                } else {
                    registerGenres();
                    Intent proceed_intent = new Intent(Genres.this, SelectAvatar.class);
                    startActivity(proceed_intent);
                }
            }
        });

    }

    private void registerGenres(){
        String userId = auth.getCurrentUser().getUid();
        DatabaseReference currentUserDbGenres = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Genres");
        currentUserDbGenres.updateChildren(userGenreInfo);
    }

    private void initUserGenreInfo(){
        userGenreInfo.put("blues", false);
        userGenreInfo.put("classical", false);
        userGenreInfo.put("country", false);
        userGenreInfo.put("edm", false);
        userGenreInfo.put("hiphop", false);
        userGenreInfo.put("jazz", false);
        userGenreInfo.put("pop", false);
        userGenreInfo.put("rock", false);
    }
}
