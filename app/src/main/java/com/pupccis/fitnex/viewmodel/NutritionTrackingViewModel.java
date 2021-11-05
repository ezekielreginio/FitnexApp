package com.pupccis.fitnex.viewmodel;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.activities.nutritiontracking.enums.Meals;
import com.pupccis.fitnex.model.FoodData;
import com.pupccis.fitnex.repository.NutritionTrackingRepository;
import com.pupccis.fitnex.utilities.Constants.nutritionTrackingConstants.MacroNutrients;
import com.pupccis.fitnex.utilities.Constants.nutritionTrackingConstants.NutritionTrackingConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class NutritionTrackingViewModel extends BaseObservable {
    //Repository Instance
    private NutritionTrackingRepository nutritionTrackingRepository = new NutritionTrackingRepository();

    private DecimalFormat df = new DecimalFormat("####0.#");

    //Mutable Live Data Attributes
    private MutableLiveData<FoodData> liveFoodData = new MutableLiveData<>();
    private MediatorLiveData<HashMap<String, Object>> mldNutritionData = new MediatorLiveData<>();

    private FoodData currentFoodData;
    private FoodData addFoodData;
    private Meals currentMeal;
    private int currentServing = 0;
    private int servingGram = 0;

    //Nutrition Tracking Attributes
    private int goalCalories = 2600;
    private int eatenCalories = 0;
    private double goalProtein = (((double) goalCalories) * 0.2) / 4;
    private double goalCarbs = (((double) goalCalories) * 0.6) / 4;
    private double goalFats = (((double) goalCalories) * 0.2) / 9;

    //Bindable Attributes
    //EditText
    @Bindable
    private String foodSearch = null;
    //TextView
    @Bindable
    private String foodName;
    @Bindable
    private String calories;
    @Bindable
    private String carbs;
    @Bindable
    private String protein;
    @Bindable
    private String fats;
    @Bindable
    private String percentageCarbs;
    @Bindable
    private String percentageProtein;
    @Bindable
    private String percentageFats;
    @Bindable
    private HashMap<MacroNutrients, Integer> macroNutrients;
    @Bindable
    private String servingAmount = "1";
    @Bindable
    private String stringCurrentMeal;

    //Nutrition Tracking Attributes
    @Bindable
    private String stringEatenCalories = eatenCalories+"";
    @Bindable
    private String stringGoalCalories = "2600";
    @Bindable
    private String stringGoalProtein = goalProtein +"";
    @Bindable
    private String stringGoalCarbs = goalCarbs +"";
    @Bindable
    private String stringGoalFats = goalFats +"";
    @Bindable
    private String stringBreakfastDescription = "Recommended: 400-420 kcal";
    @Bindable
    private String stringLunchDescription = "Recommended: 400-420 kcal";
    @Bindable
    private String stringDinnerDescription = "Recommended: 400-420 kcal";
    @Bindable
    private String stringSnacksDescription = "Recommended: 400-420 kcal";

    //Getter Methods
    public String getFoodSearch() {
        return foodSearch;
    }

    public FoodData getCurrentFoodData() {
        return currentFoodData;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getCalories() {
        return calories;
    }

    public String getCarbs() {
        return carbs;
    }

    public String getProtein() {
        return protein;
    }

    public String getFats() {
        return fats;
    }

    public String getPercentageCarbs() {
        return percentageCarbs;
    }

    public String getPercentageProtein() {
        return percentageProtein;
    }

    public String getPercentageFats() {
        return percentageFats;
    }

    public HashMap<MacroNutrients, Integer> getMacroNutrients() {
        return macroNutrients;
    }

    public String getServingAmount() {
        return servingAmount;
    }

    public String getStringCurrentMeal() { return stringCurrentMeal; }

    public String getStringEatenCalories() { return stringEatenCalories; }

    public String getStringGoalCalories() { return stringGoalCalories; }

    public String getStringGoalProtein() { return stringGoalProtein; }

    public String getStringGoalCarbs() { return stringGoalCarbs; }

    public String getStringGoalFats() { return stringGoalFats; }

    public String getStringBreakfastDescription() { return stringBreakfastDescription; }

    public String getStringLunchDescription() { return stringLunchDescription; }

    public String getStringDinnerDescription() { return stringDinnerDescription; }

    public String getStringSnacksDescription() { return stringSnacksDescription; }

    //Setter Methods
    public void setCurrentFoodData(FoodData currentFoodData) {
        this.currentFoodData = currentFoodData;
    }

    public void setFoodSearch(String foodSearch) {
        this.foodSearch = foodSearch;
        notifyPropertyChanged(BR.foodSearch);
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
        notifyPropertyChanged(BR.foodName);
    }

    public void setCalories(String calories) {
        this.calories = calories;
        notifyPropertyChanged(BR.calories);
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
        notifyPropertyChanged(BR.carbs);
    }

    public void setProtein(String protein) {
        this.protein = protein;
        notifyPropertyChanged(BR.protein);
    }

    public void setFats(String fats) {
        this.fats = fats;
        notifyPropertyChanged(BR.fats);
    }

    public void setMacroNutrients(HashMap<MacroNutrients, Integer> macroNutrients) {
        this.macroNutrients = macroNutrients;
        notifyPropertyChanged(BR.macroNutrients);
    }

    public void setPercentageCarbs(String percentageCarbs) {
        this.percentageCarbs = percentageCarbs;
        notifyPropertyChanged(BR.percentageCarbs);
    }

    public void setPercentageProtein(String percentageProtein) {
        this.percentageProtein = percentageProtein;
        notifyPropertyChanged(BR.percentageProtein);
    }

    public void setPercentageFats(String percentageFats) {
        this.percentageFats = percentageFats;
        notifyPropertyChanged(BR.percentageFats);
    }

    public void setServingAmount(String servingAmount) {
        Log.d("Serving Amount", servingAmount);
        this.servingAmount = servingAmount;
        updateNutritionInfo();
        notifyPropertyChanged(BR.servingAmount);
    }

    public void setStringCurrentMeal(String stringCurrentMeal) {
        this.stringCurrentMeal = stringCurrentMeal;
        notifyPropertyChanged(BR.stringCurrentMeal);
    }

    public void setStringEatenCalories(String stringEatenCalories) {
        this.stringEatenCalories = stringEatenCalories;
        notifyPropertyChanged(BR.stringEatenCalories);
    }

    public void setStringGoalCalories(String stringGoalCalories) {
        this.stringGoalCalories = stringGoalCalories;
        notifyPropertyChanged(BR.stringGoalCalories);
    }

    public void setStringGoalProtein(String stringGoalProtein) {
        this.stringGoalProtein = stringGoalProtein;
        notifyPropertyChanged(BR.stringGoalProtein);
    }

    public void setStringGoalCarbs(String stringGoalCarbs) {
        this.stringGoalCarbs = stringGoalCarbs;
        notifyPropertyChanged(BR.stringGoalCarbs);
    }

    public void setStringGoalFats(String stringGoalFats) {
        this.stringGoalFats = stringGoalFats;
        notifyPropertyChanged(BR.stringGoalFats);
    }

    public void setStringBreakfastDescription(String stringBreakfastDescription) {
        this.stringBreakfastDescription = stringBreakfastDescription;
        notifyPropertyChanged(BR.stringBreakfastDescription);
    }

    public void setStringLunchDescription(String stringLunchDescription) {
        this.stringLunchDescription = stringLunchDescription;
        notifyPropertyChanged(BR.stringLunchDescription);
    }

    public void setStringDinnerDescription(String stringDinnerDescription) {
        this.stringDinnerDescription = stringDinnerDescription;
        notifyPropertyChanged(BR.stringDinnerDescription);
    }

    public void setStringSnacksDescription(String stringSnacksDescription) {
        this.stringSnacksDescription = stringSnacksDescription;
        notifyPropertyChanged(BR.stringSnacksDescription);
    }

    //Mutable Live Data Observers
    public MutableLiveData<List<FoodData>> getFoodResults(RequestQueue queue, String queryFood) {
        return nutritionTrackingRepository.getFoodResults(queue, queryFood);
    }

    public MutableLiveData<HashMap<String, Object>> getMealData(Meals meal){
        return nutritionTrackingRepository.getMealData(meal);
    }

    //
    @RequiresApi(api = Build.VERSION_CODES.O)
    public MediatorLiveData<HashMap<String, Object>> mediatorLiveData(){
        mldNutritionData.addSource(nutritionTrackingRepository.getMealData(Meals.BREAKFAST), nutritionData ->{
            if(nutritionData != null){
                updateNutritionData(nutritionData);
                if(!((List) nutritionData.get("foodList")).isEmpty())
                    setStringBreakfastDescription(String.join(",", (List) nutritionData.get("foodList")));
            }
        });

        mldNutritionData.addSource(nutritionTrackingRepository.getMealData(Meals.LUNCH), nutritionData ->{
            if(nutritionData != null){
                updateNutritionData(nutritionData);
                if(!((List) nutritionData.get("foodList")).isEmpty())
                    setStringLunchDescription(String.join(",", (List) nutritionData.get("foodList")));
            }
        });

        mldNutritionData.addSource(nutritionTrackingRepository.getMealData(Meals.DINNER), nutritionData ->{
            if(nutritionData != null){
                updateNutritionData(nutritionData);
                if(!((List) nutritionData.get("foodList")).isEmpty())
                    setStringDinnerDescription(String.join(",", (List) nutritionData.get("foodList")));
            }
        });

        return mldNutritionData;
    }

    //Private Methods
    private void updateNutritionData(HashMap<String, Object> nutritionData){
        Log.d("Update Nutrition", "Executed");
        eatenCalories = eatenCalories + (int) nutritionData.get(NutritionTrackingConstants.KEY_NUTRITION_CALORIES);
        goalCalories = goalCalories - eatenCalories;
        goalCarbs = goalCarbs - (double) nutritionData.get(NutritionTrackingConstants.KEY_NUTRITION_CARBS);
        goalProtein = goalProtein - (double) nutritionData.get(NutritionTrackingConstants.KEY_NUTRITION_PROTEIN);
        goalFats = goalFats - (double) nutritionData.get(NutritionTrackingConstants.KEY_NUTRITION_FATS);

        setStringEatenCalories(eatenCalories+"");
        setStringGoalCalories(goalCalories+"");
        setStringGoalCarbs(df.format(goalCarbs));
        setStringGoalProtein(df.format(goalProtein));
        setStringGoalFats(df.format(goalFats));
    }

    //Public Methods
    public HashMap<MacroNutrients, Integer> calculateFoodInfo() {
        HashMap<MacroNutrients, Integer> foodDataMap = new HashMap<>();
        double protein = currentFoodData.getProtein();
        double carbs = currentFoodData.getCarbs();
        double fats = currentFoodData.getFats();
        double totalCalories = (protein * 4) + (carbs *4) + (fats * 9);
        foodDataMap.put(MacroNutrients.PROTEIN, (int) (((protein * 4) / totalCalories) * 100));
        foodDataMap.put(MacroNutrients.CARBS, (int) (((carbs * 4) / totalCalories) * 100));
        foodDataMap.put(MacroNutrients.FATS, (int) (((fats * 9) / totalCalories) * 100));
        return foodDataMap;
    }

    public void initializeFoodData() {
        HashMap<MacroNutrients, Integer> macroNutrients = calculateFoodInfo();
        setFoodName(currentFoodData.getName());
        setCalories(currentFoodData.getCalories()+"");
        setCarbs(currentFoodData.getCarbs()+"g");
        setProtein(currentFoodData.getProtein()+"g");
        setFats(currentFoodData.getFats()+"g");
        setPercentageProtein(macroNutrients.get(MacroNutrients.PROTEIN)+"%");
        setPercentageCarbs(macroNutrients.get(MacroNutrients.CARBS)+"%");
        setPercentageFats(macroNutrients.get(MacroNutrients.FATS)+"%");

        setMacroNutrients(macroNutrients);

    }

    public MutableLiveData<JSONArray> getServingInfo(RequestQueue queue, int fcdID) {
        return nutritionTrackingRepository.getServingInfo(queue, fcdID);
    }

    public List<String> getServingList(){
        List<String> spinnerList = new ArrayList<>();

        try{
            Log.d("Array Size", currentFoodData.getArrayServingSize().length()+"");
            for(int i=0; i<currentFoodData.getArrayServingSize().length(); i++){
                JSONObject servingInfo = currentFoodData.getArrayServingSize().getJSONObject(i);
                Log.d("serving info", servingInfo.toString());
                String portionDescription = servingInfo.getString("portionDescription");

                if(i==0)
                    this.servingGram = servingInfo.getInt("gramWeight");

                if(!portionDescription.equals("Quantity not specified"))
                    spinnerList.add(portionDescription);
                else
                    spinnerList.add("1 gram");
            }
        }
        catch (JSONException e){
            Log.d("JSONException", e.getMessage());
        }

        return spinnerList;
    }

    //Private Methods
    private void updateNutritionInfo() {
        if(servingAmount != null){
            if(!servingAmount.isEmpty()){
                DecimalFormat df = new DecimalFormat("####0.##");
                Double inputServing = Double.parseDouble(servingAmount);
                double calories = 0.0;
                double protein = 0.0;
                double carbs = 0.0;
                double fats = 0.0;
                if(currentServing != currentFoodData.getArrayServingSize().length()-1){
                    addFoodData = new FoodData.Builder()
                            .calories(getCurrentFoodData().getCalories() * Double.parseDouble(servingAmount))
                            .protein(getCurrentFoodData().getProtein() * Double.parseDouble(servingAmount))
                            .carbs(getCurrentFoodData().getCarbs() * Double.parseDouble(servingAmount))
                            .fats(getCurrentFoodData().getFats() * Double.parseDouble(servingAmount))
                            .build();
//                    calories = getCurrentFoodData().getCalories() * Double.parseDouble(servingAmount);
//                    protein = getCurrentFoodData().getProtein() * Double.parseDouble(servingAmount);
//                    carbs = getCurrentFoodData().getCarbs() * Double.parseDouble(servingAmount);
//                    fats = getCurrentFoodData().getFats() * Double.parseDouble(servingAmount);
                }
                else{
                    addFoodData = new FoodData.Builder()
                            .calories((getCurrentFoodData().getCalories() * inputServing) / servingGram)
                            .protein((getCurrentFoodData().getProtein() * inputServing) / servingGram)
                            .carbs((getCurrentFoodData().getCarbs() * inputServing) / servingGram)
                            .fats((getCurrentFoodData().getFats() * inputServing) / servingGram)
                            .build();
//                    calories = (getCurrentFoodData().getCalories() * inputServing) / servingGram;
//                    protein = (getCurrentFoodData().getProtein() * inputServing) / servingGram;
//                    carbs = (getCurrentFoodData().getCarbs() * inputServing) / servingGram;
//                    fats = (getCurrentFoodData().getFats() * inputServing) / servingGram;
                }

                setCalories(addFoodData.getCalories()+"");
                setCarbs(addFoodData.getCarbs()+"g");
                setProtein(addFoodData.getProtein()+"g");
                setFats(addFoodData.getFats()+"g");
            }
        }
    }

    public void setCurrentMeal(Meals currentMeal) {
        this.currentMeal = currentMeal;
        setStringCurrentMeal(currentMeal.toString());
    }

    public MutableLiveData<Boolean> trackFood() {
        Log.d("Current Meal", currentMeal.toString());
        FoodData foodData = new FoodData.Builder(addFoodData)
                .fcdID(currentFoodData.getFcdID())
                .servingAmount(Integer.parseInt(servingAmount))
                .servingInfo(currentServing)
                .mealType(currentMeal.toString())
                .name(currentFoodData.getName())
                .build();
        return nutritionTrackingRepository.trackFood(foodData);
    }

    public void setNutritionTrackingData(HashMap<String, Object> nutritionTrackingData){

    }
}
