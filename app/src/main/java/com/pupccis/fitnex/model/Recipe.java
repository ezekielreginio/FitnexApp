package com.pupccis.fitnex.model;

import com.pupccis.fitnex.api.enums.Privilege;
import com.pupccis.fitnex.utilities.Constants.ProgramConstants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Recipe implements Serializable {
    private String name;
    private String description;
    private String trainerID;
    private String trainerName;
    private String trainees;
    private String prepTime;
    private String recipeID;
    private int category;
    private int fats;
    private int carbs;
    private int calories;
    private Privilege privilege;
    private int protein;

    public Recipe() {
    }

    public Recipe(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.trainerID = builder.trainerID;
        this.trainerName = builder.trainerName;
        this.prepTime = builder.prepTime;
        this.category = builder.category;
        this.fats = builder.fats;
        this.carbs = builder.carbs;
        this.calories = builder.calories;
        this.privilege = builder.privilege;
        this.protein = builder.protein;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("description", description);
        result.put("calories", calories);
        result.put("protein", protein);
        result.put("privilege", privilege);
        result.put("carbs", carbs);
        result.put("fats", fats);
        result.put("category", category);
        result.put("prepTime", prepTime);
        result.put("trainerName", trainerName);
        result.put(ProgramConstants.KEY_PROGRAM_TRAINER_ID, trainerID);
        return result;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public int getProtein() {
        return protein;
    }

    public String getTrainerID() {
        return trainerID;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public String getTrainees() {
        return trainees;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public int getCategory() {
        return category;
    }

    public int getFats() {
        return fats;
    }

    public int getCarbs() {
        return carbs;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }
    //
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public void setCalories(int calories) {
//        this.calories = calories;
//    }
//
//    public void setProtein(int protein) {
//        this.protein = protein;
//    }


    public static class Builder {
        private String name;
        private String description;
        private String trainerID;
        private String trainerName;
        private String trainees;
        private String prepTime;
        private int category;
        private int fats;
        private Privilege privilege;
        private int carbs;
        private int calories;
        private int protein;

        public Builder(String name, String description, int category, String prepTime, Privilege privilege) {
            this.name = name;
            this.description = description;
            this.category = category;
            this.prepTime = prepTime;
            this.category = category;
            this.privilege = privilege;
        }

        public Builder(Recipe recipe) {
            this.name = recipe.getName();
            this.description = recipe.getDescription();
            this.calories = recipe.getCalories();
            this.protein = recipe.getProtein();
        }

        public Builder setCalories(int calories) {
            this.calories = calories;
            return this;
        }

        public Builder setProtein(int protein) {
            this.protein = protein;
            return this;
        }

        public Builder setTrainerID(String trainerID) {
            this.trainerID = trainerID;
            return this;
        }
        public Builder setTrainerName(String trainerName){
            this.trainerName = trainerName;
            return this;
        }
        public Builder setFats(int fats){
            this.fats = fats;
            return this;
        }
        public Builder setCarbs(int carbs){
            this.carbs = carbs;
            return this;
        }

        public Recipe build() {
            Recipe recipe = new Recipe(this);
            return recipe;
        }
    }
}
