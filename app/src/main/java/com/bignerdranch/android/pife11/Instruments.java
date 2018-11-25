package com.bignerdranch.android.pife11;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
    private String bass;
    private String drum;
    private String flute;
    private String guitar;
    private String piano;
    private String sing;
    private String viola;
    private String violin;
    private FirebaseAuth auth;

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

        bass = "False; 0";
        drum = "False; 0";
        flute = "False; 0";
        guitar = "False; 0";
        piano = "False; 0";
        sing = "False; 0";
        viola = "False; 0";
        violin = "False; 0";

        auth = FirebaseAuth.getInstance();

        bassCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the color is white, then we have not selected the cardView, otherwise we have\
                ColorStateList stateList = bassCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    bassCard.setCardBackgroundColor(Color.BLUE);
                    bass = "True";
                } else {
                    bassCard.setCardBackgroundColor(Color.WHITE);
                    bass = "False; 0";
                }

                View pop = (LayoutInflater.from(Instruments.this)).inflate(R.layout.activity_pop, null);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Instruments.this);
                alertBuilder.setView(pop);
                final EditText userinput = pop.findViewById(R.id.userinput);
                alertBuilder.setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String years_played = userinput.getText().toString();
                        bass = "True; " + years_played;
                    }
                });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });

        drumCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = drumCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    drumCard.setCardBackgroundColor(Color.BLUE);
                    drum = "True";
                } else {
                    drumCard.setCardBackgroundColor(Color.WHITE);
                    drum = "False; 0";
                }

                View pop = (LayoutInflater.from(Instruments.this)).inflate(R.layout.activity_pop, null);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Instruments.this);
                alertBuilder.setView(pop);
                final EditText userinput = pop.findViewById(R.id.userinput);
                alertBuilder.setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String years_played = userinput.getText().toString();
                                bass = "True; " + years_played;
                            }
                        });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });

        fluteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = fluteCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    fluteCard.setCardBackgroundColor(Color.BLUE);
                    flute = "True";
                } else {
                    fluteCard.setCardBackgroundColor(Color.WHITE);
                    flute = "False; 0";
                }

                View pop = (LayoutInflater.from(Instruments.this)).inflate(R.layout.activity_pop, null);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Instruments.this);
                alertBuilder.setView(pop);
                final EditText userinput = pop.findViewById(R.id.userinput);
                alertBuilder.setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String years_played = userinput.getText().toString();
                                bass = "True; " + years_played;
                            }
                        });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });

        guitarCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = guitarCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    guitarCard.setCardBackgroundColor(Color.BLUE);
                    guitar = "True";
                } else {
                    guitarCard.setCardBackgroundColor(Color.WHITE);
                    guitar = "False; 0";
                }

                View pop = (LayoutInflater.from(Instruments.this)).inflate(R.layout.activity_pop, null);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Instruments.this);
                alertBuilder.setView(pop);
                final EditText userinput = pop.findViewById(R.id.userinput);
                alertBuilder.setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String years_played = userinput.getText().toString();
                                bass = "True; " + years_played;
                            }
                        });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });

        pianoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = pianoCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    pianoCard.setCardBackgroundColor(Color.BLUE);
                    piano = "True";
                } else {
                    pianoCard.setCardBackgroundColor(Color.WHITE);
                    piano = "False; 0";
                }

                View pop = (LayoutInflater.from(Instruments.this)).inflate(R.layout.activity_pop, null);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Instruments.this);
                alertBuilder.setView(pop);
                final EditText userinput = pop.findViewById(R.id.userinput);
                alertBuilder.setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String years_played = userinput.getText().toString();
                                bass = "True; " + years_played;
                            }
                        });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });

        singCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = singCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    singCard.setCardBackgroundColor(Color.BLUE);
                    sing = "True";
                } else {
                    singCard.setCardBackgroundColor(Color.WHITE);
                    sing = "False; 0";
                }

                View pop = (LayoutInflater.from(Instruments.this)).inflate(R.layout.activity_pop, null);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Instruments.this);
                alertBuilder.setView(pop);
                final EditText userinput = pop.findViewById(R.id.userinput);
                alertBuilder.setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String years_played = userinput.getText().toString();
                                bass = "True; " + years_played;
                            }
                        });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });

        violaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = violaCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    violaCard.setCardBackgroundColor(Color.BLUE);
                    viola = "True";
                } else {
                    violaCard.setCardBackgroundColor(Color.WHITE);
                    viola = "False; 0";
                }

                View pop = (LayoutInflater.from(Instruments.this)).inflate(R.layout.activity_pop, null);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Instruments.this);
                alertBuilder.setView(pop);
                final EditText userinput = pop.findViewById(R.id.userinput);
                alertBuilder.setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String years_played = userinput.getText().toString();
                                bass = "True; " + years_played;
                            }
                        });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });

        violinCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorStateList stateList = violinCard.getCardBackgroundColor();

                if (stateList.getDefaultColor() == Color.WHITE){
                    violinCard.setCardBackgroundColor(Color.BLUE);
                    violin = "True";
                } else {
                    violinCard.setCardBackgroundColor(Color.WHITE);
                    violin = "False; 0";
                }

                View pop = (LayoutInflater.from(Instruments.this)).inflate(R.layout.activity_pop, null);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Instruments.this);
                alertBuilder.setView(pop);
                final EditText userinput = pop.findViewById(R.id.userinput);
                alertBuilder.setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String years_played = userinput.getText().toString();
                                bass = "True; " + years_played;
                            }
                        });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bass == "False" && drum == "False" && flute == "False" && guitar == "False" && piano == "False" && sing == "False" && viola == "False" && violin == "False"){
                    Toast.makeText(Instruments.this, "Please select at least one instrument", Toast.LENGTH_SHORT).show();
                } else {
                    UserInstrument instruments = new UserInstrument(bass, drum, flute, guitar, piano, sing, viola, violin);
                    String userId = auth.getCurrentUser().getUid();
                    DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Instruments");
                    currentUserDb.setValue(instruments);
                    Intent proceed_intent = new Intent(Instruments.this, Genres.class);
                    startActivity(proceed_intent);
                }
            }
        });


    }
}
