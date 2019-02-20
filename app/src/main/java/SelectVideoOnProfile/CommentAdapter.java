package SelectVideoOnProfile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bignerdranch.android.pife11.R;

import java.util.ArrayList;

public class CommentAdapter extends ArrayAdapter<Comment> {

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        super(context, 0, comments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Comment comment = getItem(position);
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
        likes.setText(comment.likes);
        wishes.setText(comment.wishes);
        // Return the completed view to render on screen
        return convertView;
    }


}
