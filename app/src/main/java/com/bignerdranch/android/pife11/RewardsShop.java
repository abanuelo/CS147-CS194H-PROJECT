package com.bignerdranch.android.pife11;

import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class RewardsShop extends Fragment {
    private String myAvatar;
    private String currentUserId;
    private LinearLayout constraint;
    private HorizontalScrollView scrollViewHats;
    private Button button1;
    private Button button2;
    private Button button3;

    int constrainedX;
    int constrainedY;
    int button1X;
    int button1Y;
    int button2X;
    int button2Y;

    private Handler handler;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rewards_shop, container, false);
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getUserAvatar(view);
        return view;
    }

    private void getUserAvatar(final View view) {
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Avatar");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    myAvatar = dataSnapshot.getValue().toString().trim();
                    ImageView avatarDisplay = (ImageView) view.findViewById(R.id.avatarRewardsShop);
                    if(myAvatar.equals("{avatar=Jemi}")) {
                        avatarDisplay.setImageResource(R.drawable.ic_monster_baby);
                    } else {
                        avatarDisplay.setImageResource(R.drawable.ic_nerdy_monster_baby);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards_shop);

        handler = new Handler();

        constraint = findViewById(R.id.constrained_view);
        scrollViewHats = findViewById(R.id.horizontalscrollview);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        //Snapping Feature the Shop has been implemented
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


                        Log.d("button1Y", Integer.toString(button1X));
                        Log.d("button1Y", Integer.toString(button1Y));
                        Log.d("button2X", Integer.toString(button2X));
                        Log.d("button2Y", Integer.toString(button2Y));
                        Log.d("constraintX", Integer.toString(constrainedX));
                        Log.d("constraintY", Integer.toString(constrainedY));
                        Log.d("constraint-button1", Integer.toString(Math.abs(constrainedX-button1X)));
                        Log.d("constraint-button2", Integer.toString(Math.abs(constrainedX-button2X)));

                        if (Math.abs(constrainedX-button1X) < Math.abs(constrainedX-button2X)){
                            scrollViewHats.smoothScrollTo(button1X, button1Y);
                        } else {
                            scrollViewHats.smoothScrollTo(button2X, button2Y);
                        }
                    }
                }, 700);

            }
        });


        //this feature allows us to proceed to the item in the shop
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollViewHats.smoothScrollTo((int)button3.getX(), (int)button3.getY());
            }
        });*/


    }

}
