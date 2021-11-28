package com.pupccis.fitnex.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.pupccis.fitnex.model.TopUpRequest;
import com.pupccis.fitnex.repository.TopUpRepository;

import java.util.Calendar;
import java.util.Date;

public class TopUpViewModel extends BaseObservable {
    private TopUpRepository topUpRepository = TopUpRepository.getInstance();

    @Bindable
    private String topUpAmount;
    @Bindable
    private String topUpReferenceNumber;

    public String getTopUpAmount() {
        return topUpAmount;
    }

    public void setTopUpAmount(String topUpAmount) {
        this.topUpAmount = topUpAmount;
    }

    public String getTopUpReferenceNumber() {
        return topUpReferenceNumber;
    }

    public void setTopUpReferenceNumber(String topUpReferenceNumber) {
        this.topUpReferenceNumber = topUpReferenceNumber;
    }

    public MutableLiveData<TopUpRequest>insertTopUpRequest(String traineeName){
        TopUpRequest topUpRequest =topUpInstance(traineeName);
        return topUpRepository.insertTopUpRequest(topUpRequest);
    }
    public TopUpRequest topUpInstance(String traineeName){
        long date = Calendar.getInstance().getTimeInMillis();
        TopUpRequest topUpRequest = new TopUpRequest.Builder(traineeName, topUpRepository.getTraineeID(), "pending", getTopUpReferenceNumber(), Integer.parseInt(getTopUpAmount()), date).build();
        return topUpRequest;
    }

}
