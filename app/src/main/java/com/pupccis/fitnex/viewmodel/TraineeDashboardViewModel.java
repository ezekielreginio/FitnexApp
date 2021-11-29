package com.pupccis.fitnex.viewmodel;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.MediatorLiveData;

import com.pupccis.fitnex.repository.UserRepository;

public class TraineeDashboardViewModel extends BaseObservable {
    private MediatorLiveData<String> mldUserTraineeCoins =  new MediatorLiveData<>();
    private UserRepository userRepository = new UserRepository();
    @Bindable
    private String userTraineeCoins = "0.00";

    public String getUserTraineeCoins() {
        return userTraineeCoins;
    }

    public void setUserTraineeCoins(String userTraineeCoins) {
        this.userTraineeCoins = userTraineeCoins;
        notifyPropertyChanged(BR.userTraineeCoins);
    }
    //Public Methods
    public MediatorLiveData<String> getMldUserTraineeCoins(){
        mldUserTraineeCoins.addSource(userRepository.getUserCoins(), coins->{
            Log.e("Coins sa viewmodel", coins);
            setUserTraineeCoins(coins);});
        return mldUserTraineeCoins;
    }
}
