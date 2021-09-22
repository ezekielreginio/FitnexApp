package com.pupccis.fitnex.Validation;

public class ValidationResult {
    private final boolean isValid;
    private final String errorMsg;

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
        return isValid;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
