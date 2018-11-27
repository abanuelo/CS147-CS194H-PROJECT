package com.bignerdranch.android.pife11.Matches;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bignerdranch.android.pife11.R;

import java.util.ArrayList;
import java.util.List;

public class Matches extends AppCompatActivity {
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myMatchesAdapter;
    private RecyclerView.LayoutManager myMatchesLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        myRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        myRecyclerView.setNestedScrollingEnabled(false);
        myRecyclerView.setHasFixedSize(true);

        myMatchesLayoutManager = new LinearLayoutManager(Matches.this);
        myRecyclerView.setLayoutManager(myMatchesLayoutManager);
        myMatchesAdapter = new MatchesAdapter(getDataSetMatches(), Matches.this);
        myRecyclerView.setAdapter(myMatchesAdapter);


        MatchesObject obj = new MatchesObject("asd");
        resultsMatches.add(obj);
        resultsMatches.add(obj);
        resultsMatches.add(obj);
        resultsMatches.add(obj);
        myMatchesAdapter.notifyDataSetChanged();

    }

    private ArrayList<MatchesObject> resultsMatches = new ArrayList<MatchesObject>();
    private List<MatchesObject> getDataSetMatches(){
        return resultsMatches;
    }
}
