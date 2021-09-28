package com.pupccis.fitnex.repository;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.pupccis.fitnex.model.Routine;
import com.pupccis.fitnex.utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.utilities.Constants.RoutineConstants;

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
        Query queryRoutines = db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).document(programID).collection(RoutineConstants.KEY_COLLECTION_ROUTINES);
        return queryRoutines;
    }
    public static void updateRoutine(Routine routine){
        Log.d("ROutine ID", routine.getId());
        db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS)
                .document(routine.getProgramID())
                .collection(RoutineConstants.KEY_COLLECTION_ROUTINES).document(routine.getId()).update(routine.toMap());
    }
    public static void deleteRoutine(Routine routine, String programID){
        db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS)
                .document(programID)
                .collection(RoutineConstants.KEY_COLLECTION_ROUTINES).document(routine.getId()).delete();
    }
}
