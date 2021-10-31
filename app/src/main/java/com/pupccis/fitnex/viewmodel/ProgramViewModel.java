package com.pupccis.fitnex.viewmodel;

import android.os.Bundle;
import android.util.Log;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.api.enums.Privilege;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.repository.ProgramsRepository;
import com.pupccis.fitnex.validation.Services.ProgramFitnessClassRoutineValidationService;
import com.pupccis.fitnex.validation.ValidationResult;

import java.util.HashMap;

public class ProgramViewModel extends BaseObservable {
    private ProgramsRepository programsRepository= ProgramsRepository.getInstance();
    private Program program_intent = new Program();
    //Mutable Live Data
    private MutableLiveData<Program> updateProgramLivedata = new MutableLiveData<>();
    private MutableLiveData<Program> deleteProgramLivedata = new MutableLiveData<>();
    private MutableLiveData<Program> routineProgramID = new MutableLiveData<>();
    private MutableLiveData<Program> containerIsClicked = new MutableLiveData<>();

    //Bindable Attributes
    @Bindable
    private String addProgramName = null;
    @Bindable
    private String addProgramDescription = null;
    @Bindable
    private int addProgramCategory = -1;
    @Bindable
    private Privilege addProgramPatronLevel = null;
    @Bindable
    private String addProgramSessionNumber = null;
    @Bindable
    private String addProgramDuration = null;
    @Bindable
    private HashMap<String, Object> programValidationData = null;
    @Bindable
    private String programID = null;

    //Bindable Attributes Getters
    public String getAddProgramName() {
        return addProgramName;
    }
    public String getAddProgramDescription() {
        return addProgramDescription;
    }
    public int getAddProgramCategory() {
        return addProgramCategory;
    }
    public Privilege getAddProgramPatronLevel() { return addProgramPatronLevel; }
    public String getAddProgramSessionNumber() {
        return addProgramSessionNumber;
    }
    public String getAddProgramDuration() {
        return addProgramDuration;
    }
    public HashMap<String, Object> getProgramValidationData() {
        return programValidationData;
    }
    public String getProgramID() { return programID; }



    //Bindable Attributes Setters
    public void setAddProgramName(String addProgramName) {
        this.addProgramName = addProgramName;
        onTextChangeProgram(addProgramName, com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields.NAME);
    }
    public void setAddProgramDescription(String addProgramDescription) {
        this.addProgramDescription = addProgramDescription;
        onTextChangeProgram(addProgramDescription, com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields.DESCRIPTION);
    }
    public void setAddProgramCategory(int addProgramCategory) {
        this.addProgramCategory = addProgramCategory;
    }
    public void setAddProgramPatronLevel(int addProgramPatronLevel) {
        switch (addProgramPatronLevel){
            case 1:
                this.addProgramPatronLevel = Privilege.FREE;
                break;
            case 2:
                this.addProgramPatronLevel = Privilege.BRONZE;
                break;
            case 3:
                this.addProgramPatronLevel = Privilege.SILVER;
                break;
            case 4:
                this.addProgramPatronLevel = Privilege.GOLD;
                break;
        }
        if(this.addProgramPatronLevel != null)
        Log.d("Privilege", this.addProgramPatronLevel.toString());
    }
    public void setAddProgramSessionNumber(String addProgramSessionNumber) {
        this.addProgramSessionNumber = addProgramSessionNumber;
        onTextChangeProgram(addProgramSessionNumber, com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields.SESSION_NUMBER);
    }
    public void setAddProgramDuration(String addProgramDuration) {
        this.addProgramDuration = addProgramDuration;
        onTextChangeProgram(addProgramDuration, com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields.DURATION);
    }

    public void setProgramID(String programID) {
        this.programID = programID;
    }

    public void setProgramValidationData(HashMap<String, Object> programValidationData) {
        this.programValidationData = programValidationData;
        notifyPropertyChanged(BR.programValidationData);
    }

    //Intent manager
    public Program getIntent(){
        return program_intent;
    }
    public void setIntent(Program program){
        this.program_intent = program;
    }
    public void clearIntent(){
        this.program_intent = null;
    }


    //ViewModel Methods
    public void onTextChangeProgram(String input, com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields field){
        ProgramFitnessClassRoutineValidationService programFitnessClassValidationService= new ProgramFitnessClassRoutineValidationService(input, field);
        ValidationResult result = programFitnessClassValidationService.validate();

        HashMap<String, Object> validationData = new HashMap<>();
        validationData.put("validationResult", result);
        validationData.put("field", field);
        setProgramValidationData(validationData);
    }

    public MutableLiveData<Program> insertProgram(){
        Program program = programInstance();
        return programsRepository.insertProgram(program);
    }


    //Trigger Methods
    public void triggerUpdateObserver(Program program) {
        setProgramID(program.getProgramID());
        updateProgramLivedata.postValue(program);
    }

    public void triggerDeleteObserver(Program program) {
        setProgramID(program.getProgramID());
        deleteProgramLivedata.postValue(program);
    }

    public void triggerRoutineObserver(Program program){
        setProgramID(program.getProgramID());
        routineProgramID.postValue(program);
    }

    public void triggerContainerClicked(Program program){
        setProgramID(program.getProgramID());
        containerIsClicked.postValue(program);
    }

    //Mutable Live Data Getters
    public MutableLiveData<Program> routineObserver(){
        return routineProgramID;
    }

    public MutableLiveData<Program> updateObserver(){
        return updateProgramLivedata;
    }

    public MutableLiveData<Program> deleteObserver(){
        return deleteProgramLivedata;
    }

    public MutableLiveData<Program> checkContainerClicked(){ return containerIsClicked;}

    public MutableLiveData<Boolean> checkProgramSaved(String programID){return programsRepository.checkProgramSaved(programID); }



    public void deleteProgram(String programID){
        programsRepository.deleteProgram(programID);
    }

    public MutableLiveData<Program> updateProgram() {
        Program program = programInstance();
        Log.d("Program ID", program.getProgramID());
        return programsRepository.updateProgram(program);
    }

    public Program programInstance(){
        Program program = new Program.Builder(getAddProgramName()
                ,getAddProgramDescription()
                ,getAddProgramCategory()
                ,getAddProgramPatronLevel().toString()
                ,getAddProgramSessionNumber()
                ,getAddProgramDuration())
                .setTrainerID(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setProgramID(getProgramID())
                .build();
        return program;
    }
}
