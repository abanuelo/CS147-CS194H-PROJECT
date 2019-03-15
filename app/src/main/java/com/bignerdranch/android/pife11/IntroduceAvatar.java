package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class IntroduceAvatar extends AppCompatActivity {
    private Button start;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce_avatar);
        start = findViewById(R.id.start);
        auth = FirebaseAuth.getInstance();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateKeyFirebaseObjects();
                Intent goToProfile = new Intent(IntroduceAvatar.this, Profile.class);
                startActivity(goToProfile);
            }
        });
    }

    private void initiateKeyFirebaseObjects(){
        String userId = auth.getCurrentUser().getUid();
        DatabaseReference currentUserDbGenres = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Stats");
        HashMap stats = new HashMap();
        stats.put("xp", 0);
        stats.put("lifetimeCoins", 0);
        stats.put("TutorialThree", 0);
        currentUserDbGenres.updateChildren(stats);
    }
}
