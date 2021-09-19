package com.pupccis.fitnex.ViewModel;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pupccis.fitnex.Model.Program;
import com.pupccis.fitnex.Repository.ProgramsRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class ProgramViewModel extends ViewModel {
    MutableLiveData<ArrayList<Program>> programs;
    private MutableLiveData<HashMap<String, Object>> programUpdate = new MutableLiveData<>();

    //Private Attributes
    private ProgramsRepository programsRepository;
    private Context context;

    public void init(Context context){
        if(programs != null){
            return;
        }
        this.context = context;
        programs = ProgramsRepository.getInstance().getPrograms();
        programUpdate = ProgramsRepository.getInstance().updatePrograms();
    }

    public MutableLiveData<ArrayList<Program>> getPrograms(){
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
