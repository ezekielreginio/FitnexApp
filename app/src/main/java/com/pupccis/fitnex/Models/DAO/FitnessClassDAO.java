package com.pupccis.fitnex.Models.DAO;

import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pupccis.fitnex.Models.FitnessClass;
import com.pupccis.fitnex.Utilities.Constants.FitnessClassConstants;
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.Utilities.VideoConferencingConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FitnessClassDAO {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES);

    public void createClass(FitnessClass fitnessClass){
        FirebaseDatabase.getInstance().getReference(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .push()
                .setValue(fitnessClass);
    }
    public List<FitnessClass> readClasses(String userID){
        List<FitnessClass> fitnessClassList = new ArrayList<>();
        mDatabase.child(userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot dataSnapshot : task.getResult().getChildren()){
                    FitnessClass fitnessClass = new FitnessClass();
                    fitnessClass.setClassName(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_NAME).getValue().toString());
                    fitnessClass.setTimeStart(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_START).getValue().toString());
                    fitnessClass.setTimeEnd(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_END).getValue().toString());
                    fitnessClass.setSessionNo(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_SESSION_NUMBER).getValue().toString());
                    fitnessClass.setDateCreated(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_DATE_CREATED).getValue().toString());
                    fitnessClass.setDescription(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_DESCRIPTION).getValue().toString());
                    fitnessClassList.add(fitnessClass);
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
        mDatabase = mDatabase.child(fitnessClass.getClassTrainerID()).child(fitnessClass.getClassID());
        String key = mDatabase.push().getKey();
        Map postValues = fitnessClass.toMap();
        Log.d("Post Values", postValues.toString());
        mDatabase.updateChildren(postValues);
    }
}
