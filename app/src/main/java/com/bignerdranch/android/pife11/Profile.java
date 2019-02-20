package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import SelectVideoOnProfile.SelectVideoOnProfile;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import profile_grid_layout.GridViewAdapter;
import profile_grid_layout.ImageItem;

public class Profile extends AppCompatActivity {
    private String userId;
    private TextView name, username, genre, instrument;
    private FirebaseAuth auth;
    private DatabaseReference userDatabase;
    private Button sign_out;
    private boolean practiceBool, performBool, collabBool,dressed;
    private GifImageView animation;
    private GifDrawable drawable;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private ValueEventListener listener;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(bitmap!=null){
            Log.d("BITMAP", "Not null");
        }

        if(savedInstanceState != null){
            Log.d("NOT NULL", "here");
        } else{
            Log.d("NULL", "here");
        }
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationViewPerform);
        bottomNavigationView.setSelectedItemId(R.id.user_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                                                     @Override
                                                                     public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                         switch (menuItem.getItemId()){
                             case R.id.practice_nav:
                                 Intent practice_intent = new Intent(Profile.this, ChooseRoutineActivity.class);
                                 finish();
                                 startActivity(practice_intent);
                                 break;
                             case R.id.perform_nav:
                                 Intent perform_intent = new Intent(Profile.this, DeclarePerform.class);
                                 finish();
                                 startActivity(perform_intent);
                                 break;
                             case R.id.friends_nav:
                                 Intent collab_intent = new Intent(Profile.this, CollabHiFi2.class);
                                 finish();
                                 startActivity(collab_intent);
                                 break;
                         }
                         return true;
                     }
                 }
        );




//        Button rewardShop = findViewById(R.id.shop_button);
//        rewardShop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent rewards_intent = new Intent(Profile.this, RewardsBoth.class);
//                startActivity(rewards_intent);
//            }
//        });

        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        Object profile_lookup = getIntent().getExtras();
        if (profile_lookup != null) {
            //do the regular shit
            String toasty = "Profile is: " + profile_lookup.toString();
            Toast.makeText(this, toasty, Toast.LENGTH_LONG).show();
//            set Nav-bottom on click listener differently.
        }
        else {
            //set on click listener default
        }


        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                //Create intent
                Intent intent = new Intent(Profile.this, SelectVideoOnProfile.class);
                intent.putExtra("currentUserId", "6X6Eok5NaRNWYSArcoX4Q7qpoMv2");
                intent.putExtra("currentVideo", "test.3gp");
//                intent.putExtra("image", item.getImage());
                finish();
                //Start details activity
                startActivity(intent);
            }
        });

        //Initialize the Buttons for the User Profile
        sign_out = (Button) findViewById(R.id.sign_out);

//        Initalize the Text Views
//        name = (TextView) findViewById(R.id.name);
        username = (TextView) findViewById(R.id.profile_username);
        genre = (TextView) findViewById(R.id.profile_years);
        instrument = (TextView) findViewById(R.id.profile_instruments);

        //Now we are going to iterate over FirebaseDatabase to populate TextViews
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Gets the Name and Inserts it within TextView
//                String t_name = dataSnapshot.child("name").getValue().toString().trim();
//                name.setText(t_name);

                //Gets the Username and inserts it within textView
                String t_username = dataSnapshot.child("username").getValue().toString().trim();
                username.setText("User name: " + t_username);

                //Gets the Instruments to Populate the Instruments
                String t_instruments = null;
                for (DataSnapshot instrument : dataSnapshot.child("Years").getChildren()){
                    if (t_instruments == null){
                        t_instruments = instrument.getKey() + ": " + instrument.getValue().toString().trim();
                    } else {
                        t_instruments += ", " + instrument.getKey() + ": " + instrument.getValue().toString().trim();
                    }
                }
                instrument.setText("Instruments: " + t_instruments);

                //Lastly we are going to populate the Genres Category
                String t_genres = null;
                for (DataSnapshot genre : dataSnapshot.child("Genres").getChildren()){
                    if ((boolean) genre.getValue() == true){
                        if (t_genres == null){
                            t_genres = genre.getKey();
                        } else {
                            t_genres += ", " + genre.getKey();
                        }
                    }
                }
                genre.setText("Genre: " + t_genres);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        userDatabase.addValueEventListener(listener);



        //Event that Initiates the Sign Out Process for the Profile Image
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent sign_out_intent = new Intent(Profile.this, MainActivity.class);
                finish();
                startActivity(sign_out_intent);

            }
        });

