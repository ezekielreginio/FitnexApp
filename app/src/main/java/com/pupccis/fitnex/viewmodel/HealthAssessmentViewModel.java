package com.pupccis.fitnex.viewmodel;

import android.util.Log;
import android.widget.CheckBox;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.model.HealthAssessment;
import com.pupccis.fitnex.model.User;
import com.pupccis.fitnex.repository.UserRepository;
import com.pupccis.fitnex.validation.Services.ProgramFitnessClassRoutineValidationService;
import com.pupccis.fitnex.validation.ValidationResult;
import com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HealthAssessmentViewModel extends BaseObservable {
    private UserRepository userRepository = new UserRepository();
    @Bindable
    private String height = null;
    @Bindable
    private String weight = null;
    @Bindable
    private HashMap<String, Object> healthAssessmentValidationData = null;

    private List<String> allergies = new ArrayList<>();
    private List<String> comorbidities = new ArrayList<>();

    public List<String> getAllergies() {
        return allergies;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
        onTextChange(height, ProgramFitnessClassRoutineFields.HEIGHT);
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
        onTextChange(weight, ProgramFitnessClassRoutineFields.WEIGHT);
    }

    public HashMap<String, Object> getHealthAssessmentValidationData() {
        return healthAssessmentValidationData;
    }

    public void setHealthAssessmentValidationData(HashMap<String, Object> healthAssessmentValidationData) {
        this.healthAssessmentValidationData = healthAssessmentValidationData;
        notifyPropertyChanged(BR.healthAssessmentValidationData);
    }

    public void addAllergy(String allergy){
        allergies.add(allergy);
    }
    public void removeAllergy(String allergy){
        allergies.remove(allergy);
    }

    public void addComorbidity(String comorbidity){
        comorbidities.add(comorbidity);
    }
    public void removeComorbidity(String comorbidity){
        comorbidities.remove(comorbidity);
    }
    public MutableLiveData<HealthAssessment> insertHealthAssessment(){
        HealthAssessment healthAssessment = healthInstance();
        return userRepository.insertHealthAssessment(healthAssessment);
    }
//    public void submitHealthAssessment(){
//        HealthAssessment healthAssessment = healthInstance();
//        userRepository.addUserDetails(healthAssessment);
//    }
    private HealthAssessment healthInstance(){
        HealthAssessment healthAssessment = new HealthAssessment.Builder(getHeight(), getWeight(), getAllergies()).build();
        return healthAssessment;
    }

    private void onTextChange(String input, com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields field){
        Log.d("Ontextchange trigger", "triggered");
        ProgramFitnessClassRoutineValidationService programFitnessClassValidationService= new ProgramFitnessClassRoutineValidationService(input, field);
        ValidationResult result = programFitnessClassValidationService.validate();

        HashMap<String, Object> validationData = new HashMap<>();
        validationData.put("validationResult", result);
        validationData.put("field", field);
        Log.e("Reusuoisghuiogheoighseghiserg", result.isValid()+"");
        setHealthAssessmentValidationData(validationData);
    }
}
