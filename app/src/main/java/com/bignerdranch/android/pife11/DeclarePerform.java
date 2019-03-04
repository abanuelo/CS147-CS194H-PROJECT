package com.bignerdranch.android.pife11;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class DeclarePerform extends AppCompatActivity {
    private Button Finish;
    private AppCompatEditText title, info, genre, instrument;
    private String uid, videoId;
    private DatabaseReference userDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare_perform);
        Finish = (Button) findViewById(R.id.submitPerform);
        title = findViewById(R.id.TitleText);
        info = findViewById(R.id.InfoText);
        genre = findViewById(R.id.GenreText);
        instrument = findViewById(R.id.InstrumentText);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("VideoInfo").child(videoId);
        videoId = getIntent().getStringExtra("videoId");

        //Need to grab the text files from these EditTexts and insert them into Firebase?

        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(title.getText().toString())){
                    title.setError("Please insert title for your performance.");
                    return;
                }
                if (TextUtils.isEmpty(info.getText().toString())){
                    info.setError("Please insert info about your performance.");
                    return;
                }
                if (TextUtils.isEmpty(genre.getText().toString())){
                    genre.setError("Please insert genre of your performance");
                    return;
                }
                if (TextUtils.isEmpty(instrument.getText().toString())){
                    instrument.setError("Please specify the instrument you used during your performance.");
                    return;
                }

                insertDataToFirebase();

                Intent sendToFriends = new Intent(DeclarePerform.this, SendPerform.class);

                finish();
                startActivity(sendToFriends);
            }
        });

    }

    private void insertDataToFirebase(){
        HashMap map = new HashMap();
        map.put("Title", title.getText().toString());
        map.put("Info", info.getText().toString());
        map.put("Genre", genre.getText().toString());
        map.put("Instrument", instrument.getText().toString());
        userDatabase.updateChildren(map);
    }



    public void changeCoins(){
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("xp");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String pifePoints = dataSnapshot.getValue().toString().trim();
                    TextView pointsDisplay = (TextView) findViewById(R.id.coins);
                    pointsDisplay.setText(pifePoints);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void goToStore(View view) {
        Intent practice_intent = new Intent(this, Store.class);
        startActivity(practice_intent);
    }


}
