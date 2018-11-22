package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.os.health.UidHealthStats;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {
    private EditText name;
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText reenterPassword;
    private Button register;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener auth_listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        auth = FirebaseAuth.getInstance();
        auth_listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    Intent createAccountIntent = new Intent(CreateAccount.this, Dashboard.class);
                    startActivity(createAccountIntent);
                    finish();
                    return;
                }
            }
        };

        name = (EditText) findViewById(R.id.name);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        reenterPassword = (EditText) findViewById(R.id.re_enter_password);
        register = (Button) findViewById(R.id.register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String f_email = email.getText().toString();
                final String f_password = password.getText().toString();
                auth.createUserWithEmailAndPassword(f_email, f_password).addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            Toast.makeText(CreateAccount.this, "Sign Up Error", Toast.LENGTH_SHORT).show();
                        } else {
                            String userId = auth.getCurrentUser().getUid();
                            User user = new User(name.getText().toString(), username.getText().toString());
                            DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                            currentUserDb.setValue(user);
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


