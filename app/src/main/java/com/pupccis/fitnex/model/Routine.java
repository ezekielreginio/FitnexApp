package com.pupccis.fitnex.model;

import com.pupccis.fitnex.API.globals.Observer;
import com.pupccis.fitnex.Utilities.Constants.RoutineConstants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Routine implements Serializable, Observer {

    private String name;
    private String routineID;
    private String programID;
    private int sets;
    private int reps;
    private int duration;
    private double weight;
    private Routine instance;


    public Routine(){

    }

    public Routine(Builder builder){
        this.name = builder.name;
        this.sets = builder.sets;
        this.reps = builder.reps;
        this.duration = builder.duration;
        this.weight = builder.weight;
        this.programID = builder.programID;
    }


    @Override
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("sets", sets);
        result.put("reps", reps);
        result.put("duration", duration);
        result.put("weight", weight);

        return result;
    }

    @Override
    public Object map(Map<String, Object> data) {
        instance = new Routine();
        instance.name = data.get(RoutineConstants.KEY_ROUTINE_NAME).toString();
        instance.sets = Integer.parseInt(data.get(RoutineConstants.KEY_ROUTINE_SETS).toString());
        instance.reps = Integer.parseInt(data.get(RoutineConstants.KEY_ROUTINE_REPS).toString());
        instance.duration = Integer.parseInt(data.get(RoutineConstants.KEY_ROUTINE_DURATION).toString());
        instance.weight = Double.parseDouble(data.get(RoutineConstants.KEY_ROUTINE_WEIGHT).toString());
        instance.routineID = data.get(RoutineConstants.KEY_ROUTINE_ID).toString();
        return instance;
    }

    @Override
    public String getKey() {
        return RoutineConstants.KEY_ROUTINE_ID;
    }

    @Override
    public String getId() {
        return this.routineID;
    }

    //Routine Getters
    public String getName() {
        return name;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public int getDuration() {
        return duration;
    }

    public double getWeight() {
        return weight;
    }

    public String getProgramID() {
        return programID;
    }

    public void setRoutineID(String routineID) {
        this.routineID = routineID;
    }

    public static class Builder{
        private final String name;
        private int sets;
        private int reps;
        private int duration;
        private double weight;
        private String routineID;
        private String programID;

        public Builder(String name){
            this.name = name;
        }
        public Builder sets(int sets){
            this.sets = sets;
            return this;
        }
        public Builder reps(int reps){
            this.reps = reps;
            return this;
        }
        public Builder duration(int duration){
            this.duration = duration;
            return this;
        }

        public Builder weight(double weight) {
            this.weight = weight;
            return this;
        }

        public Builder routineID(String routineID){
            this.routineID = routineID;
            return this;
        }
        public Builder programID(String programID){
            this.programID = programID;
            return this;
        }

        public Routine build(){
            Routine routine = new Routine(this);
            return routine;
        }
    }
}
