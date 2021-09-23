package com.pupccis.fitnex.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pupccis.fitnex.API.globals.DataObserver;
import com.pupccis.fitnex.Model.Routine;
import com.pupccis.fitnex.Repository.RoutinesRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class RoutineViewModel extends ViewModel {
    //MutableLiveData attributes
    private MutableLiveData<ArrayList<Object>> routines;
    private MutableLiveData<HashMap<String, Object>> routinesUpdate = new MutableLiveData<>();

    //Data Observer
    private DataObserver dataObserver = new DataObserver();

    //Private Attributes
    private RoutinesRepository routinesRepository = new RoutinesRepository();
    private Context context;
    public void init(Context context, String programID){
        if(routines != null){
            return;
        }
        this.context = context;
        routines = dataObserver.getObjects(routinesRepository.getRoutinesQuery(programID), new Routine());
    }

    public void insertRoutine(Routine routine){
        routinesRepository.insertRoutine(routine);
    }

    public MutableLiveData<ArrayList<Object>>getRoutines(){
        return routines;
    }
    public MutableLiveData<HashMap<String, Object>> getLiveDataRoutines(String programID){
        routinesUpdate = dataObserver.getLiveData(routinesRepository.getRoutinesQuery(programID), new Routine());

        return routinesUpdate;
    }
    public void updateRoutine(Routine routine){
        RoutinesRepository.updateRoutine(routine);
    }
    public static void deleteRoutine(Routine routine, String programID){
        RoutinesRepository.deleteRoutine(routine, programID);
    }
}
