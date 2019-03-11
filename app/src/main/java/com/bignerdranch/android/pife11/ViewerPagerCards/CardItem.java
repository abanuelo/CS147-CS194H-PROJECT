package com.bignerdranch.android.pife11.ViewerPagerCards;

public class CardItem {

    //private String mTextResource;
    private String mTitleResource;
    private String mGenreResourceText;
    private String mYearsResourceText;
    private String mInstrumentsResourceText;
    private String userId;
    private String hrsPractice, Coins, Performances, Feedback;

    public CardItem(String title, String genreText, String yearsText, String instrumentsText, String userId, String hrsPractice, String Coins, String Performances, String Feedback) {
        mTitleResource = title;
        mGenreResourceText = genreText;
        mYearsResourceText = yearsText;
        mInstrumentsResourceText = instrumentsText;
        this.userId = userId;
        this.hrsPractice = hrsPractice;
        this.Coins = Coins;
        this.Performances = Performances;
        this.Feedback = Feedback;

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
    public String getHrsPractice(){
        return hrsPractice;
    }

    public String getFeedback(){
        return Feedback;
    }

    public String getCoins(){
        return Coins;
    }

    public String getPerformances(){
        return Performances;
    }

    public String getTitle() {
        return mTitleResource;
    }

    public String getUserId() {return userId;}
}