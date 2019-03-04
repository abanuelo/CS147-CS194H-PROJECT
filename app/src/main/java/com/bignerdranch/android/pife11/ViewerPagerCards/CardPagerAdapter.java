package com.bignerdranch.android.pife11.ViewerPagerCards;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.pife11.DataSingleton;
import com.bignerdranch.android.pife11.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    private DatabaseReference usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String currentUId = auth.getCurrentUser().getUid();

    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    public CardItem getCardItemAt(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    public Runnable refreshPage;

    public void setRunnable(Runnable r) {
        refreshPage = r;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        final CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        final Button button = view.findViewById(R.id.collab_button);
        //final TextView name = view.findViewById(R.id.name);
        button.setBackgroundColor(Color.parseColor("#00574B"));
        final String name = mData.get(position).getTitle();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usersDb.addValueEventListener(new ValueEventListener() {
                    boolean hasChanged = false;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String key = FirebaseDatabase.getInstance().getReference().child("Chats").push().getKey();
                        if (!hasChanged) {
                            for (DataSnapshot user : dataSnapshot.getChildren()) {
                                String users_name = user.child("username").getValue().toString().trim();
                                if (users_name.equals(name)) {
                                    String userId = user.getKey().trim();
                                    usersDb.child(currentUId).child("Collaborations").child("Yes").child(userId);
                                    usersDb.child(userId).child("Collaborations").child("Matches").child(currentUId).child("ChatID").setValue(key);
                                    usersDb.child(currentUId).child("Collaborations").child("Matches").child(userId).child("ChatID").setValue(key);
                                    hasChanged = true;
                                    Log.d("Hello", "Here");
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                button.setText("Collab Sent");
                button.setBackgroundColor(Color.parseColor("#A9A9A9"));
                //cardView.setVisibility(View.GONE);
                destroyItem(container, position, cardView);
                refreshPage.run();


            }
        });

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mViews.remove(position);
        mData.remove(position);
        //mViews.set(position, null);
    }

    private void bind(CardItem item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.name);
        TextView Genre = (TextView) view.findViewById(R.id.genre);
        TextView Years = (TextView) view.findViewById(R.id.years);
        TextView Instruments = (TextView) view.findViewById(R.id.instruments);
        TextView GenreText = (TextView) view.findViewById(R.id.GenreTitle);
        TextView YearsText = (TextView) view.findViewById(R.id.YearsTitle);
        TextView InstrumentText = (TextView) view.findViewById(R.id.InstrumentTitle);


        titleTextView.setText(item.getTitle());
        Genre.setText(item.getGenre());
        Years.setText(item.getYears());
        Instruments.setText(item.getInstruments());
        GenreText.setText(item.getGenreText());
        YearsText.setText(item.getYearsText());
        InstrumentText.setText(item.getInstrumentsText());


        changeAvatarClothes(item, view);
    }



    private void changeAvatarClothes(CardItem item, View view){
        final View viewForAvatar = view;
        String currentUserId = item.getUserId();
        DatabaseReference avatarDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("AvatarClothes");
        avatarDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    System.out.println("Datasnapshot:" + dataSnapshot.getValue().toString());

                    Long hatL = (Long) dataSnapshot.child("hat").getValue();
                    int hat = hatL.intValue();
                    System.out.println("What do we have here: hat OG: " + hat);
                    Long shirtL = (Long) dataSnapshot.child("shirt").getValue();
                    int shirt = shirtL.intValue();
                    System.out.println("What do we have here: shirt OG: " + shirt);

                    //DataSingleton ds = DataSingleton.getInstance();
                    //ds.setAvatarClothes(new Pair(hat, shirt));

                    changeHat(hat, viewForAvatar);
                    changeShirt(shirt, viewForAvatar);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void changeHat(int hat, View view){
        System.out.println("What do we have here: hat: " + hat);
        // Table of contents: 0 - no hat, 1 - blue, 2 - orange, 3 - pink, 4 - yellow
        ImageView hatImg = (ImageView) view.findViewById(R.id.jemiTop);
        Drawable myDraw = view.getResources().getDrawable(R.drawable.yellowtoptrans);

        if (hat == 0) {
            myDraw = view.getResources().getDrawable(R.drawable.undressedtoptrans);
        } else if (hat == 1) {
            myDraw = view.getResources().getDrawable(R.drawable.bluetoptrans);
        } else if (hat == 2) {
            myDraw = view.getResources().getDrawable(R.drawable.orangetoptrans);
        } else if (hat == 3) {
            myDraw = view.getResources().getDrawable(R.drawable.pinktoptrans);
        }
        hatImg.setImageDrawable(myDraw);
    }

    private void changeShirt(int shirt, View view) {
        System.out.println("What do we have here: shirt: " + shirt);
        // Table of contents: 0 - no shirt, 1 - green, 2 - pink, 3 - yellow, 4 - brown
        ImageView shirtImg = (ImageView) view.findViewById(R.id.jemiBottom);

        Drawable myDraw = view.getResources().getDrawable(R.drawable.brownbottomtrans);

        if (shirt == 0) {
            myDraw = view.getResources().getDrawable(R.drawable.undressedbottomtrans);
        } else if (shirt == 1) {
            myDraw = view.getResources().getDrawable(R.drawable.greenbottomtrans);
        } else if (shirt == 2) {
            myDraw = view.getResources().getDrawable(R.drawable.pinkbottomtrans);
        } else if (shirt == 3) {
            myDraw = view.getResources().getDrawable(R.drawable.yellowbottomtrans);
        }
        shirtImg.setImageDrawable(myDraw);

    }
}