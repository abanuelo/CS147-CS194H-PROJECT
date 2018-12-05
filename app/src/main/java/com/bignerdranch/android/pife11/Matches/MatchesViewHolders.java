package com.bignerdranch.android.pife11.Matches;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.pife11.Chat.Chat;
import com.bignerdranch.android.pife11.MatchesChoice;
import com.bignerdranch.android.pife11.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchesViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView myMatchId, myMatchName;
    public CircleImageView myMatchImage;
    public MatchesViewHolders(View itemView){
        super(itemView);
        itemView.setOnClickListener(this);
        myMatchId = (TextView) itemView.findViewById(R.id.Matchid);
        myMatchName = (TextView) itemView.findViewById(R.id.MatchName);
        myMatchImage = (CircleImageView) itemView.findViewById(R.id.MatchImage);

    }
    @Override
    public void onClick(View view){
        //redirect this to see my matches and decide whether or not a collaboration wants to be set
        Intent intent = new Intent (view.getContext(), MatchesChoice.class);
        Bundle b = new Bundle();
        b.putString("matchId", myMatchId.getText().toString());
        intent.putExtras(b);
        view.getContext().startActivity(intent);
    }
}
