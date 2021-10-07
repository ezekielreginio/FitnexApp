package com.pupccis.fitnex.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.databinding.ItemContainerFitnessClassBinding;
import com.pupccis.fitnex.databinding.ItemContainerProgramBinding;
import com.pupccis.fitnex.databinding.ItemContainerRoutinesBinding;
import com.pupccis.fitnex.model.Routine;
import com.pupccis.fitnex.viewmodel.RoutineViewModel;

public class RoutineAdapter extends FirestoreRecyclerAdapter<Routine, RoutineAdapter.RoutineHolder> {
    private static ItemContainerRoutinesBinding binding;
    private RoutineViewModel routineViewModel = new RoutineViewModel();

    public RoutineAdapter(@NonNull FirestoreRecyclerOptions<Routine> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RoutineHolder holder, int position, @NonNull Routine model) {
        model.setRoutineID(this.getSnapshots().getSnapshot(position).getId());
        holder.setRoutineData(model);
    }

    @NonNull
    @Override
    public RoutineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemContainerRoutinesBinding.inflate(inflater, parent, false);
        binding.setViewModel(routineViewModel);
        return new RoutineAdapter.RoutineHolder(binding.getRoot());
    }

    //    public RoutineAdapter(@NonNull FirestoreRecyclerAdapter<Routine> options) {
//        super(options);
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull RoutineHolder holder, int position, @NonNull Routine model) {
//        model.setRoutineID(this.getSnapshots().getSnapshot(position).getId());
//        holder.setRoutineData(model);
//    }
//
//    @NonNull
//    @Override
//    public RoutineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        binding = ItemContainerRoutinesBinding.inflate(inflater, parent, false);
//        binding.setViewModel(routineViewModel);
//        return new RoutineAdapter.RoutineHolder(binding.getRoot());
//    }
//
    public RoutineViewModel getViewModel() {
        return routineViewModel;
    }
//
    public class RoutineHolder extends RecyclerView.ViewHolder {
        public RoutineHolder(@NonNull View itemView) {
            super(itemView);
        }
        void setRoutineData(Routine model){
            binding.textViewRoutineName.setText(model.getName());
            binding.setVariable(BR.routine, model);
        }

    }
}
