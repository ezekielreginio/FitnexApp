package com.pupccis.fitnex.model.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pupccis.fitnex.model.FitnessClass;
import com.pupccis.fitnex.utilities.Constants.FitnessClassConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FitnessClassDAO {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES);

    public void createClass(FitnessClass fitnessClass){
        String fitnessClassKey = FirebaseDatabase.getInstance().getReference(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES)
                .push().getKey();

        FirebaseDatabase.getInstance().getReference(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES)
                .child(fitnessClassKey)
                .setValue(fitnessClass);



        Log.d("Finished", "Created");
    }
    public List<FitnessClass> readClasses(String userID){
        List<FitnessClass> fitnessClassList = new ArrayList<>();
        mDatabase.child(userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot dataSnapshot : task.getResult().getChildren()){
                   // FitnessClass fitnessClass = new FitnessClass();
//                    fitnessClass.setClassName(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_NAME).getValue().toString());
//                    fitnessClass.setTimeStart(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_START).getValue().toString());
//                    fitnessClass.setTimeEnd(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_END).getValue().toString());
//                    fitnessClass.setSessionNo(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_SESSION_NUMBER).getValue().toString());
//                    fitnessClass.setDateCreated(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_DATE_CREATED).getValue().toString());
//                    fitnessClass.setDescription(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_DESCRIPTION).getValue().toString());
                    //fitnessClassList.add(fitnessClass);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        return fitnessClassList;
    }

    public void updateClass(FitnessClass fitnessClass){
        //mDatabase = mDatabase.child(fitnessClass.getClassTrainerID().child(program.getProgramID());
        mDatabase = mDatabase.child(fitnessClass.getClassID());
        String key = mDatabase.push().getKey();
        Map postValues = fitnessClass.toMap();
        Log.d("Post Values", postValues.toString());
        mDatabase.updateChildren(postValues);
    }

    public static void deleteClass(FitnessClass fitnessClass){
        FirebaseDatabase.getInstance().getReference(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES).child(fitnessClass.getClassID()).removeValue();
    }
}
