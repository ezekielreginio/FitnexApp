package com.pupccis.fitnex.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.pupccis.fitnex.Model.Program;
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;

import java.util.ArrayList;

public class ProgramsRepository {
    //Private Attributes
    private ArrayList<Program> programModels = new ArrayList<>();

    //Mutable Live Data
    private MutableLiveData<ArrayList<Program>> program = new MutableLiveData<>();

    //Static Attributes
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static ProgramsRepository instance;

    public static ProgramsRepository getInstance(){
        if(instance == null){
            instance = new ProgramsRepository();
        }
        return instance;
    }

    public  MutableLiveData<ArrayList<Program>> getPrograms(){
        loadPrograms();
        program.setValue(programModels);

        return program;
    }

    private void loadPrograms() {
        Query query = db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).whereEqualTo(ProgramConstants.KEY_PROGRAM_TRAINER_ID, FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.get().addOnCompleteListener(task -> {
            programModels.clear();
            for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                Program program = new Program
                        .Builder(
                            documentSnapshot.get(ProgramConstants.KEY_PROGRAM_NAME).toString(),
                            documentSnapshot.get(ProgramConstants.KEY_PROGRAM_DESCRIPTION).toString(),
                            documentSnapshot.get(ProgramConstants.KEY_PROGRAM_CATEGORY).toString(),
                            documentSnapshot.get(ProgramConstants.KEY_PROGRAM_SESSION_NUMBER).toString(),
                            documentSnapshot.get(ProgramConstants.KEY_PROGRAM_DURATION).toString(),
                            documentSnapshot.get(ProgramConstants.KEY_PROGRAM_TRAINER_ID).toString()
                        )
                        .setProgramID(documentSnapshot.getId())
                        .setTrainees(documentSnapshot.get(ProgramConstants.KEY_PROGRAM_TRAINEES).toString())
                        .build();
                programModels.add(program);
            }
            program.postValue(programModels);
        });
    }

    public void insertProgram(Program program) {
        db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).document().set(program.toMap());
    }
}
