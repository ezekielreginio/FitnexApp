package com.pupccis.fitnex.model;

import com.pupccis.fitnex.api.globals.Observer;
import com.pupccis.fitnex.utilities.Constants.FitnessClassConstants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FitnessClass implements Serializable, Observer {

    private  String className;
    private  String description;
    private  int category;
    private  String timeStart;
    private  String timeEnd;
    private  String duration;
    private  String classTrainerID;
    private  String sessionNo;
    private  String dateCreated;

    private String classID;
    private String trainees;
    private static FitnessClass instance;
    public FitnessClass(){

    }

    public FitnessClass(Builder builder) {
        this.className = builder.className;
        this.category = builder.category;
        this.timeStart = builder.timeStart;
        this.timeEnd = builder.timeEnd;
        this.sessionNo = builder.sessionNo;
        this.dateCreated = builder.dateCreated;
        this.description = builder.description;
        this.classTrainerID = builder.classTrainerID;
        this.duration = builder.duration;
    }
    @Override
    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("className", className);
        result.put("description", description);
        result.put("timeStart", timeStart);
        result.put("sessionNo", sessionNo);
        result.put("timeEnd", timeEnd);
        result.put("duration", duration);
        result.put("category", category);

        result.put(FitnessClassConstants.KEY_FITNESS_CLASSES_TRAINER_ID, classTrainerID);
        return result;
    }

    @Override
    public FitnessClass map(Map<String, Object> data) {
        instance = new FitnessClass();
        instance.className = data.get(FitnessClassConstants.KEY_FITNESS_CLASSES_NAME).toString();
        instance.description = data.get(FitnessClassConstants.KEY_FITNESS_CLASSES_DESCRIPTION).toString();
        instance.timeStart = data.get(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_START).toString();
        instance.sessionNo = data.get(FitnessClassConstants.KEY_FITNESS_CLASSES_SESSION_NUMBER).toString();
        instance.timeEnd = data.get(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_END).toString();
        instance.duration = data.get(FitnessClassConstants.KEY_FITNESS_CLASSES_DURATION).toString();
        instance.category = Integer.parseInt(data.get(FitnessClassConstants.KEY_FITNESS_CLASSES_CATEGORY).toString());
        instance.classID = data.get(FitnessClassConstants.KEY_FITNESS_CLASSES_ID).toString();
        instance.classTrainerID = data.get(FitnessClassConstants.KEY_FITNESS_CLASSES_TRAINER_ID).toString();
        return instance;
    }

    @Override
    public String getKey() {
        return FitnessClassConstants.KEY_FITNESS_CLASSES_ID;
    }

    @Override
    public String getId() {
        return this.classID;
    }

    //Fitness Class Getters


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

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getTrainees() {
        return trainees;
    }

    public void setTrainees(String trainees) {
        this.trainees = trainees;
    }

    public static FitnessClass getInstance() {
        return instance;
    }

    public static void setInstance(FitnessClass instance) {
        FitnessClass.instance = instance;
    }

    public static class Builder{
        private final String className;
        private final String description;
        private final int category;
        private final String timeStart;
        private final String timeEnd;
        private final String duration;
        private final String sessionNo;

        private String classTrainerID;
        private String dateCreated;
        private String classID;
        private String trainees;

        public Builder(String className, String description, int category, String timeStart, String timeEnd, String sessionNumber, String duration){
            this.className = className;
            this.description = description;
            this.category = category;
            this.timeStart = timeStart;
            this.timeEnd = timeEnd;
            this.duration = duration;
            this.sessionNo = sessionNumber;

        }

        public Builder setClassID(String classID){
            this.classID = classID;
            return this;
        }
        public Builder setTrainees(String trainees){
            this.trainees = trainees;
            return this;
        }
        public Builder setDateCreated(String currentTime){
            this.dateCreated = currentTime;
            return this;
        }
        public Builder setClassTrainerID(String trainer_id){
            this.classTrainerID = trainer_id;
            return this;
        }
        public FitnessClass build(){
            FitnessClass fitnessClass = new FitnessClass(this);
            return fitnessClass;
        }

    }
}
