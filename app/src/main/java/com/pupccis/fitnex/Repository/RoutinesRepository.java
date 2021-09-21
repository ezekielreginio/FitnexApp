package com.pupccis.fitnex.Repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.pupccis.fitnex.Model.Routine;
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.Utilities.Constants.RoutineConstants;

public class RoutinesRepository {
    //Static attributes
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static  RoutinesRepository instance;
    public void insertRoutine(Routine routine) {
        db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).document(RoutineConstants.KEY_COLLECTION_ROUTINES).set(routine.toMap());
    }
}
