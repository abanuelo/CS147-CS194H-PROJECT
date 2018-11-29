package com.bignerdranch.android.pife11.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.android.pife11.R;

public class ChatViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView mMessage;
    public LinearLayout mContainer;
    public ChatViewHolders(View itemView){
        super(itemView);
        itemView.setOnClickListener(this);

        mMessage = (TextView) itemView.findViewById(R.id.message);
        mContainer = (LinearLayout) itemView.findViewById(R.id.container);

    }
    @Override
    public void onClick(View view){

    }
}
