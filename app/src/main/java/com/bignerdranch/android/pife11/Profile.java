package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.pife11.Chat.Chat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.bignerdranch.android.pife11.SelectVideoOnProfile.SelectVideoOnProfile;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import profile_grid_layout.GridViewAdapter;
import profile_grid_layout.ImageItem;

public class Profile extends AppCompatActivity {
    private String userId;
    private String profile_lookup2;
    private TextView name, username, genre, instrument, posts, feedback, coinsEarned;
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
    final ArrayList<ImageItem> arr = new ArrayList<>();
    final int left_right_ImageMargin = 5;
    final int InstrumentSize = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        changeAvatarClothes();

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
                                 Intent perform_intent = new Intent(Profile.this, MyPerform.class);
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


        changeCoins();


        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        sign_out = (Button) findViewById(R.id.sign_out);
//        edit_profile = findViewById(R.id.edit_profile);
        posts = findViewById(R.id.videoCount);
        feedback = findViewById(R.id.feedback);
        coinsEarned = findViewById(R.id.coinsEarned);

        /// UserID is the person signed in
        /// Profile_Lookup is the person you are viewing

        Intent intent = getIntent();
        final String profile_lookup = intent.getStringExtra("profileId");
        if (profile_lookup != null) {
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
                                                                                     Intent perform_intent = new Intent(Profile.this, MyPerform.class);
                                                                                     finish();
                                                                                     startActivity(perform_intent);
                                                                                     break;
                                                                                 case R.id.user_nav:
                                                                                     Intent profile_intent = new Intent(Profile.this, Profile.class);
                                                                                     finish();
                                                                                     startActivity(profile_intent);
                                                                                     break;
                                                                             }
                                                                             return true;
                                                                         }
                                                                     }
            );
            bottomNavigationView.setSelectedItemId(R.id.friends_nav);


            userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(profile_lookup);

            sign_out.setVisibility(View.INVISIBLE);
            profile_lookup2 = profile_lookup;

        }
        else {
            profile_lookup2 = userId;

            sign_out.setText("Sign Out");
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
            //set on click listener default
        }

        //end of thingo
        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(Profile.this, R.layout.grid_item_layout, arr);

        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                //Create intent
                Intent intent = new Intent(Profile.this, SelectVideoOnProfile.class);
                intent.putExtra("currentUserId", profile_lookup2);
                intent.putExtra("currentVideo", item.getVideoTitle());
                intent.putExtra("title", item.getTitle());

                startActivity(intent);
            }
        });


        //Now we are going to iterate over FirebaseDatabase to populate TextViews
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Gets the Name and Inserts it within TextView
                //Gets the Username and inserts it within textView

                if (profile_lookup2 != userId) {
                    Object obj = dataSnapshot.child("username").getValue();
                    if (obj == null) return;
                    String t_username = obj.toString().trim();
                    TextView tv = findViewById(R.id.profileTitle);
                    tv.setText( t_username + "'s Profile");
                }

