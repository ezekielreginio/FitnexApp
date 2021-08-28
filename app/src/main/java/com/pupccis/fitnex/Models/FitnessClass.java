package com.pupccis.fitnex.Models;

import com.google.firebase.database.Exclude;
import com.google.type.DateTime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FitnessClass implements Serializable {

    private String timeStart;
    private String timeEnd;
    private String className;
    private String sessionNo;
    private String dateCreated;
    private String classTrainerID;
    private String description;
    private String classID;

    public FitnessClass() {
    }

    public FitnessClass(String className, String timeStart, String timeEnd, String sessionNo, String dateCreated, String description) {
        this.className = className;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.sessionNo = sessionNo;
        this.dateCreated = dateCreated;
        this.description = description;
    }
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("className", className);
        result.put("description", description);
        result.put("timeStart", timeStart);
        result.put("sessionNo", sessionNo);
        result.put("timeEnd", timeEnd);
        return result;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSessionNo() {
        return sessionNo;
    }

    public void setSessionNo(String sessionNo) {
        this.sessionNo = sessionNo;
    }
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getClassTrainerID() {
        return classTrainerID;
    }

    public void setClassTrainerID(String classTrainerID) {
        this.classTrainerID = classTrainerID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

}
