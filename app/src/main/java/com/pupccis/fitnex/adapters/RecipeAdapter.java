package com.pupccis.fitnex.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pupccis.fitnex.databinding.ItemContainerRecipeBinding;
import com.pupccis.fitnex.model.Recipe;
import com.pupccis.fitnex.viewmodel.RecipeViewModel;

public class RecipeAdapter extends FirestoreRecyclerAdapter<Recipe, RecipeAdapter.RecipeHolder> {
    private RecipeViewModel recipeViewModel;

    public RecipeAdapter(@NonNull FirestoreRecyclerOptions<Recipe> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecipeHolder holder, int position, @NonNull Recipe model) {
        model.setRecipeID(this.getSnapshots().getSnapshot(position).getId());
        holder.setRecipeData(model);
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemContainerRecipeBinding binding = ItemContainerRecipeBinding.inflate(inflater, parent, false);
        binding.setViewModel(recipeViewModel);
        return new RecipeHolder(binding);
    }

    class RecipeHolder extends RecyclerView.ViewHolder {
        private ItemContainerRecipeBinding binding;

        public RecipeHolder(@NonNull ItemContainerRecipeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void setRecipeData(Recipe model){
        binding.textRecipeName.setText(model.getName());
        }
    }
}
