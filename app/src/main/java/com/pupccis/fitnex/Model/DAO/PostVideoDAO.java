package com.pupccis.fitnex.Model.DAO;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pupccis.fitnex.Model.PostVideo;
import com.pupccis.fitnex.Utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.Utilities.Constants.PostVideoConstants;
import com.pupccis.fitnex.Utilities.Constants.UserConstants;

public class PostVideoDAO {
    private Uri videoUri;
    private String filetype;
    private String thumbnailFiletype;
    private Uri thumbnailUri;
    private StorageReference videoUploader;
    private StorageReference thumbnailUploader;
    private DatabaseReference databaseReferencePostedVideo;

    private Context context;

    private PostVideo postVideo;

    private PostVideoDAO(){

    }

    private PostVideoDAO(PostVideoDAOBuilder builder){
        this.videoUri = builder.videoUri;
        this.filetype = builder.filetype;
        this.thumbnailFiletype = builder.thumbnailFiletype;
        this.thumbnailUri = builder.thumbnailUri;
        this.context = builder.context;

        this.videoUploader = FirebaseStorage.getInstance().getReference().child("postedvideos/"+System.currentTimeMillis()+"."+getExtension());
        this.thumbnailUploader = FirebaseStorage.getInstance().getReference().child("postedvideos_thumbnail/"+System.currentTimeMillis()+"."+getThumbnailExtension());
        this.databaseReferencePostedVideo = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO);
    }

    public void postVideo(PostVideo postVideo){
        this.setPostVideo(postVideo);
        videoUploader.putFile(videoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        videoUploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri videoUri) {
                                postVideo.setVideoURL(videoUri.toString());
                                postModel(postVideo);
                            }
                        });
                    }
                });

    }

    public static void likeEventVideo(String videoId, String userId, boolean liked){
        DatabaseReference postedVideo = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).child(videoId);
        DatabaseReference user = FirebaseDatabase.getInstance().getReference(GlobalConstants.KEY_COLLECTION_USERS).child(userId);

        if(liked){
            postedVideo.child(PostVideoConstants.KEY_POST_VIDEO_LIKES).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> videoliketask) {
                    user.child(UserConstants.KEY_COLLECTION_USER_LIKED_VIDEOS).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            long value = (long) videoliketask.getResult().getValue();
                            if(task.getResult().hasChild(videoId)){
                                value = value - 1;
                                user.child(UserConstants.KEY_COLLECTION_USER_LIKED_VIDEOS).child(videoId).removeValue();
                            }
                            else {
                                value = value + 1;
                                user.child(UserConstants.KEY_COLLECTION_USER_LIKED_VIDEOS).child(videoId).setValue(true);
                            }
                            videoliketask.getResult().getRef().setValue(value);
                        }
                    });
                }
            });

            user.child(UserConstants.KEY_COLLECTION_USER_DISLIKED_VIDEOS).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> dislikevideostask) {
                    if(dislikevideostask.getResult().hasChild(videoId)){
                        dislikevideostask.getResult().child(videoId).getRef().removeValue();
                        postedVideo.child(PostVideoConstants.KEY_POST_VIDEO_DISLIKES).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                long value = (long) task.getResult().getValue();
                                value = value - 1;
                                task.getResult().getRef().setValue(value);
                            }
                        });
                    }
                }
            });
        }

        else{
            postedVideo.child(PostVideoConstants.KEY_POST_VIDEO_DISLIKES).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> videodisliketask) {
                    user.child(UserConstants.KEY_COLLECTION_USER_DISLIKED_VIDEOS).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            long value = (long) videodisliketask.getResult().getValue();
                            if(task.getResult().hasChild(videoId)){
                                value = value - 1;
                                user.child(UserConstants.KEY_COLLECTION_USER_DISLIKED_VIDEOS).child(videoId).removeValue();
                            }
                            else {
                                value = value + 1;
                                user.child(UserConstants.KEY_COLLECTION_USER_DISLIKED_VIDEOS).child(videoId).setValue(true);
                            }
                            videodisliketask.getResult().getRef().setValue(value);
                        }
                    });
                }
            });
        }

        user.child(UserConstants.KEY_COLLECTION_USER_LIKED_VIDEOS).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> likevideostask) {
                if(likevideostask.getResult().hasChild(videoId)){
                    likevideostask.getResult().child(videoId).getRef().removeValue();
                    postedVideo.child(PostVideoConstants.KEY_POST_VIDEO_LIKES).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            long value = (long) task.getResult().getValue();
                            value = value - 1;
                            task.getResult().getRef().setValue(value);
                        }
                    });
                }
            }
        });
    }

    public static void loadVideoData(PostVideo postVideoData, Context context, String userId){
        DatabaseReference videoData = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).child(postVideoData.getPostVideoID());

        videoData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PostVideo postVideo = new PostVideo.PostVideoBuilder()
                        .likes(Integer.parseInt(snapshot.child(PostVideoConstants.KEY_POST_VIDEO_LIKES).getValue().toString()))
                        .dislikes(Integer.parseInt(snapshot.child(PostVideoConstants.KEY_POST_VIDEO_DISLIKES).getValue().toString()))
                        .views(Long.parseLong(snapshot.child(PostVideoConstants.KEY_POST_VIDEO_VIEWS).getValue().toString()))
                        .date_posted((long) snapshot.child(PostVideoConstants.KEY_POST_VIDEO_DATE_POSTED).getValue())
                        .build();

                Intent intent = new Intent("posted-video-data");
                intent.putExtra("videoData", postVideo);

                FirebaseDatabase.getInstance().getReference(GlobalConstants.KEY_COLLECTION_USERS).child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        if(task.getResult().child(UserConstants.KEY_COLLECTION_USER_LIKED_VIDEOS).hasChild(postVideoData.getPostVideoID())){
                            Log.d("Liked Videos", task.getResult().child(UserConstants.KEY_COLLECTION_USER_LIKED_VIDEOS).toString());
                            intent.putExtra("liked", true);
                        }
                        else {
                            intent.putExtra("liked", false);
                        }

                        if(task.getResult().child(UserConstants.KEY_COLLECTION_USER_DISLIKED_VIDEOS).hasChild(postVideoData.getPostVideoID())){
                            intent.putExtra("disliked", true);
                        }
                        else{
                            intent.putExtra("disliked", false);
                        }
                        LocalBroadcastManager.getInstance(context).sendBroadcastSync(intent);
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public static void incrementViews(String id){
        FirebaseDatabase.getInstance()
                .getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO)
                .child(id)
                .child(PostVideoConstants.KEY_POST_VIDEO_VIEWS)
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                long value = (long) task.getResult().getValue();
                value = value + 1;
                task.getResult().getRef().setValue(value);
            }
        });

    }


    private void postModel(PostVideo postVideo){
        thumbnailUploader.putFile(thumbnailUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                thumbnailUploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        postVideo.setThumbnailURL(uri.toString());
                        databaseReferencePostedVideo.push().setValue(postVideo);

                        Intent intent = new Intent("video-upload-response");
                        // You can also include some extra data.
                        intent.putExtra("videoUploadedSuccessfully", "Video Uploaded, Users can now view your video.");
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    }
                });
            }
        });

    }

    private String getExtension(){
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(filetype);
    }

    private String getThumbnailExtension(){
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(thumbnailFiletype);
    }


    public static class PostVideoDAOBuilder{
        private final Uri videoUri;
        private final String filetype;
        private final String thumbnailFiletype;
        private final Uri thumbnailUri;
        private final Context context;

        public PostVideoDAOBuilder( String filetype, Uri videoUri, String thumbnailFiletype, Uri thumbnailUri, Context context) {
            this.videoUri = videoUri;
            this.filetype = filetype;
            this.thumbnailFiletype = thumbnailFiletype;
            this.thumbnailUri = thumbnailUri;
            this.context = context;
        }

        public PostVideoDAO build(){
            PostVideoDAO postVideoDAO = new PostVideoDAO(this);
            return postVideoDAO;
        }
    }


    public PostVideo getPostVideo() {
        return postVideo;
    }

    public void setPostVideo(PostVideo postVideo) {
        this.postVideo = postVideo;
    }
}
