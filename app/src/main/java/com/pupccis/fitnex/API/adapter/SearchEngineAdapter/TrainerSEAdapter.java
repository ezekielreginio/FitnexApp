package com.pupccis.fitnex.API.adapter.SearchEngineAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pupccis.fitnex.Models.DAO.FitnessClassDAO;
import com.pupccis.fitnex.Models.FitnessClass;
import com.pupccis.fitnex.Models.User;
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

        public TrainerSEViewHolder(@NonNull View itemView){
            super(itemView);
            textName = itemView.findViewById(R.id.searchEngineTrainerName);
            textEmail = itemView.findViewById(R.id.searchEngineTrainerEmail);
        }
        void setTrainerSEData(User users){
            textName.setText(users.getName());
            textEmail.setText(users.getEmail());
        }


    }
}
