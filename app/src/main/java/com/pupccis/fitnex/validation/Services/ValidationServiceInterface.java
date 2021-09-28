package com.pupccis.fitnex.validation.Services;

import com.pupccis.fitnex.validation.ValidationModel;
import com.pupccis.fitnex.validation.ValidationResult;

public interface ValidationServiceInterface {
    Object getInstance(ValidationModel object);
    ValidationResult validate();
}
