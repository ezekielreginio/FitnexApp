package com.pupccis.fitnex.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.ItemContainerProgramBinding;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.viewmodel.ProgramViewModel;

import timber.log.Timber;

public class ProgramAdapter extends FirestoreRecyclerAdapter<Program, ProgramAdapter.ProgramHolder> {

    private ItemContainerProgramBinding binding;
    private ProgramViewModel programViewModel = new ProgramViewModel();
    public ProgramAdapter(@NonNull FirestoreRecyclerOptions<Program> options) {
        super(options);
    }

    public ProgramViewModel getViewModel(){
        return programViewModel;
    }

    @Override
    protected void onBindViewHolder(@NonNull ProgramHolder holder, int position, @NonNull Program model) {
        model.setProgramID(this.getSnapshots().getSnapshot(position).getId());
        holder.setProgramData(model);
    }

    @NonNull
    @Override
    public ProgramHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // DataBindingUtil.inflate(inflater, R.layout.fragment_programs, container, false);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemContainerProgramBinding.inflate(inflater, parent, false);
        binding.setViewModel(programViewModel);
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_program, parent, false);

        return new ProgramHolder(binding.getRoot());
    }

    class ProgramHolder extends RecyclerView.ViewHolder{
        public ProgramHolder(@NonNull View itemView) {
            super(itemView);
        }
        void setProgramData(Program model){
            binding.textProgramName.setText(model.getName());
            binding.textProgramDescription.setText(model.getDescription());
            binding.textProgramSessionCount.setText(model.getSessionNumber());
            binding.textProgramDuration.setText(model.getDuration());
            Log.d("Program ID from model", model.getProgramID());
            binding.setVariable(BR.program, model);
        }
    }
}
