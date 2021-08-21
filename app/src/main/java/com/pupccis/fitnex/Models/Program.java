package com.pupccis.fitnex.Models;

import java.io.Serializable;

public class Program implements Serializable {
    private String name;
    private String description;
    private String category;
    private String sessionNumber;
    private String duration;

    public Program() {
    }

    public Program(String name, String description, String category, String sessionNumber, String duration) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.sessionNumber = sessionNumber;
        this.duration = duration;
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
}
