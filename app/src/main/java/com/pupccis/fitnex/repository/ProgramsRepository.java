package com.pupccis.fitnex.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.model.User;
import com.pupccis.fitnex.utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.utilities.Constants.UserConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProgramsRepository {
    //Private Attributes
    private ArrayList<Object> programModels = new ArrayList<>();

    //Mutable Live Data
    private MutableLiveData<ArrayList<Object>> program = new MutableLiveData<>();
    private MutableLiveData<HashMap<String, Object>> programUpdate = new MutableLiveData<>();

    //Static Attributes
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static ProgramsRepository instance;

    public static ProgramsRepository getInstance(){
        if(instance == null){
            instance = new ProgramsRepository();
        }
        return instance;
    }

    public MutableLiveData<Program> insertProgram(Program program) {
        MutableLiveData<Program> programLiveData = new MutableLiveData<>();
        //ProgramsRepository.getInstance().insertProgram(program);

        db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).document().set(program.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    programLiveData.postValue(program);
                }
                else{
                    programLiveData.postValue(null);
                }
            }
        });
        return programLiveData;
    }

    public Query readProgramsQuery(){
        Query query = db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).whereEqualTo(ProgramConstants.KEY_PROGRAM_TRAINER_ID, FirebaseAuth.getInstance().getUid());
        return query;
    }
    public Query searchProgramsQuery(String input){
        Query query = db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).orderBy("name").startAt(input).endAt(input+"\uf8ff");
        return query;
    }

    public MutableLiveData<Program> updateProgram(Program updatedProgram) {
        MutableLiveData<Program> programLiveData = new MutableLiveData<>();
        db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).document(updatedProgram.getProgramID()).update(updatedProgram.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    programLiveData.postValue(updatedProgram);
                }
                else{
                    programLiveData.postValue(null);
                }

            }
        });
        return programLiveData;
    }

    public void deleteProgram(String programID){
        db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).document(programID).delete();
    }

    public MutableLiveData<Boolean> checkProgramSaved(String programID) {
        MutableLiveData<Boolean> isSaved = new MutableLiveData<>();
        db.collection(UserConstants.KEY_COLLECTION_USERS)
                .document(FirebaseAuth.getInstance().getUid())
                .collection(ProgramConstants.KEY_COLLECTION_SAVED_PROGRAMS)
                .document(programID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists())
                    isSaved.postValue(true);
                else
                    isSaved.postValue(false);
            }
        });
        return isSaved;
    }

    public void saveProgram(Program program_intent) {
        db.collection(UserConstants.KEY_COLLECTION_USERS)
                .document(FirebaseAuth.getInstance().getUid())
                .collection(ProgramConstants.KEY_COLLECTION_SAVED_PROGRAMS)
                .document(program_intent.getProgramID()).set(program_intent.toMap());
    }
}
