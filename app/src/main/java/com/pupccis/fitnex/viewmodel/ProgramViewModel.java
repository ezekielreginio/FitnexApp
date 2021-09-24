package com.pupccis.fitnex.viewmodel;


import android.content.Context;
import android.text.Editable;
import android.util.Log;

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
import com.pupccis.fitnex.validation.Services.FragmentFormsValidationService;
import com.pupccis.fitnex.validation.ValidationResult;
import com.pupccis.fitnex.validation.validationFields.FragmentFields;

import java.util.ArrayList;
import java.util.HashMap;

public class ProgramViewModel extends BaseObservable {
    MutableLiveData<ArrayList<Object>> programs;
    private MutableLiveData<HashMap<String, Object>> programUpdate = new MutableLiveData<>();
    
    //Private Attributes
    private ProgramsRepository programsRepository;
    private Context context;
    private DataObserver dataObserver = new DataObserver();

    
    //Bindable Validation Results
    @Bindable
    private ValidationResult validationResultProgramName = ValidationResult.valid();
    @Bindable
    private ValidationResult validationResultProgramDescription = ValidationResult.valid();

    public ValidationResult getValidationResultProgramName() {
        return validationResultProgramName;
    }

    private void setValidationResultProgramName(ValidationResult validationResultProgramName) {
        Log.d("Set trigger", "Troggered");
        this.validationResultProgramName = validationResultProgramName;
        notifyPropertyChanged(BR.validationResultProgramName);
    }

    public ValidationResult getValidationResultProgramDescription() {
        return validationResultProgramDescription;
    }

    public void setValidationResultProgramDescription(ValidationResult validationResultProgramDescription) {
        this.validationResultProgramDescription = validationResultProgramDescription;
        notifyPropertyChanged(BR.validationResultProgramDescription);
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



    public MutableLiveData<ArrayList<Object>> getPrograms(){
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

    public void onTextChange(Editable editable, FragmentFields field){
        Log.d("Enum", field.toString());
        Log.d("textChanged", editable.toString());
        ValidationResult result = new FragmentFormsValidationService(editable.toString(), field).validate();
        Log.d("Validation Result", result.isValid()+"");

        switch (field){
            case NAME:
                setValidationResultProgramName(result);
                break;
            case DESCRIPTION:
                setValidationResultProgramDescription(result);
        }
    }
}
