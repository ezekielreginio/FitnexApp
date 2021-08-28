package com.pupccis.fitnex.Models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Program implements Serializable {
    private String name;
    private String description;
    private String category;
    private String sessionNumber;
    private String duration;
    private String trainees;
    private String programID;
    private String programTrainerID;

    public Program() {
    }

    //Constructor for Creating a program
    public Program(String name, String description, String category, String sessionNumber, String duration) {
        program_init(name, description, category, sessionNumber, duration, "0");
    }

    //Constructor w/ trainee count
    public Program(String name, String description, String category, String sessionNumber, String duration, String trainees) {
        program_init(name, description, category, sessionNumber, duration, trainees);
    }

    private void program_init(String name, String description, String category, String sessionNumber, String duration, String trainees){
        this.name = name;
        this.description = description;
        this.category = category;
        this.setSessionNumber(sessionNumber);
        this.duration = duration;
        this.setTrainees(trainees);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(String sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTrainees() {
        return trainees;
    }

    public void setTrainees(String trainees) {
        this.trainees = trainees;
    }

    public String getProgramID() {
        return programID;
    }

    public void setProgramID(String programID) {
        this.programID = programID;
    }

    public String getProgramTrainerID() {
        return programTrainerID;
    }

    public void setProgramTrainerID(String programTrainerID) {
        this.programTrainerID = programTrainerID;
    }
}
