package com.pupccis.fitnex.Validation.Services;

import com.pupccis.fitnex.Validation.InputType;
import com.pupccis.fitnex.Validation.ValidationModel;
import com.pupccis.fitnex.Validation.ValidationResult;

public class FitnessClassValidationService implements ValidationServiceInterface {
    private ValidationModel validationModel;
    private InputType inputType;
    private Object input;
    private static FitnessClassValidationService instance;

    public FitnessClassValidationService(){
    }
    private FitnessClassValidationService(ValidationModel validationModel){
        this.validationModel = validationModel;
        this.inputType = validationModel.getInputType();
        this.input = validationModel.getTextInputLayout().getEditText().getText();
    }


    @Override
    public Object getInstance(ValidationModel object) {
        instance = new FitnessClassValidationService(object);
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
                        .notZero()
                        .validate();
                break;
            case TIME:
                result = service
                        .requiredField()
                        .regexValidation("/((1[0-2]|0?[1-9]):([0-5][0-9]) ?([AaPp][Mm]))/", "Time Invalid")
                        .validate();
        }
        return result;
    }
}
