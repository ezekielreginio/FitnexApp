package com.pupccis.fitnex.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.ItemContainerAddFoodBinding;
import com.pupccis.fitnex.model.FoodData;
import com.pupccis.fitnex.viewmodel.NutritionTrackingViewModel;

import org.json.JSONArray;

import java.util.List;

public class FoodDataAdapter extends RecyclerView.Adapter<FoodDataAdapter.ViewHolder>{
    private List<FoodData> foodDataList;
    private NutritionTrackingViewModel viewModel;
    private NavController navController;
    private LifecycleOwner lifecycleOwner;
    private RequestQueue queue;
    private ConstraintLayout loadingScreen;

    public FoodDataAdapter(List<FoodData> foodDataList) {
        this.foodDataList = foodDataList;
    }

    public void setViewModel(NutritionTrackingViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setNavigation(NavController navController) {
        this.navController = navController;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) { this.lifecycleOwner = lifecycleOwner; }

    public void setQueue(RequestQueue queue) { this.queue = queue; }

    @NonNull
    @Override
    public FoodDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CreateViewHolder", "Executed");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemContainerAddFoodBinding binding = ItemContainerAddFoodBinding.inflate(inflater, parent, false);
        binding.setViewModel(viewModel);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodDataAdapter.ViewHolder holder, int position) {

        FoodData foodData = this.foodDataList.get(position);
        holder.bind(foodData);
    }

    @Override
    public int getItemCount() {return foodDataList.size(); }

    public void setLoadingScreen(ConstraintLayout constraintLayoutViewFoodProgressBar) {
        this.loadingScreen = constraintLayoutViewFoodProgressBar;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ItemContainerAddFoodBinding binding;
        public ViewHolder(@NonNull ItemContainerAddFoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(FoodData foodData) {
            binding.textViewFoodName.setText(foodData.getName());
            binding.textViewCalories.setText(foodData.getCalories()+"");
            binding.buttonAddFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadingScreen.setVisibility(View.VISIBLE);
                    binding.getViewModel().getServingInfo(queue, foodData.getFcdID()).observe(lifecycleOwner, new Observer<JSONArray>() {
                        @Override
                        public void onChanged(JSONArray arrayServingSize) {
                            Log.d("Serving Size", arrayServingSize.toString());
                            loadingScreen.setVisibility(View.GONE);
                            FoodData newFoodData = new FoodData.Builder(foodData).arrayServingSize(arrayServingSize).build();
                            binding.getViewModel().setCurrentFoodData(newFoodData);
                            navController.navigate(R.id.action_fragmentAddFood_to_fragmentFoodInfo);
                        }
                    });

                }
            });
        }
    }
}
