package com.pupccis.fitnex.handlers.view;

import android.util.Log;

import com.google.android.material.textfield.TextInputLayout;
import com.pupccis.fitnex.validation.ValidationResult;

import java.util.ArrayList;

public class ViewHandler {
    public static void errorHandler(TextInputLayout textInputLayout, ValidationResult result) {
        textInputLayout.setErrorEnabled(!result.isValid());
        textInputLayout.setError(result.getErrorMsg());
        if(result.getHelperMsg() != null)
            textInputLayout.getEditText().setError(result.getHelperMsg());
    }

    public static boolean uiErrorHandler(ArrayList<TextInputLayout> textInputLayouts){
        boolean isInvalid = false;
        for(TextInputLayout field: textInputLayouts){
            if(field.isErrorEnabled()) {
                Log.d("Field", "Invalid");
                isInvalid = true;
            }
            else if(field.getEditText().getText()==null || field.getEditText().getText().toString().isEmpty()){
                Log.d("Field", "Empty");
                field.setErrorEnabled(true);
                field.setError("This Field is Required");
                isInvalid = true;
            }
        }

        return isInvalid;
    }
}
