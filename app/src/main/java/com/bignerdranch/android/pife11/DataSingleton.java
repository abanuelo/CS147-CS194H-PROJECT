package com.bignerdranch.android.pife11;

import android.support.annotation.NonNull;

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

    private static DataSingleton instance = new DataSingleton();

    //variables
    private static ArrayList<ArrayList<String>> myListOfRoutines;

    public static DataSingleton getInstance() {

        auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();

        //The info of the actual user
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        //myListOfRoutines = new ArrayList<ArrayList<String>>();

        userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot curr: dataSnapshot.child("Routines").getChildren()) {
                    myListOfRoutines.add((ArrayList<String>) curr.getValue());
                }
                System.out.println("Debugging the routines total:" + myListOfRoutines.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //userDatabase = auth.getCurrentUser();
        /* This is for updating & creating the user info
        Map userInfo = new HashMap();
        userInfo.put("name", t_name);
        userInfo.put("username", t_username);
        userInfo.put("email", t_email);
        userInfo.put("password", t_password);
        userDatabase.updateChildren(userInfo);*/

        return instance;
    }



    private DataSingleton() {
        myListOfRoutines = new ArrayList<ArrayList<String>>();
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

        System.out.println("Debugging the routines... finishing before the callback?");
        return myListOfRoutines;
    }

    public void setRoutinesList(ArrayList<ArrayList<String>> arr) {
        myListOfRoutines = arr;



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


        /*Map userInfo = new HashMap();
        userInfo.put("Routines", routinesInfo);
        userDatabase.push().setValue(userInfo);*/


        /* This is for updating & creating the user info
        Map userInfo = new HashMap();
        userInfo.put("name", t_name);
        userInfo.put("username", t_username);
        userInfo.put("email", t_email);
        userInfo.put("password", t_password);
        userDatabase.push().setValue(userInfo);*/
    }
}
