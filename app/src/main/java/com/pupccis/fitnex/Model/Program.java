package com.pupccis.fitnex.Model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Program implements Serializable {
    private final String name;
    private final String description;
    private final String category;
    private final String sessionNumber;
    private final String duration;
    private final String trainerID;

    private String trainees;
    private String programID;

    public Program(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.category = builder.category;
        this.sessionNumber = builder.sessionNumber;
        this.duration = builder.duration;
        this.trainerID = builder.trainerID;

        this.trainees = builder.trainees;
        this.programID = builder.programID;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("name", name);
        result.put("description", description);
        result.put("category", category);
        result.put("sessionNumber", sessionNumber);
        result.put("duration", duration);
        return result;

    }

    //Getter Methods


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getSessionNumber() {
        return sessionNumber;
    }

    public String getDuration() {
        return duration;
    }

    public String getTrainerID() {
        return trainerID;
    }

    public String getTrainees() {
        return trainees;
    }

    public String getProgramID() {
        return programID;
    }

    public static class Builder{
        private final String name;
        private final String description;
        private final String category;
        private final String sessionNumber;
        private final String duration;
        private final String trainerID;

        private String trainees;
        private String programID;

        public Builder(String name, String description, String category, String sessionNumber, String duration, String trainerID){
            this.name = name;
            this.description = description;
            this.category = category;
            this.sessionNumber = sessionNumber;
            this.duration = duration;
            this.trainerID = trainerID;
            this.trainees = "0";
        }

        public Builder setTrainees(String trainees) {
            this.trainees = trainees;
            return this;
        }

        public Builder setProgramID(String programID) {
            this.programID = programID;
            return this;
        }

        public Program build(){
            Program program = new Program(this);
            return program;
        }
    }
}
