package com.pupccis.fitnex.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    public MutableLiveData<Routine> insertRoutine(Routine routine) {
        MutableLiveData<Routine> routineLiveData = new MutableLiveData<>();
        db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS)
                .document(routine.getProgramID())
                .collection(RoutineConstants.KEY_COLLECTION_ROUTINES)
                .get().addOnCompleteListener(task -> {
                    int childCount = task.getResult().size();
                    db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS)
                            .document(routine.getProgramID())
                            .collection(RoutineConstants.KEY_COLLECTION_ROUTINES)
                            .document(childCount+1+"").set(routine.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                                routineLiveData.postValue(routine);
                            else
                                routineLiveData.postValue(null);

                        }
                    });
                }
        );
      return routineLiveData;
        //db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).document(routine.getProgramID()).collection(RoutineConstants.KEY_COLLECTION_ROUTINES).document().set(routine.toMap());
    }

    public Query getRoutinesQuery(String programID){
        Query queryRoutines = db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).document(programID).collection(RoutineConstants.KEY_COLLECTION_ROUTINES);
        return queryRoutines;
    }
    public MutableLiveData<Routine> updateRoutine(Routine routine){
        MutableLiveData<Routine> routineMutableLiveData = new MutableLiveData<>();
        Log.d("ROutine ID", routine.getId());
        db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS)
                .document(routine.getProgramID())
                .collection(RoutineConstants.KEY_COLLECTION_ROUTINES).document(routine.getId()).update(routine.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    routineMutableLiveData.postValue(routine);
                else
                    routineMutableLiveData.postValue(null);
            }
        });
        return routineMutableLiveData;
    }
    public void deleteRoutine(String routineID, String programID){
        db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS)
                .document(programID)
                .collection(RoutineConstants.KEY_COLLECTION_ROUTINES).document(routineID).delete();
    }
}
