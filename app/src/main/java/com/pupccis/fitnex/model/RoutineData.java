package com.pupccis.fitnex.model;

import java.io.Serializable;

public class RoutineData implements Serializable {
    private int position, reps;
    private String routineID;
    private boolean isCompleted;
    private double weight;

    public RoutineData(){

    }

    public RoutineData(Builder builder) {
        this.position = builder.position;
        this.reps = builder.reps;
        this.routineID = builder.routineID;
        this.isCompleted = builder.isCompleted;
        this.weight = builder.weight;
    }

    public int getPosition() {
        return position;
    }

    public int getReps() {
        return reps;
    }

    public String getRoutineID() {
        return routineID;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public double getWeight() {
        return weight;
    }

    public static class Builder{
        private int position, reps;
        private String routineID;
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
