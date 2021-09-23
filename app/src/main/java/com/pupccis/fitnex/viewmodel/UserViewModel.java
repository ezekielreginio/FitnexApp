package com.pupccis.fitnex.viewmodel;

import android.text.Editable;
import android.util.Log;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.model.User;
import com.pupccis.fitnex.repository.UserRepository;
import com.pupccis.fitnex.validation.InputType;
import com.pupccis.fitnex.validation.Services.UserValidationService;
import com.pupccis.fitnex.validation.ValidationResult;

public class UserViewModel extends BaseObservable {

    @Bindable
    private String toastMessage = null;

    //Bindable Validation Results
    @Bindable
    private ValidationResult validationResultName = ValidationResult.valid();
    @Bindable
    private ValidationResult validationResultAge = ValidationResult.valid();
    @Bindable
    private ValidationResult validationResultPassword = ValidationResult.valid();

    public ValidationResult getValidationResultName() {
        return validationResultName;
    }

    private void setValidationResultName(ValidationResult validationResult) {
        this.validationResultName = validationResult;
        notifyPropertyChanged(BR.validationResultName);
    }

    public ValidationResult getValidationResultAge() {
        return validationResultAge;
    }

    public void setValidationResultAge(ValidationResult validationResultAge) {
        this.validationResultAge = validationResultAge;
        notifyPropertyChanged(BR.validationResultAge);
    }

    public ValidationResult getValidationResultPassword() {
        return validationResultPassword;
    }

    public void setValidationResultPassword(ValidationResult validationResultPassword) {
        this.validationResultPassword = validationResultPassword;
        notifyPropertyChanged(BR.validationResultPassword);
    }

    public String getToastMessage() {
        return toastMessage;
    }

    private void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
        notifyPropertyChanged(BR.toastMessage);
    }



    public static MutableLiveData<User> registerUser(User user){
        MutableLiveData<User> userLiveData = UserRepository.registerUser(user);
        return userLiveData;
    }

    public static MutableLiveData<Boolean> checkDuplicateEmail(String email){
        MutableLiveData<Boolean> booleanMutableLiveData = UserRepository.duplicateEmailLiveResponse(email);
        return  booleanMutableLiveData;
    }

    public void onRegisterClicked(){
        Log.d("register", "clicked");
        setToastMessage("Register Clicked");

    }

    public void onTextChange(Editable editable, InputType inputType, Enum field){
        Log.d("Enum", field.toString());
        Log.d("Input Type", inputType.toString());
        Log.d("textChanged", editable.toString());
        ValidationResult result = new UserValidationService(editable.toString(), inputType).validate();
        Log.d("Validation Result", result.isValid()+"");

        switch (field.toString()){
            case "NAME":
                setValidationResultName(result);
                break;
            case "AGE":
                setValidationResultAge(result);
                break;
            case "PASSWORD":
                setValidationResultPassword(result);
                break;
        }
    }

    public void onFocusChange(View view, boolean hasFocus){
        if(!hasFocus){
            Log.d("Focus", "Lost");
        }
    }
}
