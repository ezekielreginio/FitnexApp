package com.pupccis.fitnex.activities.nutritiontracking;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.activities.nutritiontracking.enums.Meals;
import com.pupccis.fitnex.databinding.FragmentNutritionTrackingBinding;

import java.text.DecimalFormat;

public class FragmentNutritionTracking extends Fragment implements View.OnClickListener {
    private FragmentNutritionTrackingBinding binding;
    //Overrideable Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nutrition_tracking, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setViewModel(((NutritionTrackingMain) getActivity()).getNutritionTrackingViewModel());
        binding.setPresenter(this);
        binding.executePendingBindings();

       binding.getViewModel().mediatorLiveData().observe(binding.getLifecycleOwner(), stringObjectHashMap -> { });

//        binding.getViewModel().getMealData(Meals.BREAKFAST).observe(binding.getLifecycleOwner(), new Observer<HashMap<String, Object>>() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onChanged(HashMap<String, Object> breakfastMealData) {
//                if(breakfastMealData != null){
//                    String foodList = "";
//                    if(breakfastMealData.get("foodList") != null){
//                        foodList = String.join(",", (List) breakfastMealData.get("foodList"));
//                    }
//
//                    eatenCalories = eatenCalories + (int) breakfastMealData.get(NutritionTrackingConstants.KEY_NUTRITION_CALORIES);
//                    carbs = goalCarbs - (double) breakfastMealData.get(NutritionTrackingConstants.KEY_NUTRITION_CARBS);
//                    protein = goalProtein - (double) breakfastMealData.get(NutritionTrackingConstants.KEY_NUTRITION_PROTEIN);
//                    fats = goalFats - (double) breakfastMealData.get(NutritionTrackingConstants.KEY_NUTRITION_FATS);
//
//                    binding.textViewBreakfastDescription.setText(foodList);
//                    binding.textViewEatenCalories.setText(eatenCalories+"");
//                    binding.textViewProteinRemaining.setText(df.format(protein));
//                    binding.textViewCarbsRemaining.setText(df.format(carbs));
//                    binding.textViewFatsRemaining.setText(df.format(fats));
//                }
//            }
//        });

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if(view == binding.imageViewAddBreakfast){
            Navigation.findNavController(view).navigate(R.id.action_fragmentNutritionTracker_to_fragmentAddFood);
            binding.getViewModel().setCurrentMeal(Meals.BREAKFAST);
        }
        else if(view == binding.imageViewAddLunch){
            Navigation.findNavController(view).navigate(R.id.action_fragmentNutritionTracker_to_fragmentAddFood);
            binding.getViewModel().setCurrentMeal(Meals.LUNCH);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    //Public Methods
    public void addMeal(View view, Meals meal){
        binding.getViewModel().setCurrentMeal(meal);
        Navigation.findNavController(view).navigate(R.id.action_fragmentNutritionTracker_to_fragmentAddFood);
    }
}