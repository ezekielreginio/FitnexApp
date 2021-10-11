package com.pupccis.fitnex.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.pupccis.fitnex.api.enums.Privilege;
import com.pupccis.fitnex.model.Patron;
import com.pupccis.fitnex.utilities.Constants.PatronConstants;
import com.pupccis.fitnex.utilities.Constants.UserConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PatronRepository {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<Boolean> insertPatronStatus = new MutableLiveData<>();
    private MutableLiveData<Boolean> patronDataCheck = new MutableLiveData<>();
    private MutableLiveData<HashMap<String, Object>> patronData = new MutableLiveData<>();
    private MutableLiveData<Privilege> privilegeStatus = new MutableLiveData<>();

    public PatronRepository(){

    }

    public MutableLiveData<Boolean> insertPatronSetup(Patron patron){
        HashMap<String, Object> patronInfo = new HashMap<>();
        patronInfo.put(PatronConstants.KEY_DATE_UPDATED, patron.getDateUpdated());

        WriteBatch batch = db.batch();
        batch.set(db.collection(PatronConstants.KEY_COLLECTION_PATRON).document(FirebaseAuth.getInstance().getUid()), patronInfo);
        batch.set(db.collection(PatronConstants.KEY_COLLECTION_PATRON).document(FirebaseAuth.getInstance().getUid()).collection(PatronConstants.KEY_COLLECTION_PRIVILEGE).document(Privilege.BRONZE.toString()), patron.getPrivilegeData().get(Privilege.BRONZE));
        batch.set(db.collection(PatronConstants.KEY_COLLECTION_PATRON).document(FirebaseAuth.getInstance().getUid()).collection(PatronConstants.KEY_COLLECTION_PRIVILEGE).document(Privilege.SILVER.toString()), patron.getPrivilegeData().get(Privilege.SILVER));
        batch.set(db.collection(PatronConstants.KEY_COLLECTION_PATRON).document(FirebaseAuth.getInstance().getUid()).collection(PatronConstants.KEY_COLLECTION_PRIVILEGE).document(Privilege.GOLD.toString()), patron.getPrivilegeData().get(Privilege.GOLD));

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                insertPatronStatus.postValue(true);
            }
        });

        return insertPatronStatus;
    }


    public MutableLiveData<Boolean> checkPatronData() {
        db.collection(PatronConstants.KEY_COLLECTION_PATRON).document(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    Log.d("Data", "Exist");
                    patronDataCheck.setValue(true);
                }
                else{
                    patronDataCheck.setValue(false);
                }
            }
        });
        return patronDataCheck;
    }

    public MutableLiveData<HashMap<String, Object>> getPatronDataCheck(String userID) {
        HashMap<Privilege, HashMap<String, Object>> privilegeData = new HashMap<>();
        HashMap<String, Object> patronDataMap = new HashMap<>();


        db.collection(PatronConstants.KEY_COLLECTION_PATRON).document(userID).collection(PatronConstants.KEY_COLLECTION_PRIVILEGE).document(Privilege.BRONZE.toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                HashMap<String, Object> bronzeData = (HashMap<String, Object>) task.getResult().getData();
                privilegeData.put(Privilege.BRONZE, bronzeData);
                patronDataMap.put(PatronConstants.KEY_COLLECTION_PRIVILEGE, privilegeData);
                patronData.postValue(patronDataMap);
            }
        });

        db.collection(PatronConstants.KEY_COLLECTION_PATRON).document(userID).collection(PatronConstants.KEY_COLLECTION_PRIVILEGE).document(Privilege.SILVER.toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                HashMap<String, Object> silverData = (HashMap<String, Object>) task.getResult().getData();
                privilegeData.put(Privilege.SILVER, silverData);
                patronData.postValue(patronDataMap);
            }
        });

        db.collection(PatronConstants.KEY_COLLECTION_PATRON).document(userID).collection(PatronConstants.KEY_COLLECTION_PRIVILEGE).document(Privilege.GOLD.toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                HashMap<String, Object> goldData = (HashMap<String, Object>) task.getResult().getData();
                privilegeData.put(Privilege.GOLD, goldData);
                patronData.postValue(patronDataMap);
            }
        });

        return patronData;
    }

    public MutableLiveData<Privilege> checkPatronStatus(String trainerID) {
        db.collection(UserConstants.KEY_COLLECTION_USERS).document(FirebaseAuth.getInstance().getUid()).collection(UserConstants.KEY_COLLECTION_SUBSCRIPTIONS).document(trainerID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists())
                    privilegeStatus.postValue(Privilege.valueOf(task.getResult().get(PatronConstants.KEY_COLLECTION_PRIVILEGE).toString()));
                else
                    privilegeStatus.postValue(Privilege.NONE);
            }
        });

        return privilegeStatus;
    }

    public MutableLiveData<Privilege> subscribe(String userID, Privilege privilege) {
        privilegeStatus = new MutableLiveData<>();
        CollectionReference privilegeCollection = db.collection(PatronConstants.KEY_COLLECTION_PATRON)
                .document(userID)
                .collection(PatronConstants.KEY_COLLECTION_PRIVILEGE);

        DocumentReference subscribersDocument = db.collection(PatronConstants.KEY_COLLECTION_PATRON)
                .document(userID)
                .collection(PatronConstants.KEY_COLLECTION_PRIVILEGE)
                .document(privilege.toString());

        if(privilege != Privilege.BRONZE)
            unsubscribe(Privilege.BRONZE, userID);
        if(privilege != Privilege.SILVER)
            unsubscribe(Privilege.SILVER, userID);
        if(privilege != Privilege.GOLD)
            unsubscribe(Privilege.GOLD, userID);

        subscribersDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ArrayList<String> subscribers = new ArrayList<>();
                if(task.getResult().get(PatronConstants.KEY_COLLECTION_SUBSCRIBERS) != null)
                    subscribers =(ArrayList) task.getResult().get(PatronConstants.KEY_COLLECTION_SUBSCRIBERS);
                if(!subscribers.contains(FirebaseAuth.getInstance().getUid())){
                    subscribers.add(FirebaseAuth.getInstance().getUid());
                    subscribersDocument.update(PatronConstants.KEY_COLLECTION_SUBSCRIBERS, subscribers);
                    db.collection(UserConstants.KEY_COLLECTION_USERS).document(FirebaseAuth.getInstance().getUid())
                            .collection(PatronConstants.KEY_COLLECTION_SUBSCRIPTIONS)
                            .document(userID).set(new Patron(privilege).mapPrivilege());
                    privilegeStatus.postValue(privilege);
                }
                else{
                    Log.d("User Already Subscribed","User is already subscribed to this privilege");
                }
            }
        });

        return privilegeStatus;
    }

    private void unsubscribe(Privilege privilege, String userID) {
        CollectionReference privilegeCollection = db.collection(PatronConstants.KEY_COLLECTION_PATRON)
                .document(userID)
                .collection(PatronConstants.KEY_COLLECTION_PRIVILEGE);

        privilegeCollection.document(privilege.toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ArrayList<String> subscribers = new ArrayList<>();
                if(task.getResult().get(PatronConstants.KEY_COLLECTION_SUBSCRIBERS) != null)
                    subscribers =(ArrayList) task.getResult().get(PatronConstants.KEY_COLLECTION_SUBSCRIBERS);
                subscribers.remove(FirebaseAuth.getInstance().getUid());
                privilegeCollection.document(privilege.toString()).update(PatronConstants.KEY_COLLECTION_SUBSCRIBERS, subscribers);
            }
        });
    }
}
