package com.pupccis.fitnex.activities.main.trainer.studio;

import static com.pupccis.fitnex.handlers.viewmodel.ViewModelHandler.getFirebaseUIPostVideoOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.pupccis.fitnex.activities.videoplayer.TrainingVideoPlayer;
import com.pupccis.fitnex.adapters.TrainerStudioVideosAdapter;
import com.pupccis.fitnex.api.enums.AccessType;
import com.pupccis.fitnex.databinding.ActivityTrainerStudioBinding;
import com.pupccis.fitnex.handlers.view.WrapContentLinearLayoutManager;
import com.pupccis.fitnex.model.PostVideo;
import com.pupccis.fitnex.model.User;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.model.VideoThumbnail;
import com.pupccis.fitnex.viewmodel.PostVideoViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class TrainerStudio extends AppCompatActivity implements View.OnClickListener {
    //Private Layout Attributes
    private LinearLayout btnAddVideo;
    private ConstraintLayout btnSearch, containerEditDelete;
    private RecyclerView trainerStudioVideos;
    private DatabaseReference mDatabase;
    private User trainer_intent;
    private PostVideoViewModel postVideoViewModel;

    private TrainerStudioVideosAdapter adapter;

    private ActivityTrainerStudioBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_trainer_studio);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trainer_studio);
        binding.setPresenter(this);
        binding.setViewModel(new PostVideoViewModel());
        binding.executePendingBindings();

        //Extra intent
        String access_type = getIntent().getSerializableExtra("access_type").toString();
        //Layout Binding:
        btnAddVideo = findViewById(R.id.linearLayoutStudioAddVideoButton);
        btnSearch = findViewById(R.id.layoutTraineeSearch);
        containerEditDelete = findViewById(R.id.constraintLayoutEditDelete);

        //EventBinding:
        btnAddVideo.setOnClickListener(this);

        //RecyclerView Firebase UI
        FirestoreRecyclerOptions<PostVideo> options = getFirebaseUIPostVideoOptions();
        binding.recyclerViewTrainerStudioVideo.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //Instantiate Adapter and Bind to RecyclerView
        adapter = new TrainerStudioVideosAdapter(options);
        adapter.setAccessType(AccessType.OWNER);
        adapter.setActivity(TrainerStudio.this);
        binding.recyclerViewTrainerStudioVideo.setAdapter(adapter);

        //Set Lifecycle and ViewModel of Binding
        binding.setLifecycleOwner(this);
        binding.setViewModel(adapter.getPostVideoViewModel());

        //Observers
//        binding.getViewModel().itemContainerClickObserver().observe(binding.getLifecycleOwner(), new Observer<PostVideo>() {
//            @Override
//            public void onChanged(PostVideo postVideo) {
//                Log.d("item", "clicked");
//                Intent intent= new Intent(getApplicationContext(), TrainingVideoPlayer.class);
//                intent.putExtra("PostVideo", postVideo);
//                startActivity(intent);
//            }
//        });

        //ViewModel Instantiation
