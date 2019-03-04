package com.bignerdranch.android.pife11.SelectVideoOnProfile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bignerdranch.android.pife11.Profile;
import com.bignerdranch.android.pife11.R;

import java.util.ArrayList;


public class CommentAdapter extends ArrayAdapter<Comment> {

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        super(context, 0, comments);
        this.context = context;
    }
    private Context context;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Comment comment = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comments, parent, false);
        }
        // Lookup view for data population
        TextView username = (TextView) convertView.findViewById(R.id.Comment_name);
        TextView likes = (TextView) convertView.findViewById(R.id.Comment_likes);
        TextView wishes = (TextView) convertView.findViewById(R.id.Comment_wishes);
        // Populate the data into the template view using the data object
        username.setText(comment.user);
        likes.setText("I like " + comment.likes);
        wishes.setText("I wish " + comment.wishes);


        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent seeProfile = new Intent(context, Profile.class);
                seeProfile.putExtra("profileId", comment.userID);
                context.startActivity(seeProfile);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }


}
