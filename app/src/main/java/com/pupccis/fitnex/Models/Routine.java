package com.pupccis.fitnex.Models;

public class Routine {
    private final String routineName;
    private int reps, sets, duration;
    private double weight;
    private Routine(RoutineBuilder builder) {
        this.routineName = builder.routineName;
        this.reps = builder.reps;
        this.sets = builder.sets;
        this.weight = builder.weight;
    }

    public String getRoutineName() {
        return routineName;
    }

    public int getReps() {
        return reps;
    }

    public int getSets() {
        return sets;
    }

    public int getDuration() {
        return duration;
    }

    public double getWeight() {
        return weight;
    }

    public static class RoutineBuilder{
        private final String routineName;
        private int reps, sets, duration;
        private double weight;
        public RoutineBuilder(String routineName) {
            this.routineName = routineName;
        }

        public RoutineBuilder reps(int reps){
            this.reps = reps;
            return this;
        }
        public RoutineBuilder sets(int sets){
            this.sets = sets;
            return this;
        }
        public RoutineBuilder duration(int duration){
            this.duration = duration;
            return this;
        }
        public RoutineBuilder weight(double weight){
            this.weight = weight;
            return this;
        }
        public Routine build(){
            Routine routine = new Routine(this);
            return routine;
        }
    }
}
