package com.bignerdranch.android.pife11.Cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.pife11.R;
import com.bumptech.glide.Glide;


import java.util.List;

public class arrayAdapter extends ArrayAdapter<Cards> {
    Context context;

    public arrayAdapter(Context context, int resourceId, List<Cards> items){
        super(context, resourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Cards card_item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);

        name.setText(card_item.getName());

        //Setting the Image for Reference
        if(card_item.getProfileImageURL().equals("default")){
            //THIS WILL USE THE AVATARS FOR
            Glide.with(convertView.getContext()).load(R.drawable.ic_nerdy_monster_baby).into(image);
        } else {
            Glide.with(convertView.getContext()).load(card_item.getProfileImageURL()).into(image);
        }


        return convertView;
    }
}
