package com.pupccis.fitnex.ViewModel;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pupccis.fitnex.Model.Program;
import com.pupccis.fitnex.Repository.ProgramsRepository;

import java.util.ArrayList;

public class ProgramViewModel extends ViewModel {
    MutableLiveData<ArrayList<Program>> programs;

    //Private Attributes
    private ProgramsRepository programsRepository;
    private Context context;

    public void init(Context context){
        if(programs != null){
            return;
        }
        this.context = context;
        programs = ProgramsRepository.getInstance().getPrograms();
    }

    public MutableLiveData<ArrayList<Program>> getPrograms(){
        return programs;
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
