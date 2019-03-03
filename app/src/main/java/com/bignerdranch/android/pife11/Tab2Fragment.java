package com.bignerdranch.android.pife11;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;

import com.bignerdranch.android.pife11.ViewerPagerCards.CardFragmentPagerAdapter;
import com.bignerdranch.android.pife11.ViewerPagerCards.CardItem;
import com.bignerdranch.android.pife11.ViewerPagerCards.CardPagerAdapter;
import com.bignerdranch.android.pife11.ViewerPagerCards.ShadowTransformer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Tab2Fragment extends Fragment   {
    private Button mButton;
    private ViewPager mViewPager;
    private CheckBox box;
    private TextView name_text;

    private Spinner InstrumentsFilter;
    private Spinner GenreFilter;
    private Spinner YearsFilter;
    private String instrument;
    private String genre;
    private String years;
    private String instrumentAdapter;
    private String genreAdapter;
    private String yearsAdapter;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;
    private boolean mShowingFragments = false;
    private DatabaseReference usersDb;
    private DatabaseReference matchesDb;
    private FirebaseAuth auth;
    private String currentUId;
    private ArrayList<String> names;
    private CardItem start;

    private FragmentActivity myContext;
    private DataSingleton ds;

    private int instrumentPosition;
    private int genrePosition;
    private int yearPosition;

    private boolean didPopulate = false;

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        //View viewCard = inflater.inflate(R.layout.adapter, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        //mButton = (Button) viewCard.findViewById(R.id.collab_button);
        //name_text = (TextView) viewCard.findViewById(R.id.name);
        //mButton =(Button) mViewPager.findViewById(R.id.collab_button);
        //mButton = (Button) view.findViewById(R.id.cardTypeBtn);
        //box = (CheckBox) view.findViewById(R.id.checkBox);
        //mButton.setOnClickListener(this);

        ds = DataSingleton.getInstance();

        names = new ArrayList<String>();
        //mCardAdapter = new CardPagerAdapter();
        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        auth = FirebaseAuth.getInstance();
        currentUId = auth.getCurrentUser().getUid();
        //System.out.println("Current U id is: " + currentUId.toString());
        matchesDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUId).child("Collaborations").child("Matches");

        InstrumentsFilter = view.findViewById(R.id.InstrumentFilter);
        GenreFilter = view.findViewById(R.id.GenreFilter);
        YearsFilter = view.findViewById(R.id.YearsFilter);
        instrumentAdapter = InstrumentsFilter.getSelectedItem().toString().toLowerCase();
        if (instrumentAdapter.contains("all")) instrumentAdapter = "all";
        genreAdapter = GenreFilter.getSelectedItem().toString().toLowerCase();
        if (genreAdapter.contains("all")) genreAdapter = "all";
        yearsAdapter = YearsFilter.getSelectedItem().toString().toLowerCase();
        if (yearsAdapter.contains("all")) yearsAdapter = "all";


        Button buttonOne = (Button) view.findViewById(R.id.applyFilter);
        buttonOne.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here
                //InstrumentsFilter = v.findViewById(R.id.InstrumentFilter);
                //didPopulate = false;

                instrumentAdapter = InstrumentsFilter.getSelectedItem().toString().toLowerCase();
                if (instrumentAdapter.contains("all")) instrumentAdapter = "all";
                genreAdapter = GenreFilter.getSelectedItem().toString().toLowerCase();
                if (genreAdapter.contains("all")) genreAdapter = "all";
                yearsAdapter = YearsFilter.getSelectedItem().toString().toLowerCase();
                if (yearsAdapter.contains("all")) yearsAdapter = "all";

                System.out.println("Let's see if this is happening!");
                populateCards();
            }
        });

        //Here insert Firebase Background for Log-In Iterate and create new ones
        if (!didPopulate) {
            mCardAdapter = new CardPagerAdapter();
            mCardAdapter.setRunnable(refreshFrag());
            populateCards();
            //We need to kinda get rid of Gerald...

            start = new CardItem("Gerald", "Genre", "Years", "Instrument", "pop", "2", "sing");
            mCardAdapter.addCardItem(start);
            mCardAdapter.notifyDataSetChanged();
        }
        System.out.println("Printing current cardAdapter status: "+ mCardAdapter.getCount());

        mFragmentCardAdapter = new CardFragmentPagerAdapter(getFragmentManager(),
                dpToPixels(2, getContext()));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);
        mCardShadowTransformer.enableScaling(true);
        mFragmentCardShadowTransformer.enableScaling(true);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(30);


        return view;

    }
