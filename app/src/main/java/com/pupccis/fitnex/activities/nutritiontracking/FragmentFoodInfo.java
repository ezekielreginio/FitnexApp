package com.pupccis.fitnex.activities.nutritiontracking;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.google.android.material.progressindicator.BaseProgressIndicator;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.FragmentFoodInfoBinding;
import com.pupccis.fitnex.model.FoodData;
import com.pupccis.fitnex.utilities.Constants.nutritionTrackingConstants.MacroNutrients;
import com.pupccis.fitnex.viewmodel.NutritionTrackingViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FragmentFoodInfo extends Fragment implements View.OnClickListener{
    private static FragmentFoodInfoBinding binding;

    public FragmentFoodInfo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        NutritionTrackingViewModel nutritionTrackingViewModel = ((NutritionTrackingMain)getActivity()).getNutritionTrackingViewModel();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_info, container, false);
        binding.setViewModel(nutritionTrackingViewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setPresenter(this);
        binding.executePendingBindings();
        binding.getViewModel().initializeFoodData();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, binding.getViewModel().getServingList());
        binding.dropdownServingList.setAdapter(adapter);
        binding.dropdownServingList.setDropDownBackgroundResource(R.color.whiteTextColor);
        binding.dropdownServingList.setText(binding.dropdownServingList.getAdapter().getItem(0).toString(), false);
        binding.dropdownServingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Item Selected", binding.dropdownServingList.getAdapter().getItem(i).toString());
            }
        });
        return binding.getRoot();
    }

    //Binding Adapters
    @SuppressLint("NewApi")
    @BindingAdapter({"macroNutrients"})
    public static void setMacroProgressBars(View view, HashMap<MacroNutrients, Integer> macroNutrients){
        if(macroNutrients != null){
            binding.circularProgressIndicatorCarbs.setProgress(macroNutrients.get(MacroNutrients.CARBS), true);
            binding.circularProgressIndicatorProtein.setProgress(macroNutrients.get(MacroNutrients.PROTEIN), true);
            binding.circularProgressIndicatorFats.setProgress(macroNutrients.get(MacroNutrients.FATS), true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        if(view == binding.buttonTracKFood){
            binding.getViewModel().trackFood().observe(binding.getLifecycleOwner(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if(aBoolean!= null){
                        if(aBoolean){
                            Navigation.findNavController(view).navigate(R.id.action_fragmentFoodInfo_to_fragmentAddFood);
                        }
                    }
                }
            });
        }
    }
}