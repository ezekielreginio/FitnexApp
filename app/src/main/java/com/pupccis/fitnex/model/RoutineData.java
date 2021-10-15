package com.pupccis.fitnex.model;

import java.io.Serializable;

public class RoutineData implements Serializable {
    private int position, reps;
    private String routineSetID, userID;
    private boolean isCompleted;
    private double weight;

    public RoutineData(){

    }

    public RoutineData(Builder builder) {
        this.position = builder.position;
        this.reps = builder.reps;
        this.routineSetID = builder.routineID;
        this.isCompleted = builder.isCompleted;
        this.weight = builder.weight;
        this.userID = builder.userID;
    }

    public int getPosition() {
        return position;
    }

    public int getReps() {
        return reps;
    }

    public double getWeight() {
        return weight;
    }

    public String getRoutineSetID() {
        return routineSetID;
    }

    public String getUserID() {
        return userID;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public static class Builder{
        private int position, reps;
        private String routineID, userID;
        private boolean isCompleted;
        private double weight;

        public Builder(){
        }

        public Builder position(int position) {
            this.position = position;
            return this;
        }

        public Builder reps(int reps) {
            this.reps = reps;
            return this;
        }

        public Builder routineID(String routineID) {
            this.routineID = routineID;
            return this;
        }

        public Builder completed(boolean completed) {
            isCompleted = completed;
            return this;
        }

        public Builder userID(String userID) {
            this.userID = userID;
            return this;
        }

        public Builder weight(double weight) {
            this.weight = weight;
            return this;
        }

        public RoutineData build(){
            RoutineData routineData = new RoutineData(this);
            return routineData;
        }
    }
}
