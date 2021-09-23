package com.pupccis.fitnex.validation.Services;

import com.pupccis.fitnex.validation.InputType;
import com.pupccis.fitnex.validation.ValidationModel;
import com.pupccis.fitnex.validation.ValidationResult;
import com.pupccis.fitnex.validation.validationFields.FragmentFields;

public class FragmentFormsValidationService{
    private ValidationModel validationModel;
    private FragmentFields field;
    private String input;
    private static FragmentFormsValidationService instance;
    public FragmentFormsValidationService(){

    }
    public FragmentFormsValidationService(String input, FragmentFields field){
        this.input = input;
        this.field = field;
    }

    public ValidationResult validate(){
        ValidationResult result = ValidationResult.valid();
        ValidationService service = new ValidationService(input);

        switch (field){
            case NAME:
                result = service
                        .requiredField()
                        .validate();
                break;
//            case INT:
//                result = service
//                        .requiredField()
//                        .validateInt()
//                        .validate();
//                break;
        }
        return result;
    }
}
