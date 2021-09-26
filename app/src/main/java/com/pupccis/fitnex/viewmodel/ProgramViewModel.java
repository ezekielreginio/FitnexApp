package com.pupccis.fitnex.viewmodel;


import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.api.globals.DataObserver;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.repository.ProgramsRepository;
import com.pupccis.fitnex.utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.validation.Services.ProgramFitnessClassValidationService;
import com.pupccis.fitnex.validation.ValidationResult;
import com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassFields;

import java.util.ArrayList;
import java.util.HashMap;

public class ProgramViewModel extends BaseObservable {
    MutableLiveData<ArrayList<Object>> programs;
    private MutableLiveData<HashMap<String, Object>> programUpdate = new MutableLiveData<>();
    
    //Private Attributes
    private ProgramsRepository programsRepository;
    private Context context;
    private DataObserver dataObserver = new DataObserver();

    //Bindable Attributes
    @Bindable
    private String addProgramName = null;
    @Bindable
    private String addProgramDescription = null;
    @Bindable
    private String addProgramCategory = null;
    @Bindable
    private String addProgramSessionNumber = null;
    @Bindable
    private String addProgramDuration = null;
    @Bindable
    private HashMap<String, Object> programValidationData = null;

    //Bindable Attributes Getters
    public String getAddProgramName() {
        return addProgramName;
    }
    public String getAddProgramDescription() {
        return addProgramDescription;
    }
    public String getAddProgramCategory() {
        return addProgramCategory;
    }
    public String getAddProgramSessionNumber() {
        return addProgramSessionNumber;
    }
    public String getAddProgramDuration() {
        return addProgramDuration;
    }
    public HashMap<String, Object> getProgramValidationData() {
        return programValidationData;
    }

    public void setProgramValidationData(HashMap<String, Object> programValidationData) {
        this.programValidationData = programValidationData;
        notifyPropertyChanged(BR.programValidationData);
    }

    //Bindable Attributes Setters
    public void setAddProgramName(String addProgramName) {
        this.addProgramName = addProgramName;
        onTextChangeProgram(addProgramName, ProgramFitnessClassFields.NAME);
    }
    public void setAddProgramDescription(String addProgramDescription) {
        this.addProgramDescription = addProgramDescription;
        onTextChangeProgram(addProgramDescription, ProgramFitnessClassFields.DESCRIPTION);
    }
    public void setAddProgramCategory(String addProgramCategory) {
        this.addProgramCategory = addProgramCategory;
    }
    public void setAddProgramSessionNumber(String addProgramSessionNumber) {
        this.addProgramSessionNumber = addProgramSessionNumber;
        onTextChangeProgram(addProgramSessionNumber, ProgramFitnessClassFields.DESCRIPTION);
    }
    public void setAddProgramDuration(String addProgramDuration) {
        this.addProgramDuration = addProgramDuration;
        onTextChangeProgram(addProgramDuration, ProgramFitnessClassFields.DURATION);
    }

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


    //ViewModel Methods
    public void onTextChangeProgram(String input, ProgramFitnessClassFields field){
        ProgramFitnessClassValidationService programFitnessClassValidationService= new ProgramFitnessClassValidationService(input, field);
        ValidationResult result = programFitnessClassValidationService.validate();

        HashMap<String, Object> validationData = new HashMap<>();
        validationData.put("validationResult", result);
        validationData.put("field", field);
        setProgramValidationData(validationData);
    }

    public MutableLiveData<ArrayList<Object>> getPrograms(){
        return programs;
    }

    public MutableLiveData<HashMap<String, Object>> getLiveDataProgramUpdate(){
        return programUpdate;
    }

    public MutableLiveData<Program> insertProgram(){
        //ProgramsRepository.getInstance().insertProgram(program);
        Program program = new Program.Builder(getAddProgramName()
                ,getAddProgramDescription()
                ,getAddProgramCategory()
                ,getAddProgramSessionNumber()
                ,getAddProgramDuration())
                .setTrainerID(FirebaseAuth.getInstance().getCurrentUser().getUid()).build();
        return ProgramsRepository.getInstance().insertProgram(program);
    }

    public void updateProgram(Program updatedProgram) {
        ProgramsRepository.getInstance().updateProgram(updatedProgram);
    }

    public void deleteProgram(String programID){
        ProgramsRepository.getInstance().deleteProgram(programID);
    }
}
