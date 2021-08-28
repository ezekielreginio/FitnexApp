package com.pupccis.fitnex.API.adapter;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.pupccis.fitnex.Activities.Main.Trainer.TrainerDashboard;
import com.pupccis.fitnex.Activities.VideoConferencing.listeners.UsersListener;
import com.pupccis.fitnex.Models.Program;
import com.pupccis.fitnex.R;

import java.util.List;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>{

    private List<Program> programs;

    public ProgramAdapter(List<Program> programs){
        this.programs = programs;
    }

    @NonNull
    @Override
    public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProgramViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_program,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramViewHolder holder, int position) {
        holder.setProgramData(programs.get(position));
    }

    @Override
    public int getItemCount() {
        return programs.size();
    }

    class ProgramViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout programContainer;
        TextView programName, programTrainees;
        public ProgramViewHolder(@NonNull View itemView) {
            super(itemView);
            programName = itemView.findViewById(R.id.textProgramName);
            programTrainees = itemView.findViewById(R.id.textProgramTrainees);
            programContainer = itemView.findViewById(R.id.layoutProgramContainer);
        }

        void setProgramData(Program program){
            programName.setText(program.getName());
            programTrainees.setText(program.getTrainees());
            programContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Toast", "click");
                }
            });

        }
    }
}
