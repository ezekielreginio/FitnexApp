package com.pupccis.fitnex.model.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pupccis.fitnex.model.FitnessModel;
import com.pupccis.fitnex.utilities.Constants.FitnessClassConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FitnessClassDAO {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES);

    public void createClass(FitnessModel fitnessModel){
        String fitnessClassKey = FirebaseDatabase.getInstance().getReference(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES)
                .push().getKey();

        FirebaseDatabase.getInstance().getReference(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES)
                .child(fitnessClassKey)
                .setValue(fitnessModel);



        Log.d("Finished", "Created");
    }
    public List<FitnessModel> readClasses(String userID){
        List<FitnessModel> fitnessModelList = new ArrayList<>();
        mDatabase.child(userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot dataSnapshot : task.getResult().getChildren()){
                   // FitnessModel fitnessClass = new FitnessModel();
//                    fitnessClass.setClassName(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_NAME).getValue().toString());
//                    fitnessClass.setTimeStart(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_START).getValue().toString());
//                    fitnessClass.setTimeEnd(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_END).getValue().toString());
//                    fitnessClass.setSessionNo(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_SESSION_NUMBER).getValue().toString());
//                    fitnessClass.setDateCreated(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_DATE_CREATED).getValue().toString());
//                    fitnessClass.setDescription(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_DESCRIPTION).getValue().toString());
                    //fitnessModelList.add(fitnessClass);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        return fitnessModelList;
    }

    public void updateClass(FitnessModel fitnessModel){
        //mDatabase = mDatabase.child(fitnessModel.getClassTrainerID().child(program.getProgramID());
        mDatabase = mDatabase.child(fitnessModel.getClassID());
        String key = mDatabase.push().getKey();
        Map postValues = fitnessModel.toMap();
        Log.d("Post Values", postValues.toString());
        mDatabase.updateChildren(postValues);
    }

    public static void deleteClass(FitnessModel fitnessModel){
        FirebaseDatabase.getInstance().getReference(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES).child(fitnessModel.getClassID()).removeValue();
    }
}
