package com.pupccis.fitnex.Repository;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pupccis.fitnex.Model.PostVideo;
import com.pupccis.fitnex.Utilities.Constants.PostVideoConstants;
import com.pupccis.fitnex.ViewModel.DataLoadListener;

import java.util.ArrayList;

public class PostedVideosRepository {
    //Private Attributes
    private ArrayList<PostVideo> postVideoModels = new ArrayList<>();

    //Static Attributes//
    static PostedVideosRepository instance;
    static Context mContext;
    static DataLoadListener dataLoadListener;

    public static PostedVideosRepository getInstance(Context context){

        mContext = context;
        if(instance == null){
            instance = new PostedVideosRepository();
        }

        dataLoadListener = (DataLoadListener) mContext;
        return instance;
    }

    public MutableLiveData<ArrayList<PostVideo>> getVideos(){
        loadVideos();

        MutableLiveData<ArrayList<PostVideo>> postVideo = new MutableLiveData<>();
        postVideo.setValue(postVideoModels);

        return postVideo;
    }

    private void loadVideos(){
        Query query = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).orderByChild(PostVideoConstants.KEY_POST_VIDEO_TRAINER_ID).equalTo(FirebaseAuth.getInstance().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postVideoModels.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    PostVideo postVideo = new PostVideo
                            .PostVideoBuilder(
                            dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_TITLE).getValue().toString(),
                            dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_CATEGORY).getValue().toString(),
                            dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_DESCRIPTION).getValue().toString(),
                            dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_TRAINER_ID).getValue().toString(),
                            (long) dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_DATE_POSTED).getValue()
                    )
                            .videoURL(dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_URL).getValue().toString())
                            .thumbnailURL(dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_THUMBNAIL_URL).getValue().toString())
                            .postVideoID(dataSnapshot.getKey())
                            .build();
                    //postVideo.setName(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_NAME).getValue().toString());

                    postVideoModels.add(postVideo);
                }
                dataLoadListener.onDataLoaded();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void uploadVideo(PostVideo postVideo){
        //NOTE: Instance Must Contain Video and Thumbnail Filetype
        StorageReference videoUploader = FirebaseStorage.getInstance().getReference().child("postedvideos/"+System.currentTimeMillis()+"."+getExtension(postVideo.getVideoFiletype()));
        videoUploader.putFile(postVideo.getVideoUri())
                .addOnSuccessListener(taskSnapshot -> {
                    videoUploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri videoUri) {
                            postVideo.setVideoURL(videoUri.toString());
                            postVideo(postVideo);
                        }
                    });
                });
    }

    private void postVideo(PostVideo postVideo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        StorageReference thumbnailUploader = FirebaseStorage.getInstance().getReference().child("postedvideos_thumbnail/"+System.currentTimeMillis()+"."+getExtension(postVideo.getThumbnailFiletype()));
        thumbnailUploader.putFile(postVideo.getThumbnailUri())
                .addOnSuccessListener(taskSnapshot -> {
                    thumbnailUploader.getDownloadUrl().addOnSuccessListener(uri -> {
                        postVideo.setThumbnailURL(uri.toString());
                        db.collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO)
                                .document().set(postVideo.map());
                    });
                });
    }


    private String getExtension(String filetype){
        if(filetype == null){
            Log.e("Missing Data", "Filetype is not found in the instance");
            return null;
        }
        else{
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            return  mimeTypeMap.getExtensionFromMimeType(filetype);
        }
    }
}
