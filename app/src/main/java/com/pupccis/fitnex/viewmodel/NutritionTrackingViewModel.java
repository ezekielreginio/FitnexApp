package com.pupccis.fitnex.viewmodel;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.model.FoodData;
import com.pupccis.fitnex.repository.NutritionTrackingRepository;
import com.pupccis.fitnex.utilities.Constants.nutritionTrackingConstants.MacroNutrients;
import com.pupccis.fitnex.utilities.Constants.nutritionTrackingConstants.NutritionTrackingConstants;

import java.util.HashMap;
import java.util.List;

public class NutritionTrackingViewModel extends BaseObservable {
    //Repository Instance
    private NutritionTrackingRepository nutritionTrackingRepository = new NutritionTrackingRepository();

    //Mutable Live Data Attributes
    private MutableLiveData<FoodData> liveFoodData = new MutableLiveData<>();

    private FoodData currentFoodData;

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

    //Mutable Live Data Observers
    public MutableLiveData<List<FoodData>> getFoodResults(RequestQueue queue, String queryFood) {
        return nutritionTrackingRepository.getFoodResults(queue, queryFood);
    }


    public MutableLiveData<FoodData> getFoodInfo(RequestQueue queue, FoodData foodData) {
        return nutritionTrackingRepository.getFoodInfo(queue, foodData);
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

    public MutableLiveData<Integer> getServingInfo(RequestQueue queue, int fcdID) {
        return nutritionTrackingRepository.getServingInfo(queue, fcdID);
    }
}
