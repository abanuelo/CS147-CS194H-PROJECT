package com.bignerdranch.android.pife11.ViewerPagerCards;

public class CardItem {

    //private String mTextResource;
    private String mTitleResource;
    private String mGenreResource;
    private String mYearsResource;
    private String mInstrumentsResource;
    private String mGenreResourceText;
    private String mYearsResourceText;
    private String mInstrumentsResourceText;

    public CardItem(String title, String genre, String years, String instruments, String genreText, String yearsText, String instrumentsText) {
        mTitleResource = title;
        //mTextResource = text;
        mGenreResource = genre;
        mYearsResource = years;
        mInstrumentsResource = instruments;
        mGenreResourceText = genreText;
        mYearsResourceText = yearsText;
        mInstrumentsResourceText = instrumentsText;
    }

    public String getGenreText(){
        return mGenreResourceText;
    }

    public String getYearsText(){
        return mYearsResourceText;
    }

    public String getInstrumentsText(){
        return mInstrumentsResourceText;
    }

    //Make this method more general
    public String getGenre(){
        return mGenreResource;
    }

    public String getYears(){
        return mYearsResource;
    }

    public String getInstruments(){
        return mInstrumentsResource;
    }

    public String getTitle() {
        return mTitleResource;
    }
}