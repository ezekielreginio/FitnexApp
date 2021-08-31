package com.pupccis.fitnex.Models.DAO;

import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

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

    private PostVideoDAO(PostVideoDAOBuilder builder){
        this.videoUri = builder.videoUri;
        this.filetype = builder.filetype;
    }

    public void postVideo(PostVideo postVideo){
        DatabaseReference databaseReferencePostedVideo = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        StorageReference uploader = FirebaseStorage.getInstance().getReference().child("postedvideos/"+System.currentTimeMillis()+"."+getExtension());
        uploader.putFile(videoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                postVideo.setVideoURL(uri.toString());
                                databaseReferencePostedVideo.push().setValue(postVideo);
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

    public static class PostVideoDAOBuilder{
        private final Uri videoUri;
        private final String filetype;


        public PostVideoDAOBuilder( String filetype, Uri videoUri) {
            this.videoUri = videoUri;
            this.filetype = filetype;
        }

        public PostVideoDAO build(){
            PostVideoDAO postVideoDAO = new PostVideoDAO(this);
            return postVideoDAO;
        }
    }
}
