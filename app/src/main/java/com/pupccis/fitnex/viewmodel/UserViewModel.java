package com.pupccis.fitnex.viewmodel;

import android.util.Log;

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

public class UserViewModel extends BaseObservable {

    MediatorLiveData mediatorLiveData = new MediatorLiveData<>();
    @Bindable
    private String registerEmail = null;
    @Bindable
    private String registerName = null;
    @Bindable
    private String registerAge = null;
    @Bindable
    private String registerPassword = null;

    //Bindable Attributes
    @Bindable
    private String toastMessage = null;

    //Bindable Validation Results
    @Bindable
    private ValidationResult validationResultName = ValidationResult.valid();
    @Bindable
    private ValidationResult validationResultAge = ValidationResult.valid();
    @Bindable
    private ValidationResult validationResultPassword = ValidationResult.valid();
    @Bindable
    private ValidationResult validationResultEmail = ValidationResult.valid();

    @Bindable
    private String triggerEmailObserver = null;


    //Getter Methods
    @Bindable
    public String getRegisterEmail() {
        return registerEmail;
    }
    @Bindable
    public String getRegisterName() {
        return registerName;
    }
    @Bindable
    public String getRegisterAge() {
        return registerAge;
    }
    @Bindable
    public String getRegisterPassword() {
        return registerPassword;
    }
    @Bindable
    public ValidationResult getValidationResultName() {
        return validationResultName;
    }
    @Bindable
    public ValidationResult getValidationResultAge() {
        return validationResultAge;
    }
    @Bindable
    public ValidationResult getValidationResultEmail() {
        return validationResultEmail;
    }
    @Bindable
    public ValidationResult getValidationResultPassword() {
        return validationResultPassword;
    }
    @Bindable
    public String getToastMessage() {
        return toastMessage;
    }
    @Bindable
    public String getTriggerEmailObserver() {
        return triggerEmailObserver;
    }

    //Setter Methods
    public void setRegisterName(String registerName) {
        this.registerName = registerName;
        notifyPropertyChanged(BR.registerName);
    }

    public void setRegisterAge(String registerAge) {
        this.registerAge = registerAge;
        notifyPropertyChanged(BR.registerAge);
    }

    public void setRegisterPassword(String registerPassword) {
        this.registerPassword = registerPassword;
        notifyPropertyChanged(BR.registerPassword);
    }

    private void setValidationResultName(ValidationResult validationResult) {
        this.validationResultName = validationResult;
        notifyPropertyChanged(BR.validationResultName);
    }

    public void setValidationResultAge(ValidationResult validationResultAge) {
        this.validationResultAge = validationResultAge;
        notifyPropertyChanged(BR.validationResultAge);
    }


    public void setValidationResultPassword(ValidationResult validationResultPassword) {
        this.validationResultPassword = validationResultPassword;
        notifyPropertyChanged(BR.validationResultPassword);
    }


    public void setValidationResultEmail(ValidationResult validationResultEmail) {
        this.validationResultEmail = validationResultEmail;
        notifyPropertyChanged(BR.validationResultEmail);
    }


    private void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
        notifyPropertyChanged(BR.toastMessage);
    }

    public void setRegisterEmail(String registerEmail) {
        this.registerEmail = registerEmail;
        notifyPropertyChanged(BR.registerEmail);
    }

    public void setTriggerEmailObserver(String triggerEmailObserver) {
        this.triggerEmailObserver = triggerEmailObserver;
        notifyPropertyChanged(BR.triggerEmailObserver);
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

    public void onTextChange(String input, RegistrationFields field){
        Log.d("Enum", field.toString());
        Log.d("textChanged", input);
        UserValidationService userValidationService= new UserValidationService(input, field);
        ValidationResult result = userValidationService.validate();
        Log.d("Validation Result", result.isValid()+"");

        switch (field){
            case NAME:
                setValidationResultName(result);
                break;
            case AGE:
                setValidationResultAge(result);
                break;
            case PASSWORD:
                setValidationResultPassword(result);
                break;
            case EMAIL:
                setValidationResultEmail(result);
                if(result.isValid()){
                    setTriggerEmailObserver("Sample bindable");
//                    userValidationService.checkEmailDuplicate().observeForever(new Observer<Boolean>() {
//                        @Override
//                        public void onChanged(Boolean aBoolean) {
//                            Log.d("Duplicate", aBoolean+"");
//                        }
//                    });
//                    mediatorLiveData.addSource(userValidationService.checkEmailDuplicate(), new Observer<Boolean>() {
//                        @Override
//                        public void onChanged(@Nullable Boolean valid) {
//                            Log.d("Duplicate", valid+"");
//                        }
//                    });

                }
                break;
        }
    }

    public MutableLiveData<Boolean> emailCheckerLiveData(){
        Log.d("Email Input", getRegisterEmail()+"");
        return UserRepository.duplicateEmailLiveResponse(getRegisterEmail()+"");
    }

    public void processEmailResponse(Boolean isValid){
        Log.d("Result from repo", isValid+"");
        if(isValid!=null){
            if(isValid)
                setValidationResultEmail(new ValidationResult(true, ""));
            else
                setValidationResultEmail(new ValidationResult(false, "This Email is already Registered"));
        }
    }

}
