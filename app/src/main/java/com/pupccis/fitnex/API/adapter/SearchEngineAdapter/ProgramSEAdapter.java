package com.pupccis.fitnex.API.adapter.SearchEngineAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.pupccis.fitnex.Models.Program;
import com.pupccis.fitnex.R;

import java.util.List;

public class ProgramSEAdapter extends RecyclerView.Adapter<ProgramSEAdapter.ProgramSEViewHolder> {
    private List<Program> programList;
    private Context context;

    public ProgramSEAdapter(List<Program> programList, Context context) {
        this.programList = programList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProgramSEViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProgramSEViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_program,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramSEViewHolder holder, int position) {
        holder.setProgramSEData(programList.get(position));
    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    class ProgramSEViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textCategory, textDescription, textSessionCount, textDuration;
        LinearLayout linearLayoutProgramInfo;
        ConstraintLayout constraintLayoutProgramContainer;
        Button buttonUpdate, buttonDelete, buttonJoin, buttonView;
        public ProgramSEViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.textProgramName);
            textCategory = itemView.findViewById(R.id.textProgramCategory);
            textDescription = itemView.findViewById(R.id.textProgramDescription);
            textSessionCount = itemView.findViewById(R.id.textProgramSessionCount);
            textDuration = itemView.findViewById(R.id.textProgramDuration);
            linearLayoutProgramInfo = itemView.findViewById(R.id.layoutProgramInfo);
            constraintLayoutProgramContainer = itemView.findViewById(R.id.layoutProgramContainer);
            buttonUpdate = itemView.findViewById(R.id.buttonProgramUpdate);
            buttonDelete = itemView.findViewById(R.id.buttonProgramDelete);
            buttonJoin = itemView.findViewById(R.id.buttonProgramJoin);
            buttonView = itemView.findViewById(R.id.buttonProgramView);
            constraintLayoutProgramContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (linearLayoutProgramInfo.getVisibility()==View.INVISIBLE){
                        linearLayoutProgramInfo.setVisibility(View.VISIBLE);
                    }
                    else {
                        linearLayoutProgramInfo.setVisibility(View.INVISIBLE);
                    }
                    buttonUpdate.setVisibility(View.GONE);
                    buttonDelete.setVisibility(View.GONE);
                    buttonJoin.setVisibility(View.VISIBLE);
                    buttonView.setVisibility(View.VISIBLE);
                    
                }
            });
        }
        void setProgramSEData(Program program){
            textName.setText(program.getName());
            textCategory.setText(program.getCategory());
            textDescription.setText(program.getDescription());
            textSessionCount.setText(program.getSessionNumber());
            textDuration.setText(program.getDuration());
        }
    }
}
