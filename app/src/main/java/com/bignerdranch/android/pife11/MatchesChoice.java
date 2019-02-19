package com.bignerdranch.android.pife11;

import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bignerdranch.android.pife11.Chat.Chat;
import com.bignerdranch.android.pife11.Scheduler.ScheduleMain;

public class MatchesChoice extends AppCompatActivity {
    private Button schedule;
    private Button chats;
    private Button liveCollab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches_choice);

        final Bundle extras = getIntent().getExtras();
        schedule = findViewById(R.id.schedule);
        chats = findViewById(R.id.chats);
        liveCollab = findViewById(R.id.collab_now);

        //Goes into the chat activity when their are inquiries on how to proceed next
        chats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent seeMatches = new Intent(MatchesChoice.this, Chat.class);
                seeMatches.putExtras(extras);
                startActivity(seeMatches);
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scheduleIntent = new Intent(MatchesChoice.this, ScheduleMain.class);
                startActivity(scheduleIntent);
            }
        });

        //sets up the custom chrome tab
        liveCollab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loadVideo = new Intent(MatchesChoice.this, Video.class);
                loadVideo.putExtras(extras);
                startActivity(loadVideo);
            }
        });
    }
}
