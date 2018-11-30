package com.bignerdranch.android.pife11;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyRewards extends Fragment {
    private String myAvatar;
    private String currentUserId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_rewards, container, false);
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getUserAvatar(view);
        return view;


    }

    private void getUserAvatar(final View view) {
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Avatar");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    myAvatar = dataSnapshot.getValue().toString().trim();
                    ImageView avatarDisplay = (ImageView) view.findViewById(R.id.avatarMyRewards);
                    if(myAvatar.equals("{avatar=Jemi}")) {
                        avatarDisplay.setImageResource(R.drawable.ic_monster_baby);
                    } else {
                        avatarDisplay.setImageResource(R.drawable.ic_nerdy_monster_baby);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
