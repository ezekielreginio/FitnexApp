package com.pupccis.fitnex.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.ItemContainerAddFoodBinding;
import com.pupccis.fitnex.model.FoodData;
import com.pupccis.fitnex.viewmodel.NutritionTrackingViewModel;

import java.util.List;

public class FoodDataAdapter extends RecyclerView.Adapter<FoodDataAdapter.ViewHolder>{
    private List<FoodData> foodDataList;
    private NutritionTrackingViewModel viewModel;
    private NavController navController;

    public FoodDataAdapter(List<FoodData> foodDataList) {
        this.foodDataList = foodDataList;
    }

    public void setViewModel(NutritionTrackingViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setNavigation(NavController navController) {
        this.navController = navController;
    }

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
                    binding.getViewModel().setCurrentFoodData(foodData);
                    navController.navigate(R.id.action_fragmentAddFood_to_fragmentFoodInfo);
                }
            });
        }
    }
}