//
                //Gets the Instruments to Populate the Instruments
                String t_instruments = "";
                for (DataSnapshot instrument : dataSnapshot.child("Years").getChildren()){
                    if (instrument != null){
                        LinearLayout ly = (LinearLayout) findViewById(R.id.profile_user_info);
                        addImageToLinearLayout(ly, instrument.getKey());
                    }
                }


                Object feedbackcount = (dataSnapshot.child("Stats").child("TutorialThree").getValue());
                if (feedbackcount != null) {
                    feedback.setText(feedbackcount.toString());
                }

                Object koinsEarned = (dataSnapshot.child("Stats").child("lifetimeCoins").getValue());
                if (koinsEarned != null) {
                    coinsEarned.setText(koinsEarned.toString());
                }
                else {
                    coinsEarned.setText("0");
                }


                int count = 0;
                for (DataSnapshot video : dataSnapshot.child("Videos").getChildren()){
                    String thumbnailId = video.getKey();

                    final String title = (String) dataSnapshot.child("VideoInfo").child(thumbnailId).child("Title").getValue();

                    final String videoId = (String) video.getValue(); //test.3pg

                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                    StorageReference imgRef = storageRef.child(("/videoThumbnails/" + profile_lookup2 + "/" + thumbnailId + ".jpg"));
                    count +=1;

                    try {
                        final File localImageFile = File.createTempFile("images", ".jpg");
                        imgRef.getFile(localImageFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                // Local temp file has been created
                                String filePath = localImageFile.getPath();
                                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                                //downloads bitmap finder
                                arr.add(new ImageItem(bitmap, title, videoId));

                                gridAdapter.notifyDataSetChanged();




                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    } catch (Exception e) {}
                }
                HashMap post = new HashMap();
                post.put("posts", count);
                posts.setText(Integer.toString(count));
                userDatabase.updateChildren(post);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        userDatabase.addListenerForSingleValueEvent(listener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (userDatabase != null && listener != null) {
            Log.d("Clear", "clearing userdatabase!");
            userDatabase.removeEventListener(listener);
        }
//        if(gridView != null){
//            gridView = null;
//        }
        if (auth != null){
            auth = null;
        }
        listener = null;
        userId = null;
        username = null;
        genre = null;
        instrument = null;
        //bitmap.recycle();
        gridAdapter.clear();
        //finish();
    }


//    private ArrayList<ImageItem> getData() {
//        final ArrayList<ImageItem> imageItems = new ArrayList<>();
//        TypedArray imgs = getResources().obtainTypedArray(R.array.gridview_proof);
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        for (int i = 0; i < imgs.length(); i++) {
//            //bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
//            bitmap = decodeSampledBitmapFromResource(getResources(), imgs.getResourceId(i, -1));
//            imageItems.add(new ImageItem(bitmap, "Image#" + i));
//        }
//        imgs.recycle();
//        //bitmap.recycle();
//        return imageItems;
//    }

    private void changeAvatarClothes(){
        System.out.println("Mooooing here: change Avatar Clothes");
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference avatarDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("AvatarClothes");
        avatarDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    System.out.println("Datasnapshot:" + dataSnapshot.getValue().toString());

                    Long hatL = (Long) dataSnapshot.child("hat").getValue();
                    int hat = hatL.intValue();
                    System.out.println("What do we have here: hat OG: " + hat);
                    Long shirtL = (Long) dataSnapshot.child("shirt").getValue();
                    int shirt = shirtL.intValue();
                    System.out.println("What do we have here: shirt OG: " + shirt);

                    //DataSingleton ds = DataSingleton.getInstance();
                    //ds.setAvatarClothes(new Pair(hat, shirt));

                    changeHat(hat);
                    changeShirt(shirt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void changeHat(int hat){
        System.out.println("What do we have here: hat: " + hat);
        // Table of contents: 0 - no hat, 1 - blue, 2 - orange, 3 - pink, 4 - yellow
        ImageView hatImg = (ImageView) findViewById(R.id.profile_img_post);
        Drawable myDraw = getResources().getDrawable(R.drawable.yellowtoptrans);

        if (hat == 0) {
            myDraw = getResources().getDrawable(R.drawable.undressedtoptrans);
        } else if (hat == 1) {
            myDraw = getResources().getDrawable(R.drawable.bluetoptrans);
        } else if (hat == 2) {
            myDraw = getResources().getDrawable(R.drawable.orangetoptrans);
        } else if (hat == 3) {
            myDraw = getResources().getDrawable(R.drawable.pinktoptrans);
        }
        hatImg.setImageDrawable(myDraw);
    }

    private void changeShirt(int shirt) {
        System.out.println("What do we have here: shirt: " + shirt);
        // Table of contents: 0 - no shirt, 1 - green, 2 - pink, 3 - yellow, 4 - brown
        ImageView shirtImg = (ImageView) findViewById(R.id.profile_img_post2);

        Drawable myDraw = getResources().getDrawable(R.drawable.brownbottomtrans);

        if (shirt == 0) {
            myDraw = getResources().getDrawable(R.drawable.undressedbottomtrans);
        } else if (shirt == 1) {
            myDraw = getResources().getDrawable(R.drawable.greenbottomtrans);
        } else if (shirt == 2) {
            myDraw = getResources().getDrawable(R.drawable.pinkbottomtrans);
        } else if (shirt == 3) {
            myDraw = getResources().getDrawable(R.drawable.yellowbottomtrans);
        }
        shirtImg.setImageDrawable(myDraw);

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


    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        return BitmapFactory.decodeResource(res, resId, options);
    }



    private void addImageToLinearLayout(LinearLayout ly, String image, int len, int wid){
        TextView desc = new TextView(this);
        LinearLayout verticalLY = new LinearLayout(this);
        verticalLY.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams container = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        desc.setLayoutParams(container);

        container.leftMargin = left_right_ImageMargin;
        container.rightMargin = left_right_ImageMargin;
        verticalLY.setLayoutParams(container);


        final  ImageView imageToAdd = new ImageView(this);
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, len, getResources().getDisplayMetrics());
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, wid, getResources().getDisplayMetrics());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(height, width);

        imageToAdd.setLayoutParams(params);

        switch (image) {
            case ("guitar"):
                imageToAdd.setImageResource(R.drawable.ic_rock_pic);
                break;
            case "bass":
                imageToAdd.setImageResource(R.drawable.ic_bass);
                break;
            case "drum":
                imageToAdd.setImageResource(R.drawable.ic_drum);
                break;
            case "flute":
                imageToAdd.setImageResource(R.drawable.ic_flute);
                break;
            case "piano":
                imageToAdd.setImageResource(R.drawable.ic_piano);
                break;
            case "sing":
                imageToAdd.setImageResource(R.drawable.ic_pop_pic);
                break;
            case "viola":
                imageToAdd.setImageResource(R.drawable.ic_viola);
                break;
            case "violin":
                imageToAdd.setImageResource(R.drawable.ic_violin);
                break;
            default:
                return;
        }

        image = image.substring(0, 1).toUpperCase() + image.substring(1);
        desc.setText(image);

        verticalLY.addView(imageToAdd);
        verticalLY.addView(desc);
        ly.addView(verticalLY);    }

    private void addImageToLinearLayout(LinearLayout ly, String image){
        addImageToLinearLayout(ly, image, InstrumentSize, InstrumentSize);
    }


}
