package com.pupccis.fitnex.Repository;

import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pupccis.fitnex.Model.PostVideo;
import com.pupccis.fitnex.Utilities.Constants.PostVideoConstants;

import java.util.ArrayList;
import java.util.HashMap;

public class PostedVideosRepository {
    //Private Attributes
    private ArrayList<PostVideo> postVideoModels = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Mutable Live Data Attributes
    private MutableLiveData<HashMap<String, Object>> postVideoUpdate = new MutableLiveData<>();
    private MutableLiveData<ArrayList<PostVideo>> postVideo = new MutableLiveData<>();

    //Static Attributes//
    static PostedVideosRepository instance;

    public static PostedVideosRepository getInstance(){

        if(instance == null){
            instance = new PostedVideosRepository();
        }

        return instance;
    }

    public MutableLiveData<ArrayList<PostVideo>> getVideos(){
        loadVideos();

        postVideo.setValue(postVideoModels);

        return postVideo;
    }

    public MutableLiveData<HashMap<String, Object>> updateVideos(){
        bindDataObserver();
        HashMap<String, Object> data = new HashMap<>();
        PostVideo postVideo = new PostVideo();
        data.put("updateType", "");
        data.put("postVideo", postVideo);
        postVideoUpdate.setValue(data);

        return postVideoUpdate;
    }


    private void loadVideos(){
        Query query = db.collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).whereEqualTo(PostVideoConstants.KEY_POST_VIDEO_TRAINER_ID, FirebaseAuth.getInstance().getUid());

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                postVideoModels.clear();
                for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                    PostVideo postVideo = new PostVideo
                            .PostVideoBuilder(
                            documentSnapshot.get(PostVideoConstants.KEY_POST_VIDEO_TITLE).toString(),
                            documentSnapshot.get(PostVideoConstants.KEY_POST_VIDEO_CATEGORY).toString(),
                            documentSnapshot.get(PostVideoConstants.KEY_POST_VIDEO_DESCRIPTION).toString(),
                            documentSnapshot.get(PostVideoConstants.KEY_POST_VIDEO_TRAINER_ID).toString(),
                            (long) documentSnapshot.get(PostVideoConstants.KEY_POST_VIDEO_DATE_POSTED)
                    )
                            .videoURL(documentSnapshot.get(PostVideoConstants.KEY_POST_VIDEO_URL).toString())
                            .thumbnailURL(documentSnapshot.get(PostVideoConstants.KEY_POST_VIDEO_THUMBNAIL_URL).toString())
                            .postVideoID(documentSnapshot.getId())
                            .build();

                    postVideoModels.add(postVideo);
                }
                Log.d("postModel", postVideoModels.toString());
                postVideo.postValue(postVideoModels);
            }
        });

        //        Query query = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).orderByChild(PostVideoConstants.KEY_POST_VIDEO_TRAINER_ID).equalTo(FirebaseAuth.getInstance().getUid());
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                postVideoModels.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    PostVideo postVideo = new PostVideo
//                            .PostVideoBuilder(
//                            dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_TITLE).getValue().toString(),
//                            dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_CATEGORY).getValue().toString(),
//                            dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_DESCRIPTION).getValue().toString(),
//                            dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_TRAINER_ID).getValue().toString(),
//                            (long) dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_DATE_POSTED).getValue()
//                    )
//                            .videoURL(dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_URL).getValue().toString())
//                            .thumbnailURL(dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_THUMBNAIL_URL).getValue().toString())
//                            .postVideoID(dataSnapshot.getKey())
//                            .build();
//                    //postVideo.setName(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_NAME).getValue().toString());
//
//                    postVideoModels.add(postVideo);
//                }
//                dataLoadListener.onDataLoaded();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    public void bindDataObserver(){
        HashMap<String, Object> data = new HashMap<>();
        Query query = db.collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).whereEqualTo(PostVideoConstants.KEY_POST_VIDEO_TRAINER_ID, FirebaseAuth.getInstance().getUid());
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                boolean flag = true;
                for(DocumentChange dc : snapshot.getDocumentChanges()){
                    PostVideo postVideo = new PostVideo
                            .PostVideoBuilder(
                            dc.getDocument().get(PostVideoConstants.KEY_POST_VIDEO_TITLE).toString(),
                            dc.getDocument().get(PostVideoConstants.KEY_POST_VIDEO_CATEGORY).toString(),
                            dc.getDocument().get(PostVideoConstants.KEY_POST_VIDEO_DESCRIPTION).toString(),
                            dc.getDocument().get(PostVideoConstants.KEY_POST_VIDEO_TRAINER_ID).toString(),
                            (long) dc.getDocument().get(PostVideoConstants.KEY_POST_VIDEO_DATE_POSTED)
                    )
                            .videoURL(dc.getDocument().get(PostVideoConstants.KEY_POST_VIDEO_URL).toString())
                            .thumbnailURL(dc.getDocument().get(PostVideoConstants.KEY_POST_VIDEO_THUMBNAIL_URL).toString())
                            .postVideoID(dc.getDocument().getId())
                            .build();
                    if(data.get("postVideo") == null)
                        data.put("postVideo", postVideo);
                    switch(dc.getType()){
                        case ADDED:
                            flag = true;
                            for(PostVideo postVideoItem : postVideoModels){
                                if(postVideoItem.getPostVideoID().equals(postVideo.getPostVideoID()))
                                    flag = false;
                            }
                            if(flag){
                                Log.d("Data Added", dc.getDocument().getId());
                                postVideoModels.add(postVideo);
                                data.put("updateType", "insert");
                                postVideoUpdate.postValue(data);
                            }
                            break;
                        case MODIFIED:
                            Log.d("Data Modified", dc.getDocument().getId());
                            postVideoModels.set(dc.getNewIndex(), postVideo);
                            data.put("index", dc.getNewIndex());
                            data.put("updateType", "update");
                            postVideoUpdate.postValue(data);
                            break;
                        case REMOVED:
                            break;
                    }

                }
                flag = true;

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
