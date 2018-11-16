package com.bignerdranch.android.pife11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private TextView textView;
    public static CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Handles Transition to Create Account Page
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        /*
            Method #1: If Users Select the Option to Register for a New Account
        */
        textView = (TextView) findViewById(R.id.createAccount);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAccount();
            }
        });


        /*
            Method #2: Users opt selecting the option to login via Facebook
         */

        loginButton = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                textView.setText("Login Success");
            }

            @Override
            public void onCancel() {
                textView.setText("Login Cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                textView.setText("Error");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void openCreateAccount() {
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }
}
    //ATTEMPTING TO IMPLEMENT A GOOGLE ACCOUNT LOG-IN FOR USERS OF THE APP
//    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
////            .requestEmail()
////            .build();
////
////    mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
////
////    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
////    updateUI(account);
////
////    @Override
////    public void onClick(View v) {
////        switch (v.getId()) {
////            case R.id.sign_in_button:
////                signIn();
////                break;
////            // ...
////        }
////    }
////
////    private void signIn() {
////        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
////        startActivityForResult(signInIntent, RC_SIGN_IN);
////    }
////
////    @Override
////    public void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////
////        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
////        if (requestCode == RC_SIGN_IN) {
////            // The Task returned from this call is always completed, no need to attach
////            // a listener.
////            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
////            handleSignInResult(task);
////        }
////    }
////
////
////    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
////        try {
////            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
////
////            // Signed in successfully, show authenticated UI.
////            updateUI(account);
////        } catch (ApiException e) {
////            // The ApiException status code indicates the detailed failure reason.
////            // Please refer to the GoogleSignInStatusCodes class reference for more information.
////            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
////            updateUI(null);
////        }
////    }



