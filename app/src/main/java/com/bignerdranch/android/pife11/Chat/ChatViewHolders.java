package com.bignerdranch.android.pife11.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.pife11.R;

public class ChatViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ChatViewHolders(View itemView){
        super(itemView);
        itemView.setOnClickListener(this);


    }
    @Override
    public void onClick(View view){

    }
}
