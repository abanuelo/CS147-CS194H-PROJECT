package com.bignerdranch.android.pife11;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragAndDropPermissions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.pife11.Cards.arrayAdapter;
import com.bignerdranch.android.pife11.Matches.Matches;
import com.bignerdranch.android.pife11.Matches.MatchesAdapter;
import com.bignerdranch.android.pife11.Matches.MatchesObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Tab1Fragment extends Fragment {
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myMatchesAdapter;
    private RecyclerView.LayoutManager myMatchesLayoutManager;
    private String currentUserId;
    private View view;
    private ValueEventListener listener;
    private ValueEventListener listener2;
    private DatabaseReference userDb;
    private DatabaseReference matchDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_matches, container, false);
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        myRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        myRecyclerView.setNestedScrollingEnabled(false);
        myRecyclerView.setHasFixedSize(true);

        myMatchesLayoutManager = new LinearLayoutManager(getContext());
        myRecyclerView.setLayoutManager(myMatchesLayoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(myRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        Drawable verticalDivider = ContextCompat.getDrawable(getActivity(), R.drawable.vertical_divider);
        divider.setDrawable(verticalDivider);
        myRecyclerView.addItemDecoration(divider);

        myMatchesAdapter = new MatchesAdapter(getDataSetMatches(), getContext());
        myRecyclerView.setAdapter(myMatchesAdapter);
        getUserMatchId();

        return view;
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
                resultsMatches.clear();
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


                    if (dataSnapshot.child("username").getValue() != null){
                        name = dataSnapshot.child("username").getValue().toString().trim();
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