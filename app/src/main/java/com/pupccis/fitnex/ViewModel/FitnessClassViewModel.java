package com.pupccis.fitnex.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pupccis.fitnex.Model.FitnessClass;
import com.pupccis.fitnex.Repository.FitnessClassesRepository;

import java.util.ArrayList;

public class FitnessClassViewModel extends ViewModel {
    MutableLiveData<ArrayList<FitnessClass>> fitnessClasses;

    //Private attributes
    private FitnessClassesRepository fitnessClassesRepository;
    private Context context;

    public void init(Context context){
        if(fitnessClasses != null){
            return;
        }
        this.context = context;
        fitnessClasses = FitnessClassesRepository.getInstance().getFitnessClasses();
    }
    public MutableLiveData<ArrayList<FitnessClass>> getFitnessClasses(){
        return fitnessClasses;
    }
}
