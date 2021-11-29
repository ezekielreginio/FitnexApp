package com.pupccis.fitnex.repository;

import static com.pupccis.fitnex.utilities.Constants.ProgramRatingConstants.KEY_COLLECTION_RATINGS;

import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pupccis.fitnex.model.ProgramRating;
import com.pupccis.fitnex.utilities.Constants.ProgramConstants;

public class ProgramRatingRepository {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();


    public MutableLiveData<Boolean> saveRating(ProgramRating programRating) {
        MutableLiveData<Boolean> booleanMutableLiveData = new MutableLiveData<>();
        StorageReference ratingPhotoUploader = FirebaseStorage.getInstance().getReference().child("rating_photos/"+System.currentTimeMillis()+"."+getExtension(programRating.getRateProgramImageFileType()));
        ratingPhotoUploader.putFile(programRating.getRateProgramImage()).addOnSuccessListener(taskSnapshot -> {
            ratingPhotoUploader.getDownloadUrl().addOnSuccessListener(ratingPhotoURL -> {
                programRating.setPhotoURL(ratingPhotoURL.toString());
                db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).document(programRating.getProgramID()).collection(KEY_COLLECTION_RATINGS).document(FirebaseAuth.getInstance().getUid()).set(programRating.map());
            });
        });

        return booleanMutableLiveData;
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

    public Query getRatings(String programID){
        Log.e("Query", "Program Rating Query "+ programID);
        Query query = db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).document(programID).collection(KEY_COLLECTION_RATINGS);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot snapshot: task.getResult()) {
                    Log.e("Snapshot", snapshot.get("userName").toString());
                }
            }
        });

        return query;
    }
}
