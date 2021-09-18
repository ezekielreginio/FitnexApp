package com.pupccis.fitnex.API.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.pupccis.fitnex.Activities.Main.Trainer.AddProgram;
import com.pupccis.fitnex.Model.DAO.ProgramDAO;
import com.pupccis.fitnex.Model.Program;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.ViewModel.ProgramViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>{

    private ArrayList<Program> programs;
    private Context context;
    private ProgramDAO programDAO = new ProgramDAO();
    private String access_type;
    private ProgramViewModel programViewModel;

    public ProgramAdapter(ArrayList<Program> programs, Context context, ProgramViewModel programViewModel, String access_type){
        this.programs = programs;
        this.context = context;
        this.access_type = access_type;
        this.programViewModel = programViewModel;
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
        LinearLayout layoutProgramInfo;
        TextView programName, programTrainees, programCategory, programDescription, programSessionCount, programDuration;
        Button programUpdate, programDelete, programView, programJoin;

        public ProgramViewHolder(@NonNull View itemView) {
            super(itemView);
            programName = itemView.findViewById(R.id.textProgramName);
            programTrainees = itemView.findViewById(R.id.textProgramTrainees);
            programCategory = itemView.findViewById(R.id.textProgramCategory);
            programDescription = itemView.findViewById(R.id.textProgramDescription);
            programSessionCount = itemView.findViewById(R.id.textProgramSessionCount);
            programDuration = itemView.findViewById(R.id.textProgramDuration);

            programContainer = itemView.findViewById(R.id.layoutProgramContainer);
            layoutProgramInfo = itemView.findViewById(R.id.layoutProgramInfo);

            programUpdate = itemView.findViewById(R.id.buttonProgramUpdate);
            programDelete = itemView.findViewById(R.id.buttonProgramDelete);
            programJoin = itemView.findViewById(R.id.buttonProgramJoin);
            programView = itemView.findViewById(R.id.buttonProgramView);
        }

        void setProgramData(Program program){
            boolean clicked = false;
            programName.setText(program.getName());
            programTrainees.setText(program.getTrainees());
            programCategory.setText(GlobalConstants.KEY_CATEGORY_ARRAY[Integer.parseInt(program.getCategory())]);
            programDescription.setText(program.getDescription());
            programSessionCount.setText(program.getSessionNumber());
            programDuration.setText(program.getDuration());
            if(program.getCategory().equals(ProgramConstants.KEY_PROGRAM_CATEGORY_CARDIO)){
                programContainer.setBackgroundResource(R.drawable.layout_bg_program_cardio);
            }
            else if(program.getCategory().equals(ProgramConstants.KEY_PROGRAM_CATEGORY_STRENGTH)){
                programContainer.setBackgroundResource(R.drawable.layout_bg_program_strength);
            }
            if(access_type==GlobalConstants.KEY_ACCESS_TYPE_VIEW){
                programJoin.setVisibility(View.VISIBLE);
                programView.setVisibility(View.VISIBLE);
                programDelete.setVisibility(View.GONE);
                programUpdate.setVisibility(View.GONE);
            }else if(access_type==GlobalConstants.KEY_ACCESS_TYPE_OWNER){
                programJoin.setVisibility(View.GONE);
                programView.setVisibility(View.GONE);
                programDelete.setVisibility(View.VISIBLE);
                programUpdate.setVisibility(View.VISIBLE);
            }

            programContainer.setOnClickListener(view -> {
                if(layoutProgramInfo.getVisibility() == View.GONE )
                    layoutProgramInfo.setVisibility(View.VISIBLE);
                else
                    layoutProgramInfo.setVisibility(View.GONE);
            });

            programUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(context, AddProgram.class);
                    intent.putExtra("program", program);
                    context.startActivity(intent);
                }
            });

            programDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    programViewModel.deleteProgram(program.getProgramID());
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you wish to delete this program?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            });



        }
    }
}
