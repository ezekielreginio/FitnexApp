package com.pupccis.fitnex.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.pupccis.fitnex.Model.PostVideo;
import com.pupccis.fitnex.Model.Program;
import com.pupccis.fitnex.Utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;

import java.util.ArrayList;
import java.util.HashMap;

public class ProgramsRepository {
    //Private Attributes
    private ArrayList<Program> programModels = new ArrayList<>();

    //Mutable Live Data
    private MutableLiveData<ArrayList<Program>> program = new MutableLiveData<>();
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

    public  MutableLiveData<ArrayList<Program>> getPrograms(){
        loadPrograms();
        program.setValue(programModels);
        return program;
    }

    public MutableLiveData<HashMap<String, Object>> updatePrograms(){
        bindDataObserver();
        HashMap<String, Object> data = new HashMap<>();
        data.put("updateType", "");
        programUpdate.setValue(data);

        return programUpdate;
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
                        .build();
                programModels.add(program);
            }
            program.postValue(programModels);
        });
    }

    public void bindDataObserver(){
        HashMap<String, Object> data = new HashMap<>();
        data.put(GlobalConstants.KEY_UPDATE_TYPE, "");
        Query query = db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).whereEqualTo(ProgramConstants.KEY_PROGRAM_TRAINER_ID, FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addSnapshotListener((snapshot, error) -> {
            boolean flag = true;
            Log.d("Event Flagged", "triggered");
            for(DocumentChange dc : snapshot.getDocumentChanges()){
                Program program = new Program.Builder(
                        dc.getDocument().get(ProgramConstants.KEY_PROGRAM_NAME).toString(),
                        dc.getDocument().get(ProgramConstants.KEY_PROGRAM_DESCRIPTION).toString(),
                        dc.getDocument().get(ProgramConstants.KEY_PROGRAM_CATEGORY).toString(),
                        dc.getDocument().get(ProgramConstants.KEY_PROGRAM_SESSION_NUMBER).toString(),
                        dc.getDocument().get(ProgramConstants.KEY_PROGRAM_DURATION).toString(),
                        dc.getDocument().get(ProgramConstants.KEY_PROGRAM_TRAINER_ID).toString()
                )
                        .setProgramID(dc.getDocument().getId())
                        .build();
                switch (dc.getType()){
                    case ADDED:
                        for(Program programItem : programModels)
                            if(programItem.getProgramID().equals(program.getProgramID()))
                                flag = false;
                        if (flag){
                            programModels.add(program);
                            data.put(GlobalConstants.KEY_UPDATE_TYPE, GlobalConstants.KEY_UPDATE_TYPE_INSERT);
                            programUpdate.postValue(data);
                        }
                        break;
                    case REMOVED:
                        Log.d("REMOVED RAISED", "triggered");
                        for(Program programItem : programModels){
                            if(programItem.getProgramID().equals(program.getProgramID())){
                                Log.d("Pumasok sa IF", "triggered");
                                Log.d("Old Index", dc.getOldIndex()+"");
                                Log.d("New Index", dc.getNewIndex()+"");
                                Log.d("Index in Model", programModels.indexOf(programItem)+"");
                                data.put(GlobalConstants.KEY_UPDATE_TYPE, GlobalConstants.KEY_UPDATE_TYPE_DELETE);
                                data.put("index", programModels.indexOf(programItem));
                                programModels.remove(dc.getOldIndex());
                                programUpdate.postValue(data);
                                break;
                            }
                        }

                        break;
                    case MODIFIED:
                        Log.d("Update New Index", dc.getNewIndex()+"");
                        data.put(GlobalConstants.KEY_UPDATE_TYPE, GlobalConstants.KEY_UPDATE_TYPE_UPDATE);
                        data.put("index", dc.getNewIndex());
                        programModels.set(dc.getNewIndex(), program);
                        programUpdate.postValue(data);
                        break;
                }

            }
        });
    }

    public void insertProgram(Program program) {
        db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).document().set(program.toMap());
    }

    public void updateProgram(Program updatedProgram) {
        Log.d("Program update name", updatedProgram.getName());
        db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).document(updatedProgram.getProgramID()).update(updatedProgram.toMap());
    }

    public void deleteProgram(String programID){
        db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).document(programID).delete();
    }
}
