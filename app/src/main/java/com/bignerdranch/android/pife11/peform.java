package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class peform extends AppCompatActivity {
    private Button watch;
    private Button perform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peform);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationViewPerform);
        bottomNavigationView.setSelectedItemId(R.id.perform_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                                                     @Override
                                                                     public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                                                                         switch (menuItem.getItemId()){
                                                                             case R.id.practice_nav:
                                                                                 Intent practice_intent = new Intent(peform.this, ChooseRoutineActivity.class);
                                                                                 startActivity(practice_intent);
                                                                                 break;
                                                                             case R.id.friends_nav:
                                                                                 Intent collab_intent = new Intent(peform.this, CollabHiFi2.class);
                                                                                 startActivity(collab_intent);
                                                                                 break;
                                                                             case R.id.user_nav:
                                                                                 Intent profile_intent = new Intent(peform.this, Profile.class);
                                                                                 startActivity(profile_intent);
                                                                                 break;
                                                                         }
                                                                         return true;
                                                                     }
                                                                 }
        );

        watch = findViewById(R.id.search);
        perform = findViewById(R.id.perform);

        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent practice_intent = new Intent(peform.this, watch_performance.class);
                startActivity(practice_intent);
            }
        });

        perform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //UPDATE THIS URL FOR THE FINAL PRESENTATION OF THE PROJECT
                String url = "https://appear.in/piferoom2";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(peform.this, Uri.parse(url));
            }
        });

        checkIfTaskComplete();


    }
    private void checkIfTaskComplete(){
        final String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats");

        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot match: dataSnapshot.getChildren()){
                        if (match.getKey().equals("perform")) {
                            int hl = Integer.parseInt(match.getValue().toString().trim());
                            if(hl == 0) {
                                //run tutorial program
                                FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("perform").setValue(Integer.toString((1)));

                            }
                        }
                    } }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}