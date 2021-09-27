package com.pupccis.fitnex.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pupccis.fitnex.databinding.ItemContainerFitnessClassBinding;
import com.pupccis.fitnex.adapters.FitnessModel;
import com.pupccis.fitnex.viewmodel.FitnessClassViewModel;

public class FitnessClassAdapter extends FirestoreRecyclerAdapter<FitnessModel, FitnessClassAdapter.FitnessClassHolder> {
    private ItemContainerFitnessClassBinding binding;
    private FitnessClassViewModel fitnessClassViewModel = new FitnessClassViewModel();



    public FitnessClassAdapter(@NonNull FirestoreRecyclerOptions<FitnessModel> options) {
        super(options);
        Log.e("Pumasok sa Constructor", "Pumasok");
    }

    @Override
    protected void onBindViewHolder(@NonNull FitnessClassHolder holder, int position, @NonNull FitnessModel model) {
        Log.e("Pumasok sa onBindViewHolder", "Pumasok");
        model.setClassID(this.getSnapshots().getSnapshot(position).getId());
        holder.setFitnessClassData(model);
    }

    @NonNull
    @Override
    public FitnessClassHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("Pumasok sa onCreateViewHolder", "Pumasok");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemContainerFitnessClassBinding.inflate(inflater, parent, false);
        binding.setViewModel(fitnessClassViewModel);
        return new FitnessClassHolder(binding.getRoot());
    }

    public FitnessClassViewModel getViewModel(){return fitnessClassViewModel;}

    class FitnessClassHolder extends RecyclerView.ViewHolder{

        public FitnessClassHolder(@NonNull View itemView) {
            super(itemView);
        }
        void setFitnessClassData(FitnessModel model){
            binding.textFitnessClassName.setText(model.getClassName());
        }

    }
}
