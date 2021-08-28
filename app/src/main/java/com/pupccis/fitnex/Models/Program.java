package com.pupccis.fitnex.Models;

import java.io.Serializable;

public class Program implements Serializable {
    private String name;
    private String description;
    private String category;
    private String sessionNumber;
    private String duration;
    private String trainees;

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
}
