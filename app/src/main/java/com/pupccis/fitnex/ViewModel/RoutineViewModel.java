package com.pupccis.fitnex.ViewModel;

import androidx.lifecycle.ViewModel;

import com.pupccis.fitnex.Model.Routine;
import com.pupccis.fitnex.Repository.RoutinesRepository;

public class RoutineViewModel extends ViewModel {
    private RoutinesRepository routinesRepository;

    public void insertRoutine(Routine routine){
        routinesRepository.insertRoutine(routine);
    }
}
