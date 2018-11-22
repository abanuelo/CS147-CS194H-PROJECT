package com.bignerdranch.android.pife11;

public class UserGenres {
    public String blues;
    public String classical;
    public String country;
    public String edm;
    public String hiphop;
    public String jazz;
    public String pop;
    public String rock;

    public UserGenres() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserGenres(String blues, String classical, String country, String edm, String hiphop, String jazz, String pop, String rock) {
        this.blues = blues;
        this.classical = classical;
        this.country = country;
        this.edm = edm;
        this.hiphop = hiphop;
        this.jazz = jazz;
        this.pop = pop;
        this.rock = rock;
    }
}
