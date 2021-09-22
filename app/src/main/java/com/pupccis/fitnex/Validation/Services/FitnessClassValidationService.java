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
    public ValidationResult validate() {
        return null;
    }
}
