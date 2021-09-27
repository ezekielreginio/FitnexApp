package com.pupccis.fitnex.adapters;

public class FitnessModel {
    private  String className;
    private  String description;
    private  int category;
    private  String timeStart;
    private  String timeEnd;
    private  String duration;
    private  String classTrainerID;
    private  String sessionNo;
    private String classID;
    public FitnessModel(){

    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getClassTrainerID() {
        return classTrainerID;
    }

    public void setClassTrainerID(String classTrainerID) {
        this.classTrainerID = classTrainerID;
    }

    public String getSessionNo() {
        return sessionNo;
    }

    public void setSessionNo(String sessionNo) {
        this.sessionNo = sessionNo;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }
}
