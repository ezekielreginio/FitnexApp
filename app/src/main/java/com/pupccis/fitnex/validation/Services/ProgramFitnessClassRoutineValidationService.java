package com.pupccis.fitnex.validation.Services;

import android.util.Log;

import com.pupccis.fitnex.validation.ValidationModel;
import com.pupccis.fitnex.validation.ValidationResult;

public class ProgramFitnessClassRoutineValidationService {
    private ValidationModel validationModel;
    private com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields field;
    private String input;
    private static ProgramFitnessClassRoutineValidationService instance;
    public ProgramFitnessClassRoutineValidationService(){

    }
    public ProgramFitnessClassRoutineValidationService(String input, com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields field){
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
            case REPS:
            case SETS:
            case WEIGHT:
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
                break;
            case CATEGORY:
                result = service
                        .requiredField()
                        .validateCategory()
                        .validate();
                break;
        }
        return result;
    }
}
