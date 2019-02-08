package com.bignerdranch.android.pife11;

import java.util.ArrayList;

public class DataSingleton {

    private static DataSingleton instance = new DataSingleton();

    public static DataSingleton getInstance() {
        return instance;
    }

    //variables
    ArrayList<ArrayList<String>> myListOfRoutines;

    private DataSingleton() {
        myListOfRoutines = new ArrayList<ArrayList<String>>();
    }

    public ArrayList<ArrayList<String>> getRoutinesList() {
        return myListOfRoutines;
    }

    public void setRoutinesList(ArrayList<ArrayList<String>> arr) {
        myListOfRoutines = arr;
    }
}
