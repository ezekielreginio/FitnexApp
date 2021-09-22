package com.pupccis.fitnex.Validation.Services;

import com.pupccis.fitnex.Validation.ValidationModel;
import com.pupccis.fitnex.Validation.ValidationResult;

public interface ValidationServiceInterface {
    Object getInstance(ValidationModel object);
    ValidationResult validate();
}
