package com.pupccis.fitnex.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.model.FoodData;
import com.pupccis.fitnex.repository.NutritionTrackingRepository;

import java.util.List;

public class NutritionTrackingViewModel extends BaseObservable {
    //Repository Instance
    private NutritionTrackingRepository nutritionTrackingRepository = new NutritionTrackingRepository();
    @Bindable
    private String foodSearch = null;

    //Getters
    public String getFoodSearch() {
        return foodSearch;
    }

    //Setters
    public void setFoodSearch(String foodSearch) {
        this.foodSearch = foodSearch;
        notifyPropertyChanged(BR.foodSearch);
    }

    public MutableLiveData<List<FoodData>> getFoodResults(RequestQueue queue, String queryFood) {
        return nutritionTrackingRepository.getFoodResults(queue, queryFood);
    }
}
