package com.bignerdranch.android.pife11;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.pife11.Matches.Matches;
import com.bignerdranch.android.pife11.Scheduler.MondaySchedule;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class Profile extends AppCompatActivity {
    private String userId;
    private TextView name, username, genre, instrument;
    private FirebaseAuth auth;
    private DatabaseReference userDatabase;
    private Button sign_out;
    private boolean practiceBool, performBool, collabBool,dressed;
    private GifImageView animation;
    private GifDrawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationViewPerform);
        bottomNavigationView.setSelectedItemId(R.id.user_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                                                     @Override
                                                                     public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                                                                         switch (menuItem.getItemId()){
                                                                             case R.id.practice_nav:
                                                                                 Intent practice_intent = new Intent(Profile.this, ChooseRoutineActivity.class);
                                                                                 startActivity(practice_intent);
                                                                                 break;
                                                                             case R.id.perform_nav:
                                                                                 Intent perform_intent = new Intent(Profile.this, peform.class);
                                                                                 startActivity(perform_intent);
                                                                                 break;
                                                                             case R.id.friends_nav:
                                                                                 Intent collab_intent = new Intent(Profile.this, CollabHiFi2.class);
                                                                                 startActivity(collab_intent);
                                                                                 break;
                                                                         }
                                                                         return true;
                                                                     }
                                                                 }
        );

//        Button rewardShop = findViewById(R.id.shop_button);
//        rewardShop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent rewards_intent = new Intent(Profile.this, RewardsBoth.class);
//                startActivity(rewards_intent);
//            }
//        });

        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        //Initialize the Buttons for the User Profile
//        sign_out = (Button) findViewById(R.id.sign_out);

        //Initalize the Text Views
//        name = (TextView) findViewById(R.id.name);
//        username = (TextView) findViewById(R.id.username);
//        genre = (TextView) findViewById(R.id.genre);
//        instrument = (TextView) findViewById(R.id.instrument);

        //Now we are going to iterate over FirebaseDatabase to populate TextViews
//        userDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                //Gets the Name and Inserts it within TextView
//                String t_name = dataSnapshot.child("name").getValue().toString().trim();
//                name.setText(t_name);
//
//                //Gets the Username and inserts it within textView
//                String t_username = dataSnapshot.child("username").getValue().toString().trim();
//                username.setText(t_username);
//
//                //Gets the Instruments to Populate the Instruments
//                String t_instruments = null;
//                for (DataSnapshot instrument : dataSnapshot.child("Years").getChildren()){
//                    if (t_instruments == null){
//                        t_instruments = instrument.getKey() + ": " + instrument.getValue().toString().trim();
//                    } else {
//                        t_instruments += ", " + instrument.getKey() + ": " + instrument.getValue().toString().trim();
//                    }
//                }
//                instrument.setText(t_instruments);
//
//                //Lastly we are going to populate the Genres Category
//                String t_genres = null;
//                for (DataSnapshot genre : dataSnapshot.child("Genres").getChildren()){
//                    if ((boolean) genre.getValue() == true){
//                        if (t_genres == null){
//                            t_genres = genre.getKey();
//                        } else {
//                            t_genres += ", " + genre.getKey();
//                        }
//                    }
//                }
//                genre.setText(t_genres);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



//        //Event that Initiates the Sign Out Process for the Profile Image
//        sign_out.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                auth.signOut();
//                Intent sign_out_intent = new Intent(Profile.this, MainActivity.class);
//                startActivity(sign_out_intent);
//                finish();
//            }
//        });

//        checkIfTaskComplete();
    }

//    private void updateAnimation(){
//        Log.i("Stats:", "Truths: " + practiceBool + performBool + collabBool);
//        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//        if (practiceBool && performBool && collabBool) {
//            //Evolve the creature
//            try{
//                //do the dialog
//                if (dressed) {
//                    drawable = new GifDrawable(getResources(), R.drawable.jemi_dressed_toddler);
//                }
//                else{
//
//                    //check if avatar ==toddler //if not evolveMessage // if so do what is below
//                    drawable = new GifDrawable(getResources(), R.drawable.jemi_plain_toddler);
//                    FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Avatar").setValue("toddler");
//
//                }
//            }
//            catch (IOException ie) {
//                Toast.makeText(getApplicationContext(),"Error with Gif",Toast.LENGTH_LONG).show();
//            }
//        }
//        else {
//
//            //Adding Gifs into the Code Content
//            try {
//                if (!practiceBool) {
//                    drawable = new GifDrawable(getResources(), R.drawable.jemi_happy);
//                }
//                else {
//                    drawable = new GifDrawable(getResources(), R.drawable.jemi_sad);
//                }
//
//
//            } catch (IOException ie) {
//                Toast.makeText(getApplicationContext(),"Error with Gif",Toast.LENGTH_LONG).show();
//                //Catch the IO Exception in case of getting an hour
//            }
//        }
//
////        drawable.setLoopCount(0);
////        animation = (GifImageView) findViewById(R.id.animation);
////        animation.setBackground(drawable);
//    }

//    private void checkIfTaskComplete(){
//
//        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats");
//        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//                    for (DataSnapshot match: dataSnapshot.getChildren()){
//                        Log.i("Stats:", match.getKey() + ": " + match.getValue().toString());
//                        if (match.getKey().equals("practice")) {
//                            int hl = Integer.parseInt(match.getValue().toString().trim());
//                            if(hl == 1) practiceBool = true;
//                        }
//                        if (match.getKey().equals("perform")) {
//                            int hl = Integer.parseInt(match.getValue().toString().trim());
//                            if(hl == 1) performBool = true;
//                        }
//                        if (match.getKey().equals("collab")) {
//                            int hl = Integer.parseInt(match.getValue().toString().trim());
//                            if(hl == 1) collabBool = true;
//                        }
//                        if (match.getKey().equals("dressed")) {
//                            String dres = match.getValue().toString();
//                            if(dres.equals("true")) dressed = true;
//                        }
//                    }
//                    updateAnimation();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }



//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
//            final Uri image_uri = data.getData();
//            resultUri = image_uri;
//            profileImage.setImageURI(resultUri);
//        }
//    }
}
