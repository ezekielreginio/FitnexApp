package com.pupccis.fitnex.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pupccis.fitnex.model.TopUpRequest;
import com.pupccis.fitnex.utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.utilities.Constants.TopUpConstants;

public class TopUpRepository {
    //Static Attributes
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static TopUpRepository instance;
    private String traineeName;

    public static TopUpRepository getInstance(){
        if(instance == null){
            instance = new TopUpRepository();
        }
        return instance;
    }

    public MutableLiveData<TopUpRequest> insertTopUpRequest(TopUpRequest topUpRequest){
        MutableLiveData<TopUpRequest> topUpRequestMutableLiveData = new MutableLiveData<>();

        db.collection(TopUpConstants.KEY_COLLECTION_TOPUP)
                .document()
                .set(topUpRequest.toMap())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    topUpRequestMutableLiveData.postValue(topUpRequest);
                else
                    topUpRequestMutableLiveData.postValue(null);
            }
        });
        return topUpRequestMutableLiveData;
    }
    public String getTraineeID(){
        String traineeID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return traineeID;
    }
    public String getTraineeName(){
        DocumentReference trainee= db.collection(GlobalConstants.KEY_COLLECTION_USERS).document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        trainee.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    traineeName = documentSnapshot.get("name").toString();
                }
            }
        });
        return traineeName;
    }
}
