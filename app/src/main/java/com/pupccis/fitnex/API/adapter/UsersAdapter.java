package com.pupccis.fitnex.API.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.User;
import com.pupccis.fitnex.video_conferencing.listeners.UsersListener;

import java.util.List;

public class UsersAdapter extends  RecyclerView.Adapter<UsersAdapter.UserViewHolder>{

    private List<User> users;
    private UsersListener usersListener;

    public UsersAdapter(List<User> users, UsersListener usersListener) {
        this.users = users;
        this.usersListener = usersListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_user,
                        parent,
                        false
                ));

    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

     class UserViewHolder extends  RecyclerView.ViewHolder{

        TextView textFirstChar, textUsername,  textEmail;
        ImageView imageAudioMeeting, imageVideoMeeting;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textFirstChar = itemView.findViewById(R.id.textFirstChar);
            textUsername = itemView.findViewById(R.id.textUsername);
            textEmail = itemView.findViewById(R.id.textEmail);
            imageVideoMeeting = itemView.findViewById(R.id.imageVideoMeeting);
            imageAudioMeeting = itemView.findViewById(R.id.imageAudioMeeting);
        }

        void setUserData(User user){
            textUsername.setText(user.getName());
            textEmail.setText(user.getEmail());

            imageAudioMeeting.setOnClickListener(v -> usersListener.initiateAudioMeeting(user));
            imageVideoMeeting.setOnClickListener(v -> usersListener.initiateVideoMeeting(user));
//            imageAudioMeeting.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    usersListener.initiateAudioMeeting(user);
//                }
//            });
//            imageVideoMeeting.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    usersListener.initiateVideoMeeting(user);
//                }
//            });
        }
    }
}

