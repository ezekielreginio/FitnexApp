package com.pupccis.fitnex.Activities.Main.Trainer.Studio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pupccis.fitnex.API.adapter.ProgramAdapter;
import com.pupccis.fitnex.API.adapter.TrainerStudioVideosAdapter;
import com.pupccis.fitnex.Activities.Main.Trainer.AddProgram;
import com.pupccis.fitnex.Activities.Main.Trainer.TrainerDashboard;
import com.pupccis.fitnex.Activities.VideoConferencing.VideoActivityDemo;
import com.pupccis.fitnex.Models.PostVideo;
import com.pupccis.fitnex.Models.Program;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.Constants.PostVideoConstants;
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.Utilities.VideoConferencingConstants;

import java.util.ArrayList;
import java.util.List;

public class TrainerStudio extends AppCompatActivity implements View.OnClickListener {
    //Private Layout Attributes
    private LinearLayout btnAddVideo;
    private RecyclerView trainerStudioVideos;
    private DatabaseReference mDatabase;

    private List<PostVideo> postVideoList = new ArrayList<>();

    private TrainerStudioVideosAdapter trainerStudioVideosAdapter;

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


        Query query = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).orderByChild(PostVideoConstants.KEY_POST_VIDEO_TRAINER_ID).equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postVideoList.clear();
                //DataSnapshot dataSnapshot : snapshot.getChildren()

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    PostVideo postVideo = new PostVideo
                            .PostVideoBuilder(
                            dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_TITLE).getValue().toString(),
                            dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_CATEGORY).getValue().toString(),
                            dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_DESCRIPTION).getValue().toString(),
                            dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_TRAINER_ID).getValue().toString()
                            )
                            .thumbnailURL(dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_THUMBNAIL_URL).getValue().toString())
                            .build();
                    //postVideo.setName(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_NAME).getValue().toString());

                    postVideoList.add(postVideo);


                }
                trainerStudioVideosAdapter = new TrainerStudioVideosAdapter(postVideoList, getApplicationContext());
                trainerStudioVideos.setLayoutManager(new LinearLayoutManager(TrainerStudio.this));
                trainerStudioVideos.setItemAnimator(new DefaultItemAnimator());
                trainerStudioVideos.setAdapter(trainerStudioVideosAdapter);
                trainerStudioVideosAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            void setAdapter(){
//                programAdapter = new ProgramAdapter(programs, getContext());
//                programAdapter.notifyDataSetChanged();
//                programsRecyclerView.setAdapter(programAdapter);
            }
        });
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