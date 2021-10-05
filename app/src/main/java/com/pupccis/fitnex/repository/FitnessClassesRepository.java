package com.pupccis.fitnex.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.pupccis.fitnex.model.FitnessClass;
import com.pupccis.fitnex.utilities.Constants.FitnessClassConstants;
import com.pupccis.fitnex.utilities.Constants.ProgramConstants;

import java.util.ArrayList;

public class FitnessClassesRepository {
    //Private attributes
    private ArrayList<FitnessClass> fitnessClassesModels = new ArrayList<>();


    //Mutable Live data


    //Static attributes
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static  FitnessClassesRepository instance;

    public static FitnessClassesRepository getInstance() {
        if(instance == null){
            instance = new FitnessClassesRepository();
        }
        return instance;
    }

    public static Query getFitnessClassesQuery(){
        Query queryFitnessClass = db.collection(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES).whereEqualTo(FitnessClassConstants.KEY_FITNESS_CLASSES_TRAINER_ID, FirebaseAuth.getInstance().getCurrentUser().getUid());
        //fitnessClass = dataObserver.getObjects(queryFitnessClass, new FitnessClass());
        return queryFitnessClass;
    }

//    public MutableLiveData<ArrayList<FitnessClass>> getFitnessClasses() {
//        loadFitnessClasses();
//        fitnessClass.setValue(fitnessClassesModels);
//
//        return fitnessClass;
//    }

    private void loadFitnessClasses() {

        Query query = db.collection(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES).whereEqualTo(FitnessClassConstants.KEY_FITNESS_CLASSES_TRAINER_ID, FirebaseAuth.getInstance().getCurrentUser().getUid());        // Query query = FirebaseDatabase.getInstance().getReference(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES).orderByChild(FitnessClassConstants.KEY_FITNESS_CLASSES_TRAINER_ID).equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

        query.get().addOnCompleteListener(task -> {
            fitnessClassesModels.clear();
            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                FitnessClass fitnessClass = new FitnessClass.Builder(documentSnapshot.get(FitnessClassConstants.KEY_FITNESS_CLASSES_NAME).toString()
                        ,documentSnapshot.get(FitnessClassConstants.KEY_FITNESS_CLASSES_DESCRIPTION).toString()
                        ,(int)documentSnapshot.get(FitnessClassConstants.KEY_FITNESS_CLASSES_CATEGORY)
                        ,documentSnapshot.get(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_START).toString()
                        ,documentSnapshot.get(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_END).toString()
                        ,documentSnapshot.get(FitnessClassConstants.KEY_FITNESS_CLASSES_SESSION_NUMBER).toString()
                        ,documentSnapshot.get(FitnessClassConstants.KEY_FITNESS_CLASSES_DURATION).toString())
                        .build();
//                    fitnessClass.setClassName(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_NAME).getValue().toString());
//                    fitnessClass.setTimeStart(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_START).getValue().toString());
//                    fitnessClass.setTimeEnd(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_END).getValue().toString());
//                    fitnessClass.setSessionNo(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_SESSION_NUMBER).getValue().toString());
//                    fitnessClass.setDescription(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_DESCRIPTION).getValue().toString());
//                    fitnessClass.setDuration(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_DURATION).getValue().toString());
//                    fitnessClass.setCategory(Integer.parseInt(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_CATEGORY).getValue().toString()));
//                    fitnessClass.setClassID(dataSnapshot.getKey());
//                    fitnessClass.setClassTrainerID(userPreferences.getString(VideoConferencingConstants.KEY_USER_ID));
                fitnessClassesModels.add(fitnessClass);
            }
        });
    }

    public static MutableLiveData<FitnessClass> insertFitnessClass(FitnessClass fitnessClass) {
        MutableLiveData<FitnessClass> fitnessClassLiveData = new MutableLiveData<>();
        db.collection(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES).document().set(fitnessClass.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    fitnessClassLiveData.postValue(fitnessClass);
                }
                else{
                    fitnessClassLiveData.postValue(null);
                }
            }
        });
        return fitnessClassLiveData;
    }

    public MutableLiveData<FitnessClass>  updateFitnessClass(FitnessClass updatedFitnessClass){
        MutableLiveData<FitnessClass> fitnessClassLiveData = new MutableLiveData<>();
        db.collection(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES).document(updatedFitnessClass.getClassID()).update(updatedFitnessClass.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    fitnessClassLiveData.postValue(updatedFitnessClass);
                }
                else{
                    fitnessClassLiveData.postValue(null);
                }
            }
        });
        return fitnessClassLiveData;
    }
    public void  deleteFitnessClass(String fitnessClassId){
        db.collection(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES).document(fitnessClassId).delete();
    }

    public Query readFitnessClassesQuery() {
        Query query = db.collection(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES).whereEqualTo(FitnessClassConstants.KEY_FITNESS_CLASSES_TRAINER_ID, FirebaseAuth.getInstance().getUid());
        return query;
    }
    public Query searchFitnessClassesQuery(String input) {
        Query query = db.collection(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES).orderBy("className").startAt(input).endAt(input+"\uf8ff");
        return query;
    }
}
