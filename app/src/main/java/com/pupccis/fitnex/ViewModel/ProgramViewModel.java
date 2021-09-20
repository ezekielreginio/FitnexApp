package com.pupccis.fitnex.ViewModel;


import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.pupccis.fitnex.API.globals.DataObserver;
import com.pupccis.fitnex.Model.Program;
import com.pupccis.fitnex.Repository.ProgramsRepository;
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;

import java.util.ArrayList;
import java.util.HashMap;

public class ProgramViewModel extends ViewModel {
    MutableLiveData<ArrayList<Object>> programs;
    private MutableLiveData<HashMap<String, Object>> programUpdate = new MutableLiveData<>();

    //Private Attributes
    private ProgramsRepository programsRepository;
    private Context context;
    private DataObserver dataObserver = new DataObserver();

    public void init(Context context){
        if(programs != null){
            return;
        }
        this.context = context;

        //programs = ProgramsRepository.getInstance().getPrograms();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query queryProgram = db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).whereEqualTo(ProgramConstants.KEY_PROGRAM_TRAINER_ID, FirebaseAuth.getInstance().getCurrentUser().getUid());
        programs = dataObserver.getObjects(queryProgram, new Program());

        ArrayList<Object> programModels = new ArrayList<>();
        Program program = new Program();

        Query query = db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS).whereEqualTo(ProgramConstants.KEY_PROGRAM_TRAINER_ID, FirebaseAuth.getInstance().getCurrentUser().getUid());

        programUpdate = dataObserver.getLiveData(query, program);
    }

    public MutableLiveData<ArrayList<Object>> getPrograms(){
        return programs;
    }

    public MutableLiveData<HashMap<String, Object>> getLiveDataProgramUpdate(){
        return programUpdate;
    }

    public void insertProgram(Program program){
        ProgramsRepository.getInstance().insertProgram(program);
    }

    public void updateProgram(Program updatedProgram) {
        ProgramsRepository.getInstance().updateProgram(updatedProgram);
    }

    public void deleteProgram(String programID){
        ProgramsRepository.getInstance().deleteProgram(programID);
    }
}
