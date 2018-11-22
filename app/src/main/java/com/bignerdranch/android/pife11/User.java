package com.bignerdranch.android.pife11;

public class User {
    public String name;
    public String username;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String username) {
        this.name = name;
        this.username = username;
    }

}
