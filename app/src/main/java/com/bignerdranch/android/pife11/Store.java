package com.bignerdranch.android.pife11;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Store extends AppCompatActivity {
    private String myAvatar;
    private String currentUserId;
    private String pifePoints;
    private Button hat, shirt;
    private LinearLayout constraint;
    private LinearLayout constraint2;
    private View view;

    private TextView pifePointsLocation, PriceTag1, PriceTag2;

    private Handler handler;
    private ImageView shirt_placement, hat_placement;
    private DatabaseReference userDb;
    private HorizontalScrollView scrollViewHats;
    private HorizontalScrollView scrollViewHats2;

    private Button button1;
    private Button button3;
    private Button button2;
    private Button button4;

    int constrainedX;
    int constrainedY;
    int button1X;
    int button1Y;
    int button2X;
    int button2Y;
    int button3X;
    int button3Y;

    int constrainedX2;
    int constrainedY2;
    int button1X2;
    int button1Y2;
    int button2X2;
    int button2Y2;
    int button3X2;
    int button3Y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("dressed");
        getUserAvatar();
        getUserPifePoints();
        handler = new Handler();

        pifePointsLocation = findViewById(R.id.pifepoints);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("xp");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    pifePointsLocation.setText(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //gets the hat and shirt
        hat = findViewById(R.id.button2);
//        shirt = findViewById(R.id.button4);

        hat_placement = findViewById(R.id.hat);
        shirt_placement = findViewById(R.id.shirt);

        //Gets the button for the constraints
        constraint = findViewById(R.id.shapeLayout);
        scrollViewHats = findViewById(R.id.horizontalscrollview);
        scrollViewHats2 = findViewById(R.id.horizontalscrollview2);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
//        button4 = findViewById(R.id.button4);

        //Sets the visibility true if we are fully dressed

        userDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String value = dataSnapshot.getValue().toString().trim();
                    if (value.equals("true")) {
                        hat.setVisibility(View.VISIBLE);
//                        shirt.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        scrollViewHats.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        constrainedX = (int) scrollViewHats.getScrollX();
                        constrainedY = (int) scrollViewHats.getScrollY();

                        button1X = (int) button1.getX();
                        button1Y = (int) button1.getY();

                        button2X = (int) button2.getX();
                        button2Y = (int) button2.getY();

                        if (Math.abs(constrainedX - button1X) < Math.abs(constrainedX - button2X) && Math.abs(constrainedX - button1X) < Math.abs(constrainedX - button3X)) {
                            scrollViewHats.smoothScrollTo(button1X, button1Y);
                            hat_placement.setImageResource(0);
                            //CHANGE THE PRICE TAG HERE
                        } else if (Math.abs(constrainedX - button2X) < Math.abs(constrainedX - button1X) && Math.abs(constrainedX - button2X) < Math.abs(constrainedX - button3X)) {
                            scrollViewHats.smoothScrollTo(button2X, button2Y);

                            hat_placement.setImageResource(R.drawable.ic_jemi_orange_hat);

                        } else {
                            hat_placement.setImageResource(0);
                        }
                    }
                }, 600);

            }
        });


        scrollViewHats2.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        constrainedX = (int) scrollViewHats2.getScrollX();
                        constrainedY = (int) scrollViewHats2.getScrollY();

                        button1X = (int) button3.getX();
                        button1Y = (int) button3.getY();

//                        button2X = (int) button4.getX();
//                        button2Y = (int) button4.getY();

                        if (Math.abs(constrainedX - button1X) < Math.abs(constrainedX - button2X) && Math.abs(constrainedX - button1X) < Math.abs(constrainedX - button3X)) {
                            scrollViewHats2.smoothScrollTo(button1X, button1Y);
                            shirt_placement.setImageResource(0);
                            //CHANGE THE PRICE TAG HERE
                        } else if (Math.abs(constrainedX - button2X) < Math.abs(constrainedX - button1X) && Math.abs(constrainedX - button2X) < Math.abs(constrainedX - button3X)) {
                            scrollViewHats2.smoothScrollTo(button2X, button2Y);
                            shirt_placement.setImageResource(R.drawable.ic_jemi_green_shirt);

                        } else {
                            shirt_placement.setImageResource(0);
                        }
                    }
                }, 600);

            }
        });

    }




    private void getUserAvatar() {
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Avatar");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//                    myAvatar = dataSnapshot.getValue().toString().trim();
//                    ImageView avatarDisplay = (ImageView) findViewById(R.id.avatarMyRewards);
//                    if(myAvatar.equals("{avatar=Jemi}")) {
//                        avatarDisplay.setImageResource(R.drawable.ic_monster_baby);
//                    } else if (myAvatar.equals("toddler")) {
//                        //always make this the monster baby
//                        avatarDisplay.setImageResource(R.drawable.ic_jemi_toddler_arms_down);
//                    } else if (myAvatar.equals("dressed")){
//                        avatarDisplay.setImageResource(R.drawable.ic_jemi_toddler_arms_down);
//                    } else {
//                        avatarDisplay.setImageResource(R.drawable.ic_monster_baby);
//                    }
//
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void getUserPifePoints() {
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("xp");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    pifePoints = dataSnapshot.getValue().toString().trim();
                    TextView pointsDisplay = (TextView) findViewById(R.id.pifepoints);
                    pointsDisplay.setText(pifePoints);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
