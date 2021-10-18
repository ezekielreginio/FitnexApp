package com.pupccis.fitnex.activities.nutritiontracking;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.adapters.FoodDataAdapter;
import com.pupccis.fitnex.databinding.FragmentAddFoodBinding;
import com.pupccis.fitnex.handlers.view.WrapContentLinearLayoutManager;
import com.pupccis.fitnex.model.FoodData;
import com.pupccis.fitnex.viewmodel.NutritionTrackingViewModel;

import java.util.List;


public class FragmentAddFood extends Fragment{
    private static FragmentAddFoodBinding binding;
    private static RequestQueue queue;

    private static Context context;

    public FragmentAddFood() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        NutritionTrackingViewModel nutritionTrackingViewModel = ((NutritionTrackingMain)getActivity()).getNutritionTrackingViewModel();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_food, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(nutritionTrackingViewModel);
        binding.executePendingBindings();
        binding.recyclerViewFoodQuery.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        queue = Volley.newRequestQueue(getActivity());

        context = getContext();

        return binding.getRoot();
    }

    @BindingAdapter({"foodSearch"})
    public static void searchFood(View view, String queryFood){
        if(queryFood != null){
            Log.d("Food Query", queryFood);
            binding.getViewModel().getFoodResults(queue, queryFood).observe(binding.getLifecycleOwner(), new Observer<List<FoodData>>() {
                @Override
                public void onChanged(List<FoodData> foodDataList) {
                    for(FoodData data : foodDataList){
                        Log.d("Food Data", data.getName());
                        Log.d("Calories", data.getCalories()+"");
                    }
                    FoodDataAdapter adapter = new FoodDataAdapter(foodDataList);
                    binding.recyclerViewFoodQuery.setAdapter(adapter);
                    binding.recyclerViewFoodQuery.setLayoutManager(new WrapContentLinearLayoutManager(getAppContext(), LinearLayoutManager.VERTICAL, false));
                }
            });
        }
    }

    public static Context getAppContext() {
        return FragmentAddFood.context;
    }
}