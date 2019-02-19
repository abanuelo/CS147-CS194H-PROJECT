package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class Video extends AppCompatActivity {

    private VideoView video;
    private Button feedback;
    private StorageReference videoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        video = findViewById(R.id.video);
        feedback = findViewById(R.id.feedback);

        try{
            final File localFile = File.createTempFile("test", "3gp");
            String matchId = getIntent().getExtras().getString("matchId");
            Log.d("MATCHID", matchId);
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            videoRef = storageRef.child("/videos/" + matchId + "/test.3gp");
            videoRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.d("Success", "here");
                    video.setVideoURI(Uri.fromFile(localFile));
                    video.start();
                }
            });
        } catch (Exception e){

        }

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giveFeedback = new Intent(Video.this, GiveFeedback.class);
                startActivity(giveFeedback);
            }
        });
    }
}
