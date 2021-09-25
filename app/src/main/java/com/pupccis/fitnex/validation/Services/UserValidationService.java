package com.pupccis.fitnex.validation.Services;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.pupccis.fitnex.repository.UserRepository;
import com.pupccis.fitnex.validation.InputType;
import com.pupccis.fitnex.validation.ValidationModel;
import com.pupccis.fitnex.validation.ValidationResult;
import com.pupccis.fitnex.validation.validationFields.RegistrationFields;


public class UserValidationService{
    private ValidationModel validationModel;
    private RegistrationFields field;
    private String input;
    private static UserValidationService instance;

    public UserValidationService(String input, RegistrationFields field){
        this.input = input;
        this.field = field;
    }

    public ValidationResult validate(){
        ValidationResult result = ValidationResult.valid();
        ValidationService service = new ValidationService(this.input);

        switch (field){
            case NAME:
                result = service
                        .requiredField()
                        .validate();
                break;
            case AGE:
                result = service
                        .requiredField()
                        .validateAge()
                        .validate();
                break;
            case EMAIL:
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



}
