package com.bignerdranch.android.pife11.SelectVideoOnProfile;

import android.content.Intent;
import android.icu.text.IDNA;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bignerdranch.android.pife11.GiveFeedback;
import com.bignerdranch.android.pife11.Profile;
import com.bignerdranch.android.pife11.R;
import com.bignerdranch.android.pife11.Store;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class SelectVideoOnProfile extends AppCompatActivity {

    private VideoView video;
    private StorageReference videoRef;
    private DatabaseReference userDatabase;
    private MediaController mediaController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_video_on_profile);

        //Get needed variables to Extract Video From FireBase
        Intent intent = getIntent();
        final String videoUserId = intent.getStringExtra("currentUserId");
        //6X6Eok5NaRNWYSArcoX4Q7qpoMv2
        final String currVideo = intent.getStringExtra("currentVideo");
        final String currentVideo = currVideo + ".mp4"; //test.3pg

        changeCoins();
        video = findViewById(R.id.videoView);

        video.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (video.isPlaying()) {
                    video.pause();
                }
                else {
                    video.start();
                }
                return false;
            }
        } );

        TextView tv = (TextView) findViewById(R.id.viewVideoTitle);
        tv.setText(intent.getStringExtra("title"));

        try{
            final File localFile = File.createTempFile(currentVideo, "mp4");
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            videoRef = storageRef.child("/videos/" + videoUserId + "/" + currentVideo);
            Toast t = Toast.makeText(this, "Video is loading...", Toast.LENGTH_LONG);
            t.show();
            videoRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    video.setVideoURI(Uri.fromFile(localFile));
                    video.start();
                }
            });
        } catch(Exception e){
            Log.d("Error", "This failed to download the video from Firebase");
            Toast.makeText(this, "Error downloading video from Firebase!", Toast.LENGTH_SHORT);
        }


        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(videoUserId);
        userDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String description = dataSnapshot.child("VideoInfo").child(currVideo).child("Info").getValue().toString();
                TextView vd = (TextView) findViewById(R.id.VideoDescription);
                vd.setText(description);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Button exit_btn = findViewById(R.id.select_video_exit);
        final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        TextView commentsDivider = (TextView) findViewById(R.id.DividerBetweenVideoAndComments);
        ListView listView = (ListView) findViewById(R.id.videoComments);
        final TextView nocomment = (TextView) findViewById(R.id.noComment);


        if (userid.equals(videoUserId)){
            ArrayList<Comment> arrayOfComments = new ArrayList<Comment>();
            final CommentAdapter adapter = new CommentAdapter(this, arrayOfComments);

            commentsDivider.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(adapter);

            final DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Comments").child(currVideo);

            //loop thru children

            matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot comment : dataSnapshot.getChildren()){
                        String date = comment.child("date").getValue().toString().trim();
                        String like = "I like " + comment.child("ilike").getValue().toString().trim();
                        String wish = "I wish " +comment.child("iwish").getValue().toString().trim();
                        String user = comment.child("user").getValue().toString().trim();
                        String userName = comment.child("username").getValue().toString().trim();

                        adapter.add(new Comment(userName, date, like, wish, user));
                    }

                    if (adapter.isEmpty()) {
                        nocomment.setVisibility(View.VISIBLE);
                    }else {
                        nocomment.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            exit_btn.setVisibility(View.GONE);

        } else {
            listView.setVisibility(View.GONE);
            commentsDivider.setVisibility(View.GONE);
            nocomment.setVisibility(View.GONE);


            exit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sign_out_intent = new Intent(SelectVideoOnProfile.this, GiveFeedback.class);
                    sign_out_intent.putExtra("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    sign_out_intent.putExtra("videoId", currVideo);
                    sign_out_intent.putExtra("videoUserId", videoUserId);
                    finish();
                    startActivity(sign_out_intent);
                }
            });
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


//    public void playVideo(View v) {
//        Log.d("PlayVideo", "PlayVideo is Working!!");
//        if (video.isPlaying()) return;
//        video.seekTo(0);
//        video.start();
//    }
}
