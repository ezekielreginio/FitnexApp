package com.pupccis.fitnex.repository;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pupccis.fitnex.api.enums.LikeType;
import com.pupccis.fitnex.model.PostVideo;
import com.pupccis.fitnex.model.VideoComment;
import com.pupccis.fitnex.utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.utilities.Constants.PostVideoConstants;
import com.pupccis.fitnex.utilities.Constants.UserConstants;
import com.pupccis.fitnex.utilities.Constants.VideoCommentConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public Query searchVideosQuery(String input){
        Query query = db.collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).orderBy("videoTitle").startAt(input).endAt(input+"\uf8ff");
        return query;
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

    //New Code
    //Private Attributes
    private DocumentReference userRef = db.collection(GlobalConstants.KEY_COLLECTION_USERS).document(FirebaseAuth.getInstance().getUid());

    public Query readPostVideosQuery(){
        Query query = FirebaseFirestore.getInstance().collection("posted_videos").whereEqualTo("trainerID", FirebaseAuth.getInstance().getUid());

        return query;
    }

    public Query readVideoCommentsQuery(String videoID){
        Query query = FirebaseFirestore.getInstance().collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).document(videoID).collection(VideoCommentConstants.KEY_COLLECTION_COMMENTS);
        return query;
    }
    public Query readVideoCommentRepliesQuery(VideoComment comment){
        Query query = FirebaseFirestore.getInstance().collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).document(comment.getVideoId()).collection(VideoCommentConstants.KEY_COLLECTION_COMMENTS).document(comment.getCommentId()).collection(VideoCommentConstants.KEY_COLLECTION_REPLIES);
        return query;
    }

    public MutableLiveData<HashMap<String, Object>> getVideoLikesData(String videoID){
        MutableLiveData<HashMap<String, Object>> livevideoLikesData = new MutableLiveData<>();
        db.collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).document(videoID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                HashMap<String, Object> videoLikesData = new HashMap<>();
                int likes = Integer.parseInt(value.get(PostVideoConstants.KEY_POST_VIDEO_LIKES).toString());
                int dislikes = Integer.parseInt(value.get(PostVideoConstants.KEY_POST_VIDEO_DISLIKES).toString());
                Log.d("likes", likes+"");
                videoLikesData.put(PostVideoConstants.KEY_POST_VIDEO_LIKES, likes);
                videoLikesData.put(PostVideoConstants.KEY_POST_VIDEO_DISLIKES, dislikes);

                livevideoLikesData.postValue(videoLikesData);
            }
        });


        return livevideoLikesData;
    }

    public MutableLiveData<HashMap<String, Boolean>> getUserLikeStatus(String videoID) {
        MutableLiveData<HashMap<String, Boolean>> userIsLiked = new MutableLiveData<>();
        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                HashMap<String, Boolean> response = new HashMap<>();
                if((List<String>) documentSnapshot.get(UserConstants.KEY_COLLECTION_USER_LIKED_VIDEOS)!= null) {
                    List<String> liked_videos = (List<String>) documentSnapshot.get(UserConstants.KEY_COLLECTION_USER_LIKED_VIDEOS);
                    if(liked_videos.contains(videoID))
                        response.put("liked", true);
                    else
                        response.put("liked", false);
                }
                if((List<String>) documentSnapshot.get(UserConstants.KEY_COLLECTION_USER_LIKED_VIDEOS)!= null){
                    List<String> disliked_videos = (List<String>) documentSnapshot.get(UserConstants.KEY_COLLECTION_USER_DISLIKED_VIDEOS);
                    if(disliked_videos.contains(videoID))
                        response.put("disliked", true);
                    else
                        response.put("disliked", false);
                }
                userIsLiked.postValue(response);
            }
        });

        return userIsLiked;
    }

    public void likeVideo(String videoID, String button){
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> liked_videos = new ArrayList<>();
                List<String> disliked_videos = new ArrayList<>();
                boolean isLiked = false;
                boolean isDisliked = false;
                if((List<String>) documentSnapshot.get(UserConstants.KEY_COLLECTION_USER_LIKED_VIDEOS)!= null){
                    liked_videos = (List<String>) documentSnapshot.get(UserConstants.KEY_COLLECTION_USER_LIKED_VIDEOS);
                    if(liked_videos.contains(videoID))
                        isLiked = true;
                }
                if((List<String>) documentSnapshot.get(UserConstants.KEY_COLLECTION_USER_DISLIKED_VIDEOS)!= null){
                    disliked_videos = (List<String>) documentSnapshot.get(UserConstants.KEY_COLLECTION_USER_DISLIKED_VIDEOS);
                    if(disliked_videos.contains(videoID))
                        isDisliked = true;
                }

                //Like Logic
                if(button.equals("liked")){
                    if(!isLiked)
                        executeLike(liked_videos, videoID);
                    else{
                        liked_videos.remove(videoID);
                        userRef.update(UserConstants.KEY_COLLECTION_USER_LIKED_VIDEOS, liked_videos);
                        updateLikesCounter(videoID, -1);
                    }
                    if(isDisliked){
                        disliked_videos.remove(videoID);
                        userRef.update(UserConstants.KEY_COLLECTION_USER_DISLIKED_VIDEOS, disliked_videos);
                        updateDislikesCounter(videoID, -1);
                    }
                }

                else if(button.equals("disliked")){
                    if(!isDisliked)
                        executeDislike(disliked_videos, videoID);
                    else{
                        disliked_videos.remove(videoID);
                        userRef.update(UserConstants.KEY_COLLECTION_USER_DISLIKED_VIDEOS, disliked_videos);
                        updateDislikesCounter(videoID, -1);
                    }
                    if(isLiked){
                        liked_videos.remove(videoID);
                        userRef.update(UserConstants.KEY_COLLECTION_USER_LIKED_VIDEOS, liked_videos);
                        updateLikesCounter(videoID, -1);
                    }
                }

            }
        });
    }



    private void executeLike(List<String> liked_videos, String videoID){
        liked_videos.add(videoID);
        userRef.update(UserConstants.KEY_COLLECTION_USER_LIKED_VIDEOS, liked_videos);

        updateLikesCounter(videoID, 1);
    }

    private void executeDislike(List<String> disliked_videos, String videoID) {
        disliked_videos.add(videoID);
        userRef.update(UserConstants.KEY_COLLECTION_USER_DISLIKED_VIDEOS, disliked_videos);

        updateDislikesCounter(videoID, 1);
    }

    private void updateLikesCounter(String videoID, int value){
        db.collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).document(videoID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                int likes = Integer.parseInt(documentSnapshot.get(PostVideoConstants.KEY_POST_VIDEO_LIKES).toString());
                likes = likes + value;
                db.collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).document(videoID).update(PostVideoConstants.KEY_POST_VIDEO_LIKES, likes);
            }
        });
    }
    private void updateDislikesCounter(String videoID, int value) {
        db.collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).document(videoID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                int dislikes = Integer.parseInt(documentSnapshot.get(PostVideoConstants.KEY_POST_VIDEO_DISLIKES).toString());
                dislikes = dislikes + value;
                db.collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).document(videoID).update(PostVideoConstants.KEY_POST_VIDEO_DISLIKES, dislikes);
            }
        });
    }

    //Comments Repository
    public void postComment(VideoComment comment){
        db.collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).document(comment.getVideoId()).collection(VideoCommentConstants.KEY_COLLECTION_COMMENTS).document().set(comment.map());
    }

    public void postReply(VideoComment comment) {
        db.collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO)
                .document(comment.getVideoId())
                .collection(VideoCommentConstants.KEY_COLLECTION_COMMENTS)
                .document(comment.getParentCommentId())
                .collection(VideoCommentConstants.KEY_COLLECTION_REPLIES)
                .document()
                .set(comment.map());
    }

    public void deleteComment(VideoComment comment) {
        if(comment.getType().equals(VideoCommentConstants.KEY_VIDEO_COMMENT))
            db.collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO)
                    .document(comment.getVideoId())
                    .collection(VideoCommentConstants.KEY_COLLECTION_COMMENTS)
                    .document(comment.getCommentId()).delete();
        else
            db.collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO)
                    .document(comment.getVideoId())
                    .collection(VideoCommentConstants.KEY_COLLECTION_COMMENTS)
                    .document(comment.getParentCommentId())
                    .collection(VideoCommentConstants.KEY_COLLECTION_REPLIES)
                    .document(comment.getCommentId())
                    .delete();
    }

    public void likeComment(VideoComment comment, LikeType likeType){
        DocumentReference commentsDoc = null;
        if(comment.getType().equals(VideoCommentConstants.KEY_VIDEO_COMMENT))
            commentsDoc = db.collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO)
                    .document(comment.getVideoId())
                    .collection(VideoCommentConstants.KEY_COLLECTION_COMMENTS)
                    .document(comment.getCommentId());
        else
            commentsDoc = db.collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO)
                    .document(comment.getVideoId())
                    .collection(VideoCommentConstants.KEY_COLLECTION_COMMENTS)
                    .document(comment.getParentCommentId())
                    .collection(VideoCommentConstants.KEY_COLLECTION_REPLIES)
                    .document(comment.getCommentId());
        commentsDoc.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                DocumentReference innerDoc = null;
                if(comment.getType().equals(VideoCommentConstants.KEY_VIDEO_COMMENT))
                    innerDoc = db.collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO)
                            .document(comment.getVideoId())
                            .collection(VideoCommentConstants.KEY_COLLECTION_COMMENTS)
                            .document(comment.getCommentId());
                else
                    innerDoc = db.collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO)
                            .document(comment.getVideoId())
                            .collection(VideoCommentConstants.KEY_COLLECTION_COMMENTS)
                            .document(comment.getParentCommentId())
                            .collection(VideoCommentConstants.KEY_COLLECTION_REPLIES)
                            .document(comment.getCommentId());

                Log.d("Success", "query");
                List<String> likes = new ArrayList<>();
                List<String> dislikes = new ArrayList<>();
                boolean isLiked = false;
                boolean isDisliked = false;

                if((List<String>) documentSnapshot.get(VideoCommentConstants.KEY_VIDEO_COMMENT_LIKES)!= null){
                    likes = (List<String>) documentSnapshot.get(VideoCommentConstants.KEY_VIDEO_COMMENT_LIKES);
                    Log.d("array", likes.toString());
                    if(likes.contains(FirebaseAuth.getInstance().getUid())){
                        Log.d("Like", "Exist");
                        isLiked = true;
                    }
                }

                if((List<String>) documentSnapshot.get(VideoCommentConstants.KEY_VIDEO_COMMENT_DISLIKES)!= null){
                    dislikes = (List<String>) documentSnapshot.get(VideoCommentConstants.KEY_VIDEO_COMMENT_DISLIKES);
                    if(dislikes.contains(FirebaseAuth.getInstance().getUid())){
                        Log.d("Like", "Exist");
                        isDisliked = true;
                    }
                }

                switch (likeType){
                    case LIKE:
                        if(!isLiked)
                            likes.add(FirebaseAuth.getInstance().getUid());
                        else
                            likes.remove(comment.getUserID());
                        innerDoc.update(VideoCommentConstants.KEY_VIDEO_COMMENT_LIKES, likes);

                        if(isDisliked){
                            dislikes.remove(comment.getUserID());
                            innerDoc.update(VideoCommentConstants.KEY_VIDEO_COMMENT_DISLIKES, dislikes);
                        }
                        break;

                    case DISLIKE:
                        if(!isDisliked)
                            dislikes.add(FirebaseAuth.getInstance().getUid());
                        else
                            dislikes.remove(comment.getUserID());
                        innerDoc.update(VideoCommentConstants.KEY_VIDEO_COMMENT_DISLIKES, dislikes);

                        if(isLiked){
                            likes.remove(comment.getUserID());
                            innerDoc.update(VideoCommentConstants.KEY_VIDEO_COMMENT_LIKES, likes);
                        }
                        break;
                }
            }
        });
    }

    public MutableLiveData<HashMap<String, Object>> getCommentLikesData(VideoComment comment) {
        MutableLiveData<HashMap<String, Object>> liveData = new MutableLiveData<>();
        HashMap<String, Object> commentLikesData = new HashMap<>();

        db.collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO)
                .document(comment.getVideoId())
                .collection(VideoCommentConstants.KEY_COLLECTION_COMMENTS)
                .document(comment.getCommentId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                int likes = 0;
                int dislikes = 0;
                if((List<String>) value.get(VideoCommentConstants.KEY_VIDEO_COMMENT_LIKES)!= null)
                    likes = ((List<String>) value.get(VideoCommentConstants.KEY_VIDEO_COMMENT_LIKES)).size();
                if((List<String>) value.get(VideoCommentConstants.KEY_VIDEO_COMMENT_DISLIKES)!= null)
                    dislikes = ((List<String>) value.get(VideoCommentConstants.KEY_VIDEO_COMMENT_DISLIKES)).size();
                commentLikesData.put(VideoCommentConstants.KEY_VIDEO_COMMENT_LIKES, likes);
                commentLikesData.put(VideoCommentConstants.KEY_VIDEO_COMMENT_DISLIKES, dislikes);

                liveData.postValue(commentLikesData);
            }
        });

        return liveData;
    }


    public MutableLiveData<HashMap<String, Object>> getRepliesCounter(VideoComment comment) {
        MutableLiveData<HashMap<String, Object>> replyData = new MutableLiveData<>();
        db.collection(PostVideoConstants.KEY_COLLECTION_POST_VIDEO)
                .document(comment.getVideoId())
                .collection(VideoCommentConstants.KEY_COLLECTION_COMMENTS)
                .document(comment.getCommentId())
                .collection(VideoCommentConstants.KEY_COLLECTION_REPLIES)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        HashMap<String, Object> data = new HashMap<>();
                        Integer size = value.getDocuments().size();
                        data.put("replyCount", size);
                        data.put("commentID", comment.getCommentId());
                        replyData.postValue(data);
                    }
                });
        return replyData;
    }
}
