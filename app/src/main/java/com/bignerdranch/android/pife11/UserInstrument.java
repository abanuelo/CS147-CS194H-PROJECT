package com.bignerdranch.android.pife11;

public class UserInstrument {
    public String bass;
    public String drums;
    public String flute;
    public String guitar;
    public String piano;
    public String sing;
    public String viola;
    public String violin;

    public UserInstrument() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserInstrument(String bass, String drums, String flute, String guitar, String piano, String sing, String viola, String violin) {
        this.bass = bass;
        this.drums = drums;
        this.flute = flute;
        this.guitar = guitar;
        this.piano = piano;
        this.sing = sing;
        this.viola = viola;
        this.violin = violin;
    }
}

