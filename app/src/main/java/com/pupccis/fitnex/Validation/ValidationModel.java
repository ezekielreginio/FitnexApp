package com.pupccis.fitnex.Validation;

import android.widget.EditText;
import android.widget.TextView;

public class ValidationModel {
    private final EditText editTextField;
    private final TextView editTextHelper;
    private final InputType inputType;

    public ValidationModel(EditText editTextField, TextView editTextHelper, InputType inputType) {
        this.editTextField = editTextField;
        this.editTextHelper = editTextHelper;
        this.inputType = inputType;
    }

    public EditText getEditTextField() {
        return editTextField;
    }

    public TextView getEditTextHelper() {
        return editTextHelper;
    }

    public InputType getInputType() {
        return inputType;
    }
}


