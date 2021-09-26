package com.pupccis.fitnex.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pupccis.fitnex.R;

public class ProgramAdapter extends FirestoreRecyclerAdapter<ProgramModel, ProgramAdapter.ProgramHolder> {

    public ProgramAdapter(@NonNull FirestoreRecyclerOptions<ProgramModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProgramHolder holder, int position, @NonNull ProgramModel model) {
        holder.textProgramName.setText(model.getName());
    }

    @NonNull
    @Override
    public ProgramHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_program, parent, false);

        return new ProgramHolder(v);
    }

    class ProgramHolder extends RecyclerView.ViewHolder{
        TextView textProgramName;
        public ProgramHolder(@NonNull View itemView) {
            super(itemView);
            textProgramName = itemView.findViewById(R.id.textProgramName);
        }
    }
}
