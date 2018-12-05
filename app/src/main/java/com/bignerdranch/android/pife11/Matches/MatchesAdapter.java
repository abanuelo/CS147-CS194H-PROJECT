package com.bignerdranch.android.pife11.Matches;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.pife11.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesViewHolders>{
    private List<MatchesObject> matchesList;
    private Context context;
    private String text;
    private MatchesViewHolders result;

    public MatchesAdapter(List<MatchesObject> matchesList, Context context){
        this.matchesList = matchesList;
        this.context = context;
    }

    @Override
    public MatchesViewHolders onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        MatchesViewHolders rcv = new MatchesViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder (MatchesViewHolders holder, int position){
        //holder.myMatchId.setText(matchesList.get(position).getUserId());
        result = holder;
        String match_id = matchesList.get(position).getUserId();
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(match_id).child("username");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    text = dataSnapshot.getValue().toString().trim();
                    result.myMatchId.setText(text);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        result.myMatchName.setText(matchesList.get(position).getName());
        if (!matchesList.get(position).getProfileImageURL().equals("default")){
            Glide.with(context).load(matchesList.get(position).getProfileImageURL()).into(result.myMatchImage);
        }

    }

    @Override
    public int getItemCount(){
        return this.matchesList.size();
    }
}
