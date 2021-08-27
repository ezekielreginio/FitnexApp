package com.pupccis.fitnex.API.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.pupccis.fitnex.Activities.Main.Trainer.AddClass;
import com.pupccis.fitnex.Activities.Main.Trainer.AddProgram;
import com.pupccis.fitnex.Models.DAO.FitnessClassDAO;
import com.pupccis.fitnex.Models.FitnessClass;
import com.pupccis.fitnex.R;

import java.util.List;
import java.util.Map;

public class FitnessClassAdapter extends RecyclerView.Adapter<FitnessClassAdapter.FitnessClassViewHolder>{

    private List<FitnessClass> fitnessClasses;
    private Context context;
    private FitnessClassDAO fitnessClassDAO;

    public FitnessClassAdapter(List<FitnessClass> fitnessClasses, Context context){
        this.fitnessClasses = fitnessClasses;
        this.context = context;
    }

    @NonNull
    @Override
    public FitnessClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FitnessClassViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_fitness_class,
                        parent,
                        false
                )

        );
    }

    @Override
    public void onBindViewHolder(@NonNull FitnessClassViewHolder holder, int position) {
        holder.setFitnessClassData(fitnessClasses.get(position));
    }

    @Override
    public int getItemCount() {
        return fitnessClasses.size();
    }


    class FitnessClassViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layoutClassInfo;
        ConstraintLayout classContainer;
        TextView className, classTimeStart, classTimeEnd, classSessionNo, classTrainer, classDescription;
        Button fitnessClassUpdate, fitnessClassDelete;
        public FitnessClassViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.textClassCategory);
            classTimeStart = itemView.findViewById(R.id.textTimeStart);
            classTimeEnd = itemView.findViewById(R.id.textTimeEnd);
            classSessionNo = itemView.findViewById(R.id.textClassSessionCount);
            classDescription = itemView.findViewById(R.id.textClassDescription);
            classContainer = itemView.findViewById(R.id.layoutClassContainer);
            layoutClassInfo = itemView.findViewById(R.id.layoutClassInfo);
            fitnessClassUpdate = (Button) itemView.findViewById(R.id.buttonClassUpdate);
            fitnessClassDelete = (Button) itemView.findViewById(R.id.buttonClassDelete);
        }
        void setFitnessClassData(FitnessClass fitnessClass){
            boolean clicked = false;

            className.setText(fitnessClass.getClassName());
            classTimeStart.setText(fitnessClass.getTimeStart());
            classTimeEnd.setText(fitnessClass.getTimeEnd());
            classSessionNo.setText(fitnessClass.getSessionNo());
            classDescription.setText(fitnessClass.getDescription());
            classContainer.setOnClickListener(view -> {
                if(layoutClassInfo.getVisibility()==View.GONE)
                    layoutClassInfo.setVisibility(View.VISIBLE);
                else
                    layoutClassInfo.setVisibility(View.GONE);
            });

            fitnessClassUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(context, AddClass.class);
                    intent.putExtra("fitness", fitnessClass);
                    context.startActivity(intent);
                }
            });

            fitnessClassDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   fitnessClassDAO.deleteClass(fitnessClass);
                }
            });
        }
    }
}
