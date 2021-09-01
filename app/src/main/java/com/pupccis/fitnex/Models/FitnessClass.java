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
    private String duration;
    private int category;

    public FitnessClass() {
    }

    public FitnessClass(String className,String description, int category, String timeStart, String timeEnd, String sessionNo, String duration, String dateCreated, String classTrainerID) {
        this.className = className;
        this.category = category;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.sessionNo = sessionNo;
        this.dateCreated = dateCreated;
        this.description = description;
        this.classTrainerID = classTrainerID;
        this.duration = duration;
    }
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("className", className);
        result.put("description", description);
        result.put("timeStart", timeStart);
        result.put("sessionNo", sessionNo);
        result.put("timeEnd", timeEnd);
        result.put("duration", duration);
        result.put("category", category);
        return result;
    }

    //Fitness Class Getters


    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public String getClassName() {
        return className;
    }

    public String getSessionNo() {
        return sessionNo;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getClassTrainerID() {
        return classTrainerID;
    }

    public String getDescription() {
        return description;
    }

    public String getClassID() {
        return classID;
    }

    public String getDuration() {
        return duration;
    }

    public int getCategory() {
        return category;
    }

    //Fitness Class Setters

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setSessionNo(String sessionNo) {
        this.sessionNo = sessionNo;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setClassTrainerID(String classTrainerID) {
        this.classTrainerID = classTrainerID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
