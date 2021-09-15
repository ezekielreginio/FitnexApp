package com.pupccis.fitnex.API.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.pupccis.fitnex.Activities.Routine.RoutinePage;
import com.pupccis.fitnex.Models.Routine;
import com.pupccis.fitnex.R;

import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>  {
    private List<Routine> routines;
    private String access_type, category;
    private View.OnDragListener dragListener;
    public RoutineAdapter(List<Routine> routines, String access_type, String category) {
        this.routines = routines;
        this.access_type = access_type;
        this.category = category;
    }

    @NonNull
    @Override
    public RoutineAdapter.RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RoutineViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_routines,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        holder.setRoutineData(routines.get(position));
    }

    public void setList(List<Routine> routineList){
        this.routines = routineList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return routines.size();
    }

    class RoutineViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewRoutineName, textViewRoutineDescriptionFirst, textViewRoutineDescriptionSecond;
        private ImageView RoutineDrag;
        private ConstraintLayout constraintLayoutEditDelete;
        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRoutineName = itemView.findViewById(R.id.textViewRoutineName);
            textViewRoutineDescriptionFirst = itemView.findViewById(R.id.textViewRoutineDescriptionFirst);
            textViewRoutineDescriptionSecond = itemView.findViewById(R.id.textViewRoutineDescriptionSecond);
            constraintLayoutEditDelete = itemView.findViewById(R.id.constraintLayoutEditDelete);

        }
        void setRoutineData(Routine routine){
            textViewRoutineName.setText(routine.getRoutineName());
            if(category.equals("2")){
                textViewRoutineDescriptionFirst.setText(routine.getReps()+" Reps");
                textViewRoutineDescriptionSecond.setText(routine.getSets()+ "Sets");
            }else{
                //textViewRoutineDescription1.setText(routine.getDuration());
                Log.d("Duration", ""+routine.getDuration());
                textViewRoutineDescriptionSecond.setVisibility(View.INVISIBLE);
            }
        }
    }
}
