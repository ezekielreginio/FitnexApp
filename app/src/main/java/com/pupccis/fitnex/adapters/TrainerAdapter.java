package com.pupccis.fitnex.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pupccis.fitnex.databinding.ItemContainerSearchEngineTrainerBinding;
import com.pupccis.fitnex.model.User;

public class TrainerAdapter extends FirestoreRecyclerAdapter<User, TrainerAdapter.TrainerHolder> {
    private ItemContainerSearchEngineTrainerBinding binding;
    public TrainerAdapter(@NonNull FirestoreRecyclerOptions<User> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull TrainerHolder holder, int position, @NonNull User model) {
        holder.setTrainerData(model);
    }

    @NonNull
    @Override
    public TrainerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemContainerSearchEngineTrainerBinding.inflate(inflater, parent, false);

        return new TrainerHolder(binding.getRoot());
    }

    class TrainerHolder extends RecyclerView.ViewHolder{

        public TrainerHolder(@NonNull View itemView) {
            super(itemView);
        }
        void setTrainerData(User model){
            binding.searchEngineTrainerName.setText(model.getName());
            binding.searchEngineTrainerEmail.setText(model.getEmail());
        }
    }
}
