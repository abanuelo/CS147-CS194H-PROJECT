package com.bignerdranch.android.pife11;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity {

    private TextView register;
    private EditText email;
    private EditText password;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener auth_listener;
    private Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Handles Transition to Create Account Page
        super.onCreate(savedInstanceState);
//        Fabric.with(this, new Crashlytics.Builder().core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()).build());
        setContentView(R.layout.activity_main);


        /*
            Method #0: Allowing Edit Text Boxes to Clear upon clicking
         */
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.norm_login);

        /*
            Method #1: If Users Select the Option to Register for a New Account
        */
        register = (TextView) findViewById(R.id.createAccount);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_intent = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(register_intent);
            }
        });


        auth = FirebaseAuth.getInstance();

        auth_listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    Intent createAccountIntent = new Intent(MainActivity.this, Profile.class);
                    startActivity(createAccountIntent);
                    finish();
                }
            }
        };


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String f_email = email.getText().toString();
                final String f_password = password.getText().toString();
                auth.signInWithEmailAndPassword(f_email, f_password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


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
}







