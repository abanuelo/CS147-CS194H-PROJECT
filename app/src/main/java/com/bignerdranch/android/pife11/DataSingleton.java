package com.bignerdranch.android.pife11;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.bignerdranch.android.pife11.ViewerPagerCards.CardPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataSingleton {


    private static DatabaseReference userDatabase;
    private static FirebaseAuth auth;

    private static CardPagerAdapter allPossibleFriends = null;


    private static DataSingleton instance = new DataSingleton();



    public static DataSingleton getInstance() {
        auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();
        allPossibleFriends = new CardPagerAdapter();
        //The info of the actual user
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        if (avatarClothes == null) avatarClothes = new Pair(0, 0);
        return instance;
    }

    public static CardPagerAdapter getAllPossibleFriends() {
        return allPossibleFriends;
    }

    public static void setAllPossibleFriends(CardPagerAdapter allPossibleFriends) {
        DataSingleton.allPossibleFriends = allPossibleFriends;
    }

    //variables
    private ArrayList<ArrayList<String>> myListOfRoutines;

    public Pair getAvatarClothes() {
        return avatarClothes;
    }

    public void setAvatarClothes(Pair avatarClothes) {



        Map avatarInfo = new HashMap();
        avatarInfo.put("hat", avatarClothes.first);
        avatarInfo.put("shirt", avatarClothes.second);


        System.out.println("Debugging the avatarInfo: " + avatarInfo.toString());


        //Adding to firebase
        DatabaseReference routinesDB = userDatabase.child("AvatarClothes");
        routinesDB.updateChildren(avatarInfo);

        this.avatarClothes = avatarClothes;
    }

    private static Pair avatarClothes = null;

    public long getPracticeLength() {
        return practiceLength;
    }

    public void setPracticeLength(long practiceLength) {
        this.practiceLength = practiceLength;
    }

    private long practiceLength;

    private DataSingleton() {
        myListOfRoutines = new ArrayList<ArrayList<String>>();
        practiceLength = 0;
    }

    public ArrayList<ArrayList<String>> getRoutinesList() {
        //DatabaseReference routinesDB = userDatabase.child("Routines");
        //myListOfRoutines = new ArrayList<ArrayList<String>>();
        /*userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot curr: dataSnapshot.child("Routines").getChildren()) {*/
                    /*ArrayList<String> toBeAdded = new ArrayList<String>();
                    //System.out.println("Debugging the routines reading:" + curr.toString());
                    Map currRoutine = (Map) curr.getValue();
                    Iterator<Map.Entry<String, String>> itr = currRoutine.entrySet().iterator();
                    while(itr.hasNext()) {
                        Map.Entry<String, String> entry = itr.next();
                        toBeAdded.add(Integer.parseInt(entry.getKey()), entry.getValue());
                    }*/
                    /*myListOfRoutines.add((ArrayList<String>) curr.getValue());
                }
                System.out.println("Debugging the routines total:" + myListOfRoutines.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        //Get the routines from the database?

        //System.out.println("Debugging the routines... finishing before the callback?");
        return myListOfRoutines;
    }

    public void setRoutinesList(ArrayList<ArrayList<String>> arr) {

        //Creating the routines to be added to firebase
        Map routinesInfo = new HashMap();
        for (ArrayList<String> curr: arr) {
            Map currMapified = new HashMap();
            //currMapified.put("length", curr.size());
            for (int i = 0; i < curr.size(); i++) {
                currMapified.put(Integer.toString(i), curr.get(i));
            }
            routinesInfo.put(curr.get(0), currMapified);
        }
        System.out.println("Debugging the routinesInfo: " + routinesInfo.toString());

        //Adding to firebase
        DatabaseReference routinesDB = userDatabase.child("Routines");
        routinesDB.updateChildren(routinesInfo);

        myListOfRoutines = arr;
    }
}
