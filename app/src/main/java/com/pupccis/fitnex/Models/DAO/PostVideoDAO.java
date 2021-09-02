package com.pupccis.fitnex.Models.DAO;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pupccis.fitnex.Models.PostVideo;
import com.pupccis.fitnex.Utilities.Constants.PostVideoConstants;

public class PostVideoDAO {
    private final Uri videoUri;
    private final String filetype;
    private final String thumbnailFiletype;
    private final Uri thumbnailUri;
    private StorageReference videoUploader;
    private StorageReference thumbnailUploader;
    private DatabaseReference databaseReferencePostedVideo;

    private Context context;

    private PostVideo postVideo;

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

    private void processVideoUploading(String filetype){

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
