package com.pupccis.fitnex.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.databinding.ItemContainerTimerBinding;
import com.pupccis.fitnex.model.Routine;
import com.pupccis.fitnex.viewmodel.RoutineViewModel;

import java.util.List;

public class RoutineTrackerAdapter extends RecyclerView.Adapter<RoutineTrackerAdapter.ViewHolder>{

    private List<Routine> routineData;
    private RoutineViewModel routineViewModel;

    public RoutineTrackerAdapter(List<Routine> routine) {
        this.routineData = routine;
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
        Routine routine = routineData.get(position);
        holder.bind(routine, position);
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
        public void bind(Routine routine, int position){
            int pos = position+1;
            if(routine.isCompleted()){
                binding.textViewSetNumber.setText("\u2713");
            }
            else{
                binding.textViewSetNumber.setText(pos+"");
                binding.textViewWeight.setHint(routine.getWeight()+"");
                binding.textViewReps.setHint(routine.getReps()+"");
            }

            binding.setVariable(BR.position, position);

            binding.textViewReps.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(b){
                        binding.getViewModel().setCurrentRoutineID(routine.getId());
                        binding.getViewModel().setCurrentRoutinePosition(position);
                        Log.d("Routine ID", routine.getId());
                        Log.d("Current Position", position+"");
                    }

                }
            });

        }
    }
}
