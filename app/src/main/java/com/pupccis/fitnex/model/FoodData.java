package com.pupccis.fitnex.model;

import java.io.Serializable;

public class FoodData implements Serializable {
    private String name, datatype, brand;
    private Double calories, protein, fats, carbs;

    private int fcdID, servingSize;

    public FoodData() {
    }

    public FoodData(Builder builder) {
        this.fcdID = builder.fcdID;
        this.name = builder.name;
        this.datatype = builder.datatype;
        this.brand = builder.brand;
        this.calories = builder.calories;
        this.servingSize = builder.servingSize;
        this.protein = builder.protein;
        this.fats = builder.fats;
        this.carbs = builder.carbs;
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

    public int getServingSize() {
        return servingSize;
    }

    public Double getProtein() {
        return protein;
    }

    public Double getFats() {
        return fats;
    }

    public Double getCarbs() {
        return carbs;
    }

    public static class Builder{
        private String name, datatype, brand;
        private Double calories, protein, fats, carbs;
        private int fcdID, servingSize;

        public Builder() {
        }

        public Builder(FoodData foodData){
            this.fcdID = foodData.getFcdID();
            this.name = foodData.getName();
            this.datatype = foodData.getDatatype();
            this.brand = foodData.getBrand();
            this.calories = foodData.getCalories();
            this.servingSize = foodData.getServingSize();
            this.protein = foodData.getProtein();
            this.fats = foodData.getFats();
            this.carbs = foodData.getCarbs();
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

        public Builder servingSize(int servingSize) {
            this.servingSize = servingSize;
            return this;
        }

        public Builder protein(Double protein) {
            this.protein = protein;
            return this;
        }
        public Builder fats(Double fats) {
            this.fats = fats;
            return this;
        }
        public Builder carbs(Double carbs) {
            this.carbs = carbs;
            return this;
        }
        public FoodData build(){
            FoodData foodData = new FoodData(this);
            return foodData;
        }
    }
}
