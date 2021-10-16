package com.pupccis.fitnex.repository;

import android.util.Log;

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
import com.google.firebase.firestore.QuerySnapshot;
import com.pupccis.fitnex.model.RealtimeRoutine;
import com.pupccis.fitnex.model.Routine;
import com.pupccis.fitnex.model.RoutineData;
import com.pupccis.fitnex.utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.utilities.Constants.RoutineConstants;

import java.util.ArrayList;
import java.util.List;

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

    public com.google.firebase.database.Query getRealtimeRoutinesQuery(String programID) {
       return FirebaseDatabase.getInstance().getReference(RoutineConstants.KEY_COLLECTION_ROUTINES).child(programID);
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

    public MutableLiveData<List<Routine>> getRoutineData(String programID) {
        MutableLiveData<List<Routine>> routineMutableLiveData = new MutableLiveData<>();
        List<Routine> routineData = new ArrayList<>();
        db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS)
                .document(programID)
                .collection(RoutineConstants.KEY_COLLECTION_ROUTINES).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot snapshot: task.getResult().getDocuments()){
                    Routine routine = new Routine.Builder(snapshot.get(RoutineConstants.KEY_ROUTINE_NAME).toString())
                            .category(0)
                            .sets(Integer.parseInt(snapshot.get(RoutineConstants.KEY_ROUTINE_SETS).toString()))
                            .reps(Integer.parseInt(snapshot.get(RoutineConstants.KEY_ROUTINE_REPS).toString()))
                            .weight(Double.parseDouble(snapshot.get(RoutineConstants.KEY_ROUTINE_WEIGHT).toString()))
                            .duration(Integer.parseInt(snapshot.get(RoutineConstants.KEY_ROUTINE_DURATION).toString()))
                            .routineID(snapshot.getId())
                            .programID(programID)
                            .build();
                    routineData.add(routine);
                }
                routineMutableLiveData.postValue(routineData);
            }
        });
        return routineMutableLiveData;
    }

    public void updateRealtimeRoutineTracker(RoutineData routineData, int routinePosition, String programID) {
        FirebaseDatabase.getInstance()
                .getReference(RoutineConstants.KEY_COLLECTION_ROUTINES)
                .child(programID)
                .child(routineData.getUserID())
                .child(RoutineConstants.KEY_COLLECTION_ROUTINE)
                .child(routineData.getRoutineSetID())
                .child(routinePosition+"")
                .setValue(routineData);
    }

    public void startRealtimeRoutine(RealtimeRoutine realtimeRoutine) {
        FirebaseDatabase.getInstance()
                .getReference(RoutineConstants.KEY_COLLECTION_ROUTINES)
                .child(realtimeRoutine.getProgramID())
                .child(FirebaseAuth.getInstance().getUid())
                .setValue(realtimeRoutine);
    }


    public MutableLiveData<List<RoutineData>> observeRoutineRealtime(Routine routine) {
        MutableLiveData<List<RoutineData>> routineDataLiveData = new MutableLiveData<>();
        List<RoutineData> routineDataList = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference(RoutineConstants.KEY_COLLECTION_ROUTINES)
                .child(routine.getProgramID())
                .child(routine.getUserID())
                .child(RoutineConstants.KEY_COLLECTION_ROUTINE)
                .child(routine.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    RoutineData routineData = new RoutineData.Builder()
                            .completed((boolean) data.child(RoutineConstants.KEY_ROUTINE_COMPLETED).getValue())
                            .position(Integer.parseInt(data.getKey()))
                            .reps(Integer.parseInt(data.child(RoutineConstants.KEY_ROUTINE_REPS).getValue().toString()))
                            .weight(Integer.parseInt(data.child(RoutineConstants.KEY_ROUTINE_WEIGHT).getValue().toString()))
                            .routineID(data.child(RoutineConstants.KEY_ROUTINE_ID).getValue().toString())
                            .build();
                    routineDataList.add(routineData);
                }
                routineDataLiveData.postValue(routineDataList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return routineDataLiveData;
    }

    public void setResting(String programID, String userID, boolean isResting) {
        FirebaseDatabase.getInstance()
                .getReference(RoutineConstants.KEY_COLLECTION_ROUTINES)
                .child(programID)
                .child(userID)
                .child(RoutineConstants.KEY_ROUTINE_IS_RESTING)
                .setValue(isResting);
//
//        db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS)
//                .document(routine.getProgramID())
//                .collection(RoutineConstants.KEY_COLLECTION_ROUTINES).document(routine.getId())..update(RoutineConstants.KEY_ROUTINE_IS_RESTING, isResting);
    }

    public MutableLiveData<Boolean> observeRestingStatus(Routine routine) {
        MutableLiveData<Boolean> isResting = new MutableLiveData<>();
        FirebaseDatabase.getInstance()
                .getReference(RoutineConstants.KEY_COLLECTION_ROUTINES)
                .child(routine.getProgramID())
                .child(routine.getUserID())
                .child(RoutineConstants.KEY_ROUTINE_IS_RESTING).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isResting.postValue((Boolean) snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return isResting;
    }

    public void deleteRealtimeRoutine(String programID, String userID) {
        FirebaseDatabase.getInstance()
                .getReference(RoutineConstants.KEY_COLLECTION_ROUTINES)
                .child(programID)
                .child(userID).removeValue();
    }
}
