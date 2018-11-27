package com.bignerdranch.android.pife11.Matches;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.android.pife11.R;

public class MatchesViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView myMatchId;
    public MatchesViewHolders(View itemView){
        super(itemView);
        itemView.setOnClickListener(this);
        myMatchId = (TextView) itemView.findViewById(R.id.Matchid);
    }
    @Override
    public void onClick(View view){

    }
}
