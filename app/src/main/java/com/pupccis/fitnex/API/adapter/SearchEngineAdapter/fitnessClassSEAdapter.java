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

import com.pupccis.fitnex.Models.FitnessClass;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.Constants.GlobalConstants;

import java.util.List;

public class fitnessClassSEAdapter extends RecyclerView.Adapter<fitnessClassSEAdapter.fitnessClassSEViewHolder>{
    private FitnessClass fitnessClass;
    private List<FitnessClass> fitnessClassList;
    private Context context;

    public fitnessClassSEAdapter(List<FitnessClass> fitnessClassList, Context context) {
        this.fitnessClassList = fitnessClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public fitnessClassSEViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new fitnessClassSEViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_fitness_class,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull fitnessClassSEViewHolder holder, int position) {
        holder.setClassSEData(fitnessClassList.get(position));
    }

    @Override
    public int getItemCount() {
        return fitnessClassList.size();
    }


    class fitnessClassSEViewHolder extends RecyclerView.ViewHolder{
        private TextView textName, textCategory, textDescription, textTimeStart, textTimeEnd, textSessionNumber, textDuration;
        private Button buttonUpdate, buttonDelete, buttonJoin, buttonView;
        private LinearLayout classInfo;
        private ConstraintLayout classContainer;
        public fitnessClassSEViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textFitnessClassName);
            textCategory = itemView.findViewById(R.id.textFitnessClassCategory);
            textDescription = itemView.findViewById(R.id.textClassDescription);
            textTimeStart = itemView.findViewById(R.id.textTimeStart);
            textTimeEnd = itemView.findViewById(R.id.textTimeEnd);
            textSessionNumber = itemView.findViewById(R.id.textClassSessionCount);
            textDuration = itemView.findViewById(R.id.textClassDuration);
        }

        void setClassSEData(FitnessClass fitnessClass){
            textName.setText(fitnessClass.getClassName());
            textCategory.setText(GlobalConstants.KEY_CATEGORY_ARRAY[fitnessClass.getCategory()]);
            textDescription.setText(fitnessClass.getDescription());
            textTimeStart.setText(fitnessClass.getTimeStart());
            textTimeEnd.setText(fitnessClass.getTimeEnd());
            textSessionNumber.setText(fitnessClass.getSessionNo());
            textDuration.setText(fitnessClass.getDuration());
        }
    }
}
