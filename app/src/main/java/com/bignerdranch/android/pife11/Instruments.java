package com.bignerdranch.android.pife11;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

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
    private FirebaseAuth auth;
    private Map userInstrumentInfo;
    private Map userYearsInstrumentPlayedInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruments);

        //Extracts all the Card Views from the Instruments XML file
        proceed = (Button) findViewById(R.id.proceed);
        bassCard = (CardView) findViewById(R.id.cardView0);
        drumCard = (CardView) findViewById(R.id.cardView1);
        fluteCard = (CardView) findViewById(R.id.cardView2);
        guitarCard = (CardView) findViewById(R.id.cardView3);
        pianoCard = (CardView) findViewById(R.id.cardView4);
        singCard = (CardView) findViewById(R.id.cardView5);
        violaCard = (CardView) findViewById(R.id.cardView6);
        violinCard = (CardView) findViewById(R.id.cardView7);

        //Create the Firebase Authentification object
        auth = FirebaseAuth.getInstance();

        //Declares the Maps that are going to hold Information regarding Instrument use
        userInstrumentInfo = new HashMap();
        userYearsInstrumentPlayedInfo = new HashMap();

        //Initializes the Maps for Future Calls
        initUserInstrumentInfo();

        //Functionality when the bassCard is selected
        bassCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the color is white, then we have not selected the cardView, otherwise we have
                String instrument = "bass";
                ColorStateList stateList = bassCard.getCardBackgroundColor();
                boolean result = false;

                if (stateList.getDefaultColor() == Color.WHITE){
                    bassCard.setCardBackgroundColor(Color.LTGRAY);
                    result = true;
                    yearsPlayedInstrument(instrument);
                } else {
                    bassCard.setCardBackgroundColor(Color.WHITE);
                    result = false;
                }
                updateIntrumentInfo(instrument, result);
            }
        });

        drumCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String instrument = "drum";
                ColorStateList stateList = drumCard.getCardBackgroundColor();
                boolean result = false;

                if (stateList.getDefaultColor() == Color.WHITE){
                    drumCard.setCardBackgroundColor(Color.LTGRAY);
                    result = true;
                    yearsPlayedInstrument(instrument);
                } else {
                    drumCard.setCardBackgroundColor(Color.WHITE);
                    result = false;
                }
                updateIntrumentInfo(instrument, result);
            }
        });

        fluteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String instrument = "flute";
                ColorStateList stateList = fluteCard.getCardBackgroundColor();
                boolean result = false;

                if (stateList.getDefaultColor() == Color.WHITE){
                    fluteCard.setCardBackgroundColor(Color.LTGRAY);
                    result = true;
                    yearsPlayedInstrument(instrument);
                } else {
                    fluteCard.setCardBackgroundColor(Color.WHITE);
                    result = false;
                }
                updateIntrumentInfo(instrument, result);
            }
        });

        guitarCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String instrument = "guitar";
                ColorStateList stateList = guitarCard.getCardBackgroundColor();
                boolean result = false;

                if (stateList.getDefaultColor() == Color.WHITE){
                    guitarCard.setCardBackgroundColor(Color.LTGRAY);
                    result = true;
                    yearsPlayedInstrument(instrument);
                } else {
                    guitarCard.setCardBackgroundColor(Color.WHITE);
                    result = false;
                }
                updateIntrumentInfo(instrument, result);
            }
        });

        pianoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String instrument = "piano";
                ColorStateList stateList = pianoCard.getCardBackgroundColor();
                boolean result = false;

                if (stateList.getDefaultColor() == Color.WHITE){
                    pianoCard.setCardBackgroundColor(Color.LTGRAY);
                    result = true;
                    yearsPlayedInstrument(instrument);
                } else {
                    pianoCard.setCardBackgroundColor(Color.WHITE);
                    result = false;
                }
                updateIntrumentInfo(instrument, result);
            }
        });

        singCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String instrument = "sing";
                ColorStateList stateList = singCard.getCardBackgroundColor();
                boolean result = false;

                if (stateList.getDefaultColor() == Color.WHITE){
                    singCard.setCardBackgroundColor(Color.LTGRAY);
                    result = true;
                    yearsPlayedInstrument(instrument);
                } else {
                    singCard.setCardBackgroundColor(Color.WHITE);
                    result = false;
                }
                updateIntrumentInfo(instrument, result);
            }
        });

        violaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String instrument = "viola";
                ColorStateList stateList = violaCard.getCardBackgroundColor();
                boolean result = false;

                if (stateList.getDefaultColor() == Color.WHITE){
                    violaCard.setCardBackgroundColor(Color.LTGRAY);
                    result = true;
                    yearsPlayedInstrument(instrument);
                } else {
                    violaCard.setCardBackgroundColor(Color.WHITE);
                    result = false;
                }
                updateIntrumentInfo(instrument, result);
            }
        });

        violinCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String instrument = "violin";
                ColorStateList stateList = violinCard.getCardBackgroundColor();
                boolean result = false;

                if (stateList.getDefaultColor() == Color.WHITE){
                    violinCard.setCardBackgroundColor(Color.LTGRAY);
                    result = true;
                    yearsPlayedInstrument(instrument);
                } else {
                    violinCard.setCardBackgroundColor(Color.WHITE);
                    result = false;
                }
                updateIntrumentInfo(instrument, result);
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //In the case the user has not selected any instruments
                if ((boolean) userInstrumentInfo.get("bass") && (boolean) userInstrumentInfo.get("drum") && (boolean) userInstrumentInfo.get("flute") && (boolean) userInstrumentInfo.get("guitar") && (boolean) userInstrumentInfo.get("piano") && (boolean) userInstrumentInfo.get("sing") && (boolean) userInstrumentInfo.get("viola") && (boolean) userInstrumentInfo.get("violin")){
                    Toast.makeText(Instruments.this, "Please select at least one instrument", Toast.LENGTH_SHORT).show();
                } else {
                    registerInstruments();
                    Intent proceed_intent = new Intent(Instruments.this, Genres.class);
                    startActivity(proceed_intent);
                }
            }
        });

    }

    //Responsible for selecting each of the CardViews in order to gain access to an aspect of Instruments
    private void yearsPlayedInstrument(final String instrument){
        View pop = (LayoutInflater.from(Instruments.this)).inflate(R.layout.activity_pop, null);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Instruments.this);
        alertBuilder.setView(pop);
        final EditText userInput = pop.findViewById(R.id.userinput);

        alertBuilder.setCancelable(true)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int years_played = Integer.parseInt(userInput.getText().toString().trim());
                        //Checks to see if we the entry already exists in the hashmap
                        userYearsInstrumentPlayedInfo.put(instrument, years_played);
                    }
                });
        Dialog dialog = alertBuilder.create();
        dialog.show();
    }

    //Updating the instrument map which state whether a user plays an instrument or not
    private void updateIntrumentInfo(String instrument, boolean result){
        userInstrumentInfo.replace(instrument, result);
        if(userYearsInstrumentPlayedInfo.get(instrument)!=null && result == false){
            userYearsInstrumentPlayedInfo.remove(instrument);
        }
    }

    //initalizes the database for future calls
    private void initUserInstrumentInfo(){
        userInstrumentInfo.put("bass", false);
        userInstrumentInfo.put("drum", false);
        userInstrumentInfo.put("flute", false);
        userInstrumentInfo.put("guitar", false);
        userInstrumentInfo.put("piano", false);
        userInstrumentInfo.put("sing", false);
        userInstrumentInfo.put("viola", false);
        userInstrumentInfo.put("violin", false);
    }

    private void registerInstruments(){
        String userId = auth.getCurrentUser().getUid();
        DatabaseReference currentUserDbInstruments = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Instruments");
        currentUserDbInstruments.updateChildren(userInstrumentInfo);
        DatabaseReference currentUserDbYearsInstruments = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Years");
        currentUserDbYearsInstruments.updateChildren(userYearsInstrumentPlayedInfo);
    }
}
