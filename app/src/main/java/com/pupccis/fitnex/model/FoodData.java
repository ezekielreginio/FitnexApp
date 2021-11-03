package com.pupccis.fitnex.model;

import com.pupccis.fitnex.activities.nutritiontracking.enums.MacroNutrients;
import com.pupccis.fitnex.activities.nutritiontracking.enums.Meals;
import com.pupccis.fitnex.utilities.Constants.nutritionTrackingConstants.NutritionTrackingConstants;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.HashMap;

public class FoodData implements Serializable {
    private String name, datatype, brand, mealType;
    private Double calories, protein, fats, carbs, totalCalories, totalCarbs, totalProtein, totalFat;
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

        this.totalCalories = builder.totalCalories;
        this.totalCarbs = builder.totalCarbs;
        this.totalProtein = builder.totalProtein;
        this.totalFat = builder.totalFat;
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

    public Double getTotalCalories() { return totalCalories; }

    public Double getTotalCarbs() { return totalCarbs; }

    public Double getTotalProtein() { return totalProtein; }

    public Double getTotalFat() { return totalFat; }

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

    public HashMap<String, Object> totalDataMap(FoodData mealFoodData){
        HashMap<String, Object> totalDataMap = new HashMap<>();
        totalDataMap.put(NutritionTrackingConstants.KEY_NUTRITION_TOTAL_CALORIES, totalCalories);
        totalDataMap.put(NutritionTrackingConstants.KEY_NUTRITION_TOTAL_CARBS, totalCarbs);
        totalDataMap.put(NutritionTrackingConstants.KEY_NUTRITION_TOTAL_FATS, totalFat);
        totalDataMap.put(NutritionTrackingConstants.KEY_NUTRITION_TOTAL_PROTEIN, totalProtein);

        totalDataMap.put(NutritionTrackingConstants.mealData(Meals.valueOf(mealType), MacroNutrients.CALORIES), mealFoodData.getTotalCalories());
        totalDataMap.put(NutritionTrackingConstants.mealData(Meals.valueOf(mealType), MacroNutrients.PROTEIN), mealFoodData.getTotalProtein());
        totalDataMap.put(NutritionTrackingConstants.mealData(Meals.valueOf(mealType), MacroNutrients.FATS), mealFoodData.getTotalFat());
        totalDataMap.put(NutritionTrackingConstants.mealData(Meals.valueOf(mealType), MacroNutrients.CARBS), mealFoodData.getTotalCarbs());
        return totalDataMap;
    }

    public static class Builder{
        private String name, datatype, brand, mealType;
        private Double calories, protein, fats, carbs, totalCalories, totalProtein, totalCarbs, totalFat;
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
        public Builder totalCalories(Double totalCalories) {
            this.totalCalories = totalCalories;
            return this;
        }
        public Builder totalProtein(Double totalProtein) {
            this.totalProtein = totalProtein;
            return this;
        }
        public Builder totalCarbs(Double totalCarbs) {
            this.totalCarbs = totalCarbs;
            return this;
        }
        public Builder totalFat(Double totalFat) {
            this.totalFat = totalFat;
            return this;
        }
        public FoodData build(){
            FoodData foodData = new FoodData(this);
            return foodData;
        }
    }
}
