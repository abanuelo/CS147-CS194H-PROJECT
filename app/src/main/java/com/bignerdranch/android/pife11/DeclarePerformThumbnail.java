package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class DeclarePerformThumbnail extends AppCompatActivity{

    private Button startPerform;
    private static final int REQUEST_CODE = 101;
    private Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare_perform);
        startPerform = (Button) findViewById(R.id.submitPerform);

        startPerform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record(view);
            }
        });
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
                Intent sendRecording = new Intent(DeclarePerformThumbnail.this, MyPerform.class);
                sendRecording.setData(videoUri);
                startActivity(sendRecording);
            }
        } catch (Exception ex){

        }
    }


}
