package com.pupccis.fitnex.validation.Services;

import androidx.lifecycle.MutableLiveData;

public interface EmailDuplicateChecker {
    MutableLiveData<Boolean> checkEmailDuplicate();
}
