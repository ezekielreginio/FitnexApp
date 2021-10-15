package com.pupccis.fitnex.viewmodel;

import android.util.Log;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.model.User;
import com.pupccis.fitnex.repository.UserRepository;
import com.pupccis.fitnex.validation.Services.UserValidationService;
import com.pupccis.fitnex.validation.ValidationResult;
import com.pupccis.fitnex.validation.validationFields.RegistrationFields;

import java.util.HashMap;

public class UserViewModel extends BaseObservable{

    @Bindable
    private String registerEmail = null;
    @Bindable
    private String registerName = null;
    @Bindable
    private String registerAge = null;
    @Bindable
    private String registerPassword = null;
    @Bindable
    private HashMap<String, Object> validationData = null;

    //Getter Methods
    public String getRegisterEmail() {
        return registerEmail;
    }

    public String getRegisterName() {
        return registerName;
    }

    public String getRegisterAge() {
        return registerAge;
    }

    public String getRegisterPassword() {
        return registerPassword;
    }

    public HashMap<String, Object> getValidationData() {
        return validationData;
    }

    public void setValidationData(HashMap<String, Object> validationData) {
        this.validationData = validationData;
        notifyPropertyChanged(BR.validationData);
    }

    //Setter Methods
    public void setRegisterName(String registerName) {
        this.registerName = registerName;
        notifyPropertyChanged(BR.registerName);
        onTextChange(registerName, RegistrationFields.NAME);
    }

    public void setRegisterAge(String registerAge) {
        this.registerAge = registerAge;
        notifyPropertyChanged(BR.registerAge);
        onTextChange(registerAge, RegistrationFields.AGE);
    }

    public void setRegisterPassword(String registerPassword) {
        this.registerPassword = registerPassword;
        notifyPropertyChanged(BR.registerPassword);
        onTextChange(registerPassword, RegistrationFields.PASSWORD);
    }

    public void setRegisterEmail(String registerEmail) {
        Log.d("register email", "click");
        this.registerEmail = registerEmail;
        notifyPropertyChanged(BR.registerEmail);
        onTextChange(registerEmail, RegistrationFields.EMAIL);
    }

    //ViewModel Methods
    public void onTextChange(String input, RegistrationFields field){
        UserValidationService userValidationService= new UserValidationService(input, field);
        ValidationResult result = userValidationService.validate();

        HashMap<String, Object> validationData = new HashMap<>();
        validationData.put("validationResult", result);
        validationData.put("field", field);
        setValidationData(validationData);
    }

    public MutableLiveData<ValidationResult> emailCheckerLiveData(){
        Log.d("Email Input", getRegisterEmail()+"");
        return UserRepository.getInstance().duplicateEmailLiveResponse(getRegisterEmail());
    }

    public MutableLiveData<User> registerUser(){
        User user = new User.Builder(getRegisterName(), getRegisterEmail())
                .setAge(Integer.parseInt(getRegisterAge()))
                .setPassword(getRegisterPassword())
                .build();
        return UserRepository.registerUser(user);
    }

    public MutableLiveData<Boolean> checkHealthData() {
        return UserRepository.getInstance().checkHealthData();
    }
}
