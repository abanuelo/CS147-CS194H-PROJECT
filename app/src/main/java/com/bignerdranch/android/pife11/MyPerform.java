package com.bignerdranch.android.pife11;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class MyPerform extends AppCompatActivity {

    private ImageView pic;
    private Button upload;
    private Button rerecord;
    private ProgressBar pbar;
    private VideoView video;
    private Uri videoUri;
    private FirebaseAuth auth;
    private StorageReference videoRef;
    private String videoId;
    private String uid;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //UploadTask uploadTask = videoRef.putFile(videoUri);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_perform);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        String currentTime = Calendar.getInstance().getTime().toString();
        currentTime = currentTime.replaceAll(":","");
        currentTime = currentTime.replaceAll("\\s","");
        currentTime = currentTime.toLowerCase();
        videoId = currentTime;

        //WE WANT TO BE ABLE TO RANDOMIZE THESE LINKS TO GET MULTIPLE LINKS
        videoRef = storageRef.child("/videos/" + uid + "/" + currentTime+ ".3gp");


        pbar = (ProgressBar) findViewById(R.id.pbar);
        video = (VideoView) findViewById(R.id.video);
        rerecord = (Button) findViewById(R.id.record);
        upload = (Button) findViewById(R.id.upload);
        record(getCurrentFocus());
        //video.setVideoURI(videoUri);
        //video.start();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoUri == null){
                    Toast.makeText(MyPerform.this, "No Video To Upload! Please press re-record.", Toast.LENGTH_SHORT).show();
                } else {
                    upload();//Invisible in the backend development of the app
                    Intent sendPerformance = new Intent(MyPerform.this, UploadThumbnail.class);
                    sendPerformance.putExtra("videoId", videoId);
                    startActivity(sendPerformance);
                }
            }
        });

        rerecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record(view);
            }
        });

    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    public void upload(){
        UploadTask uploadTask = videoRef.putFile(videoUri);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                updateProgress(taskSnapshot);

            }
        });

    }


    public void updateProgress(UploadTask.TaskSnapshot taskSnapshot){
        long fileSize = taskSnapshot.getTotalByteCount();
        long uploadBytes = taskSnapshot.getBytesTransferred();
        long progress = (100 * uploadBytes) / fileSize;
        pbar.setProgress((int) progress);
    }

    public void record(View view){
        Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(cameraIntent, REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        try {
            videoUri = data.getData();
            video.setVideoURI(videoUri);
            video.start();
        } catch (Exception ex){

        }
    }

}
