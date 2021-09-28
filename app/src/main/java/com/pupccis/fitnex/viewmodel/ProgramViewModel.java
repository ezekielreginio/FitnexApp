package com.pupccis.fitnex.viewmodel;


import android.content.Context;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ProgramViewModel extends BaseObservable implements Serializable {
    MutableLiveData<ArrayList<Object>> programs;
    private MutableLiveData<HashMap<String, Object>> programUpdate = new MutableLiveData<>();

    private ProgramsRepository programsRepository= ProgramsRepository.getInstance();

    //Mutable Live Data
    private MutableLiveData<Program> updateProgramLivedata = new MutableLiveData<>();
    private MutableLiveData<Program> deleteProgramLivedata = new MutableLiveData<>();
    
    //Private Attributes
    private Context context;
    private DataObserver dataObserver = new DataObserver();

    //Bindable Attributes
    @Bindable
    private String addProgramName = null;
    @Bindable
    private String addProgramDescription = null;
    @Bindable
    private int addProgramCategory = -1;
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
    public void setAddProgramCategory(int addProgramCategory) {
        this.addProgramCategory = addProgramCategory;
    }
    public void setAddProgramSessionNumber(String addProgramSessionNumber) {
        this.addProgramSessionNumber = addProgramSessionNumber;
        onTextChangeProgram(addProgramSessionNumber, ProgramFitnessClassFields.SESSION_NUMBER);
    }
    public void setAddProgramDuration(String addProgramDuration) {
        this.addProgramDuration = addProgramDuration;
        onTextChangeProgram(addProgramDuration, ProgramFitnessClassFields.DURATION);
    }

    public void setProgramID(String programID) {
        this.programID = programID;
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

    public FirestoreRecyclerOptions<Program> getFirebaseUIOptions(){
        Log.d("Pumasok", "pumasok");
       return new FirestoreRecyclerOptions.Builder<Program>()
               .setQuery(ProgramsRepository.getInstance().readProgramsQuery(), Program.class)
               .build();
    }

    public MutableLiveData<Program> insertProgram(){
        //ProgramsRepository.getInstance().insertProgram(program);
        Program program = programInstance();
        return programsRepository.insertProgram(program);
    }

    public void triggerUpdateObserver(Program program) {
        setProgramID(program.getProgramID());
        updateProgramLivedata.postValue(program);
        //ProgramsRepository.getInstance().updateProgram(updatedProgram);
    }

    public void triggerDeleteObserver(Program program) {
        setProgramID(program.getProgramID());
        deleteProgramLivedata.postValue(program);
        //ProgramsRepository.getInstance().updateProgram(updatedProgram);
    }

    public MutableLiveData<Program> updateObserver(){
        return updateProgramLivedata;
    }

    public MutableLiveData<Program> deleteObserver(){
        return deleteProgramLivedata;
    }

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
                ,getAddProgramSessionNumber()
                ,getAddProgramDuration())
                .setTrainerID(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setProgramID(getProgramID())
                .build();
        return program;
    }
}
