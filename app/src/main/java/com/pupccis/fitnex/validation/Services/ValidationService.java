package com.pupccis.fitnex.validation.Services;

import android.util.Patterns;

import com.pupccis.fitnex.validation.InputType;
import com.pupccis.fitnex.validation.ValidationModel;
import com.pupccis.fitnex.validation.ValidationResult;

import java.util.regex.Pattern;

public class ValidationService {
    private ValidationResult validationResult;
    private ValidationModel validationModel;
    private InputType inputType;
    private String input;
    ValidationResult result = ValidationResult.valid();

    public ValidationService(){

    }
    public ValidationService(String input){
        this.input = input;
    }

    public ValidationService (String input, InputType inputType){
        this.inputType = inputType;
        this.input = input;
    }


    public ValidationService validateString(String pattern){
        if(result.isValid()){
            if(input.equals("") || input == null){
                result = ValidationResult.invalid("This Field is Required");
            }
        }
        return this;
    }
    public ValidationService regexValidation(String pattern, String errorMessage){

        boolean isMatch = Pattern.compile(pattern).matcher(input).matches();
        if(isMatch)
            result = ValidationResult.invalid(errorMessage);
        return this;
    }
    public ValidationService validateInt(){
        if(result.isValid()){
            try{
                int number = Integer.parseInt(input);
                if(number<0){
                    result = ValidationResult.invalid("This must be higher than 0");
                }
            }catch (NumberFormatException ex){
                result = ValidationResult.invalid("Invalid input, this must be a Number");
            }
        }
        return this;
    }
    public ValidationService notZero(){
        if(result.isValid()){
            int number = Integer.parseInt(input);
            if (number == 0){
                result = ValidationResult.invalid("Input cannot be 0");
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
                result = new ValidationResult().invalid("This Field is required");
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
