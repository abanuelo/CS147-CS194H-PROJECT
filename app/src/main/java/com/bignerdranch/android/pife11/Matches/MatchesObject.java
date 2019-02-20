package com.bignerdranch.android.pife11.Matches;

import com.bignerdranch.android.pife11.R;

public class MatchesObject {
    private String userId;
    private String name;
    private String profileImageURL;
    private int checkVisibility;

    public MatchesObject (String userId, String name, String profileImageURL, int checkVisibility){
        this.userId = userId;
        this.name = name;
        this.profileImageURL = profileImageURL;
        this.checkVisibility = checkVisibility;
    }

    public int getCheckVisibility(){
        return checkVisibility;
    }

    public void setCheckVisibility(int checkVisibility) {
        this.checkVisibility = checkVisibility;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getProfileImageURL(){
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL){
        this.profileImageURL = profileImageURL;
    }
}
