package com.pupccis.fitnex.activities.nutritiontracking;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.ActivityNutritionTrackingMainBinding;
import com.pupccis.fitnex.viewmodel.NutritionTrackingViewModel;

public class NutritionTrackingMain extends AppCompatActivity  {
    private ActivityNutritionTrackingMainBinding binding;
    private NutritionTrackingViewModel nutritionTrackingViewModel = new NutritionTrackingViewModel();
    //Activity Getter Methods

    public NutritionTrackingViewModel getNutritionTrackingViewModel() {
        return nutritionTrackingViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nutrition_tracking_main);
        binding.setLifecycleOwner(this);
        binding.setViewModel(nutritionTrackingViewModel);
        binding.setPresenter(this);
        binding.executePendingBindings();
    }

}