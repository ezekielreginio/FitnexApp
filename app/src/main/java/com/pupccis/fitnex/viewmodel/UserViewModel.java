package com.pupccis.fitnex.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pupccis.fitnex.Model.User;
import com.pupccis.fitnex.Repository.UserRepository;

public class UserViewModel extends ViewModel {

    public static MutableLiveData<User> registerUser(User user){
        MutableLiveData<User> userLiveData = UserRepository.registerUser(user);
        return userLiveData;
    }

    public static MutableLiveData<Boolean> checkDuplicateEmail(String email){
        MutableLiveData<Boolean> booleanMutableLiveData = UserRepository.duplicateEmailLiveResponse(email);
        return  booleanMutableLiveData;
    }
}
