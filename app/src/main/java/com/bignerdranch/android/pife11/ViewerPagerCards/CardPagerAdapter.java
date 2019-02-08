package com.bignerdranch.android.pife11.ViewerPagerCards;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.pife11.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;

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

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(CardItem item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView Genre = (TextView) view.findViewById(R.id.GenreTitle);
        TextView Years = (TextView) view.findViewById(R.id.YearsTitle);
        TextView Instruments = (TextView) view.findViewById(R.id.InstrumentTitle);
        TextView GenreText = (TextView) view.findViewById(R.id.genre);
        TextView YearsText = (TextView) view.findViewById(R.id.years);
        TextView InstrumentText = (TextView) view.findViewById(R.id.instruments);


        titleTextView.setText(item.getTitle());
        Genre.setText(item.getGenre());
        Years.setText(item.getYears());
        Instruments.setText(item.getInstruments());
        GenreText.setText(item.getGenreText());
        YearsText.setText(item.getYearsText());
        InstrumentText.setText(item.getInstrumentsText());
    }

}