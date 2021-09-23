package com.pupccis.fitnex.validation.Services;

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
        }
        return result;
    }
}
