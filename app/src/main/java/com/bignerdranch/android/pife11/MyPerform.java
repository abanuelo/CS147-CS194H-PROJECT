package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class MyPerform extends AppCompatActivity {

    private Button upload;
    private Button rerecord;
    private ProgressBar pbar;
    private VideoView video;
    private Uri videoUri;
    private FirebaseAuth auth;
    private StorageReference videoRef;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //UploadTask uploadTask = videoRef.putFile(videoUri);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_perform);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        videoRef = storageRef.child("/videos/" + uid + "/test.3gp");
//        String videoUrl = getIntent().getStringExtra("videoUri");
//        videoUri = Uri.parse(videoUrl);
        videoUri = getIntent().getData();
        Log.d("MyPerformUri", videoUri.toString());
        pbar = (ProgressBar) findViewById(R.id.pbar);
        video = (VideoView) findViewById(R.id.video);
        rerecord = (Button) findViewById(R.id.record);
        upload = (Button) findViewById(R.id.upload);
        video.setVideoURI(videoUri);
        video.start();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();//Invisible in the backend development of the app
                Intent sendPerformance = new Intent(MyPerform.this, SendPerform.class);
                startActivity(sendPerformance);
            }
        });

        rerecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record(view);
            }
        });



        //record(view);
        //download(view);
//        video.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                video.start();
//            }
//        });
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

//    public void download(View view){
//        try{
//            final File localfile = File.createTempFile("test", "3gp");
//            videoRef.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    video = (VideoView) findViewById(R.id.video);
//                    video.setVideoURI(Uri.fromFile(localfile));
//                    video.start();
//                }
//            });
//        } catch(Exception e){
//
//        }
//    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        try {
//            videoUri = data.getData();
//            UploadTask uploadTask = videoRef.putFile(videoUri);
//            //Error Checking Performed For Video Upload Feature
//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                    updateProgress(taskSnapshot);
//                }
//            });
//        } catch (Exception ex){
//
//        }
//    }
}
