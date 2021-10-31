package com.pupccis.fitnex.activities.nutritiontracking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.FragmentNutritionTrackingBinding;

public class FragmentNutritionTracking extends Fragment implements View.OnClickListener {

    private FragmentNutritionTrackingBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nutrition_tracking, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setPresenter(this);
//        binding.executePendingBindings();

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if(view == binding.imageViewAddBreakfast){
            Navigation.findNavController(view).navigate(R.id.action_fragmentNutritionTracker_to_fragmentAddFood);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}