//
//    @Override
//    public void onClick(View view) {
//        if (!mShowingFragments) {
//            mButton.setText("Views");
//            mViewPager.setAdapter(mFragmentCardAdapter);
//            mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
//        } else {
//            mButton.setText("Fragments");
//            mViewPager.setAdapter(mCardAdapter);
//            mViewPager.setPageTransformer(false, mCardShadowTransformer);
//        }
//
//        mShowingFragments = !mShowingFragments;
//    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }



    public void populateCards(){

        usersDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotOrig) {

                final DataSnapshot dataSnapshot = dataSnapshotOrig;

                matchesDb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override

                    public void onDataChange(@NonNull DataSnapshot dataSnapshotMatches) {

                        ArrayList<String> matches = new ArrayList<>();
                        for (DataSnapshot match: dataSnapshotMatches.getChildren()) {
                            matches.add((String) match.getKey());
                        }

                        didPopulate = true;
                        CardPagerAdapter allPossibleFriends = new CardPagerAdapter();
                        for (DataSnapshot user : dataSnapshot.getChildren()){
                            System.out.println("Here we go!" + user.toString());

                            String userKey = user.getKey();
                            if (shouldIncludeInNew(matches, userKey)) {

                                //Get user name from backend
                                String name = user.child("name").getValue().toString().trim();
                                //Get user genre from backend
                                String genres = "";
                                String years = "";
                                String instruments = "";
                                for (DataSnapshot genre : user.child("Genres").getChildren()) {
                                    if ((boolean) genre.getValue() == true) {
                                        if (genres.length() == 0) {
                                            genres = genre.getKey();
                                        } else {
                                            genres = genres + ", " + genre.getKey();
                                        }
                                    }
                                }
                                for (DataSnapshot year : user.child("Years").getChildren()) {
                                    years = year.getValue().toString();
                                }
                                for (DataSnapshot instrument : user.child("Instruments").getChildren()) {
                                    if ((boolean) instrument.getValue() == true) {
                                        if (instruments.length() == 0) {
                                            instruments = instrument.getKey();
                                        } else {
                                            instruments = instruments + " " + instrument.getKey();
                                        }
                                    }
                                }
                                names.add(name);
                                mCardAdapter.addCardItem(new CardItem(name, "Genre", "Years", "Instruments", genres, years, instruments));
                                mCardAdapter.notifyDataSetChanged();

                                allPossibleFriends.addCardItem(new CardItem(name, "Genre", "Years", "Instruments", genres, years, instruments));
                            }
                        }
                        ds.setAllPossibleFriends(allPossibleFriends);
                        assert(allPossibleFriends.getCount() != 0);

                        mCardAdapter = new CardPagerAdapter();
                        mCardAdapter.setRunnable(refreshFrag());
                        CardPagerAdapter dsAdapter = ds.getAllPossibleFriends();
                        for (int j = 0; j < dsAdapter.getCount(); j++) {
                            CardItem curr = dsAdapter.getCardItemAt(j);
                            if (shouldIncludeUser(curr.getInstrumentsText(), curr.getGenreText(), curr.getYearsText())) {
                                mCardAdapter.addCardItem(dsAdapter.getCardItemAt(j));
                            }
                            //instrumentPosition = i;
                        }

                        refreshFragment();
                        System.out.println("Printing current length: " + mCardAdapter.getCount());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                /*didPopulate = true;
                CardPagerAdapter allPossibleFriends = new CardPagerAdapter();
                for (DataSnapshot user : dataSnapshot.getChildren()){
                    //Get user name from backend
                    String name = user.child("name").getValue().toString().trim();
                    //Get user genre from backend
                    String genres = "";
                    String years = "";
                    String instruments = "";
                    for (DataSnapshot genre: user.child("Genres").getChildren()) {
                        if ((boolean) genre.getValue() == true) {
                            if (genres.length() == 0) {
                                genres = genre.getKey();
                            } else {
                                genres = genres + ", " + genre.getKey();
                            }
                        }
                    }
                    for (DataSnapshot year: user.child("Years").getChildren()) {
                        years = year.getValue().toString();
                    }
                    for (DataSnapshot instrument: user.child("Instruments").getChildren()) {
                        if ((boolean) instrument.getValue() == true) {
                            if (instruments.length() == 0) {
                                instruments = instrument.getKey();
                            } else {
                                instruments = instruments + " " + instrument.getKey();
                            }
                        }
                    }
                    names.add(name);
                    mCardAdapter.addCardItem(new CardItem(name, "Genre","Years", "Instruments", genres, years, instruments));
                    mCardAdapter.notifyDataSetChanged();

                    allPossibleFriends.addCardItem(new CardItem(name, "Genre","Years", "Instruments", genres, years, instruments));
                }
                ds.setAllPossibleFriends(allPossibleFriends);
                assert(allPossibleFriends.getCount() != 0);

                mCardAdapter = new CardPagerAdapter();
                mCardAdapter.setRunnable(refreshFrag());
                CardPagerAdapter dsAdapter = ds.getAllPossibleFriends();
                for (int j = 0; j < dsAdapter.getCount(); j++) {
                    CardItem curr = dsAdapter.getCardItemAt(j);
                    if (shouldIncludeUser(curr.getInstrumentsText(), curr.getGenreText(), curr.getYearsText())) {
                        mCardAdapter.addCardItem(dsAdapter.getCardItemAt(j));
                    }
                    //instrumentPosition = i;
                }

                refreshFragment();
                System.out.println("Printing current length: " + mCardAdapter.getCount());*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void refreshFragment() {
        FragmentTransaction ft = myContext.getSupportFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    public void refreshPage() {
        myContext.finish();
        startActivity(myContext.getIntent());
    }

    @Override
    public void onAttach(Activity activity){
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    public Runnable refreshFrag() {
        return new Runnable() {
            public void run() {
                mCardAdapter.notifyDataSetChanged();
                refreshFragment();
            }
        };
    }

    private boolean shouldIncludeInNew(ArrayList<String> matches, String userKey){
        if (userKey.equals(currentUId)) return false;
        for (String curr : matches) {
            if (curr.equals(userKey)) {
                return false;
            }
        }
        return true;
    }

    private boolean shouldIncludeUser(String text, String genres_text, String years_text) {
        text = text.toLowerCase();
        genres_text = genres_text.toLowerCase();
        years_text = years_text.toLowerCase();
        System.out.println("Debugging this boolean function: " + text + " | " + genres_text + " | " + years_text);
        System.out.println("Debugging this boolean function part II: " + instrumentAdapter + " | " + genreAdapter + " | " + yearsAdapter);

        boolean instruRes = (text.contains(instrumentAdapter) || instrumentAdapter.equals("all") );
        boolean genreRes = (genres_text.contains(genreAdapter) || genreAdapter.equals("all") );
        boolean yearsRes = (years_text.contains(yearsAdapter) || yearsAdapter.equals("all") );

        /*if (text.contains(instrumentAdapter) && genreAdapter.equals("all") && yearsAdapter.equals("all")) {
            return true;
        } else if (text.contains(instrumentAdapter) && genres_text.contains(genreAdapter) && yearsAdapter.equals("all")) {
            return true;
        } else if (text.contains(instrumentAdapter) && genres_text.contains(genreAdapter) && years_text.contains(yearsAdapter)){
            return true;
        } else if(instrumentAdapter.equals("all") && genreAdapter.equals("all") && yearsAdapter.equals("all")){
            return true;
        } else if(text.contains(instrumentAdapter) && genreAdapter.equals("all") && years_text.contains(yearsAdapter)){
            return true;
        }*/
        if ((instruRes && genreRes && yearsRes)) System.out.println("Debugging this boolean function: We passed!");
        return (instruRes && genreRes && yearsRes);
    }

//    @Override
//    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//        mCardShadowTransformer.enableScaling(b);
//        mFragmentCardShadowTransformer.enableScaling(b);
//    }
}
