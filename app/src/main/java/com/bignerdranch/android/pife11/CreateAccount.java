package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.AsyncTask;
import android.widget.Toast;
import java.net.URL;
import java.io.OutputStreamWriter;
import android.util.Log;
import java.net.HttpURLConnection;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class CreateAccount extends AppCompatActivity{
    private EditText name;
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText reenterPassword;
    private Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        name = (EditText) findViewById(R.id.name);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        reenterPassword = (EditText) findViewById(R.id.re_enter_password);
        register = (Button) findViewById(R.id.register);


        //Clicking on Register Button Officially Registers Your Profile on MongoDB
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save(v);
                Intent intent = new Intent(CreateAccount.this, Dashboard.class);
                startActivity(intent);
            }
        });
    }

    /*
        Method 0: Create account by uploading it to Collections on MongoDB
     */
    public void save(View v) {
        UserInfo contact = new UserInfo();

        contact.setName(name.getText().toString());
        contact.setUsername(username.getText().toString());
        contact.setEmail(email.getText().toString());
        contact.setPassword(password.getText().toString());

        MongoLabSaveContact tsk = new MongoLabSaveContact();
        tsk.execute(contact);

        Toast.makeText(this, "Saved to MongoDB!!", Toast.LENGTH_SHORT).show();
    }

    /*
        Saves Contact To the Collections Section of the MongoDB Database
     */
    final class MongoLabSaveContact extends AsyncTask<Object, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Object... params) {
            UserInfo contact = (UserInfo) params[0];
            Log.d("contact", ""+contact);

            try {
                SupportData sd = new SupportData();
                URL url = new URL(sd.buildContactsSaveURL());

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");

                OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());

                osw.write(sd.createContact(contact));
                osw.flush();
                osw.close();

                if(connection.getResponseCode() <205)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            } catch (Exception e) {
                e.getMessage();
                Log.d("Got error", e.getMessage());
                return false;
            }
        }
    }

}
