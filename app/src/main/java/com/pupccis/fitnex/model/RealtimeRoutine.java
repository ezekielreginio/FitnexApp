package com.pupccis.fitnex.model;

import java.io.Serializable;

public class RealtimeRoutine implements Serializable {
    private String traineeName, programID;
    private long dateStarted;

    public RealtimeRoutine(String traineeName, String programID) {
        this.traineeName = traineeName;
        this.dateStarted = System.currentTimeMillis();
        this.programID = programID;
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
}
