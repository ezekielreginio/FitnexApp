package com.pupccis.fitnex.model;

import java.io.Serializable;

public class FoodData implements Serializable {
    private String name, datatype, brand;
    private Double calories;

    private int fcdID;

    public FoodData() {
    }

    public FoodData(Builder builder) {
        this.fcdID = builder.fcdID;
        this.name = builder.name;
        this.datatype = builder.datatype;
        this.brand = builder.brand;
        this.calories = builder.calories;
    }

    //Getter Methods
    public int getFcdID() {
        return fcdID;
    }

    public String getName() {
        return name;
    }

    public String getDatatype() {
        return datatype;
    }

    public String getBrand() {
        return brand;
    }

    public Double getCalories() {
        return calories;
    }

    public static class Builder{
        private String name, datatype, brand;
        private Double calories;
        private int fcdID;

        public Builder() {
        }

        public Builder fcdID(int fcdID) {
            this.fcdID = fcdID;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder datatype(String datatype) {
            this.datatype = datatype;
            return this;
        }

        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder calories(Double calories) {
            this.calories = calories;
            return this;
        }

        public FoodData build(){
            FoodData foodData = new FoodData(this);
            return foodData;
        }
    }
}
