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
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.ActivityAddClassBinding;
import com.pupccis.fitnex.databinding.ItemContainerFitnessClassBinding;
import com.pupccis.fitnex.databinding.ItemContainerProgramBinding;
import com.pupccis.fitnex.model.FitnessClass;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.viewmodel.FitnessClassViewModel;
import com.pupccis.fitnex.api.enums.AccessType;

public class FitnessClassAdapter extends FirestoreRecyclerAdapter<FitnessClass, FitnessClassAdapter.FitnessClassHolder> {
    private FitnessClassViewModel fitnessClassViewModel = new FitnessClassViewModel();
    private AccessType accessType;


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
    public void setFitnessClassViewModel(FitnessClassViewModel fitnessClassViewModel){
        this.fitnessClassViewModel = fitnessClassViewModel;
    }

    public void setAccesstype(AccessType accessType) {
        this.accessType = accessType;
    }

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
            binding.textClassDuration.setText(model.getDuration()+" minutes");
            binding.setVariable(BR.fitnessClass, model);
//            switch (accessType){
//                case VIEW:
//                    binding.layoutClassButtonViewer.setVisibility(View.VISIBLE);
//                    binding.layoutClassButtonOwner.setVisibility(View.GONE);
//                    break;
//            }
            binding.layoutFitnessClassContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (binding.layoutfitnessClassInfo.getVisibility()) {
                        case View.GONE:
                            binding.layoutfitnessClassInfo.setVisibility(View.VISIBLE);
                            binding.imageViewExpand.setImageResource(R.drawable.ic_expand_more);
//                            notifyItemChanged(getAbsoluteAdapterPosition());
                            break;
                        case View.VISIBLE:
                            binding.layoutfitnessClassInfo.setVisibility(View.GONE);
                            binding.imageViewExpand.setImageResource(R.drawable.ic_expand_less);
                            break;
                    }


                }
            });

        }
    }

}
