package com.pupccis.fitnex.model;

import java.io.Serializable;

public class RealtimeRoutine implements Serializable {
    private String traineeName, programID, userID, fcm_token, email;
    private long dateStarted;

    public RealtimeRoutine() {
    }

    public RealtimeRoutine(String traineeName, String programID, String fcm_token, String email) {
        this.traineeName = traineeName;
        this.dateStarted = System.currentTimeMillis();
        this.programID = programID;
        this.fcm_token = fcm_token;
        this.email = email;
    }

    public String getTraineeName() {
        return traineeName;
    }

    public void setTraineeName(String traineeName) {
        this.traineeName = traineeName;
    }

    public long getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(long dateStarted) {
        this.dateStarted = dateStarted;
    }

    public String getProgramID() {
        return programID;
    }

    public void setProgramID(String programID) {
        this.programID = programID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
