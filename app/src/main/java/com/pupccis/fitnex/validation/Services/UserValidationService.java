package com.pupccis.fitnex.validation.Services;

import androidx.lifecycle.MutableLiveData;

import com.pupccis.fitnex.repository.UserRepository;
import com.pupccis.fitnex.validation.InputType;
import com.pupccis.fitnex.validation.ValidationModel;
import com.pupccis.fitnex.validation.ValidationResult;


public class UserValidationService implements ValidationServiceInterface, EmailDuplicateChecker{
    private ValidationModel validationModel;
    private InputType inputType;
    private String input;
    private static UserValidationService instance;

    public UserValidationService(String input, InputType inputType){
        this.input = input;
        this.inputType = inputType;
    }

    @Override
    public UserValidationService getInstance(ValidationModel object){
        return null;
    }

    @Override
    public ValidationResult validate(){
        ValidationResult result = ValidationResult.valid();
        ValidationService service = new ValidationService(this.input, this.inputType);

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
