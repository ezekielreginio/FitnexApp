package com.pupccis.fitnex.viewmodel;

import static com.pupccis.fitnex.repository.FitnessClassesRepository.getFitnessClassesQuery;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pupccis.fitnex.api.globals.DataObserver;
import com.pupccis.fitnex.model.FitnessClass;
import com.pupccis.fitnex.repository.FitnessClassesRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class FitnessClassViewModel extends ViewModel {
    //MutableLiveData attributes
    private MutableLiveData<ArrayList<Object>> fitnessClasses;
    private MutableLiveData<HashMap<String, Object>> fitnessClassesUpdate = new MutableLiveData<>();

    //Data Observer
    private DataObserver dataObserver = new DataObserver();

    //Private attributes
    private FitnessClassesRepository fitnessClassesRepository;
    private Context context;

    public void init(Context context){
        if(fitnessClasses != null){
            return;
        }
        this.context = context;
        //fitnessClasses = FitnessClassesRepository.getInstance().getFitnessClasses();
        fitnessClasses = dataObserver.getObjects(getFitnessClassesQuery(), new FitnessClass());

        fitnessClassesUpdate = dataObserver.getLiveData(getFitnessClassesQuery(), new FitnessClass());
    }
    public MutableLiveData<ArrayList<Object>> getFitnessClasses(){
        return fitnessClasses;
    }

    public void insertFitnessClass(FitnessClass fitnessClass) {
        FitnessClassesRepository.insertFitnessClass(fitnessClass);
    }

    public MutableLiveData<HashMap<String, Object>> getLiveDataFitnessClassesUpdate(){
        return fitnessClassesUpdate;
    }

    public static void updateFitnessClass(FitnessClass fitnessClass){
        FitnessClassesRepository.updateFitnessClass(fitnessClass);
    }
    public static void deleteFitnessClass(String fitnessClassId){
        FitnessClassesRepository.deleteFitnessClass(fitnessClassId);
    }
}
