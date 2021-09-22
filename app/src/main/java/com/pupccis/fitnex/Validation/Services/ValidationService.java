package com.pupccis.fitnex.Validation.Services;

import android.util.Patterns;

import com.pupccis.fitnex.Validation.InputType;
import com.pupccis.fitnex.Validation.ValidationModel;
import com.pupccis.fitnex.Validation.ValidationResult;

public class ValidationService {
    private ValidationResult validationResult;
    private ValidationModel validationModel;
    private InputType inputType;
    private String input;
    ValidationResult result = ValidationResult.valid();

    public ValidationService (ValidationModel model){
        this.validationModel = model;
        this.inputType = model.getInputType();
        input = validationModel.getTextInputLayout().getEditText().getText().toString();
    }


    public ValidationService validateString(String pattern){
        if(result.isValid()){
            if(input.equals("") || input == null){
                result = ValidationResult.invalid("This Field is Required");
            }
        }
        return this;
    }

    public ValidationService validateAge(){
        if(result.isValid()){
            try{
                int number = Integer.parseInt(input);
                if(number > 85 || number < 18)
                    result = ValidationResult.invalid("Age must be between 18-85");
            }
            catch (NumberFormatException ex){
                result = ValidationResult.invalid("Invalid Input, Age must be a Number");
            }
        }
        return this;
    }


    public ValidationService validateEmail(){
        if(!Patterns.EMAIL_ADDRESS.matcher(input).matches()){
            result = ValidationResult.invalid("Invalid Email Address");
        }
        return this;
    }

    public ValidationService checkPasswordLength(){
        if(input.length() < 6)
            result = ValidationResult.invalid("Password Should have at Least 6 Characters");
        return this;
    }

    public ValidationService requiredField(){

        if(result.isValid()){
            if(input.equals("") || input == null){
                result = ValidationResult.invalid("This Field is Required");
            }
        }

        return this;
    }


    public ValidationResult validate(){
        return result;
    }

    protected ValidationResult getValidationResult() {
        return validationResult;
    }
}
