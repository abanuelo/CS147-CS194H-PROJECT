package com.bignerdranch.android.pife11;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class UploadThumbnail extends AppCompatActivity {

    private Button upload, next;
    private ImageView thumbnail;
    private int SELECT_FILE = 1;

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
                startActivity(fillOutPerformanceInfo);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            onSelectFromGalleryResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data){
        Bitmap bm = null;
        if (data != null){
            try{
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
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
