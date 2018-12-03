package com.bignerdranch.android.pife11;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

public class Profile extends AppCompatActivity {
    private String userId;
    private TextView name, username, genre, instrument;
    private CircleImageView profileImage;
    private FirebaseAuth auth;
    private DatabaseReference userDatabase;
    private Button sign_out, schedule, history, matches;
    private Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        //Initialize the Buttons for the User Profile
        sign_out = (Button) findViewById(R.id.sign_out);
        matches = (Button) findViewById(R.id.matches);
        history = (Button) findViewById(R.id.history);
        schedule = (Button) findViewById(R.id.schedule);

        //Initalize the Text Views
        name = (TextView) findViewById(R.id.name);
        username = (TextView) findViewById(R.id.username);
        genre = (TextView) findViewById(R.id.genre);
        instrument = (TextView) findViewById(R.id.instrument);

        //Sets the Profile picture Ready for the Jave Profile Class
        profileImage = (CircleImageView) findViewById(R.id.profile_image);

        //Clicking into the Schedule
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setSchedule = new Intent(Profile.this, MondaySchedule.class);
                startActivity(setSchedule);
            }
        });

        //Now we are going to iterate over FirebaseDatabase to populate TextViews
        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Gets the Name and Inserts it within TextView
                String t_name = dataSnapshot.child("name").getValue().toString().trim();
                name.setText(t_name);

                //Gets the Username and inserts it within textView
                String t_username = dataSnapshot.child("username").getValue().toString().trim();
                username.setText(t_username);

                //Gets the Instruments to Populate the Instruments
                String t_instruments = null;
                for (DataSnapshot instrument : dataSnapshot.child("Years").getChildren()){
                    if (t_instruments == null){
                        t_instruments = instrument.getKey() + ": " + instrument.getValue().toString().trim();
                    } else {
                        t_instruments += ", " + instrument.getKey() + ": " + instrument.getValue().toString().trim();
                    }
                }
                instrument.setText(t_instruments);

                //Lastly we are going to populate the Genres Category
                String t_genres = null;
                for (DataSnapshot genre : dataSnapshot.child("Genres").getChildren()){
                    if ((boolean) genre.getValue() == true){
                        if (t_genres == null){
                            t_genres = genre.getKey();
                        } else {
                            t_genres += ", " + genre.getKey();
                        }
                    }
                }
                genre.setText(t_genres);

                //P.S. we also need the image in case they have it
                if (!dataSnapshot.child("profileImageURL").getValue().toString().trim().equals("default")){
                    Glide.with(getApplicationContext()).load(dataSnapshot.child("profileImageURL").getValue().toString().trim()).into(profileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Now whenever you click the profile image, you should in theory make sure that it takes them to
        //their art gallery
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });


        //Event that Initiates the Sign Out Process for the Profile Image
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent sign_out_intent = new Intent(Profile.this, MainActivity.class);
                startActivity(sign_out_intent);
                finish();
            }
        });


        //This is going to be the click listener associated for all matches
        matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent see_matches = new Intent(Profile.this, Matches.class);
                startActivity(see_matches);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            final Uri image_uri = data.getData();
            resultUri = image_uri;
            profileImage.setImageURI(resultUri);
        }
    }
}
