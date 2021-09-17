package com.pupccis.fitnex.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pupccis.fitnex.Model.PostVideo;
import com.pupccis.fitnex.Utilities.Constants.PostVideoConstants;
import com.pupccis.fitnex.ViewModel.DataLoadListener;

import java.util.ArrayList;

public class Repo {

    static Repo instance;
    private ArrayList<PostVideo> postVideoModels;

    static Context mContext;
    static DataLoadListener dataLoadListener;

    public static Repo getInstance(Context context){

        mContext = context;
        if(instance == null){
            instance = new Repo();
        }

        dataLoadListener = (DataLoadListener) mContext;
        return instance;
    }

    public MutableLiveData<ArrayList<PostVideo>> getPostVideos(){
        loadNames();

        MutableLiveData<ArrayList<PostVideo>> postVideo = new MutableLiveData<>();
        postVideo.setValue(postVideoModels);

        return postVideo;
    }

    private void loadNames(){
        Query query = query = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).orderByChild(PostVideoConstants.KEY_POST_VIDEO_TRAINER_ID).equalTo(FirebaseAuth.getInstance().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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

                    postVideoModels.add(postVideo);
                }
                dataLoadListener.onNameLoaded();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
