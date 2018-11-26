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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    private String userId, name, username, profileImageURL, genres, instruments;
    private EditText nameEditText, usernameEditText, genreEditText, instrumentEditText;
    private ImageView profileImage;
    private FirebaseAuth auth;
    private DatabaseReference customerDatabase;
    private Button sign_out, schedule, history, feedback;
    private Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        customerDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        sign_out = findViewById(R.id.sign_out);
        feedback = findViewById(R.id.feedback);
        history = findViewById(R.id.history);
        schedule = findViewById(R.id.schedule);

        //The next step of this process is to autopopulate the edit texts based on the
        //current profile that we have in place to build the platform
        nameEditText = findViewById(R.id.name);
        usernameEditText = findViewById(R.id.username);
        genreEditText = findViewById(R.id.genre);
        instrumentEditText = findViewById(R.id.instrument);
        profileImage = findViewById(R.id.profile_image);


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
