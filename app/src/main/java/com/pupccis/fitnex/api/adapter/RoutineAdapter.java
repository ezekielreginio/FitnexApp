package com.pupccis.fitnex.api.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.pupccis.fitnex.activities.routine.AddRoutine;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.model.Routine;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.viewmodel.RoutineViewModel;

import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder> {
    private List<Object> routines;
    private String access_type, category;
    private Context context;
    private Program program;

    public RoutineAdapter(List<Object> routines, String access_type, Program program, Context context) {
        this.routines = routines;
        this.access_type = access_type;
        this.program = program;
        this.context = context;
    }

    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        holder.setRoutineData((Routine) routines.get(position));
    }

    @Override
    public int getItemCount() {
        Log.d("size routine", routines.size()+"");
        return routines.size();
    }
    public void setList(List<Object> routineList){
        this.routines = routineList;
        notifyDataSetChanged();
    }
    class RoutineViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewRoutineName, textViewRoutineDescriptionFirst, textViewRoutineDescriptionSecond;
        private ConstraintLayout constraintLayoutEditDelete, constraintLayoutRoutineThumbnailEdit, constraintLayoutRoutineThumbnailDelete;

        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRoutineName = itemView.findViewById(R.id.textViewRoutineName);
            textViewRoutineDescriptionFirst = itemView.findViewById(R.id.textViewRoutineDescriptionFirst);
            textViewRoutineDescriptionSecond = itemView.findViewById(R.id.textViewRoutineDescriptionSecond);
            constraintLayoutEditDelete = itemView.findViewById(R.id.constraintLayoutEditDelete);
            constraintLayoutRoutineThumbnailEdit = itemView.findViewById(R.id.constraintLayoutRoutineThumbnailEdit);
            constraintLayoutRoutineThumbnailDelete = itemView.findViewById(R.id.constraintLayoutRoutineThumbnailDelete);
        }
        void setRoutineData(Routine routine){
            textViewRoutineName.setText(routine.getName());
            Log.d("Routine ID", routine.getId());
            if(program.getCategory() == 2){
                textViewRoutineDescriptionFirst.setText(routine.getReps()+" Reps");
                textViewRoutineDescriptionSecond.setText(routine.getSets()+ "Sets");
            }else{
                //textViewRoutineDescription1.setText(routine.getDuration());
                textViewRoutineDescriptionSecond.setVisibility(View.INVISIBLE);
            }
            constraintLayoutRoutineThumbnailEdit.setOnClickListener(view -> {
                Intent intent = new Intent(context, AddRoutine.class);
                intent.putExtra("program", program);
                intent.putExtra("routine", routine);
                context.startActivity(intent);

            });
            constraintLayoutRoutineThumbnailDelete.setOnClickListener(view -> {
                RoutineViewModel.deleteRoutine(routine, program.getProgramID());
            });
        }


    }

}