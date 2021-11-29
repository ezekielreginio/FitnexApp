package com.pupccis.fitnex.model;

import com.pupccis.fitnex.utilities.Constants.TopUpConstants;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TopUpRequest implements Serializable {
    private String traineeName;
    private String traineeID;
    private String referenceNumber;
    private int status;
    private int amount;
    private long date;

    private TopUpRequest(Builder builder){
        this.traineeName = builder.traineeName;
        this.traineeID = builder.traineeID;
        this.status = builder.status;
        this.referenceNumber = builder.referenceNumber;
        this.amount = builder.amount;
        this.date = builder.date;
    }

    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("traineeName", traineeName);
        result.put("traineeID", traineeID);
        result.put(TopUpConstants.KEY_TOPUP_STATUS, status);
        result.put(TopUpConstants.KEY_TOPUP_REFERENCE_NUMBER, referenceNumber);
        result.put(TopUpConstants.KEY_TOPUP_AMOUNT, amount);
        result.put(TopUpConstants.KEY_TOPUP_DATE, date);

        return result;

    }
    public String getTraineeName() {
        return traineeName;
    }

    public String getTraineeID() {
        return traineeID;
    }

    public int getStatus() {
        return status;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public int getAmount() {
        return amount;
    }

    public long getDate() {
        return date;
    }

    public static class Builder{
        private String traineeName;
        private String traineeID;
        private String referenceNumber;
        private int status;
        private int amount;
        private long date;

        public Builder(String traineeName, String traineeID, int status, String referenceNumber, int amount, long date) {
            this.traineeName = traineeName;
            this.traineeID = traineeID;
            this.status = status;
            this.referenceNumber = referenceNumber;
            this.amount = amount;
            this.date = date;
        }

        public Builder(TopUpRequest topUpRequest){
            this.traineeName = topUpRequest.getTraineeName();
            this.traineeID = topUpRequest.getTraineeID();
            this.status = topUpRequest.getStatus();
            this.referenceNumber = topUpRequest.getReferenceNumber();
            this.amount = topUpRequest.getAmount();
            this.date = topUpRequest.getDate();
        }
        public TopUpRequest build(){
            TopUpRequest topUpRequest = new TopUpRequest(this);
            return topUpRequest;
        }
    }

}
