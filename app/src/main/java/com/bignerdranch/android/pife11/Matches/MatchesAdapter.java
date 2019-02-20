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
import android.widget.ImageView;

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
    private Boolean setImage = false;
    private ImageView notification;

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
        final int position2 = position;
        //final MatchesViewHolders holder2 = holder;
        final String nameId = matchesList.get(position).getUserId();
        holder.myMatchId.setText(matchesList.get(position).getUserId());
        holder.myMatchName.setText(matchesList.get(position).getName());
        holder.check.setVisibility(View.GONE);
        if (holder.check.getVisibility() == View.VISIBLE){
            matchesList.get(position).setCheckVisibility(1);
        }
        //Attempting to create notification
//        if (context.toString().contains("CollabHiFi2")){
//            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Checked");
//            userDb.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()){
//                        Log.d("000","here");
//                        for (DataSnapshot users: dataSnapshot.getChildren()){
//                            if (users.getKey().equals(nameId)){
//                                Log.d("001","here");
//                                holder2.check.setVisibility(View.INVISIBLE);
//                                long result = (long) users.getValue();
//                                if (result == (long) 1){
//                                    Log.d("Checked", users.getKey());
//                                    holder2.notification.setVisibility(View.VISIBLE);
//                                }
//                            }
//
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }

        if (!matchesList.get(position).getProfileImageURL().equals("default")){
            Glide.with(context).load(matchesList.get(position).getProfileImageURL()).into(holder.myMatchImage);
        }

    }

    @Override
    public int getItemCount(){
        return this.matchesList.size();
    }

}
