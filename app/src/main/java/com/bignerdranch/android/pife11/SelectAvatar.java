package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SelectAvatar extends AppCompatActivity {
    private FirebaseAuth auth;
    private ImageView derpy_creature;
    private ImageView nerdy_creature;
    private Button select;
    private TextView name;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_select_avatar);

            derpy_creature = (ImageView) findViewById(R.id.derpy_creature);
            nerdy_creature = (ImageView) findViewById(R.id.nerdy_creature);
            select = (Button)findViewById(R.id.select_button);
            name = (TextView)findViewById(R.id.name);

            auth = FirebaseAuth.getInstance();

            //currently looking at Jemi
            derpy_creature.setOnTouchListener(new OnSwipeTouchListener(SelectAvatar.this) {
                TextView bio = (TextView)findViewById(R.id.bio);

                public void onSwipeTop() {
                }
                public void onSwipeRight() {
                    //display derpy Jemi
                    nerdy_creature.setVisibility(View.INVISIBLE);
                    derpy_creature.setVisibility(View.VISIBLE);
                    name.setText("Jemi");
                    bio.setText("Loves new friends and people\nEnjoys sitting everywhere and derping around");
                    select.setText("Select " + name.getText());
                }
                public void onSwipeLeft() {
                    //display nerdy Ronald

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
                TextView bio = (TextView)findViewById(R.id.bio);

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

            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userId = auth.getCurrentUser().getUid();
                    DatabaseReference currentUserDbAvatar = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Avatar");
                    Map avatarName = new HashMap();
                    avatarName.put("avatar", name.getText().toString().trim());
                    currentUserDbAvatar.updateChildren(avatarName);

                    DatabaseReference currentUserDbStats = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Stats");

                    Map heartLevel = new HashMap();
                    heartLevel.put("heartlevel", "50");
                    currentUserDbStats.updateChildren(heartLevel);
                    //Add Heart Level

                    // Xp - 0
                    Map XP = new HashMap();
                    XP.put("xp", "0");
                    currentUserDbStats.updateChildren((XP));

                    Map Streak = new HashMap();
                    XP.put("streak", "0");
                    currentUserDbStats.updateChildren((Streak));
                    // Days Streak

                    Map lastPlayed = new HashMap();

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd,MMMM,YYYY");
                    String strDate = sdf.format(c.getTime());

                    lastPlayed.put("lastplayed",strDate );
                    currentUserDbStats.updateChildren((lastPlayed));

                    // Day Last Played



                    Intent goToHomepage = new Intent(SelectAvatar.this, Dashboard.class);
                    startActivity(goToHomepage);
                }
            });
        }
}
