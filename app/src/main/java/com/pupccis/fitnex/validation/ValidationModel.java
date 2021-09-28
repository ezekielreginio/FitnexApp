package com.pupccis.fitnex.validation;

import com.google.android.material.textfield.TextInputLayout;

public class ValidationModel {
    private final TextInputLayout textInputLayout;
    private final InputType inputType;

    public ValidationModel(TextInputLayout textInputLayout, InputType inputType) {
        this.textInputLayout = textInputLayout;
        this.inputType = inputType;
    }


    public TextInputLayout getTextInputLayout() {
        return textInputLayout;
    }

    public InputType getInputType() {
        return inputType;
    }

}


