package com.pupccis.fitnex.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.databinding.ItemContainerTimerBinding;
import com.pupccis.fitnex.model.Routine;
import com.pupccis.fitnex.model.RoutineData;
import com.pupccis.fitnex.viewmodel.RoutineViewModel;

import java.util.List;

public class RoutineTrackerAdapter extends RecyclerView.Adapter<RoutineTrackerAdapter.ViewHolder>{

    private List<RoutineData> routineData;
    private Routine initRoutine;
    private RoutineViewModel routineViewModel;

    public RoutineTrackerAdapter(List<RoutineData> routineData, Routine initRoutine) {
        this.routineData = routineData;
        this.initRoutine = initRoutine;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemContainerTimerBinding binding = ItemContainerTimerBinding.inflate(inflater, parent, false);
        binding.setViewModel(getRoutineViewModel());
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RoutineData routineData = this.routineData.get(position);
        holder.bind(routineData, position);
    }

    @Override
    public int getItemCount() {
        return routineData.size();
    }

    public RoutineViewModel getRoutineViewModel() {
        return routineViewModel;
    }

    public void setRoutineViewModel(RoutineViewModel routineViewModel) {
        this.routineViewModel = routineViewModel;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ItemContainerTimerBinding binding;
        public ViewHolder(@NonNull ItemContainerTimerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(RoutineData routineData, int position){

            if(routineData.isCompleted()){
                binding.textViewSetNumber.setText("\u2713");
                binding.textViewWeight.setText(routineData.getWeight()+"");
                binding.textViewReps.setText(routineData.getReps()+"");

            }
            else{
                int pos = position+1;
                binding.textViewSetNumber.setText(pos+"");
                binding.textViewWeight.setHint(routineData.getWeight()+"");
                binding.textViewReps.setHint(routineData.getReps()+"");
            }

            binding.getViewModel().resetRoutineTracker();

            binding.textViewWeight.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    binding.getViewModel().setRoutineWeight(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            binding.textViewReps.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    binding.getViewModel().setRoutineReps(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
        }
    }
}