//        checkIfTaskComplete();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (userDatabase != null && listener != null) {
            Log.d("Clear", "clearing userdatabase!");
            userDatabase.removeEventListener(listener);
        }
        if(gridView != null){
            gridView = null;
        }
        if (auth != null){
            auth = null;
        }
        listener = null;
        userId = null;
        username = null;
        genre = null;
        instrument = null;
        bitmap.recycle();
        gridAdapter.clear();
        finish();
    }

//    private void updateAnimation(){
//        Log.i("Stats:", "Truths: " + practiceBool + performBool + collabBool);
//        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//        if (practiceBool && performBool && collabBool) {
//            //Evolve the creature
//            try{
//                //do the dialog
//                if (dressed) {
//                    drawable = new GifDrawable(getResources(), R.drawable.jemi_dressed_toddler);
//                }
//                else{
//
//                    //check if avatar ==toddler //if not evolveMessage // if so do what is below
//                    drawable = new GifDrawable(getResources(), R.drawable.jemi_plain_toddler);
//                    FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Avatar").setValue("toddler");
//
//                }
//            }
//            catch (IOException ie) {
//                Toast.makeText(getApplicationContext(),"Error with Gif",Toast.LENGTH_LONG).show();
//            }
//        }
//        else {
//
//            //Adding Gifs into the Code Content
//            try {
//                if (!practiceBool) {
//                    drawable = new GifDrawable(getResources(), R.drawable.jemi_happy);
//                }
//                else {
//                    drawable = new GifDrawable(getResources(), R.drawable.jemi_sad);
//                }
//
//
//            } catch (IOException ie) {
//                Toast.makeText(getApplicationContext(),"Error with Gif",Toast.LENGTH_LONG).show();
//                //Catch the IO Exception in case of getting an hour
//            }
//        }
//
////        drawable.setLoopCount(0);
////        animation = (GifImageView) findViewById(R.id.animation);
////        animation.setBackground(drawable);
//    }

//    private void checkIfTaskComplete(){
//
//        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats");
//        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//                    for (DataSnapshot match: dataSnapshot.getChildren()){
//                        Log.i("Stats:", match.getKey() + ": " + match.getValue().toString());
//                        if (match.getKey().equals("practice")) {
//                            int hl = Integer.parseInt(match.getValue().toString().trim());
//                            if(hl == 1) practiceBool = true;
//                        }
//                        if (match.getKey().equals("perform")) {
//                            int hl = Integer.parseInt(match.getValue().toString().trim());
//                            if(hl == 1) performBool = true;
//                        }
//                        if (match.getKey().equals("collab")) {
//                            int hl = Integer.parseInt(match.getValue().toString().trim());
//                            if(hl == 1) collabBool = true;
//                        }
//                        if (match.getKey().equals("dressed")) {
//                            String dres = match.getValue().toString();
//                            if(dres.equals("true")) dressed = true;
//                        }
//                    }
//                    updateAnimation();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.gridview_proof);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        for (int i = 0; i < imgs.length(); i++) {

            //bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            bitmap = decodeSampledBitmapFromResource(getResources(), imgs.getResourceId(i, -1));
//
////            TODO: HERE IS WHERE YOU SET THE TITLE, THE SECOND PARAMETER
            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        imgs.recycle();
        //bitmap.recycle();
        return imageItems;
    }


    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        return BitmapFactory.decodeResource(res, resId, options);
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
//            final Uri image_uri = data.getData();
//            resultUri = image_uri;
//            profileImage.setImageURI(resultUri);
//        }
//    }
}
