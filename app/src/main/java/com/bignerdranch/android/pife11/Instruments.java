package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

public class Instruments extends AppCompatActivity {
    private Button proceed;
    private CardView bassCard;
    private CardView drumCard;
    private CardView fluteCard;
    private CardView guitarCard;
    private CardView pianoCard;
    private CardView singCard;
    private CardView violaCard;
    private CardView violinCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruments);

        proceed = findViewById(R.id.proceed);
        bassCard = findViewById(R.id.cardView0);
        drumCard = findViewById(R.id.cardView1);
        fluteCard = findViewById(R.id.cardView2);
        guitarCard = findViewById(R.id.cardView3);
        pianoCard = findViewById(R.id.cardView4);
        singCard = findViewById(R.id.cardView5);
        violaCard = findViewById(R.id.cardView6);
        violinCard = findViewById(R.id.cardView7);

        bassCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the color is white, then we have not selected the cardView, otherwise we have\
                ColorStateList stateList = bassCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    bassCard.setCardBackgroundColor(Color.BLUE);
                } else {
                    bassCard.setCardBackgroundColor(Color.WHITE);
                }

            }
        });

        drumCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = drumCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    drumCard.setCardBackgroundColor(Color.BLUE);
                } else {
                    drumCard.setCardBackgroundColor(Color.WHITE);
                }
            }
        });

        fluteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = fluteCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    fluteCard.setCardBackgroundColor(Color.BLUE);
                } else {
                    fluteCard.setCardBackgroundColor(Color.WHITE);
                }
            }
        });

        guitarCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = guitarCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    guitarCard.setCardBackgroundColor(Color.BLUE);
                } else {
                    guitarCard.setCardBackgroundColor(Color.WHITE);
                }
            }
        });

        pianoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = pianoCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    pianoCard.setCardBackgroundColor(Color.BLUE);
                } else {
                    pianoCard.setCardBackgroundColor(Color.WHITE);
                }
            }
        });

        singCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = singCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    singCard.setCardBackgroundColor(Color.BLUE);
                } else {
                    singCard.setCardBackgroundColor(Color.WHITE);
                }
            }
        });

        violaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = violaCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    violaCard.setCardBackgroundColor(Color.BLUE);
                } else {
                    violaCard.setCardBackgroundColor(Color.WHITE);
                }
            }
        });

        violinCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = violinCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    violinCard.setCardBackgroundColor(Color.BLUE);
                } else {
                    violinCard.setCardBackgroundColor(Color.WHITE);
                }
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent proceed_intent = new Intent(Instruments.this, Genres.class);
                startActivity(proceed_intent);
            }
        });


    }
}
