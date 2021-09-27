package com.pupccis.fitnex.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pupccis.fitnex.databinding.ActivityAddClassBinding;
import com.pupccis.fitnex.databinding.ItemContainerFitnessClassBinding;
import com.pupccis.fitnex.databinding.ItemContainerProgramBinding;
import com.pupccis.fitnex.model.FitnessClass;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.viewmodel.FitnessClassViewModel;

public class FitnessClassAdapter extends FirestoreRecyclerAdapter<FitnessClass, FitnessClassAdapter.FitnessClassHolder> {
    private ItemContainerFitnessClassBinding binding;
    private FitnessClassViewModel fitnessClassViewModel = new FitnessClassViewModel();



    public FitnessClassAdapter(@NonNull FirestoreRecyclerOptions<FitnessClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FitnessClassHolder holder, int position, @NonNull FitnessClass model) {
        Log.e("Pumasok sa onBindViewHolder", "Pumasok");
        model.setClassID(this.getSnapshots().getSnapshot(position).getId());
        holder.setFitnessClassData(model);
    }

    @NonNull
    @Override
    public FitnessClassHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        void setFitnessClassData(FitnessClass model){
            binding.textFitnessClassName.setText(model.getClassName());
        }

    }
}
