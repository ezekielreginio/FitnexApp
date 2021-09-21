package com.pupccis.fitnex.Repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.pupccis.fitnex.Model.Routine;
import com.pupccis.fitnex.Utilities.Constants.FitnessClassConstants;
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.Utilities.Constants.RoutineConstants;

import java.util.HashMap;

public class RoutinesRepository {
    //Static attributes
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static RoutinesRepository instance;

    public static RoutinesRepository getInstance(){
        if(instance == null){
            instance = new RoutinesRepository();
        }
        return instance;
    }
    public static void insertRoutine(Routine routine) {
        db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS)
                .document(routine.getProgramID())
                .collection(RoutineConstants.KEY_COLLECTION_ROUTINES)
                .get().addOnCompleteListener(task -> {
                    int childCount = task.getResult().size();
                    db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS)
                            .document(routine.getProgramID())
                            .collection(RoutineConstants.KEY_COLLECTION_ROUTINES)
                            .document(childCount+1+"").set(routine.toMap());
                }
        );
        //db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).document(routine.getProgramID()).collection(RoutineConstants.KEY_COLLECTION_ROUTINES).document().set(routine.toMap());
    }

    public Query getRoutinesQuery(String programID){
        //Query queryRoutines = db.collection(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES).whereEqualTo(FitnessClassConstants.KEY_FITNESS_CLASSES_TRAINER_ID, FirebaseAuth.getInstance().getCurrentUser().getUid());

        Query queryRoutines = db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).document(programID).collection(RoutineConstants.KEY_COLLECTION_ROUTINES);
        return queryRoutines;
    }
}
