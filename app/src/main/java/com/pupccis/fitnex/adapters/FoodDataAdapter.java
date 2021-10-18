package com.pupccis.fitnex.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pupccis.fitnex.databinding.ItemContainerAddFoodBinding;
import com.pupccis.fitnex.model.FoodData;

import java.util.List;

public class FoodDataAdapter extends RecyclerView.Adapter<FoodDataAdapter.ViewHolder>{
    private List<FoodData> foodDataList;

    public FoodDataAdapter(List<FoodData> foodDataList) {
        this.foodDataList = foodDataList;

    }

    @NonNull
    @Override
    public FoodDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CreateViewHolder", "Executed");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemContainerAddFoodBinding binding = ItemContainerAddFoodBinding.inflate(inflater, parent, false);
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
        }
    }
}
