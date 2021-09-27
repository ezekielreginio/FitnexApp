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
import com.pupccis.fitnex.model.FitnessModel;
import com.pupccis.fitnex.utilities.Constants.FitnessClassConstants;

import java.util.ArrayList;

public class FitnessClassesRepository {
    //Private attributes
    private ArrayList<FitnessModel> fitnessClassesModels = new ArrayList<>();


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
        //fitnessClass = dataObserver.getObjects(queryFitnessClass, new FitnessModel());
        return queryFitnessClass;
    }

//    public MutableLiveData<ArrayList<FitnessModel>> getFitnessClasses() {
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
                FitnessModel fitnessModel = new FitnessModel.Builder(documentSnapshot.get(FitnessClassConstants.KEY_FITNESS_CLASSES_NAME).toString()
                        ,documentSnapshot.get(FitnessClassConstants.KEY_FITNESS_CLASSES_DESCRIPTION).toString()
                        ,(int)documentSnapshot.get(FitnessClassConstants.KEY_FITNESS_CLASSES_CATEGORY)
                        ,documentSnapshot.get(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_START).toString()
                        ,documentSnapshot.get(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_END).toString()
                        ,documentSnapshot.get(FitnessClassConstants.KEY_FITNESS_CLASSES_SESSION_NUMBER).toString()
                        ,documentSnapshot.get(FitnessClassConstants.KEY_FITNESS_CLASSES_DURATION).toString())
                        .build();
//                    fitnessModel.setClassName(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_NAME).getValue().toString());
//                    fitnessModel.setTimeStart(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_START).getValue().toString());
//                    fitnessModel.setTimeEnd(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_END).getValue().toString());
//                    fitnessModel.setSessionNo(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_SESSION_NUMBER).getValue().toString());
//                    fitnessModel.setDescription(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_DESCRIPTION).getValue().toString());
//                    fitnessModel.setDuration(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_DURATION).getValue().toString());
//                    fitnessModel.setCategory(Integer.parseInt(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_CATEGORY).getValue().toString()));
//                    fitnessModel.setClassID(dataSnapshot.getKey());
//                    fitnessModel.setClassTrainerID(userPreferences.getString(VideoConferencingConstants.KEY_USER_ID));
                fitnessClassesModels.add(fitnessModel);
            }
        });
    }

    public static MutableLiveData<FitnessModel> insertFitnessClass(FitnessModel fitnessModel) {
        MutableLiveData<FitnessModel> fitnessClassLiveData = new MutableLiveData<>();
        db.collection(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES).document().set(fitnessModel.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    fitnessClassLiveData.postValue(fitnessModel);
                }
                else{
                    fitnessClassLiveData.postValue(null);
                }
            }
        });
        return fitnessClassLiveData;
    }

    public static void updateFitnessClass(FitnessModel fitnessModel){
        Log.d("Program ID", fitnessModel.getClassID());
        db.collection(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES).document(fitnessModel.getClassID()).update(fitnessModel.toMap());
    }
    public static void deleteFitnessClass(String fitnessClassId){
        db.collection(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES).document(fitnessClassId).delete();
    }

    public Query readFitnessClassesQuery() {
        Log.e("Pumasok sa Query", "Pumasokkkk");
        Query query = db.collection(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES);
        return query;
    }
}
