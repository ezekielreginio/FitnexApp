package com.pupccis.fitnex.validation.Services;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.android.material.textfield.TextInputLayout;
import com.pupccis.fitnex.activities.Login.FitnexRegister;
import com.pupccis.fitnex.validation.InputType;
import com.pupccis.fitnex.validation.ValidationModel;
import com.pupccis.fitnex.validation.ValidationResult;

import java.util.ArrayList;

public class ValidationEventBinder {
    private FitnexRegister obj;
    private MutableLiveData<Boolean> isValid = new MutableLiveData<>();

    public ValidationEventBinder(){

    }

    public ValidationEventBinder(FitnexRegister obj){
        this.obj= obj;
    }

    public MutableLiveData<Boolean> getIsValid(){
        isValid.setValue(false);
        return isValid;
    }

    public void bindEvents(ArrayList<ValidationModel> fields, Object Service){
        for(ValidationModel field: fields){
            if(field.getInputType().equals(InputType.EMAIL)  || field.getInputType().equals(InputType.NEW_EMAIL)){
                field.getTextInputLayout().getEditText().setOnFocusChangeListener((view, hasFocus) -> {
                    ValidationServiceInterface validationService = (ValidationServiceInterface)Service;
                    validationService = (ValidationServiceInterface) validationService.getInstance(field);
                    if(!hasFocus){
                        ValidationResult result = (ValidationResult) validationService.validate();
                        TextInputLayout textInputLayout = field.getTextInputLayout();
                        validateFields(result, field);

                        if(result.isValid()){
                           EmailDuplicateChecker emailDuplicateChecker = (EmailDuplicateChecker) validationService;
                           emailDuplicateChecker.checkEmailDuplicate().observe(obj, new Observer<Boolean>() {
                               @Override
                               public void onChanged(Boolean aBoolean) {
                                   if(aBoolean == null)
                                       textInputLayout.setHelperText("Checking for Email Availability...");
                                   else
                                       if(!aBoolean){
                                           textInputLayout.setErrorEnabled(true);
                                           textInputLayout.setError("This Email is Registered");
                                       }
                                       else{
                                           int[][] states = new int[][] {
                                                   new int[] { android.R.attr.state_enabled}, // enabled
                                                   new int[] {-android.R.attr.state_enabled}, // disabled
                                                   new int[] {-android.R.attr.state_checked}, // unchecked
                                                   new int[] { android.R.attr.state_pressed}  // pressed
                                           };
                                           int[] colors = new int[]{Color.rgb(40, 167, 69), Color.rgb(40, 167, 69), Color.rgb(40, 167, 69), Color.rgb(40, 167, 69)};
                                           ColorStateList list = new ColorStateList(states, colors);
                                           textInputLayout.setHelperText("This Email is Available!");
                                           textInputLayout.setHelperTextColor(list);
                                       }
                               }
                           });
                        }
                    }
                });
            }
            else{
                field.getTextInputLayout().getEditText().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        ValidationServiceInterface validationService = (ValidationServiceInterface)Service;
                        validationService = (ValidationServiceInterface) validationService.getInstance(field);
                        ValidationResult result =  validationService.validate();
                        validateFields(result, field);
                    }
                });
            }

        }
    }

    private void validateFields(ValidationResult result, ValidationModel field){
        TextInputLayout textInputLayout = field.getTextInputLayout();
        if(!result.isValid()){
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(result.getErrorMsg());
            isValid.postValue(false);
        }
        else{
            textInputLayout.setErrorEnabled(false);
            isValid.postValue(true);
        }
    }
}
