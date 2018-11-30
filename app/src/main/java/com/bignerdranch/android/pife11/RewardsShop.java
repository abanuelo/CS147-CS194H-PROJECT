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
import android.widget.ImageButton;
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
import android.widget.TextView;

import org.w3c.dom.Text;

public class RewardsShop extends Fragment {
    private String pifePoints;
    private String myAvatar;
    private String currentUserId;
    private LinearLayout constraint;
    private LinearLayout constraint2;
    private HorizontalScrollView scrollViewHats;
    private HorizontalScrollView scrollViewHats2;
    private Button button1;
    private Button button12;
    private Button button2;
    private Button button22;

    int constrainedX;
    int constrainedY;
    int button1X;
    int button1Y;
    int button2X;
    int button2Y;
    int constrainedX2;
    int constrainedY2;
    int button1X2;
    int button1Y2;
    int button2X2;
    int button2Y2;


    private Handler handler;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rewards_shop, container, false);
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getUserAvatar(view);
        getUserPifePoints(view);


        handler = new Handler();

        constraint = view.findViewById(R.id.constrained_view);
        scrollViewHats = view.findViewById(R.id.horizontalscrollview);
        button1 = view.findViewById(R.id.button1);
        //button1.setImageResource(R.drawable.ic_jemi_shirt);
        button2 = view.findViewById(R.id.button2);

        constraint2 = view.findViewById(R.id.constrained_view2);
        scrollViewHats2 = view.findViewById(R.id.horizontalscrollview2);
        button12 = view.findViewById(R.id.button12);
        button22 = view.findViewById(R.id.button22);

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

                        if (Math.abs(constrainedX-button1X) < Math.abs(constrainedX-button2X)){
                            scrollViewHats.smoothScrollTo(button1X, button1Y);
                        } else {
                            scrollViewHats.smoothScrollTo(button2X, button2Y);
                        }
                    }
                }, 600);

            }
        });

        //Snapping Feature the Shop has been implemented
        scrollViewHats2.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        constrainedX2 = (int) scrollViewHats2.getScrollX();
                        constrainedY2 = (int) scrollViewHats2.getScrollY();

                        button1X2 = (int) button12.getX();
                        button1Y2 = (int) button12.getY();

                        button2X2 = (int) button22.getX();
                        button2Y2 = (int) button22.getY();

                        if (Math.abs(constrainedX2-button1X2) < Math.abs(constrainedX2-button2X2)){
                            scrollViewHats2.smoothScrollTo(button1X2, button1Y2);

                        } else {
                            scrollViewHats2.smoothScrollTo(button2X2, button2Y2);
                        }
                    }
                }, 600);

            }
        });


        //this feature allows us to proceed to the item in the shop
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollViewHats.smoothScrollTo((int)button2.getX(), (int)button2.getY());
            }
        });

        //this feature allows us to proceed to the item in the shop
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollViewHats2.smoothScrollTo((int)button22.getX(), (int)button22.getY());
            }
        });

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
    }

    private void getUserPifePoints(final View view) {
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("Stats").child("xp");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    pifePoints = dataSnapshot.getValue().toString().trim();
                    TextView pointsDisplay = (TextView) view.findViewById(R.id.pifepoints);
                    pointsDisplay.setText(pifePoints);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
