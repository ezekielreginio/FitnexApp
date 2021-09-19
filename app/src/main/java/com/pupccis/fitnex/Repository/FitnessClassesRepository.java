package com.pupccis.fitnex.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.pupccis.fitnex.API.adapter.FitnessClassAdapter;
import com.pupccis.fitnex.Model.FitnessClass;
import com.pupccis.fitnex.Utilities.Constants.FitnessClassConstants;
import com.pupccis.fitnex.Utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.Utilities.Preferences.UserPreferences;
import com.pupccis.fitnex.Utilities.VideoConferencingConstants;

import java.util.ArrayList;

public class FitnessClassesRepository {
    //Private attributes
    private ArrayList<FitnessClass> fitnessClassesModels = new ArrayList<>();

    //Mutable Live data
    MutableLiveData<ArrayList<FitnessClass>> fitnessClass = new MutableLiveData<>();

    //Static attributes
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static  FitnessClassesRepository instance;

    public static FitnessClassesRepository getInstance() {
        if(instance == null){
            instance = new FitnessClassesRepository();
        }
        return instance;
    }

    public MutableLiveData<ArrayList<FitnessClass>> getFitnessClasses() {
        loadFitnessClasses();
        fitnessClass.setValue(fitnessClassesModels);

        return fitnessClass;
    }

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
}
