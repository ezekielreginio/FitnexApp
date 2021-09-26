package com.pupccis.fitnex.api.adapter.SearchEngineAdapter;

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

import com.pupccis.fitnex.activities.main.trainer.Studio.TrainerStudio;
import com.pupccis.fitnex.model.DAO.FitnessClassDAO;
import com.pupccis.fitnex.model.User;
import com.pupccis.fitnex.R;

import java.util.List;

public class TrainerSEAdapter extends RecyclerView.Adapter<TrainerSEAdapter.TrainerSEViewHolder>{
    private List<User> userList;
    private Context context;
    private FitnessClassDAO fitnessClassDAO = new FitnessClassDAO();

    public TrainerSEAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public TrainerSEViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrainerSEViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_search_engine_trainer,
                        parent,
                        false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TrainerSEViewHolder holder, int position) {
        holder.setTrainerSEData(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class TrainerSEViewHolder extends RecyclerView.ViewHolder{
        TextView textName, textEmail;
        ConstraintLayout constraintLayoutTrainerContainer;
        public TrainerSEViewHolder(@NonNull View itemView){
            super(itemView);
            textName = itemView.findViewById(R.id.searchEngineTrainerName);
            textEmail = itemView.findViewById(R.id.searchEngineTrainerEmail);
            constraintLayoutTrainerContainer = itemView.findViewById(R.id.layoutTrainerContainer);

        }
        void setTrainerSEData(User user){
            textName.setText(user.getName());
            textEmail.setText(user.getEmail());

            constraintLayoutTrainerContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TrainerStudio.class);
                    intent.putExtra("trainer_id", user.getUserID());
                    intent.putExtra("access_type", "view");

                    Log.d("User ID", user.getUserID());
                    context.startActivity(intent);
                }
            });
        }


    }
}
