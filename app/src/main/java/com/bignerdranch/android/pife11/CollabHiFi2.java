package com.bignerdranch.android.pife11;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CollabHiFi2 extends AppCompatActivity {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DatabaseReference matchDb;
    private ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collab_hi_fi2);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "Friends");
        adapter.addFragment(new Tab2Fragment(), "Find New");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        checkIfTaskComplete();


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationViewPerform);
        bottomNavigationView.setSelectedItemId(R.id.friends_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                                                     @Override
                                                                     public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                                                                         switch (menuItem.getItemId()){
                                                                             case R.id.practice_nav:
                                                                                 Intent practice_intent = new Intent(CollabHiFi2.this, ChooseRoutineActivity.class);
                                                                                 finish();
                                                                                 startActivity(practice_intent);
                                                                                 break;
                                                                             case R.id.perform_nav:
                                                                                 Intent perform_intent = new Intent(CollabHiFi2.this, DeclarePerform.class);
                                                                                 finish();
                                                                                 startActivity(perform_intent);
                                                                                 break;
                                                                             case R.id.user_nav:
                                                                                 Intent profile_intent = new Intent(CollabHiFi2.this, Profile.class);
                                                                                 finish();
                                                                                 startActivity(profile_intent);
                                                                                 break;
                                                                         }
                                                                         return true;
                                                                     }
                                                                 }
        );

    }
    private void checkIfTaskComplete(){
        final String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats");

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot match: dataSnapshot.getChildren()){
                        if (match.getKey().equals("collab")) {
                            int hl = Integer.parseInt(match.getValue().toString().trim());
                            if(hl == 0) {
                                //run tutorial program
                                FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("collab").setValue(Integer.toString((1)));

                            }
                        }
                    } }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        matchDb.addValueEventListener(listener);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (matchDb != null && listener != null) {
            Log.d("Clear", "clearing userdatabase!");
            matchDb.removeEventListener(listener);
        }
    }

}