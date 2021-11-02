package com.pupccis.fitnex.model;

import com.pupccis.fitnex.utilities.Constants.nutritionTrackingConstants.NutritionTrackingConstants;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.HashMap;

public class FoodData implements Serializable {
    private String name, datatype, brand, mealType;
    private Double calories, protein, fats, carbs;
    private int fcdID, servingAmount, servingInfo;
    private JSONArray arrayServingSize;


    public FoodData() {
    }

    public FoodData(Builder builder) {
        this.fcdID = builder.fcdID;
        this.name = builder.name;
        this.datatype = builder.datatype;
        this.brand = builder.brand;
        this.calories = builder.calories;
        this.servingAmount = builder.servingAmount;
        this.protein = builder.protein;
        this.fats = builder.fats;
        this.carbs = builder.carbs;
        this.arrayServingSize = builder.arrayServingSize;
        this.servingInfo = builder.servingInfo;
        this.mealType = builder.mealType;
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

    public int getServingAmount() {
        return servingAmount;
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

    public JSONArray getArrayServingSize() {
        return arrayServingSize;
    }

    public int getServingInfo() {
        return servingInfo;
    }

    public String getMealType() {
        return mealType;
    }

    public HashMap<String, Object> trackFoodMap() {
        HashMap<String, Object> trackFoodMap = new HashMap<>();
        trackFoodMap.put(NutritionTrackingConstants.KEY_NUTRITION_FOOD_NAME, name);
        trackFoodMap.put(NutritionTrackingConstants.KEY_NUTRITION_CALORIES, calories);
        trackFoodMap.put(NutritionTrackingConstants.KEY_NUTRITION_SERVING_AMOUNT, servingAmount);
        trackFoodMap.put(NutritionTrackingConstants.KEY_NUTRITION_SERVING_INFO, servingInfo);
        trackFoodMap.put(NutritionTrackingConstants.KEY_NUTRITION_CARBS, carbs);
        trackFoodMap.put(NutritionTrackingConstants.KEY_NUTRITION_PROTEIN, protein);
        trackFoodMap.put(NutritionTrackingConstants.KEY_NUTRITION_FATS, fats);
        return trackFoodMap;
    }

    public static class Builder{
        private String name, datatype, brand, mealType;
        private Double calories, protein, fats, carbs;
        private int fcdID, servingAmount, servingInfo;
        private JSONArray arrayServingSize;

        public Builder() {
        }

        public Builder(FoodData foodData){
            this.fcdID = foodData.getFcdID();
            this.name = foodData.getName();
            this.datatype = foodData.getDatatype();
            this.brand = foodData.getBrand();
            this.calories = foodData.getCalories();
            this.servingAmount = foodData.getServingAmount();
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

        public Builder arrayServingSize(JSONArray arrayServingSize){
            this.arrayServingSize = arrayServingSize;
            return this;
        }

        public Builder servingAmount(int servingAmount) {
            this.servingAmount = servingAmount;
            return this;
        }

        public Builder servingInfo(int servingInfo) {
            this.servingInfo = servingInfo;
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
        public Builder mealType(String mealType) {
            this.mealType = mealType;
            return this;
        }
        public FoodData build(){
            FoodData foodData = new FoodData(this);
            return foodData;
        }
    }
}
