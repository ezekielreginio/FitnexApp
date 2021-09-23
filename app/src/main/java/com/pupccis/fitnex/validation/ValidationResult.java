package com.pupccis.fitnex.validation;

public class ValidationResult {
    private boolean isValid;
    private String errorMsg;

    public ValidationResult(){

    }

    public ValidationResult(boolean isValid, String errorMsg) {
        this.isValid = isValid;
        this.errorMsg = errorMsg;
    }

    public static ValidationResult valid() {
        return new ValidationResult(true, null);
    }

    public static ValidationResult invalid(String errorMsg) {
        return new ValidationResult(false, errorMsg);
    }

    public boolean notValid() {
        return !isValid;
    }

    public boolean isValid() {
        return this.isValid;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
