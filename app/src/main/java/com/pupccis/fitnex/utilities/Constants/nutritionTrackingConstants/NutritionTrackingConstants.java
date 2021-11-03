package com.pupccis.fitnex.utilities.Constants.nutritionTrackingConstants;

import com.pupccis.fitnex.activities.nutritiontracking.enums.MacroNutrients;
import com.pupccis.fitnex.activities.nutritiontracking.enums.Meals;

import java.util.HashMap;

public class NutritionTrackingConstants {

    public static final String KEY_COLLECTION_NUTRITION_TRACKING = "nutrition_tracking";
    public static final String KEY_NUTRITION_TOTAL_CALORIES = "totalCalories";
    public static final String KEY_NUTRITION_TOTAL_CARBS = "totalCarbs";
    public static final String KEY_NUTRITION_TOTAL_PROTEIN = "totalProtein";
    public static final String KEY_NUTRITION_TOTAL_FATS = "totalFats";

    public static final String KEY_NUTRITION_CALORIES = "calories";
    public static final String KEY_NUTRITION_FOOD_NAME = "foodName";
    public static final String KEY_NUTRITION_SERVING_AMOUNT = "servingAmount";
    public static final String KEY_NUTRITION_SERVING_INFO = "servingInfo";
    public static final String KEY_NUTRITION_PROTEIN = "protein";
    public static final String KEY_NUTRITION_FATS = "fats";
    public static final String KEY_NUTRITION_CARBS = "carbs";

    public static String mealData(Meals meal, MacroNutrients macroNutrients){
        HashMap<Meals, HashMap<MacroNutrients, String>> mealMap = new HashMap<>();
        HashMap<MacroNutrients, String> breakfastMealMap = new HashMap<>();
        HashMap<MacroNutrients, String> lunchMealMap = new HashMap<>();
        HashMap<MacroNutrients, String> dinnerMealMap = new HashMap<>();
        HashMap<MacroNutrients, String> snacksMealMap = new HashMap<>();

        breakfastMealMap.put(MacroNutrients.CALORIES, "breakfastTotalCalories");
        breakfastMealMap.put(MacroNutrients.PROTEIN, "breakfastTotalProtein");
        breakfastMealMap.put(MacroNutrients.CARBS, "breakfastTotalCarbs");
        breakfastMealMap.put(MacroNutrients.FATS, "breakfastTotalFats");

        lunchMealMap.put(MacroNutrients.CALORIES, "lunchTotalCalories");
        lunchMealMap.put(MacroNutrients.PROTEIN, "lunchTotalProtein");
        lunchMealMap.put(MacroNutrients.CARBS, "lunchTotalCarbs");
        lunchMealMap.put(MacroNutrients.FATS, "lunchTotalFats");

        dinnerMealMap.put(MacroNutrients.CALORIES, "dinnerTotalCalories");
        dinnerMealMap.put(MacroNutrients.PROTEIN, "dinnerTotalProtein");
        dinnerMealMap.put(MacroNutrients.CARBS, "dinnerTotalCarbs");
        dinnerMealMap.put(MacroNutrients.FATS, "dinnerTotalFats");

        snacksMealMap.put(MacroNutrients.CALORIES, "snacksTotalCalories");
        snacksMealMap.put(MacroNutrients.PROTEIN, "snacksTotalProtein");
        snacksMealMap.put(MacroNutrients.CARBS, "snacksTotalCarbs");
        snacksMealMap.put(MacroNutrients.FATS, "snacksTotalFats");

        mealMap.put(Meals.BREAKFAST, breakfastMealMap);
        mealMap.put(Meals.LUNCH, lunchMealMap);
        mealMap.put(Meals.DINNER, dinnerMealMap);
        mealMap.put(Meals.SNACKS, snacksMealMap);

        return mealMap.get(meal).get(macroNutrients);
    }

    public class NutrientID{
        public static final int ENERGY = 1008;
        public static final int PROTEIN = 1003;
        public static final int FAT = 1004;
        public static final int CARBS = 1005;
    }

    public class NutrientPercentage{
        public static final String PROTEIN = "percentageProtein";
        public static final String FAT = "percentageFats";
        public static final String CARBS = "percentageCarbs";
    }


}
