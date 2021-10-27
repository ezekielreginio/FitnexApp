package com.pupccis.fitnex.api.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.pupccis.fitnex.activities.trainingdashboard.AddFitnessClass;
import com.pupccis.fitnex.model.DAO.FitnessClassDAO;
import com.pupccis.fitnex.model.FitnessClass;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.utilities.Constants.GlobalConstants;

import java.util.ArrayList;

public class FitnessClassAdapter extends RecyclerView.Adapter<FitnessClassAdapter.FitnessClassViewHolder>{

    private ArrayList<Object> fitnessClasses;
    private Context context;
    private FitnessClassDAO fitnessClassDAO;
    private String access_type;



    public FitnessClassAdapter(ArrayList<Object> fitnessClasses, Context context, String access_type){
        this.fitnessClasses = fitnessClasses;
        this.context = context;
        this.access_type = access_type;
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

        holder.setFitnessClassData((FitnessClass) fitnessClasses.get(position));

    }

    @Override
    public int getItemCount() {
        return fitnessClasses.size();
    }


    class FitnessClassViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layoutClassInfo;
        ConstraintLayout classContainer;
        TextView className, classTimeStart, classTimeEnd, classSessionNo, classTrainer, classDescription, category, classDuration;
        Button fitnessClassUpdate, fitnessClassDelete, fitnessClassJoin, fitnessClassView;
        public FitnessClassViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.textFitnessClassName);
            category = itemView.findViewById(R.id.textFitnessClassCategory);
            classTimeStart = itemView.findViewById(R.id.textTimeStart);
            classTimeEnd = itemView.findViewById(R.id.textTimeEnd);
            classSessionNo = itemView.findViewById(R.id.textClassSessionCount);
            classDescription = itemView.findViewById(R.id.textClassDescription);
//            classDuration = itemView.findViewById(R.id.textClassDuration);
//            classContainer = itemView.findViewById(R.id.layoutClassContainer);
//            layoutClassInfo = itemView.findViewById(R.id.layoutClassInfo);
//            fitnessClassUpdate = (Button) itemView.findViewById(R.id.buttonClassUpdate);
//            fitnessClassDelete = (Button) itemView.findViewById(R.id.buttonClassDelete);
//            fitnessClassJoin = itemView.findViewById(R.id.buttonClassJoin);
//            fitnessClassView = itemView.findViewById(R.id.buttonClassView);
        }
        void setFitnessClassData(FitnessClass fitnessClass){
            boolean clicked = false;

            className.setText(fitnessClass.getClassName());
            category.setText(GlobalConstants.KEY_CATEGORY_ARRAY[fitnessClass.getCategory()]);
            classTimeStart.setText(fitnessClass.getTimeStart());
            classTimeEnd.setText(fitnessClass.getTimeEnd());
            classSessionNo.setText(fitnessClass.getSessionNo());
            classDescription.setText(fitnessClass.getDescription());
            classDuration.setText(fitnessClass.getDuration());
            classContainer.setOnClickListener(view -> {
                if(layoutClassInfo.getVisibility()==View.GONE)
                    layoutClassInfo.setVisibility(View.VISIBLE);
                else
                    layoutClassInfo.setVisibility(View.GONE);
            });
            if(access_type==GlobalConstants.KEY_ACCESS_TYPE_VIEW){
                fitnessClassJoin.setVisibility(View.VISIBLE);
                fitnessClassView.setVisibility(View.VISIBLE);
                fitnessClassDelete.setVisibility(View.GONE);
                fitnessClassUpdate.setVisibility(View.GONE);
            }else if(access_type==GlobalConstants.KEY_ACCESS_TYPE_OWNER){
                fitnessClassJoin.setVisibility(View.GONE);
                fitnessClassView.setVisibility(View.GONE);
                fitnessClassDelete.setVisibility(View.VISIBLE);
                fitnessClassUpdate.setVisibility(View.VISIBLE);
            }


            fitnessClassUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(context, AddFitnessClass.class);
                    intent.putExtra("fitness", fitnessClass);
                    context.startActivity(intent);
                }
            });

            fitnessClassDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            switch (which){
//                                case DialogInterface.BUTTON_POSITIVE:
//                                    FitnessClassViewModel.deleteFitnessClass();
//                                    break;
//
//                                case DialogInterface.BUTTON_NEGATIVE:
//                                    break;
//                            }
//                        }
//                    };
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setMessage("Are you sure you wish to delete this fitness class?").setPositiveButton("Yes", dialogClickListener)
//                            .setNegativeButton("No", dialogClickListener).show();
                }
            });
        }
    }
}
