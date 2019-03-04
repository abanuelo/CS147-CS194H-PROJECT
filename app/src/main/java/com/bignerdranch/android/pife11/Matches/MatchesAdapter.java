package com.bignerdranch.android.pife11.Matches;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bignerdranch.android.pife11.DataSingleton;
import com.bignerdranch.android.pife11.R;
import com.bignerdranch.android.pife11.ViewerPagerCards.CardItem;
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
    public void onViewAttachedToWindow (MatchesViewHolders holder){

    }

    @Override
    public MatchesViewHolders onCreateViewHolder(ViewGroup parent, int viewType){
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
//        layoutView.findViewById(R.id.jemiTop);
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

        changeAvatarClothes(nameId, holder);

        holder.check.setVisibility(View.GONE);
        if (holder.check.getVisibility() == View.VISIBLE){
            matchesList.get(position).setCheckVisibility(1);
        }

//        if (!matchesList.get(position).getProfileImageURL().equals("default")){
//            Glide.with(context).load(matchesList.get(position).getProfileImageURL()).into(holder.myMatchImage);
//        }

    }

    private void changeAvatarClothes(String currentUserId, final MatchesViewHolders holders){

        DatabaseReference avatarDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("AvatarClothes");
        avatarDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    System.out.println("Datasnapshot:" + dataSnapshot.getValue().toString());

                    Long hatL = (Long) dataSnapshot.child("hat").getValue();
                    int hat = hatL.intValue();
                    System.out.println("What do we have here: hat OG: " + hat);
                    Long shirtL = (Long) dataSnapshot.child("shirt").getValue();
                    int shirt = shirtL.intValue();
                    System.out.println("What do we have here: shirt OG: " + shirt);

                    DataSingleton ds = DataSingleton.getInstance();
                    ds.setAvatarClothes(new Pair(hat, shirt));


                    changeHat(hat, holders.context);
                    changeShirt(shirt, holders.context);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void changeHat(int hat, View view){
        System.out.println("What do we have here: hat: " + hat);
        // Table of contents: 0 - no hat, 1 - blue, 2 - orange, 3 - pink, 4 - yellow
        ImageView hatImg = (ImageView) view.findViewById(R.id.jemiTop);
        Drawable myDraw = view.getResources().getDrawable(R.drawable.yellowtoptrans);

        if (hat == 0) {
            myDraw = view.getResources().getDrawable(R.drawable.undressedtoptrans);
        } else if (hat == 1) {
            myDraw = view.getResources().getDrawable(R.drawable.bluetoptrans);
        } else if (hat == 2) {
            myDraw = view.getResources().getDrawable(R.drawable.orangetoptrans);
        } else if (hat == 3) {
            myDraw = view.getResources().getDrawable(R.drawable.pinktoptrans);
        }
        hatImg.setImageDrawable(myDraw);
    }

    private void changeShirt(int shirt, View view) {
        System.out.println("What do we have here: shirt: " + shirt);
        // Table of contents: 0 - no shirt, 1 - green, 2 - pink, 3 - yellow, 4 - brown
        ImageView shirtImg = (ImageView) view.findViewById(R.id.jemiBottom);

        Drawable myDraw = view.getResources().getDrawable(R.drawable.brownbottomtrans);

        if (shirt == 0) {
            myDraw = view.getResources().getDrawable(R.drawable.undressedbottomtrans);
        } else if (shirt == 1) {
            myDraw = view.getResources().getDrawable(R.drawable.greenbottomtrans);
        } else if (shirt == 2) {
            myDraw = view.getResources().getDrawable(R.drawable.pinkbottomtrans);
        } else if (shirt == 3) {
            myDraw = view.getResources().getDrawable(R.drawable.yellowbottomtrans);
        }
        shirtImg.setImageDrawable(myDraw);

    }

    @Override
    public int getItemCount(){
        return this.matchesList.size();
    }

}
