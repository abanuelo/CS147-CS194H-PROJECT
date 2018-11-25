package com.bignerdranch.android.pife11;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SearchCollab extends AppCompatActivity {
    private Cards card_data[];

    private arrayAdapter arrayAdapter;
    private int i;
    private FirebaseAuth auth;
    private String currentUId;
    private DatabaseReference usersDb;

    ListView listView;
    List<Cards> rowItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_collab);

        //declaration of the private variables introduced above
        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        auth = FirebaseAuth.getInstance();
        currentUId = auth.getCurrentUser().getUid();

        //row items defined
        rowItems = new ArrayList<Cards>();

        //Importing the Filters from Filter feature
        ArrayList<String> filter_genres = getIntent().getExtras().getStringArrayList("GENRES");
        ArrayList<String> filter_instruments = getIntent().getExtras().getStringArrayList("INSTRUMENTS");

        //new commands that will include the names of people
        getFilteredUsers(filter_genres, filter_instruments);

        arrayAdapter = new arrayAdapter(this, R.layout.item, rowItems);
        SwipeFlingAdapterView flingContainer = findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Cards obj = (Cards) dataObject;
                String userId = obj.getUserId();
                usersDb.child(userId).child("Collaborations").child("No").child(currentUId).setValue(true);
                makeToast(SearchCollab.this, "Left!");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Cards obj = (Cards) dataObject;
                String userId = obj.getUserId();
                usersDb.child(userId).child("Collaborations").child("Yes").child(currentUId).setValue(true);
                isConnectionMatch(userId);
                makeToast(SearchCollab.this, "Right!");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
//                Cards a = new Cards("XML", "XML");
//                rowItems.add(a);
//                arrayAdapter.notifyDataSetChanged();
//                Log.d("LIST", "notified");
//                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(SearchCollab.this, "Clicked!");
            }
        });

    }

    private void isConnectionMatch(String userId) {
        DatabaseReference currentUserConnectionsDB = usersDb.child(currentUId).child("Collaborations").child("Yes").child(userId);
        currentUserConnectionsDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Toast.makeText(SearchCollab.this, "New collaboration has been made!", Toast.LENGTH_LONG).show();
                    usersDb.child(dataSnapshot.getKey()).child("Collaborations").child("Matches").child(currentUId).setValue(true);
                    usersDb.child(currentUId).child("Collaborations").child("Matches").child(dataSnapshot.getKey()).setValue(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void getFilteredUsers(final ArrayList<String> filter_genres, final ArrayList<String> filter_instruments){
        final ArrayList<String> similar_genre_users = new ArrayList<String>();
        final ArrayList<String> filter_users = new ArrayList<String>();
        DatabaseReference instrumentDB = FirebaseDatabase.getInstance().getReference().child("Users");

        instrumentDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //First part will Filter on users who have a similar genre to collaborate
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String current_profile = snapshot.getKey();
                    for (DataSnapshot genre_type : snapshot.child("Genres").getChildren()){
                        for (String filter_genre : filter_genres){
                            if (genre_type.getKey().trim().equals(filter_genre.trim())){
                                if (genre_type.getValue().toString().trim().equals("True")){
                                    if (!similar_genre_users.contains(current_profile.trim())){
                                        similar_genre_users.add(current_profile.trim());
                                    }
                                }
                            }
                        }
                    }
                }

                //The second part will fiter on users who have a musical instrument the intended part wants to collab with
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (similar_genre_users.contains(snapshot.getKey())){
                        for (DataSnapshot instrument_type : snapshot.child("Instruments").getChildren()){
                            for (String instrument : filter_instruments){
                                String instrument_type_value = instrument_type.getValue().toString().trim();
                                String[] tokens = instrument_type_value.split(";");
                                if (instrument_type.getKey().trim().equals(instrument.trim()) && tokens[0].equals("True")){
                                    if (!filter_users.contains(snapshot.getKey().trim())) {
                                        filter_users.add(snapshot.getKey().trim());
                                    }
                                }
                            }
                        }
                    }
                }

                //Adding the Users Name to the Cards using regex compilations
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    for (String filter_user : filter_users){
                        if (snapshot.getKey().trim().equals(filter_user.trim()) && !snapshot.getKey().trim().equals(currentUId.trim())){ //adding the component where you cannot collaborate with yourself
                            //Additional if statement needs to be added to not re-add or see people on firebase database
                            if (!snapshot.child("Collaborations").child("No").hasChild(currentUId) && !snapshot.child("Collaborations").child("Yes").hasChild(currentUId)){
                                String value = snapshot.getValue().toString().trim();
                                Pattern p = Pattern.compile("\\bname=[a-zA-Z]* [a-zA-Z]*");
                                Matcher m = p.matcher(value);
                                if (m.find()) {
                                    String name = m.group(0);
                                    String[] name_delim = name.split("=");
                                    Cards Item = new Cards(snapshot.getKey().trim(), name_delim[1]);
                                    rowItems.add(Item);
                                    arrayAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }


}
