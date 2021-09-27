package com.pupccis.fitnex.validation.Services;

import android.util.Log;

import com.pupccis.fitnex.validation.ValidationModel;
import com.pupccis.fitnex.validation.ValidationResult;
import com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassFields;

public class ProgramFitnessClassValidationService {
    private ValidationModel validationModel;
    private ProgramFitnessClassFields field;
    private String input;
    private static ProgramFitnessClassValidationService instance;
    public ProgramFitnessClassValidationService(){

    }
    public ProgramFitnessClassValidationService(String input, ProgramFitnessClassFields field){
        this.input = input;
        this.field = field;
    }

    public ValidationResult validate(){
        ValidationResult result = ValidationResult.valid();
        ValidationService service = new ValidationService(input);

        switch (field){
            case NAME:
            case DESCRIPTION:
                Log.d("Validation service Trigger", "triggered");
                result = service
                        .requiredField()
                        .validate();
                break;
            case SESSION_NUMBER:
            case DURATION:
                result = service
                        .requiredField()
                        .validateInt()
                        .validate();
                break;
            case TIME_END:
            case TIME_START:
                result = service
                        .requiredField()
                        //.regexValidation("[a-z]", "Invalid Time Input")
                        .regexValidation("((1[0-2]|0?[1-9]):([0-5][0-9]) ?([AaPp][Mm]))", "Invalid Time Input")
                        .validate();
        }
        return result;
    }
}
