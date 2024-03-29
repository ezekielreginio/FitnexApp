package com.pupccis.fitnex.model;

import com.pupccis.fitnex.api.globals.Observer;
import com.pupccis.fitnex.utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.viewmodel.ProgramViewModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Program implements Serializable {

    private String name;
    private String description;
    private int category;
    private String sessionNumber;
    private String duration;
    private String trainerID;

    private String trainees;
    private String programID;

    private static Program instance;

    public Program(){

    }

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

    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("name", name);
        result.put("description", description);
        result.put("category", category);
        result.put("sessionNumber", sessionNumber);
        result.put("duration", duration);

        result.put(ProgramConstants.KEY_PROGRAM_TRAINER_ID, trainerID);
        return result;

    }


    //Getter Methods
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCategory() {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setSessionNumber(String sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setTrainerID(String trainerID) {
        this.trainerID = trainerID;
    }

    public void setTrainees(String trainees) {
        this.trainees = trainees;
    }

    public void setProgramID(String programID) {
        this.programID = programID;
    }

    public static class Builder{
        private final String name;
        private final String description;
        private final int category;
        private final String sessionNumber;
        private final String duration;
        private String trainerID;

        private String trainees;
        private String programID;

        public Builder(String name, String description, int category, String sessionNumber, String duration){
            this.name = name;
            this.description = description;
            this.category = category;
            this.sessionNumber = sessionNumber;
            this.duration = duration;
            this.trainees = "0";
        }

        public Builder (Program program){
            this.name = program.getName();
            this.description = program.getDescription();
            this.category = program.getCategory();
            this.sessionNumber = program.getSessionNumber();
            this.duration = program.getDuration();
            this.trainerID = program.getTrainerID();
            this.trainees = program.getTrainees();
        }

        public Builder setTrainees(String trainees) {
            this.trainees = trainees;
            return this;
        }

        public Builder setProgramID(String programID) {
            this.programID = programID;
            return this;
        }

        public Builder setTrainerID(String trainerID){
            this.trainerID = trainerID;
            return this;
        }

        public Program build(){
            Program program = new Program(this);
            return program;
        }
    }

}
