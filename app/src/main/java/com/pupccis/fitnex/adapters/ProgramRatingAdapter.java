package com.pupccis.fitnex.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pupccis.fitnex.databinding.ItemContainerRatingBinding;
import com.pupccis.fitnex.model.ProgramRating;

public class ProgramRatingAdapter extends FirestoreRecyclerAdapter<ProgramRating, ProgramRatingAdapter.ProgramRatingHolder> {
    public ProgramRatingAdapter(@NonNull FirestoreRecyclerOptions<ProgramRating> options) {
        super(options);
        Log.e("Adapter", "executed");
    }

    @Override
    protected void onBindViewHolder(@NonNull ProgramRatingAdapter.ProgramRatingHolder holder, int position, @NonNull ProgramRating model) {
        Log.e("on Bind", "executed");
        holder.bind(model);
    }

    @NonNull
    @Override
    public ProgramRatingAdapter.ProgramRatingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("on Create", "executed");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemContainerRatingBinding binding = ItemContainerRatingBinding.inflate(inflater, parent, false);

        return new ProgramRatingHolder(binding);
    }

    public class ProgramRatingHolder extends RecyclerView.ViewHolder{
        ItemContainerRatingBinding binding;

        public ProgramRatingHolder(ItemContainerRatingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ProgramRating model){
            binding.textViewRatingDescription.setText(model.getRatingDescription());
            binding.textViewRatingUsername.setText(model.getUserName());
        }
    }
}
