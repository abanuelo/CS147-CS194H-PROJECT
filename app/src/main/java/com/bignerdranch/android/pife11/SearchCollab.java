package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bignerdranch.android.pife11.Cards.Cards;
import com.bignerdranch.android.pife11.Cards.arrayAdapter;
import com.bignerdranch.android.pife11.Matches.Matches;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.List;


public class SearchCollab extends AppCompatActivity {
    //private Cards card_data[];

    private com.bignerdranch.android.pife11.Cards.arrayAdapter arrayAdapter;
    //private int i;
    private FirebaseAuth auth;
    private String currentUId;
    private Button myMatches;
    private ImageView filter;
    private RewardsBoth.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private DatabaseReference usersDb;

    //ListView listView;
    List<Cards> rowItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_collab);

        //used to get different tabs

//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        //declaration of the private variables introduced above
        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        auth = FirebaseAuth.getInstance();
        currentUId = auth.getCurrentUser().getUid();

        //row items defined
        rowItems = new ArrayList<Cards>();

        //initializing the filter_genres
        ArrayList<String> filter_genres = new ArrayList<String>();
        filter_genres.add("blues");
        filter_genres.add("classical");
        filter_genres.add("country");
        filter_genres.add("edm");
        filter_genres.add("hiphop");
        filter_genres.add("jazz");
        filter_genres.add("pop");
        filter_genres.add("rock");

        //initalizing the filter children
        ArrayList<String> filter_instruments = new ArrayList<String>();
        filter_instruments.add("bass");
        filter_instruments.add("drums");
        filter_instruments.add("flute");
        filter_instruments.add("guitar");
        filter_instruments.add("piano");
        filter_instruments.add("sing");
        filter_instruments.add("viola");
        filter_instruments.add("violin");

        //Importing the Filters from Filter feature
//        try {
//            filter_genres = getIntent().getExtras().getStringArrayList("GENRES");
//            filter_instruments = getIntent().getExtras().getStringArrayList("INSTRUMENTS");
//        } catch (Exception e){
//            System.out.println("Error " + e.getMessage());
//        }

        myMatches = findViewById(R.id.mymatches);
        filter = findViewById(R.id.filter);

        myMatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMatches = new Intent(SearchCollab.this, Matches.class);
                startActivity(goToMatches);
            }
        });
//        try {
//            filter_genres = getIntent().getExtras().getStringArrayList("GENRES");
//            filter_instruments = getIntent().getExtras().getStringArrayList("INSTRUMENTS");
//        } catch (Exception e){
//            System.out.println("Error " + e.getMessage());
//        }

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToFilters = new Intent(SearchCollab.this, CollabMain.class);
                startActivity(goToFilters);
            }
        });


        myMatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMatches = new Intent(SearchCollab.this, Matches.class);
                startActivity(goToMatches);
            }
        });

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
                makeToast(SearchCollab.this, "No Collaboration");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Cards obj = (Cards) dataObject;
                String userId = obj.getUserId();
                usersDb.child(userId).child("Collaborations").child("Yes").child(currentUId).setValue(true);
                isConnectionMatch(userId);
                makeToast(SearchCollab.this, "Collaboration");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

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
                    String key = FirebaseDatabase.getInstance().getReference().child("Chats").push().getKey();

                    usersDb.child(dataSnapshot.getKey()).child("Collaborations").child("Matches").child(currentUId).child("ChatID").setValue(key);
                    usersDb.child(currentUId).child("Collaborations").child("Matches").child(dataSnapshot.getKey()).child("ChatID").setValue(key);

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

        usersDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (similar_genre_users.isEmpty() && filter_genres.isEmpty()){
                    ArrayList<String> all_users = getAllUsers(dataSnapshot);
                    createCardView(dataSnapshot, all_users);
                } else {
                    ArrayList<String> filtered_instrument_users = filterByInstruments(dataSnapshot, filter_instruments);
                    ArrayList<String> filtered_genres_users = filterByGenres(dataSnapshot, filtered_instrument_users);
                    ArrayList<String> all_filtered_users = filtered_genres_users;
                    createCardView(dataSnapshot, all_filtered_users);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    //All filtered users have been accounted for and the remainder of the operations is
    //to make sure that we are push those names to the Card Views
    public void createCardView(DataSnapshot dataSnapshot, ArrayList<String> all_filtered_users){
        for (DataSnapshot user : dataSnapshot.getChildren()) {
            String curr_profile = user.getKey().trim();
            if (all_filtered_users.contains(curr_profile) && !currentUId.equals(curr_profile)){
                String name = user.child("name").getValue().toString().trim();
                String profile_image_url = user.child("profileImageURL").getValue().toString().trim();
                Cards profile_card = new Cards(curr_profile, name, profile_image_url);
                rowItems.add(profile_card);
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }

    //After the filter has initiated a filter for instruments, the next step is to filter by genres
    public ArrayList<String> filterByGenres(DataSnapshot dataSnapshot, ArrayList<String> filtered_instrument_users){
        ArrayList<String> result = new ArrayList<String>();
        for (DataSnapshot user: dataSnapshot.getChildren()){
            String curr_profile = user.getKey().trim();
            if (filtered_instrument_users.contains(curr_profile)){
                for (DataSnapshot genre: user.child("Genres").getChildren()){
                    if ((boolean)genre.getValue() == true) result.add(curr_profile);
                }
            }
        }
        return result;
    }

    //Method will look for only those customers that have an instrument capability desired by the user
    public ArrayList<String> filterByInstruments(DataSnapshot dataSnapshot, ArrayList<String> filter_instruments){
        ArrayList<String> result = new ArrayList<String>();
        for (DataSnapshot user: dataSnapshot.getChildren()){
            String curr_profile = user.getKey().trim();
            for (DataSnapshot instrument : user.child("Years").getChildren()){
                if (filter_instruments.contains(instrument.getKey().trim())){
                    result.add(curr_profile);
                }
            }
        }
        return result;
    }

    //Gets all the Users for the DataSnapShot if no filters are applied to the user profile
    public ArrayList<String> getAllUsers(DataSnapshot dataSnapshot){
        ArrayList<String> result = new ArrayList<String>();
        for (DataSnapshot user: dataSnapshot.getChildren()){
            String curr_profile = user.getKey().trim();
            result.add(curr_profile);
        }
        return result;
    }


    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }


}
