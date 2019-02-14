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
import java.util.Map;

public class DataSingleton {


    private static DatabaseReference userDatabase;
    private static FirebaseAuth auth;

    private static DataSingleton instance = new DataSingleton();

    public static DataSingleton getInstance() {

        auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();

        //The info of the actual user
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

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

    //variables
    ArrayList<ArrayList<String>> myListOfRoutines;

    private DataSingleton() {
        myListOfRoutines = new ArrayList<ArrayList<String>>();
    }

    public ArrayList<ArrayList<String>> getRoutinesList() {
        DatabaseReference routinesDB = userDatabase.child("Routines");
        /*routinesDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        //Get the routines from the database?

        return myListOfRoutines;
    }

    public void setRoutinesList(ArrayList<ArrayList<String>> arr) {
        myListOfRoutines = arr;



        //Creating the routines to be added to firebase
        Map routinesInfo = new HashMap();
        for (ArrayList<String> curr: arr) {
            Map currMapified = new HashMap();
            currMapified.put("length", curr.size());
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
