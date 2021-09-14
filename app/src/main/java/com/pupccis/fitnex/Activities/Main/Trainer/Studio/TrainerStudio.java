package com.pupccis.fitnex.Activities.Main.Trainer.Studio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.pupccis.fitnex.Activities.Main.Trainee.TraineeDashboard;
import com.pupccis.fitnex.Activities.Main.Trainer.AddProgram;
import com.pupccis.fitnex.Activities.Main.Trainer.TrainerDashboard;
import com.pupccis.fitnex.Activities.SearchEngine.SearchEngine;
import com.pupccis.fitnex.Activities.VideoConferencing.VideoActivityDemo;
import com.pupccis.fitnex.Models.PostVideo;
import com.pupccis.fitnex.Models.Program;
import com.pupccis.fitnex.Models.User;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.Utilities.Constants.PostVideoConstants;
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.Utilities.VideoConferencingConstants;

import java.util.ArrayList;
import java.util.List;

public class TrainerStudio extends AppCompatActivity implements View.OnClickListener {
    //Private Layout Attributes
    private LinearLayout btnAddVideo;
    private ConstraintLayout btnSearch, containerEditDelete;
    private RecyclerView trainerStudioVideos;
    private DatabaseReference mDatabase;
    private User trainer_intent;

    private List<PostVideo> postVideoList = new ArrayList<>();

    private TrainerStudioVideosAdapter trainerStudioVideosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_studio);

        //Extra intent
        String access_type = getIntent().getSerializableExtra("access_type").toString();
        //Layout Binding:
        btnAddVideo = findViewById(R.id.linearLayoutStudioAddVideoButton);
        btnSearch = findViewById(R.id.layoutTraineeSearch);
        containerEditDelete = findViewById(R.id.constraintLayoutEditDelete);

        //EventBinding:
        btnAddVideo.setOnClickListener(this);

        //RecyclerView
        trainerStudioVideos = (RecyclerView) findViewById(R.id.recyclerViewTrainerStudioVideo);
        trainerStudioVideos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Query query = null;
        if(access_type.equals(GlobalConstants.KEY_ACCESS_TYPE_VIEW)){
//            containerEditDelete.setVisibility(View.GONE);
            btnAddVideo.setVisibility(View.GONE);
            btnSearch.setVisibility(View.VISIBLE);
            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent( TrainerStudio.this, SearchEngine.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.stay);
                }
            });

            String trainer_id = getIntent().getSerializableExtra("trainer_id").toString();
            Log.d("Trainer ID", trainer_id);
            query = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).orderByChild(PostVideoConstants.KEY_POST_VIDEO_TRAINER_ID).equalTo(trainer_id);
        }
        else if(access_type.equals(GlobalConstants.KEY_ACCESS_TYPE_OWNER)){
            query = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).orderByChild(PostVideoConstants.KEY_POST_VIDEO_TRAINER_ID).equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        }
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
                            dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_TRAINER_ID).getValue().toString(),
                            (long) dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_DATE_POSTED).getValue()
                    )
                            .videoUri(dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_URL).getValue().toString())
                            .thumbnailURL(dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_THUMBNAIL_URL).getValue().toString())
                            .postVideoID(dataSnapshot.getKey())
                            .build();
                    //postVideo.setName(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_NAME).getValue().toString());

                    postVideoList.add(postVideo);


                }
                // trainerStudioVideosAdapter = new TrainerStudioVideosAdapter(postVideoList, TrainerStudio.this, getIntent());
                // trainerStudioVideos.setLayoutManager(new LinearLayoutManager(TrainerStudio.this));
                // trainerStudioVideos.setItemAnimator(new DefaultItemAnimator());
                trainerStudioVideosAdapter = new TrainerStudioVideosAdapter(postVideoList, TrainerStudio.this, getIntent().getSerializableExtra("access_type").toString());
                //trainerStudioVideosAdapter.notifyDataSetChanged();
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
            case (R.id.linearLayoutStudioAddVideoButton):
                Intent intent = new Intent(TrainerStudio.this, AddVideo.class);
                startActivity(intent);
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