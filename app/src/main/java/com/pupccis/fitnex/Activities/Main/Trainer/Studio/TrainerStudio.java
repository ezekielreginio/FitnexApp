package com.pupccis.fitnex.Activities.Main.Trainer.Studio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pupccis.fitnex.Activities.Main.Trainer.AddProgram;
import com.pupccis.fitnex.Activities.Main.Trainer.TrainerDashboard;
import com.pupccis.fitnex.Activities.VideoConferencing.VideoActivityDemo;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.Constants.PostVideoConstants;
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.Utilities.VideoConferencingConstants;

public class TrainerStudio extends AppCompatActivity implements View.OnClickListener {
    //Private Layout Attributes
    private LinearLayout btnAddVideo;
    private RecyclerView trainerStudioVideos;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_studio);

        //Layout Binding:
        btnAddVideo = findViewById(R.id.linearLayoutAddVideoButton);

        //EventBinding:
        btnAddVideo.setOnClickListener(this);

        //RecyclerView
        trainerStudioVideos = (RecyclerView) findViewById(R.id.recyclerViewTrainerStudioVideo);

        mDatabase = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //LocalBroadcast Receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(videoUploadMessageReceiver,
                new IntentFilter("video-upload-response"));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case (R.id.linearLayoutAddVideoButton):
                startActivity(new Intent(TrainerStudio.this, AddVideo.class));
                overridePendingTransition(R.anim.slide_in_bottom,R.anim.stay);
                break;
        }
    }

    private BroadcastReceiver videoUploadMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String uploadResponse = intent.getStringExtra("videoUploadedSuccessfully");
            if(uploadResponse != null){
                Log.d("Broadcast Received", "received");
                Toast.makeText(getApplicationContext(), uploadResponse,Toast.LENGTH_SHORT).show();
            }
        }
    };
}