package com.bignerdranch.android.pife11;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

public class RewardsBoth extends AppCompatActivity {
    private TextView description;
    private ImageView heartTrophy;
    private ImageView smileyTrophy;
    private ImageView lightningTrophy;
    private ImageView sunTrophy;
    private ImageView clockTrophy;
    private ImageView bannerTrophy;
    private ImageView starTrophy;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards_both);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        description = findViewById(R.id.description_view);

        //TROPHIES
        heartTrophy = findViewById(R.id.heart_trophy);
        smileyTrophy = findViewById(R.id.smiley_trophy);
        lightningTrophy = findViewById(R.id.lightning_trophy);
        sunTrophy = findViewById(R.id.sun_trophy);
        clockTrophy = findViewById(R.id.clock_trophy);
        bannerTrophy = findViewById(R.id.banner_trophy);
        starTrophy = findViewById(R.id.star_trophy);


        //DISPLAY TROPHY DESCRIPTIONS ON CLICK (not great code but w/e)
        heartTrophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description.setText("Reach max happiness every day for a week!");
            }
        });
        smileyTrophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description.setText("Buy 5 items for your pet!");
            }
        });
        lightningTrophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description.setText("Participate in at least 3 back-to-back collaborations \n(At least 15 mins in length and \nstarting within 15 mins of the end of one collab)");
            }
        });
        sunTrophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description.setText("Practice at least 15 mins. every day for two weeks!");
            }
        });
        clockTrophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description.setText("Meet your practice goal 10 times in a row!");
            }
        });
        bannerTrophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description.setText("Give 20 pieces of feedback!");
            }
        });
        starTrophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description.setText("Give your first performance!");
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rewards_both, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_rewards_both, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    RewardsShop rewards_shop = new RewardsShop();
                    return rewards_shop;
                case 1:
                    MyRewards my_rewards = new MyRewards();
                    return my_rewards;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }
}
