package com.bignerdranch.android.pife11;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.health.UidHealthStats;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateAccount extends AppCompatActivity {
    private EditText name, username, email, password;
    private ImageView profile_image;
    private Button register;
    private Uri resultUri;
    private FirebaseAuth auth;
    private DatabaseReference userDatabase;
    private DatabaseReference checkedDatabase;
    private FirebaseAuth.AuthStateListener auth_listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //Creates Firebase Authentification Feature
        auth = FirebaseAuth.getInstance();

        //Creates Authentification Feature
        auth_listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    Intent createAccountIntent = new Intent(CreateAccount.this, Instruments.class);
                    startActivity(createAccountIntent);
                    finish();
                }
            }
        };

        //Edit Texts from the Create Account Activity
        name = (EditText) findViewById(R.id.name);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);

        //Image View Profile Image from Create Account Activity
        profile_image = (ImageView) findViewById(R.id.profile_image);

        //Register User Inserted information to Firebase, if possible
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String f_email = email.getText().toString().trim();
                final String f_password = password.getText().toString().trim();

                auth.createUserWithEmailAndPassword(f_email, f_password).addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {

                            Toast.makeText(CreateAccount.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            saveUserInfo();
                        }
                    }
                });
            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getPic = new Intent(Intent.ACTION_PICK);
                getPic.setType("image/*");
                startActivityForResult(getPic, 1);
            }
        });

    }
    private void saveUserInfo(){
        String userId = auth.getCurrentUser().getUid();
        String t_name = name.getText().toString().trim();
        String t_username = username.getText().toString().trim();
        String t_email = email.getText().toString().trim();
        String t_password = password.getText().toString().trim();

        //Sets the Firebase Database for user
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        Map userInfo = new HashMap();
        userInfo.put("name", t_name);
        userInfo.put("username", t_username);
        userInfo.put("email", t_email);
        userInfo.put("password", t_password);

        userDatabase.updateChildren(userInfo);

        checkedDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Checked");
        //Sets the Checked Option for Firebase for user


        //Storing the Image into the Firebase Storage Component
        if(resultUri != null){
            final StorageReference filepath = FirebaseStorage.getInstance().getReference().child("profileImages").child(userId);
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            final UploadTask uploadTask = filepath.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> downloadURL = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    downloadURL.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            resultUri = uri;
                            Map userInfo2 = new HashMap();
                            userInfo2.put("profileImageURL", resultUri.toString());
                            userDatabase.updateChildren(userInfo2);
                        }
                    });
                }
            });
        }else{
            //Toast.makeText(CreateAccount.this, "FAILED ATTEMPT", Toast.LENGTH_SHORT).show();
            Map userInfo3 = new HashMap();
            userInfo3.put("profileImageURL", "default");
            userDatabase.updateChildren(userInfo3);
            finish();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        auth.addAuthStateListener(auth_listener);
    }

    @Override
    protected void onStop(){
        super.onStop();
        auth.removeAuthStateListener(auth_listener);
    }

    //Method for Selecting the Profile Image from the Device
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK) {
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            profile_image.setImageURI(resultUri);
        }
    }
}


