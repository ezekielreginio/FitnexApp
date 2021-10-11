package com.pupccis.fitnex.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.pupccis.fitnex.activities.videoconferencing.listeners.UsersListener;
import com.pupccis.fitnex.databinding.ItemContainerActiveTraineeBinding;
import com.pupccis.fitnex.model.RealtimeRoutine;
import com.pupccis.fitnex.model.User;

public class RealtimeRoutineAdapter extends FirebaseRecyclerAdapter<RealtimeRoutine, RealtimeRoutineAdapter.RealtimeRoutineHolder> {
    private UsersListener usersListener;
    public RealtimeRoutineAdapter(@NonNull FirebaseRecyclerOptions<RealtimeRoutine> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RealtimeRoutineHolder holder, int position, @NonNull RealtimeRoutine model) {
        model.setRoutineID(this.getSnapshots().getSnapshot(position).getKey());
        holder.bind(model);
    }

    @NonNull
    @Override
    public RealtimeRoutineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemContainerActiveTraineeBinding binding = ItemContainerActiveTraineeBinding.inflate(inflater, parent, false);

        return new RealtimeRoutineHolder(binding);
    }

    public void setUsersListener(UsersListener usersListener) {
        this.usersListener = usersListener;
    }

    class RealtimeRoutineHolder extends RecyclerView.ViewHolder{
        private ItemContainerActiveTraineeBinding binding;

        public RealtimeRoutineHolder(@NonNull ItemContainerActiveTraineeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(RealtimeRoutine model){
            User user = new User(model.getTraineeName(), model.getEmail(), model.getFcm_token());
            binding.textUsername.setText(model.getTraineeName());
            binding.imageAudioMeeting.setOnClickListener(v -> usersListener.initiateVideoMeeting(user));
        }
    }
}
