package com.pupccis.fitnex.Validation.Services;

import androidx.lifecycle.MutableLiveData;

import com.pupccis.fitnex.Repository.UserRepository;
import com.pupccis.fitnex.Validation.InputType;
import com.pupccis.fitnex.Validation.ValidationModel;
import com.pupccis.fitnex.Validation.ValidationResult;


public class UserValidationService implements ValidationServiceInterface, EmailDuplicateChecker{
    private ValidationModel validationModel;
    private InputType inputType;
    private Object input;
    private static UserValidationService instance;

    public UserValidationService(){

    }

    public UserValidationService(ValidationModel validationModel) {
        this.validationModel = validationModel;
        this.inputType = validationModel.getInputType();
        this.input = validationModel.getTextInputLayout().getEditText().getText();
    }

    @Override
    public UserValidationService getInstance(ValidationModel object){
        instance = new UserValidationService(object);
        return instance;
    }

    @Override
    public ValidationResult validate(){
        ValidationResult result = ValidationResult.valid();
        ValidationService service = new ValidationService(this.validationModel);

        switch (inputType){
            case STRING:
                result = service
                        .requiredField()
                        .validate();
                break;
            case INT:
                result = service
                        .requiredField()
                        .validateAge()
                        .validate();
                break;
            case NEW_EMAIL:
                result = service
                        .requiredField()
                        .validateEmail()
                        .validate();
                break;
            case PASSWORD:
                result = service
                        .checkPasswordLength()
                        .validate();
                break;
        }
        return result;
    }

    @Override
    public MutableLiveData<Boolean> checkEmailDuplicate() {

        MutableLiveData<Boolean> booleanMutableLiveData = UserRepository.duplicateEmailLiveResponse(input.toString());
        return booleanMutableLiveData;
    }


//    public ValidationResult validate(ValidationModel model){
//        String input = model.getTextInputLayout().getEditText().getText().toString();
//        if(model.getInputType().equals(InputType.STRING)){
//            ValidationResult result = validateString(input).validate();
//            return result;
//        }
//        return null;
//    }



}
