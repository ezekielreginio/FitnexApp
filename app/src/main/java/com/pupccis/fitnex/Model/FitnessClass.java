package com.pupccis.fitnex.Model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FitnessClass implements Serializable {

    private final String className;
    private final String description;
    private final int category;
    private final String timeStart;
    private final String timeEnd;
    private final String duration;
    private final String classTrainerID;
    private final String sessionNo;
    private final String dateCreated;

    private String classID;
    private String trainees;

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
