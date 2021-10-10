package com.pupccis.fitnex.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pupccis.fitnex.databinding.ActivityAddClassBinding;
import com.pupccis.fitnex.databinding.ItemContainerFitnessClassBinding;
import com.pupccis.fitnex.databinding.ItemContainerProgramBinding;
import com.pupccis.fitnex.model.FitnessClass;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.viewmodel.FitnessClassViewModel;

public class FitnessClassAdapter extends FirestoreRecyclerAdapter<FitnessClass, FitnessClassAdapter.FitnessClassHolder> {
    private FitnessClassViewModel fitnessClassViewModel = new FitnessClassViewModel();



    public FitnessClassAdapter(@NonNull FirestoreRecyclerOptions<FitnessClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FitnessClassHolder holder, int position, @NonNull FitnessClass model) {
        model.setClassID(this.getSnapshots().getSnapshot(position).getId());
        holder.setFitnessClassData(model);
        Log.e("Pumasok sa onBindViewHolder", model.getClassID());
    }

    @NonNull
    @Override
    public FitnessClassHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemContainerFitnessClassBinding binding = ItemContainerFitnessClassBinding.inflate(inflater, parent, false);
        binding.setViewModel(fitnessClassViewModel);
        return new FitnessClassHolder(binding);
    }

    public FitnessClassViewModel getViewModel(){return fitnessClassViewModel;}

    class FitnessClassHolder extends RecyclerView.ViewHolder{
        private ItemContainerFitnessClassBinding binding;

        public FitnessClassHolder(@NonNull ItemContainerFitnessClassBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void setFitnessClassData(FitnessClass model){
            binding.textFitnessClassName.setText(model.getClassName());
            binding.textFitnessClassCategory.setText(GlobalConstants.KEY_CATEGORY_ARRAY[model.getCategory()-1]);
            binding.textClassDescription.setText(model.getDescription());
            binding.textTimeStart.setText(model.getTimeStart());
            binding.textTimeEnd.setText(model.getTimeEnd());
            binding.textClassSessionCount.setText(model.getSessionNo());
            binding.textClassDuration.setText(model.getDuration());
            binding.setVariable(BR.fitnessClass, model);

            binding.layoutClassInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Layout", "Clicked");
                    if(binding.layoutClassInfo.getVisibility() == View.VISIBLE)
                        binding.layoutClassInfo.setVisibility(View.GONE);
                    else
                        binding.layoutClassInfo.setVisibility(View.VISIBLE);
                }
            });
        }

    }
}
