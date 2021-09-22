package com.pupccis.fitnex.Validation.Services;

import androidx.lifecycle.MutableLiveData;

public interface EmailDuplicateChecker {
    MutableLiveData<Boolean> checkEmailDuplicate();
}
