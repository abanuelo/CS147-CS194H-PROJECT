package com.bignerdranch.android.pife11;

public class Cards {
    private String userId;
    private String name;
    private String profileImageURL;

    public Cards (String userId, String name, String profileImageURL){
        this.userId = userId;
        this.name = name;
        this.profileImageURL = profileImageURL;
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

    public void setProfileImageURL(String name){
        this.profileImageURL = profileImageURL;
    }


}
