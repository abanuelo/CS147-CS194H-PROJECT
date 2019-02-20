package com.bignerdranch.android.pife11.Matches;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.pife11.Chat.Chat;
import com.bignerdranch.android.pife11.MatchesChoice;
import com.bignerdranch.android.pife11.Profile;
import com.bignerdranch.android.pife11.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchesViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView check;
    public ImageView notification;
    public TextView myMatchId, myMatchName;
    public CircleImageView myMatchImage;
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public MatchesViewHolders(View itemView){
        super(itemView);
        itemView.setOnClickListener(this);
        myMatchId = (TextView) itemView.findViewById(R.id.Matchid);
        myMatchName = (TextView) itemView.findViewById(R.id.MatchName);
        myMatchImage = (CircleImageView) itemView.findViewById(R.id.MatchImage);
        check = (ImageView) itemView.findViewById(R.id.check);
        notification = (ImageView) itemView.findViewById(R.id.new_song);
    }


    @Override
    public void onClick(View view){
        //redirect this to see my matches and decide whether or not a collaboration wants to be set
        if (view.getContext().toString().contains("SendPerform")) {
            //Log.d("Located!", "Yes");
            if (check.getVisibility() == view.GONE) {
                check.setVisibility(view.VISIBLE);
                DatabaseReference checkDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Checked");
                HashMap info = new HashMap();
                info.put(myMatchId.getText().toString(), 1);
                checkDb.updateChildren(info);
            } else {
                check.setVisibility(view.GONE);
                DatabaseReference checkDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Checked");
                HashMap info = new HashMap();
                info.put(myMatchId.getText().toString(), 0);
                checkDb.updateChildren(info);
            }
        } else {
            Intent intent = new Intent(view.getContext(), Profile.class);
            Bundle b = new Bundle();
            b.putString("matchId", myMatchId.getText().toString());
            intent.putExtras(b);
            view.getContext().startActivity(intent);
        }
    }
}
