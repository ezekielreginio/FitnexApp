package com.pupccis.fitnex.viewmodel;

import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.model.TrainerApplicant;
import com.pupccis.fitnex.model.User;
import com.pupccis.fitnex.repository.UserRepository;
import com.pupccis.fitnex.validation.Services.UserValidationService;
import com.pupccis.fitnex.validation.ValidationResult;
import com.pupccis.fitnex.validation.validationFields.RegistrationFields;

import java.util.HashMap;

public class UserViewModel extends BaseObservable{
    //Private Attributes
    private Uri applicantResume = null;
    private Uri applicantProfilePicture = null;
    private String applicantResumeFileType = null;
    private String applicantProfilePictureFileType = null;

    //Bindable Attributes
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

    //Fitness Trainer Application Form
    @Bindable
    private String applicantName = null;
    @Bindable
    private String applicantBirthDate = null;
    @Bindable
    private String applicantEmail = null;
    @Bindable
    private String applicantPhoneNumber = null;

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

    public String getApplicantName() {
        return applicantName;
    }

    public String getApplicantBirthDate() {
        return applicantBirthDate;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public String getApplicantPhoneNumber() {
        return applicantPhoneNumber;
    }

    public Uri getApplicantResume() {
        return applicantResume;
    }

    public String getApplicantResumeFileType() {
        return applicantResumeFileType;
    }

    public Uri getApplicantProfilePicture() {
        return applicantProfilePicture;
    }

    public String getApplicantProfilePictureFileType() {
        return applicantProfilePictureFileType;
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


    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
        notifyPropertyChanged(BR.applicantName);
    }

    public void setApplicantBirthDate(String applicantBirthDate) {
        this.applicantBirthDate = applicantBirthDate;
        notifyPropertyChanged(BR.applicantBirthDate);
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
        notifyPropertyChanged(BR.applicantEmail);
    }

    public void setApplicantPhoneNumber(String applicantPhoneNumber) {
        this.applicantPhoneNumber = applicantPhoneNumber;
        notifyPropertyChanged(BR.applicantPhoneNumber);
    }

    public void setApplicantResume(Uri applicantResume) {
        this.applicantResume = applicantResume;
    }

    public void setApplicantResumeFileType(String applicantResumeFileType) {
        this.applicantResumeFileType = applicantResumeFileType;
    }

    public void setApplicantProfilePicture(Uri applicantProfilePicture) {
        this.applicantProfilePicture = applicantProfilePicture;
    }

    public void setApplicantProfilePictureFileType(String applicantProfilePictureFileType) {
        this.applicantProfilePictureFileType = applicantProfilePictureFileType;
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

    public MutableLiveData<Boolean> applyTrainer(){
        TrainerApplicant trainerApplicant = new TrainerApplicant.Builder(getApplicantEmail(),
                getApplicantName(),
                getApplicantBirthDate(),
                getApplicantPhoneNumber(),
                getApplicantProfilePicture(),
                getApplicantResume(),
                getApplicantProfilePictureFileType(),
                getApplicantResumeFileType())
                .build();
        return UserRepository.applyTrainer(trainerApplicant);
    }

    public MutableLiveData<Boolean> checkHealthData() {
        return UserRepository.getInstance().checkHealthData();
    }
}
