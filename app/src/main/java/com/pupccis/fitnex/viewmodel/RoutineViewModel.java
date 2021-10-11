package com.pupccis.fitnex.viewmodel;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.api.globals.DataObserver;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.model.RealtimeRoutine;
import com.pupccis.fitnex.model.Routine;
import com.pupccis.fitnex.model.RoutineData;
import com.pupccis.fitnex.repository.ProgramsRepository;
import com.pupccis.fitnex.repository.RoutinesRepository;
import com.pupccis.fitnex.validation.Services.ProgramFitnessClassRoutineValidationService;
import com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields;
import com.pupccis.fitnex.validation.ValidationResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoutineViewModel extends BaseObservable {
    //MutableLiveData attributes
    MutableLiveData<Routine> updateMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Routine> deleteMutableLiveData = new MutableLiveData<>();

    //Bindable Attributes
    @Bindable
    private String addRoutineName = null;
    @Bindable
    private String addRoutineCategory = null;
    @Bindable
    private String addRoutineRepCount = null;
    @Bindable
    private String addRoutineSetCount = null;
    @Bindable
    private String addRoutineWeight = null;
    @Bindable
    private String addRoutineDuration = null;
    @Bindable
    private HashMap<String, Object> routineValidationData = null;
    @Bindable
    private String routineID = null;

    @Bindable
    private String routineReps = null;
    @Bindable
    private String routineWeight = null;

    //Position Tracker
    private String currentRoutineID = null;
    private int currentRoutinePosition = -1;

    //Data Observer
    private DataObserver dataObserver = new DataObserver();

    //Private Attributes
    private RoutinesRepository routinesRepository = new RoutinesRepository();
    private String programID = null;
    private HashMap<String, HashMap<Integer, RoutineData>> routineDataList = new HashMap<>();

    public String getAddRoutineName() {
        return addRoutineName;
    }
    public String getAddRoutineCategory() {
        return addRoutineCategory;
    }
    public String getAddRoutineRepCount() {
        return addRoutineRepCount;
    }
    public String getAddRoutineSetCount() {
        return addRoutineSetCount;
    }
    public String getAddRoutineWeight() {
        return addRoutineWeight;
    }
    public String getAddRoutineDuration() {
        return addRoutineDuration;
    }
    public HashMap<String, Object> getRoutineValidationData() {
        return routineValidationData;
    }
    public String getProgramID() {
        return programID;
    }
    public String getRoutineID() {
        return routineID;
    }

    public String getRoutineReps() { return routineReps; }
    public String getRoutineWeight() { return routineWeight; }

    public String getCurrentRoutineID() { return currentRoutineID; }
    public int getCurrentRoutinePosition() { return currentRoutinePosition; }

    public void setAddRoutineName(String addRoutineName) {
        this.addRoutineName = addRoutineName;
        onTextChangeRoutine(addRoutineName, ProgramFitnessClassRoutineFields.NAME);
    }
    public void setAddRoutineCategory(String addRoutineCategory) {
        this.addRoutineCategory = addRoutineCategory;
        onTextChangeRoutine(addRoutineCategory, ProgramFitnessClassRoutineFields.CATEGORY);
    }
    public void setAddRoutineRepCount(String addRoutineRepCount) {
        this.addRoutineRepCount = addRoutineRepCount;
        onTextChangeRoutine(addRoutineRepCount, ProgramFitnessClassRoutineFields.REPS);
    }
    public void setAddRoutineSetCount(String addRoutineSetCount) {
        this.addRoutineSetCount = addRoutineSetCount;
        onTextChangeRoutine(addRoutineSetCount, ProgramFitnessClassRoutineFields.SETS);
    }
    public void setAddRoutineWeight(String addRoutineWeight) {
        this.addRoutineWeight = addRoutineWeight;
        onTextChangeRoutine(addRoutineWeight, ProgramFitnessClassRoutineFields.WEIGHT);
    }
    public void setAddRoutineDuration(String addRoutineDuration) {
        this.addRoutineDuration = addRoutineDuration;
        onTextChangeRoutine(addRoutineDuration, ProgramFitnessClassRoutineFields.DURATION);
    }
    public void setRoutineValidationData(HashMap<String, Object> routineValidationData) {
        this.routineValidationData = routineValidationData;
        notifyPropertyChanged(BR.routineValidationData);
    }
    public void setProgramID(String programID) {
        this.programID = programID;
    }
    public void setRoutineID(String routineID) {
        this.routineID = routineID;
    }

    public void setRoutineReps(String routineReps) {
        this.routineReps = routineReps;
    }
    public void setRoutineWeight(String routineWeight) { this.routineWeight = routineWeight; }

    public RoutineData setRoutineDataList(Routine routine, int routinePosition, String programID) {
        HashMap<Integer, RoutineData> routineDataMap = new HashMap<>();
        int routineReps = routine.getReps();
        double routineWeight = routineWeight = routine.getWeight();;
        if(routineDataList.get(routine.getId())!= null)
            routineDataMap = routineDataList.get(routine.getId());

        if(getRoutineReps() != null)
            if(!getRoutineReps().isEmpty())
                routineReps = Integer.parseInt(getRoutineReps());
        if(getRoutineWeight() != null)
            if(!getRoutineWeight().isEmpty())
                routineWeight = Double.parseDouble(getRoutineWeight());

        RoutineData routineData = new RoutineData.Builder()
                .position(routinePosition)
                .completed(true)
                .reps(routineReps)
                .weight(routineWeight)
                .routineID(routine.getId())
                .build();

        routineDataMap.put(routinePosition, routineData);
        routineDataList.put(routine.getId(), routineDataMap);
        routinesRepository.updateRealtimeRoutineTracker(routineData, routinePosition, programID);
        return routineData;
//        this.routineDataList = routineDataList;
    }

    public RoutineData setRoutineData(Routine routine){
        RoutineData routineData = new RoutineData.Builder()
                .completed(false)
                .weight(routine.getWeight())
                .reps(routine.getReps())
                .build();
        return routineData;
    }

    public void resetRoutineTracker(){
        setRoutineReps(null);
        setRoutineWeight(null);
    }

    //    public void insertRoutine(Routine routine){
//        routinesRepository.insertRoutine(routine);
//    }
//

    public Routine routineInstance(){
        Routine routine = new Routine.Builder(getAddRoutineName())
                .category(0)
                .sets(Integer.parseInt(getAddRoutineSetCount()))
                .reps(Integer.parseInt(getAddRoutineRepCount()))
                .weight(Integer.parseInt(getAddRoutineWeight()))
                .duration(Integer.parseInt(getAddRoutineDuration()))
                .programID(getProgramID())
                .routineID(getRoutineID())
                .build();
        return routine;
    }
    public MutableLiveData<Routine> insertRoutine(){
        Routine routine = routineInstance();
        return routinesRepository.insertRoutine(routine);
    }
    public MutableLiveData<Routine> updateRoutine(){
        Routine routine = routineInstance();
        return routinesRepository.updateRoutine(routine);
    }
    public void deleteRoutine(String programID){
        //Routine routine = routineInstance();
        routinesRepository.deleteRoutine(getRoutineID(), programID);
    }
    public void triggerUpdateObserver(Routine routine){
        setRoutineID(routine.getId());
        Log.e("ID sa routine view model",getRoutineID());
        updateMutableLiveData.postValue(routine);
    }

    public void triggerDeleteObserver(Routine routine){
        setRoutineID(routine.getId());
        deleteMutableLiveData.postValue(routine);
    }
    public MutableLiveData<Routine> updateObserver(){
        return updateMutableLiveData;
    }
    public MutableLiveData<Routine> deleteObserver(){
        return deleteMutableLiveData;
    }
//    public MutableLiveData<ArrayList<Object>>getRoutines(){
//        return routines;
//    }
//    public void updateRoutine(Routine routine){
//        RoutinesRepository.updateRoutine(routine);
//    }
//    public static void deleteRoutine(Routine routine, String programID){
//        RoutinesRepository.deleteRoutine(routine, programID);
//    }
    public void onTextChangeRoutine(String input, ProgramFitnessClassRoutineFields field){
        ProgramFitnessClassRoutineValidationService programFitnessClassRoutineValidationService= new ProgramFitnessClassRoutineValidationService(input, field);
        ValidationResult result = programFitnessClassRoutineValidationService.validate();

        HashMap<String, Object> validationData = new HashMap<>();
        validationData.put("validationResult", result);
        validationData.put("field", field);
        setRoutineValidationData(validationData);
    }

    public MutableLiveData<List<Routine>> getRoutineData(String programID) {
        return routinesRepository.getRoutineData(programID);
    }

    public void saveProgram(Program program_intent) {
        MutableLiveData<Boolean> isSaved = new MutableLiveData<>();
        ProgramsRepository programsRepository = new ProgramsRepository();
        programsRepository.saveProgram(program_intent);
    }

    public void startRoutine(String traineeName, String programID, String fcm_token, String email) {
        RealtimeRoutine realtimeRoutine = new RealtimeRoutine(traineeName, programID, fcm_token, email);
        routinesRepository.startRealtimeRoutine(realtimeRoutine);
    }
}
