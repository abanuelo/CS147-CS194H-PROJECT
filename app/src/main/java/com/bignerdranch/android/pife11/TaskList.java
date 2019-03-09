package com.bignerdranch.android.pife11;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

//Credit to https://www.javatpoint.com/android-custom-listview for its help

public class TaskList extends ArrayAdapter<String> {

    Activity context;
    ArrayList<String> tasks;
    boolean isChoosingRoutines;

    public TaskList(Activity context, ArrayList<String> tasks, boolean isChoosingRoutines) {
        super(context, -1, tasks);
        this.context = context;
        this.tasks = tasks;
        this.isChoosingRoutines = isChoosingRoutines;

        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.show_one_task, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.musicalGoal);
        titleText.setText(tasks.get(position));

        final int pos = position;

        if (isChoosingRoutines) {
            Button b = (Button) rowView.findViewById(R.id.chooseRoutineButton);
            b.setVisibility(View.VISIBLE);

            CheckBox cb = (CheckBox) rowView.findViewById(R.id.isTaskDone);
            cb.setVisibility(View.GONE);

            final String routName = tasks.get(pos).toString();

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Object o = tasks.get(pos);
                    System.out.println(o.toString());

                    Intent practice_intent = new Intent(getContext(), PracticeHiFi2.class);
                    practice_intent.putExtra("SOURCE", "CHOOSE");
                    practice_intent.putExtra("ROUTINE_NAME", routName);
                    //System.out.println("Changing to routine:" + o.toString());
                    getContext().startActivity(practice_intent);
                }
            });

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Object o = tasks.get(pos);
                    System.out.println(o.toString());

                    Intent practice_intent = new Intent(getContext(), PracticeHiFi2.class);
                    practice_intent.putExtra("SOURCE", "CHOOSE");
                    practice_intent.putExtra("ROUTINE_NAME", routName);
                    //System.out.println("Changing to routine:" + o.toString());
                    getContext().startActivity(practice_intent);
                }
            });

        } else {
            Button b = (Button) rowView.findViewById(R.id.chooseRoutineButton);
            b.setVisibility(View.GONE);

            CheckBox cb = (CheckBox) rowView.findViewById(R.id.isTaskDone);
            cb.setVisibility(View.VISIBLE);
        }

        return rowView;
    };

}
