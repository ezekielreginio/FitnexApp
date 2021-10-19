package com.pupccis.fitnex.activities.nutritiontracking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.FragmentFoodInfoBinding;
import com.pupccis.fitnex.model.FoodData;
import com.pupccis.fitnex.viewmodel.NutritionTrackingViewModel;

public class FragmentFoodInfo extends Fragment {
    private FragmentFoodInfoBinding binding;

    public FragmentFoodInfo() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FoodData foodData = binding.getViewModel().getCurrentFoodData();
        binding.textViewFoodInfoTitle.setText(foodData.getName());
        binding.textViewCalories.setText(foodData.getCalories()+"");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        NutritionTrackingViewModel nutritionTrackingViewModel = ((NutritionTrackingMain)getActivity()).getNutritionTrackingViewModel();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_info, container, false);
        binding.setViewModel(nutritionTrackingViewModel);
        binding.setLifecycleOwner(this);
        binding.setPresenter(this);
        binding.executePendingBindings();

        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.getViewModel().setCurrentFoodData(null);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("On Pause", "Invoked");
        binding.getViewModel().setCurrentFoodData(null);
    }
}