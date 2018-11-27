package com.bignerdranch.android.pife11.Matches;

public class MatchesObject {
    private String userId;

    public MatchesObject (String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }
}
