package com.pupccis.fitnex.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pupccis.fitnex.Repository.UserRepository;

public class UserViewModel extends ViewModel {

    public static MutableLiveData<Boolean> checkDuplicateEmail(String email){
        MutableLiveData<Boolean> booleanMutableLiveData = UserRepository.duplicateEmailLiveResponse(email);
        return  booleanMutableLiveData;
    }
}
