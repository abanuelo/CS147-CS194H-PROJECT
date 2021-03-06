package com.bignerdranch.android.pife11.Matches;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.pife11.DataSingleton;
import com.bignerdranch.android.pife11.Profile;
import com.bignerdranch.android.pife11.R;
import com.bignerdranch.android.pife11.ViewerPagerCards.CardItem;
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
    public Button viewProfile;
    public ImageView notification;
    public TextView myMatchId, myMatchName;
    public  ImageView myJemiTopImage, myJemiBottom;
    public View context;

    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public MatchesViewHolders(View itemView){
        super(itemView);
        itemView.setOnClickListener(this);
        myMatchId = (TextView) itemView.findViewById(R.id.Matchid);
        myMatchName = (TextView) itemView.findViewById(R.id.MatchName);
        myJemiTopImage = (ImageView) itemView.findViewById(R.id.jemiTop);
        myJemiBottom = (ImageView) itemView.findViewById(R.id.jemiBottom);
        viewProfile = (Button) itemView.findViewById(R.id.SeeProfile);
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Location", "clicked Button");
                Intent intent = new Intent(view.getContext(), Profile.class);
                intent.putExtra("profileId", myMatchId.getText().toString());
                view.getContext().startActivity(intent);
            }
        });

        context = itemView;


    }


    @Override
    public void onClick(View view){
            Intent intent = new Intent(view.getContext(), Profile.class);
            intent.putExtra("profileId", myMatchId.getText().toString());
            view.getContext().startActivity(intent);
    }
}
