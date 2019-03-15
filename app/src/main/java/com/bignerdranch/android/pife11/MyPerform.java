package com.bignerdranch.android.pife11;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MyPerform extends AppCompatActivity {
    private Button Finish;
    private AppCompatEditText title, info;
    private String uid, videoId;
    private DatabaseReference userDatabase0, userDatabase;
    private ImageView pic;
    private Button upload, cancel;
    private Button rerecord;
    private ProgressBar pbar;
    private VideoView video;
    private Uri videoUri;
    private FirebaseAuth auth;
    private StorageReference videoRef;
    //private Button upload, next;
    private ImageView thumbnail;
    private int SELECT_FILE = 1;
    //private String videoId, uid;
    private Bitmap bm = null;
    private Uri thumbnailURI = null;
    private Uri imageUri = null;
    //private String videoId;
    //private String uid;
    private static int REQUEST_CODE = 101;
    private static int REQUEST_PHOTO = 102;

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
        videoRef = storageRef.child("/videos/" + uid + "/" + currentTime + ".3gp");


        Finish = (Button) findViewById(R.id.submitPerform);
        title = findViewById(R.id.TitleText);
        info = findViewById(R.id.InfoText);

        /*uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //videoId = getIntent().getStringExtra("videoId");
        userDatabase0 = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("VideoInfo");
        System.out.println("MOOOOO: " + videoId);
        userDatabase = userDatabase0.child(videoId);*/

        upload = findViewById(R.id.upload_thumb);
        //next = findViewById(R.id.next);
        thumbnail = findViewById(R.id.thumbnail);
//        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.play);
//        thumbnail.setImageBitmap(icon);
//        thumbnail.setImageResource(R.drawable.play);
//        Bitmap bmToInsert = ((BitmapDrawable)thumbnail.getDrawable()).getBitmap();
//        thumbnail.setImageBitmap(bmToInsert);
//        imageUri = getImageUri(getApplicationContext(), bmToInsert);
        cancel = findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToProfile = new Intent(MyPerform.this, Profile.class);
                startActivity(goToProfile);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //thumbnail.setBackground(null);
                galleryIntent();
            }
        });

        /*next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/


        //Need to grab the text files from these EditTexts and insert them into Firebase?

        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean hasError = false;

//                if (thumbnail.getBackground() != null){
//                    thumbnail.setImageResource(R.drawable.play);
//                }

                if (TextUtils.isEmpty(title.getText().toString())){
                    title.setError("Please insert title for your performance.");
                    hasError = true;
                }
                if (TextUtils.isEmpty(info.getText().toString())){
                    info.setError("Please insert info about your performance.");
                    hasError = true;
                }
                if (hasError) return;

                uploadToBackend();

                insertDataToFirebase();

                Intent sendToFriends = new Intent(MyPerform.this, Profile.class);

                //uploadToBackend();

                finish();
                startActivity(sendToFriends);
            }
        });


        record(getCurrentFocus());
        changeCoins();

        // THIS IS THE CODE CORRESPONDING WITH THE OLD STUFF!
        /*pbar = (ProgressBar) findViewById(R.id.pbar);
        video = (VideoView) findViewById(R.id.video);
        rerecord = (Button) findViewById(R.id.record);
        upload = (Button) findViewById(R.id.upload);
        record(getCurrentFocus());

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
        });*/

    }

    private void uploadToBackend(){
        //Firebase Storage Variables
        System.out.println("MOOOO DO we get here?");
        //videoId = getIntent().getStringExtra("videoId");
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference videoRef = storageRef.child("/videos/" + uid + "/" + videoId + ".mp4");
        StorageReference imageRef = storageRef.child("/videoThumbnails/" + uid + "/" + videoId + ".jpg");

        Uri video_file = videoUri;

        Uri img_file = null;
        if (thumbnailURI == null){
            img_file = Uri.parse("android.resource://com.bignerdranch.android.pife11/drawable/play");
        } else {
            img_file = thumbnailURI;
        }

        UploadTask uploadTask = videoRef.putFile(video_file);
        UploadTask uploadTask2 = imageRef.putFile(img_file);

        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });


        //upload connection btn two
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Videos");
        matchDb.child(videoId).setValue(videoId);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PHOTO){
            onSelectFromGalleryResult(data);
            Log.d("Arrived at this activity!", "here");
        }else {
            try {
                videoUri = data.getData();
                Log.d("CURRENT VIDEO URI", videoUri.toString());

                if (videoUri.toString() == null) {
                    Intent profile = new Intent(this, Profile.class);
                    startActivity(profile);
                }
            } catch (Exception ex){
                Intent profile = new Intent(this, Profile.class);
                startActivity(profile);
            }
        }
    }

    private void onSelectFromGalleryResult(Intent data){
        bm = null;
        if (data != null){
            try{
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                thumbnailURI = data.getData();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        if (bm != null){
            thumbnail.setBackgroundResource(android.R.color.transparent);
            thumbnail.setImageBitmap(bm);
            //imageUri = getImageUri(getApplicationContext(), bm);
        }

    }

    private void galleryIntent(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_PHOTO);
        startActivityForResult(intent, REQUEST_PHOTO);
    }

    private void insertDataToFirebase(){

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //videoId = getIntent().getStringExtra("videoId");
        userDatabase0 = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("VideoInfo");
        System.out.println("MOOOOO: " + videoId);
        userDatabase = userDatabase0.child(videoId);

        HashMap map = new HashMap();
        map.put("Title", title.getText().toString());
        map.put("Info", info.getText().toString());
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

    @Override
    public void onBackPressed() {
        Intent profile = new Intent(this, Profile.class);
        startActivity(profile);
    }


    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, videoId + ".jpg", null);
        return Uri.parse(path);
    }

    public void record(View view){
        Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(cameraIntent, REQUEST_CODE);
    }

    /*protected void onActivityResult(int requestCode, int resultCode, Intent data){

        try {
            videoUri = data.getData();
            video.setVideoURI(videoUri);
            video.start();
            if (videoUri == null) {
                Intent profile = new Intent(this, Profile.class);
                startActivity(profile);
            }
        } catch (Exception ex){
            Intent profile = new Intent(this, Profile.class);
            startActivity(profile);
        }
    }*/
}
