package com.bignerdranch.android.pife11;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

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

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;
    private boolean mShowingFragments = false;
    private DatabaseReference usersDb;
    private FirebaseAuth auth;
    private String currentUId;
    private ArrayList<String> names;

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        //mButton = (Button) view.findViewById(R.id.cardTypeBtn);
        //box = (CheckBox) view.findViewById(R.id.checkBox);
        //mButton.setOnClickListener(this);

        names = new ArrayList<String>();
        mCardAdapter = new CardPagerAdapter();
        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        auth = FirebaseAuth.getInstance();
        currentUId = auth.getCurrentUser().getUid();

        //Here insert Firebase Background for Log-In Iterate and create new ones
        populateCards();
        Log.d("NAMES", names.toString());
//        for(String name: names){
//            mCardAdapter.addCardItem(new CardItem(name, name));
//        }
        mCardAdapter.addCardItem(new CardItem("Gerald", "Genre", "Years", "Instrument", "pop", "2", "sing"));
        //System.out.println(names);

//        mCardAdapter.addCardItem(new CardItem("Title", "Text"));
//        mCardAdapter.addCardItem(new CardItem("Title", "Text"));
//        mCardAdapter.addCardItem(new CardItem("Title", "Text"));
//        mCardAdapter.addCardItem(new CardItem("Title", "Text"));
//        mCardAdapter.addCardItem(new CardItem("Title", "Text"));
        mFragmentCardAdapter = new CardFragmentPagerAdapter(getFragmentManager(),
                dpToPixels(2, getContext()));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);
        mCardShadowTransformer.enableScaling(true);
        mFragmentCardShadowTransformer.enableScaling(true);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
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

    public void populateCards(){

        usersDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
                                genres = genres + " " + genre.getKey();
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    @Override
//    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//        mCardShadowTransformer.enableScaling(b);
//        mFragmentCardShadowTransformer.enableScaling(b);
//    }
}
