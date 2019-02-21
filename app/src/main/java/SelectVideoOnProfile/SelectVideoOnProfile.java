package SelectVideoOnProfile;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bignerdranch.android.pife11.GiveFeedback;
import com.bignerdranch.android.pife11.Profile;
import com.bignerdranch.android.pife11.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class SelectVideoOnProfile extends AppCompatActivity {

    private VideoView video;
    private StorageReference videoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_video_on_profile);

        //Get needed variables to Extract Video From FireBase
        Intent intent = getIntent();
        final String videoUserId = intent.getStringExtra("currentUserId");
        //6X6Eok5NaRNWYSArcoX4Q7qpoMv2
        final String currentVideo = intent.getStringExtra("currentVideo") + ".3gp"; //test.3pg

//        String[] splited = currentVideo.split("[.]");
//        String prefix = splited[0]; //passed into createTempFile
//        String suffix = splited[1]; //passed into createTempFile

        video = findViewById(R.id.videoView);


        //Get the video from the database
        try{
            final File localFile = File.createTempFile(currentVideo, "3gp");
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            videoRef = storageRef.child("/videos/" + videoUserId + "/" + currentVideo);
            videoRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    video.setVideoURI(Uri.fromFile(localFile));
                    video.start();
                }
            });
        } catch(Exception e){
            Log.d("Error", "This failed to download the video from Firebase");
            Toast.makeText(this, "Error downloading video from Firebase!", Toast.LENGTH_SHORT);
        }





        Button exit_btn = findViewById(R.id.select_video_exit);
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ListView listView = (ListView) findViewById(R.id.videoComments);

        if (userid.equals(videoUserId)){
            ArrayList<Comment> arrayOfComments = new ArrayList<Comment>();
            CommentAdapter adapter = new CommentAdapter(this, arrayOfComments);

            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(adapter);

            Comment newComment = new Comment("John", "December 12th, 2019", "I like your smile", "I wish you smiled more");
            Comment newComment1 = new Comment("Arm", "December 12th, 2019", "I like your sauce", "I wish you got THE sauce");
            Comment newComment2 = new Comment("Ab", "December 12th, 2019", "I like your fart lol", "I wish you looked at the camera");
            adapter.add(newComment);
            adapter.add(newComment1);
            adapter.add(newComment2);

            exit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sign_out_intent = new Intent(SelectVideoOnProfile.this, Profile.class);
                    sign_out_intent.putExtra("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    sign_out_intent.putExtra("videoId", currentVideo);
                    sign_out_intent.putExtra("videoUserId", videoUserId);
                    finish();
                    startActivity(sign_out_intent);

                }
            });

        } else {
            listView.setVisibility(View.GONE);
            exit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sign_out_intent = new Intent(SelectVideoOnProfile.this, GiveFeedback.class);
                    sign_out_intent.putExtra("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    sign_out_intent.putExtra("videoId", currentVideo);
                    sign_out_intent.putExtra("videoUserId", videoUserId);
                    finish();
                    startActivity(sign_out_intent);

                }
            });
        }





    }
}