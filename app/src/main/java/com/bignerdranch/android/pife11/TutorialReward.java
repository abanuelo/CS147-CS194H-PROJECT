package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class TutorialReward extends AppCompatActivity {

    int pifePoints = 0;
    String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {


//        DatabaseReference statsDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("xp");
//        statsDb.setValue(pifePoints + 15);
        DatabaseReference firstTutorialSuccess = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats");
        HashMap map = new HashMap();
        map.put("FirstTutorial", true);
        firstTutorialSuccess.updateChildren(map);


        getUserPifePoints();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_reward);
    }

    public void returnToProfile(View view){
        Intent practice_intent = new Intent(this, Store.class);
        practice_intent.putExtra("firstTutorial", true);
        startActivity(practice_intent);
    }

    private void getUserPifePoints() {
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    pifePoints = Integer.parseInt(dataSnapshot.child("xp").getValue().toString()) + 15;
                    int lifetimecoins = Integer.parseInt(dataSnapshot.child("lifetimeCoins").getValue().toString()) + 15;

                    DatabaseReference statsDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats");
                    HashMap map = new HashMap();
                    map.put("xp", pifePoints);
                    map.put("lifetimeCoins", lifetimecoins);
                    statsDb.updateChildren(map);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
