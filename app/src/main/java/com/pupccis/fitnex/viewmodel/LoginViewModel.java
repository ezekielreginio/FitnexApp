package com.pupccis.fitnex.viewmodel;

import static com.pupccis.fitnex.handlers.viewmodel.ViewModelHandler.onTextChange;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.model.User;
import com.pupccis.fitnex.repository.UserRepository;
import com.pupccis.fitnex.validation.validationFields.RegistrationFields;

import java.util.HashMap;

public class LoginViewModel extends BaseObservable {
    @Bindable
    private String loginEmail;
    @Bindable
    private String loginPassword;
    @Bindable
    private HashMap<String, Object> loginValidationData = null;

    //Getter Methods
    public String getLoginEmail() {
        return loginEmail;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public HashMap<String, Object> getLoginValidationData() {
        return loginValidationData;
    }

    //Setter Methods
    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
        notifyPropertyChanged(BR.loginEmail);
        setLoginValidationData(onTextChange(loginEmail, RegistrationFields.EMAIL));
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
        notifyPropertyChanged(BR.loginPassword);
        setLoginValidationData(onTextChange(loginPassword, RegistrationFields.PASSWORD));
    }

    public void setLoginValidationData(HashMap<String, Object> loginValidationData) {
        this.loginValidationData = loginValidationData;
        notifyPropertyChanged(BR.loginValidationData);
    }

    public MutableLiveData<User> loginUser(){

        return UserRepository.getInstance().loginUser(getLoginEmail(), getLoginPassword());
    }
}
