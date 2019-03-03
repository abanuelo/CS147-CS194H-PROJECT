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


//        String videoUrl = getIntent().getStringExtra("videoUri");
//        videoUri = Uri.parse(videoUrl);
        //videoUri = getIntent().getData();
//        Log.d("MyPerformUri", videoUri.toString());
  //      Log.d("FilePath", videoUri.getEncodedPath());

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

    public void upload(){
        UploadTask uploadTask = videoRef.putFile(videoUri);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                updateProgress(taskSnapshot);

            }
        });

        //upload thumbnail
//        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//        StorageReference imageRef = storageRef.child("/videoThumbnails/" + uid + "/" + videoId + ".jpg");
//
//        Uri file = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.drawable.baby);
//        uploadTask = imageRef.putFile(file);
//
//        // Register observers to listen for when the download is done or if it fails
//                uploadTask.addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle unsuccessful uploads
//                    }
//                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
//                        // ...
//                    }
//                });

    }

    public void updateProgress(UploadTask.TaskSnapshot taskSnapshot){
        long fileSize = taskSnapshot.getTotalByteCount();
        long uploadBytes = taskSnapshot.getBytesTransferred();
        long progress = (100 * uploadBytes) / fileSize;
        pbar.setProgress((int) progress);
    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "test.3gp", null);
        return Uri.parse(path);
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
