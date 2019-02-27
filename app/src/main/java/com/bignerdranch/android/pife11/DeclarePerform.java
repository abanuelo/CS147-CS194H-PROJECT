package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class DeclarePerform extends AppCompatActivity {
    private Button startPerform;
    private static final int REQUEST_CODE = 101;
    private Uri videoUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare_perform);
        startPerform = (Button) findViewById(R.id.startPerform);

        startPerform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record(view);
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationViewPerform);
        bottomNavigationView.setSelectedItemId(R.id.perform_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                                                     @Override
                                                                     public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                 switch (menuItem.getItemId()){
                     case R.id.practice_nav:
                         Intent practice_intent = new Intent(DeclarePerform.this, ChooseRoutineActivity.class);
                         startActivity(practice_intent);
                         break;
                     case R.id.friends_nav:
                         Intent collab_intent = new Intent(DeclarePerform.this, CollabHiFi2.class);
                         startActivity(collab_intent);
                         break;
                     case R.id.user_nav:
                         Intent profile_intent = new Intent(DeclarePerform.this, Profile.class);
                         startActivity(profile_intent);
                         break;
                 }
                 return true;
             }
         }
        );

        changeCoins();
    }

    public void record(View view){
        Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(cameraIntent, REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        try {
            videoUri = data.getData();
            Log.d("videoUri", videoUri.toString());
            if (videoUri != null){
                Intent sendRecording = new Intent(DeclarePerform.this, MyPerform.class);
                sendRecording.setData(videoUri);
                startActivity(sendRecording);
            }
        } catch (Exception ex){

        }
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
