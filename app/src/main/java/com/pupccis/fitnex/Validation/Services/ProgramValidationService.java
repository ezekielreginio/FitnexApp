package com.pupccis.fitnex.Validation.Services;

import com.pupccis.fitnex.Validation.InputType;
import com.pupccis.fitnex.Validation.ValidationModel;
import com.pupccis.fitnex.Validation.ValidationResult;

public class ProgramValidationService implements ValidationServiceInterface{
    private ValidationModel validationModel;
    private InputType inputType;
    private Object input;
    private static ProgramValidationService instance;
    public ProgramValidationService(){

    }
    private ProgramValidationService(ValidationModel validationModel){
        this.validationModel = validationModel;
        this.inputType = validationModel.getInputType();
        this.input = validationModel.getTextInputLayout().getEditText().getText();
    }

    @Override
    public Object getInstance(ValidationModel object) {
        instance = new ProgramValidationService(object);
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
                        .validateInt()
                        .validate();
                break;
        }
        return result;
    }
}
