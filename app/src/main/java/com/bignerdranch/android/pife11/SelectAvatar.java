package com.bignerdranch.android.pife11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectAvatar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);

        final ImageView derpy_creature = (ImageView) findViewById(R.id.derpy_creature);
        final ImageView nerdy_creature = (ImageView) findViewById(R.id.nerdy_creature);

        //currently looking at Jemi
        derpy_creature.setOnTouchListener(new OnSwipeTouchListener(SelectAvatar.this) {
            TextView name = (TextView)findViewById(R.id.name);
            TextView bio = (TextView)findViewById(R.id.bio);
            Button select = (Button)findViewById(R.id.select_button);
            public void onSwipeTop() {
            }
            public void onSwipeRight() {
                //display derpy Jemi
                Toast.makeText(SelectAvatar.this, "right", Toast.LENGTH_SHORT).show();

                nerdy_creature.setVisibility(View.INVISIBLE);
                derpy_creature.setVisibility(View.VISIBLE);
                name.setText("Jemi");
                bio.setText("Loves new friends and people\nEnjoys sitting everywhere and derping around");
                select.setText("Select " + name.getText());
            }
            public void onSwipeLeft() {
                //display nerdy Ronald
                Toast.makeText(SelectAvatar.this, "left", Toast.LENGTH_SHORT).show();

                derpy_creature.setVisibility(View.INVISIBLE);
                nerdy_creature.setVisibility(View.VISIBLE);
                name.setText("Ronald");
                bio.setText("Loves books and cartoons\nEnjoys snacking and playing make believe");
                select.setText("Select " + name.getText());
            }
            public void onSwipeBottom() {
            }
        });

        //currently looking at Ronald
        nerdy_creature.setOnTouchListener(new OnSwipeTouchListener(SelectAvatar.this) {
            TextView name = (TextView)findViewById(R.id.name);
            TextView bio = (TextView)findViewById(R.id.bio);
            Button select = (Button)findViewById(R.id.select_button);
            public void onSwipeTop() {
            }
            public void onSwipeRight() {
                //display derpy Jemi
                //Toast.makeText(SelectAvatar.this, "right", Toast.LENGTH_SHORT).show();

                nerdy_creature.setVisibility(View.INVISIBLE);
                derpy_creature.setVisibility(View.VISIBLE);
                name.setText("Jemi");
                bio.setText("Loves new friends and people\nEnjoys sitting everywhere and derping around");
                select.setText("Select " + name.getText());
            }
            public void onSwipeLeft() {
                //display nerdy Ronald
                //Toast.makeText(SelectAvatar.this, "left", Toast.LENGTH_SHORT).show();

                derpy_creature.setVisibility(View.INVISIBLE);
                nerdy_creature.setVisibility(View.VISIBLE);
                name.setText("Ronald");
                bio.setText("Loves books and cartoons\nEnjoys snacking and playing make believe");
                select.setText("Select " + name.getText());
            }
            public void onSwipeBottom() {
            }
        });
    }
}
