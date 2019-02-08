package com.bignerdranch.android.pife11;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

//Credit to https://www.javatpoint.com/android-custom-listview for its help

public class TaskList extends ArrayAdapter<String> {

    Activity context;
    ArrayList<String> tasks;

    public TaskList(Activity context, ArrayList<String> tasks) {
        super(context, -1, tasks);
        this.context = context;
        this.tasks = tasks;
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.show_one_task, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.musicalGoal);
        titleText.setText(tasks.get(position));

        return rowView;
    };
}
