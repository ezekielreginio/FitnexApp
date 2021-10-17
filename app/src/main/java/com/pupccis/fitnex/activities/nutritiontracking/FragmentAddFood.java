package com.pupccis.fitnex.activities.nutritiontracking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.FragmentAddFoodBinding;


public class FragmentAddFood extends Fragment {
    private FragmentAddFoodBinding binding;

    public FragmentAddFood() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_food, container, false);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        return binding.getRoot();
    }
}