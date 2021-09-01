package com.pupccis.fitnex.API.adapter.VIewHolders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.pupccis.fitnex.Activities.Main.Trainer.AddProgram;
import com.pupccis.fitnex.Models.DAO.ProgramDAO;
import com.pupccis.fitnex.Models.Program;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;

public class ProgramViewHolder extends RecyclerView.ViewHolder{
    ConstraintLayout programContainer;
    LinearLayout layoutProgramInfo;
    TextView programName, programTrainees, programCategory, programDescription, programSessionCount, programDuration;
    Button programUpdate, programDelete;

    ProgramDAO programDAO = new ProgramDAO();

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
    }

    void setProgramData(Program program){
        boolean clicked = false;

        programName.setText(program.getName());
        programTrainees.setText(program.getTrainees());
        programCategory.setText(program.getCategory());
        programDescription.setText(program.getDescription());
        programSessionCount.setText(program.getSessionNumber());
        programDuration.setText(program.getDuration());
        Log.d("Category", program.getCategory());
        if(program.getCategory().equals(ProgramConstants.KEY_PROGRAM_CATEGORY_CARDIO)){
            programContainer.setBackgroundResource(R.drawable.layout_bg_program_cardio);
        }
        else if(program.getCategory().equals(ProgramConstants.KEY_PROGRAM_CATEGORY_STRENGTH)){
            programContainer.setBackgroundResource(R.drawable.layout_bg_program_strength);
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
                //Intent intent= new Intent(context, AddProgram.class);
                //intent.putExtra("program", program);
                //context.startActivity(intent);
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
                                programDAO.deleteProgram(program);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setMessage("Are you sure you wish to delete this program?").setPositiveButton("Yes", dialogClickListener)
//                        .setNegativeButton("No", dialogClickListener).show();
            }
        });



    }
}
