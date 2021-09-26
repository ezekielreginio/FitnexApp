package com.pupccis.fitnex.viewmodel;

import static com.pupccis.fitnex.repository.FitnessClassesRepository.getFitnessClassesQuery;

import android.content.Context;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.api.globals.DataObserver;
import com.pupccis.fitnex.model.FitnessClass;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.repository.FitnessClassesRepository;
import com.pupccis.fitnex.validation.Services.ProgramFitnessClassValidationService;
import com.pupccis.fitnex.validation.ValidationResult;
import com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassFields;

import java.util.ArrayList;
import java.util.HashMap;

public class FitnessClassViewModel extends BaseObservable {
    //MutableLiveData attributes
    private MutableLiveData<ArrayList<Object>> fitnessClasses;
    private MutableLiveData<HashMap<String, Object>> fitnessClassesUpdate = new MutableLiveData<>();

    //Data Observer
    private DataObserver dataObserver = new DataObserver();

    //Private attributes
    private FitnessClassesRepository fitnessClassesRepository;
    private Context context;
    @Bindable
    private String addFitnessClassName = null;
    @Bindable
    private String addFitnessClassDescription = null;
    @Bindable
    private String addFitnessClassCategory = null;
    @Bindable
    private String addFitnessClassTimeStart = null;
    @Bindable
    private String addFitnessClassTimeEnd = null;
    @Bindable
    private String addFitnessClassSessionNumber = null;
    @Bindable
    private String addFitnessClassDuration = null;
    @Bindable
    private HashMap<String, Object> fitnessClassValidationData = null;


    public String getAddFitnessClassName() {
        return addFitnessClassName;
    }

    public void setAddFitnessClassName(String addFitnessClassName) {
        this.addFitnessClassName = addFitnessClassName;
        onTextChangeFitnessClass(addFitnessClassName, ProgramFitnessClassFields.NAME);
    }

    public String getAddFitnessClassDescription() {
        return addFitnessClassDescription;
    }

    public void setAddFitnessClassDescription(String addFitnessClassDescription) {
        this.addFitnessClassDescription = addFitnessClassDescription;
        onTextChangeFitnessClass(addFitnessClassDescription, ProgramFitnessClassFields.DESCRIPTION);
    }

    public String getAddFitnessClassCategory() {
        return addFitnessClassCategory;
    }

    public void setAddFitnessClassCategory(String addFitnessClassCategory) {
        this.addFitnessClassCategory = addFitnessClassCategory;
    }

    public String getAddFitnessClassTimeStart() {
        return addFitnessClassTimeStart;
    }

    public void setAddFitnessClassTimeStart(String addFitnessClassTimeStart) {
        this.addFitnessClassTimeStart = addFitnessClassTimeStart;
        onTextChangeFitnessClass(addFitnessClassTimeStart, ProgramFitnessClassFields.TIME_START);
    }

    public String getAddFitnessClassTimeEnd() {
        return addFitnessClassTimeEnd;
    }

    public void setAddFitnessClassTimeEnd(String addFitnessClassTimeEnd) {
        this.addFitnessClassTimeEnd = addFitnessClassTimeEnd;
        onTextChangeFitnessClass(addFitnessClassTimeEnd, ProgramFitnessClassFields.TIME_END);
    }

    public String getAddFitnessClassSessionNumber() {
        return addFitnessClassSessionNumber;
    }

    public void setAddFitnessClassSessionNumber(String addFitnessClassSessionNumber) {
        this.addFitnessClassSessionNumber = addFitnessClassSessionNumber;
        onTextChangeFitnessClass(addFitnessClassSessionNumber, ProgramFitnessClassFields.SESSION_NUMBER);
    }

    public String getAddFitnessClassDuration() {
        return addFitnessClassDuration;
    }

    public void setAddFitnessClassDuration(String addFitnessClassDuration) {
        this.addFitnessClassDuration = addFitnessClassDuration;
        onTextChangeFitnessClass(addFitnessClassDuration, ProgramFitnessClassFields.DURATION);
    }

    public HashMap<String, Object> getFitnessClassValidationData() {
        return fitnessClassValidationData;
    }

    public void setFitnessClassValidationData(HashMap<String, Object> fitnessClassValidationData) {
        this.fitnessClassValidationData = fitnessClassValidationData;
        notifyPropertyChanged(BR.fitnessClassValidationData);
    }

    public void init(Context context){
        if(fitnessClasses != null){
            return;
        }
        this.context = context;
        //fitnessClasses = FitnessClassesRepository.getInstance().getFitnessClasses();
        fitnessClasses = dataObserver.getObjects(getFitnessClassesQuery(), new FitnessClass());

        fitnessClassesUpdate = dataObserver.getLiveData(getFitnessClassesQuery(), new FitnessClass());
    }
    public void onTextChangeFitnessClass (String input, ProgramFitnessClassFields field){
        Log.d("Ontextchange trigger", "triggered");
        ProgramFitnessClassValidationService programFitnessClassValidationService= new ProgramFitnessClassValidationService(input, field);
        ValidationResult result = programFitnessClassValidationService.validate();

        HashMap<String, Object> validationData = new HashMap<>();
        validationData.put("validationResult", result);
        validationData.put("field", field);
        setFitnessClassValidationData(validationData);
    }
    public MutableLiveData<ArrayList<Object>> getFitnessClasses(){
        return fitnessClasses;
    }

    public MutableLiveData<FitnessClass> insertFitnessClass() {
        FitnessClass fitnessClass = new FitnessClass.Builder(getAddFitnessClassName()
                ,getAddFitnessClassDescription()
                ,0
                ,getAddFitnessClassTimeStart()
                ,getAddFitnessClassTimeEnd(), getAddFitnessClassSessionNumber()
                ,getAddFitnessClassDuration())
                .build();

        return FitnessClassesRepository.insertFitnessClass(fitnessClass);
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
