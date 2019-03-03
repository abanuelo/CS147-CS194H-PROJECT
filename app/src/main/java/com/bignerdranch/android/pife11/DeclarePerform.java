package com.bignerdranch.android.pife11;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class DeclarePerform extends AppCompatActivity {
    private Button startPerform, uploadThumbnail;
    private static final int REQUEST_CODE = 101;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Uri videoUri;
    private ImageView thumbnail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare_perform);
        startPerform = (Button) findViewById(R.id.startPerform);
        thumbnail = (ImageView) findViewById(R.id.thumbnail);
        uploadThumbnail = (Button) findViewById(R.id.upload_thumbnail);

        uploadThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

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

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            thumbnail.setImageBitmap(photo);
        }

        try {
            videoUri = data.getData();
            Log.d("videoUri", videoUri.toString());
            if (videoUri != null){
                //create video id
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String currentTime = Calendar.getInstance().getTime().toString();
                currentTime = currentTime.replaceAll(":","");
                currentTime = currentTime.replaceAll("\\s","");
                currentTime = currentTime.toLowerCase();
                // videoId = currentTime;

                StorageReference imageRef = storageRef.child("/videoThumbnails/" + uid + "/" + currentTime + ".jpg");
                Bitmap photo = (Bitmap) data.getExtras().get("data");

                Uri file = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.drawable.baby);
                if (photo.getByteCount() != 0) {
                    file = getImageUri(this, photo);
                }

                UploadTask uploadTask = imageRef.putFile(file);

                // Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                    }
                });



                //pass in video id thru sendRecording
                Intent sendRecording = new Intent(DeclarePerform.this, MyPerform.class);
                sendRecording.putExtra("videoID", currentTime);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }
}