//        postVideoViewModel = new ViewModelProvider(TrainerStudio.this).get(PostVideoViewModel.class);
//        postVideoViewModel.init(TrainerStudio.this);
//
//        //RecyclerView
//        trainerStudioVideos = (RecyclerView) findViewById(R.id.recyclerViewTrainerStudioVideo);
//        trainerStudioVideos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//
//        //RecyclerViewAdapter
//        trainerStudioVideosAdapter = new TrainerStudioVideosAdapter(binding.getViewModel().getLiveDataPostVideos().getValue(), TrainerStudio.this, getIntent().getSerializableExtra("access_type").toString());
//        trainerStudioVideos.setAdapter(trainerStudioVideosAdapter);
//
//        //Observers
//        binding.getViewModel().getLiveDataPostVideos().observe(this, new Observer<ArrayList<PostVideo>>() {
//            @Override
//            public void onChanged(ArrayList<PostVideo> postVideos) {
//                trainerStudioVideosAdapter.notifyDataSetChanged();
//                binding.getViewModel().getLiveDataPostVideos().removeObserver(this::onChanged);
//            }
//        });
//
//        binding.getViewModel().getLiveDataPostVideoUpdate().observe(this, new Observer<HashMap<String, Object>>() {
//            @Override
//            public void onChanged(HashMap<String, Object> stringObjectHashMap) {
//                Log.d("Observer Triggered", "triggered");
//
//                if(stringObjectHashMap.get("updateType").equals("insert")){
//                    Log.d("Update Registered", "Data Inserted");
//                    trainerStudioVideosAdapter.notifyItemInserted(trainerStudioVideosAdapter.getItemCount());
//                }
//
//                else if(stringObjectHashMap.get("updateType").equals("update")){
//                    Log.d("Update Registered", "Data Updated");
//                    trainerStudioVideosAdapter.notifyItemChanged((int)stringObjectHashMap.get("index"));
//                }
//
//            }
//        });


//        Query query = null;
//        if(access_type.equals(GlobalConstants.KEY_ACCESS_TYPE_VIEW)){
////            containerEditDelete.setVisibility(View.GONE);
//            btnAddVideo.setVisibility(View.GONE);
//            btnSearch.setVisibility(View.VISIBLE);
//            btnSearch.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent( TrainerStudio.this, SearchEngine.class);
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.stay);
//                }
//            });
//
//            String trainer_id = getIntent().getSerializableExtra("trainer_id").toString();
//            Log.d("Trainer ID", trainer_id);
//            query = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).orderByChild(PostVideoConstants.KEY_POST_VIDEO_TRAINER_ID).equalTo(trainer_id);
//        }
//        else if(access_type.equals(GlobalConstants.KEY_ACCESS_TYPE_OWNER)){
//            query = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).orderByChild(PostVideoConstants.KEY_POST_VIDEO_TRAINER_ID).equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        }
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                postVideoList.clear();
//                //DataSnapshot dataSnapshot : snapshot.getChildren()
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//
//                    PostVideo postVideo = new PostVideo
//                            .PostVideoBuilder(
//                            dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_TITLE).getValue().toString(),
//                            dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_CATEGORY).getValue().toString(),
//                            dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_DESCRIPTION).getValue().toString(),
//                            dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_TRAINER_ID).getValue().toString(),
//                            (long) dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_DATE_POSTED).getValue()
//                    )
//                            .videoUri(dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_URL).getValue().toString())
//                            .thumbnailURL(dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_THUMBNAIL_URL).getValue().toString())
//                            .postVideoID(dataSnapshot.getKey())
//                            .build();
//                    //postVideo.setName(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_NAME).getValue().toString());
//
//                    postVideoList.add(postVideo);
//
//
//                }
//                // trainerStudioVideosAdapter = new TrainerStudioVideosAdapter(postVideoList, TrainerStudio.this, getIntent());
//                // trainerStudioVideos.setLayoutManager(new LinearLayoutManager(TrainerStudio.this));
//                // trainerStudioVideos.setItemAnimator(new DefaultItemAnimator());
//                trainerStudioVideosAdapter = new TrainerStudioVideosAdapter(postVideoList, TrainerStudio.this, getIntent().getSerializableExtra("access_type").toString());
//                //trainerStudioVideosAdapter.notifyDataSetChanged();
//                trainerStudioVideos.setAdapter(trainerStudioVideosAdapter);
//                trainerStudioVideosAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//            void setAdapter(){
////                programAdapter = new ProgramAdapter(programs, getContext());
////                programAdapter.notifyDataSetChanged();
////                programsRecyclerView.setAdapter(programAdapter);
//            }
//        });
    }

    @Override
    protected void onStart() {
        Log.d("On Start", "Triggered");
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
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


}