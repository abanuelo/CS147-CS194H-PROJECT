package com.bignerdranch.android.pife11;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class UploadThumbnail extends AppCompatActivity {

    private Button upload, next;
    private ImageView thumbnail;
    private int SELECT_FILE = 1;
    private String videoId, uid;
    private Bitmap bm = null;
    private Uri thumbnailURI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_thumbnail);
        upload = findViewById(R.id.upload_thumb);
        next = findViewById(R.id.next);
        thumbnail = findViewById(R.id.thumbnail);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryIntent();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fillOutPerformanceInfo = new Intent(UploadThumbnail.this, DeclarePerform.class);
                fillOutPerformanceInfo.putExtra("videoId", videoId);
                uploadToBackend();
                startActivity(fillOutPerformanceInfo);
            }
        });
    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "test.3gp", null);
        return Uri.parse(path);
    }


    private void uploadToBackend(){
        //Firebase Storage Variables
        videoId = getIntent().getStringExtra("videoId");
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageRef.child("/videoThumbnails/" + uid + "/" + videoId + ".jpg");


        Uri file = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.drawable.play);
        if (thumbnailURI != null) {
            file = thumbnailURI;
        }
        UploadTask uploadTask = imageRef.putFile(file);


        //upload connection btn two
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Videos");
        matchDb.child(videoId).setValue(videoId);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            onSelectFromGalleryResult(data);
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

        thumbnail.setImageBitmap(bm);
    }

    private void galleryIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File "), SELECT_FILE);
    }



}
