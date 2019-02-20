package com.bignerdranch.android.pife11.Matches;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bignerdranch.android.pife11.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Matches extends AppCompatActivity {
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myMatchesAdapter;
    private RecyclerView.LayoutManager myMatchesLayoutManager;
    private String currentUserId;
    private ValueEventListener listener;
    private ValueEventListener listener2;
    private DatabaseReference matchDb;
    private DatabaseReference userDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        myRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        myRecyclerView.setNestedScrollingEnabled(false);
        myRecyclerView.setHasFixedSize(true);

        myMatchesLayoutManager = new LinearLayoutManager(Matches.this);
        myRecyclerView.setLayoutManager(myMatchesLayoutManager);
        myMatchesAdapter = new MatchesAdapter(getDataSetMatches(), Matches.this);
        myRecyclerView.setAdapter(myMatchesAdapter);

        getUserMatchId();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (matchDb != null && listener != null) {
            matchDb.removeEventListener(listener);
        }

        if (userDb != null && listener2 != null) {
            userDb.removeEventListener(listener2);
        }
    }

    private void getUserMatchId() {
        matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Collaborations").child("Matches");
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot match: dataSnapshot.getChildren()){
                        FetchMatchInformation(match.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        matchDb.addValueEventListener(listener);
    }

    private void FetchMatchInformation(String key){
        userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        listener2 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String userId = dataSnapshot.getKey();
                    String name = "";
                    String profileImageURL = "";


                    if (dataSnapshot.child("name").getValue() != null){
                        name = dataSnapshot.child("name").getValue().toString().trim();
                    }
                    if (dataSnapshot.child("profileImageURL").getValue() != null){
                        profileImageURL = dataSnapshot.child("profileImageURL").getValue().toString().trim();
                    }

                    MatchesObject obj = new MatchesObject(userId, name, profileImageURL, 0);
                    resultsMatches.add(obj);
                    myMatchesAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        userDb.addValueEventListener(listener2);

    }

    private ArrayList<MatchesObject> resultsMatches = new ArrayList<MatchesObject>();
    private List<MatchesObject> getDataSetMatches(){
        return resultsMatches;
    }
}
