package com.bignerdranch.android.pife11;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.service.chooser.ChooserTargetService;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


// EXAMPLE FOR READING THINGS FROM FIREBASE
//
//   private void FetchHeartLevel(){
////        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats");
////
////        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                if (dataSnapshot.exists()){
////                    for (DataSnapshot match: dataSnapshot.getChildren()){
////                        Log.i("Ab-matchkey", match.getKey());
////                        Log.i("Ab-matchVal", match.getValue().toString().trim());
////                        if (match.getKey().equals("heartlevel")) {
////                            int hl = Integer.parseInt(match.getValue().toString().trim());
////                            heartlevel = findViewById(R.id.PracticeHeartLevel);
////                            heartlevel.setProgress(hl);
////                        }
////                    }
////                }
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError databaseError) {
////
////            }
////        });
//
//    }


public class ChooseRoutineActivity extends Activity {
    private ImageView store;
    private DatabaseReference userDatabase;
    private ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_routine);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationViewPerform);
        bottomNavigationView.setSelectedItemId(R.id.practice_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.perform_nav:
                        Intent practice_intent = new Intent(ChooseRoutineActivity.this, MyPerform.class);
                        startActivity(practice_intent);
                        break;
                     case R.id.friends_nav:
                         Intent collab_intent = new Intent(ChooseRoutineActivity.this, CollabHiFi2.class);
                         startActivity(collab_intent);
                         break;
                     case R.id.user_nav:
                         Intent profile_intent = new Intent(ChooseRoutineActivity.this, Profile.class);
                         startActivity(profile_intent);
                         break;
                         }
                         return true;
                     }
                 }
        );

        populateRoutinesList();

        store = findViewById(R.id.shop);
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToShop = new Intent(ChooseRoutineActivity.this, Store.class);
                startActivity(goToShop);
            }
        });

        changeCoins();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (userDatabase != null && listener != null) {
            userDatabase.removeEventListener(listener);
        }
    }

    private void populateRoutinesList() {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);


        DatabaseReference routinesDB = userDatabase.child("Routines");
        //ArrayList<ArrayList<String>> myListOfRoutines = new ArrayList<ArrayList<String>>();


        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ArrayList<String>> dsRoutines = new ArrayList<ArrayList<String>>();
                ArrayList<String> items = new ArrayList<>();

                //Getting the info from Firebase
                for (DataSnapshot curr: dataSnapshot.child("Routines").getChildren()) {
                    System.out.println("Trying this out: " + curr.getValue().toString());

                    ArrayList<String> toBeAdded = new ArrayList<String>();
                    //System.out.println("Debugging the routines reading:" + curr.toString());
                    /*ArrayList currRoutine = (ArrayList) curr.getValue();
                    Iterator<Map.Entry<String, String>> itr = currRoutine.entrySet().iterator();
                    while(itr.hasNext()) {
                        Map.Entry<String, String> entry = itr.next();
                        toBeAdded.add(Integer.parseInt(entry.getKey()), entry.getValue());
                    }*/
                    items.add((String) curr.getKey());
                    dsRoutines.add((ArrayList<String>) curr.getValue());
                }
                System.out.println("Debugging the routines total:" + dsRoutines.toString());
                DataSingleton.getInstance().setRoutinesList(dsRoutines);



                //Populate the list of routines with the names of the routines (the first string in each routine list)
                /*for (ArrayList<String> curr: dsRoutines){
                    System.out.println("Make List Add:" + curr.toString());
                    items.add(curr.get(0));
                }*/

                ArrayAdapter<String> routines = new ArrayAdapter<String>(ChooseRoutineActivity.this, android.R.layout.simple_list_item_1, items);
                final ListView routinesList = (ListView) findViewById(R.id.routinesList);
                routinesList.setAdapter(routines);
                routinesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Object o = routinesList.getItemAtPosition(position);
                        System.out.println(o.toString());

                        Intent practice_intent = new Intent(ChooseRoutineActivity.this, PracticeHiFi2.class);
                        practice_intent.putExtra("SOURCE", "CHOOSE");
                        practice_intent.putExtra("ROUTINE_NAME", o.toString());
                        //System.out.println("Changing to routine:" + o.toString());
                        startActivity(practice_intent);

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        //Get the routines from the database?

        //System.out.println("Debugging the routines... finishing before the callback?");


        /*DataSingleton ds = DataSingleton.getInstance();
        ArrayList<ArrayList<String>> dsRoutines = ds.getRoutinesList();

        ArrayList<String> items = new ArrayList<>();

        //Populate the list of routines with the names of the routines (the first string in each routine list)
        for (ArrayList<String> curr: dsRoutines){
            System.out.println("Make List Add:" + curr.toString());
            items.add(curr.get(0));
        }



        ArrayAdapter<String> routines = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        final ListView routinesList = (ListView) findViewById(R.id.routinesList);
        routinesList.setAdapter(routines);
        routinesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = routinesList.getItemAtPosition(position);
                System.out.println(o.toString());

                Intent practice_intent = new Intent(ChooseRoutineActivity.this, PracticeHiFi2.class);
                practice_intent.putExtra("SOURCE", "CHOOSE");
                practice_intent.putExtra("ROUTINE_NAME", o.toString());
                startActivity(practice_intent);

            }
        });*/
        userDatabase.addValueEventListener(listener);
    }

    public void addRoutine(View view){
        Intent practice_intent = new Intent(ChooseRoutineActivity.this, AddNewRoutine.class);
        startActivity(practice_intent);
    }

    public void goToOpenPractice(View view) {
        Intent practice_intent = new Intent(ChooseRoutineActivity.this, PracticeHiFi2.class);
        practice_intent.putExtra("SOURCE", "CHOOSE");
        practice_intent.putExtra("ROUTINE_NAME", "Open Practice");
        startActivity(practice_intent);
    }

    public void changeCoins(){
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("xp");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String pifePoints = dataSnapshot.getValue().toString().trim();
                    TextView pointsDisplay = (TextView) findViewById(R.id.coins);
                    pointsDisplay.setText(pifePoints);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void goToStore(View view) {
        Intent practice_intent = new Intent(this, Store.class);
        startActivity(practice_intent);
    }
}
