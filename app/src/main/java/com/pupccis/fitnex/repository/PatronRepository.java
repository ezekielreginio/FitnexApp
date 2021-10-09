package com.pupccis.fitnex.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.pupccis.fitnex.api.enums.Privilege;
import com.pupccis.fitnex.model.Patron;
import com.pupccis.fitnex.utilities.Constants.PatronConstants;

import java.util.HashMap;

public class PatronRepository {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<Boolean> insertPatronStatus = new MutableLiveData<>();

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



}
