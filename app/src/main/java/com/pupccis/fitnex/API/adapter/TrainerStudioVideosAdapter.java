package com.pupccis.fitnex.API.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pupccis.fitnex.Models.PostVideo;
import com.pupccis.fitnex.R;

import java.util.List;

public class TrainerStudioVideosAdapter extends RecyclerView.Adapter<TrainerStudioVideosAdapter.TrainerStudioVideosViewHolder>{
    private List<PostVideo> postVideoList;

    @NonNull
    @Override
    public TrainerStudioVideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrainerStudioVideosViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_studio_video,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TrainerStudioVideosViewHolder holder, int position) {
        holder.setTrainerStudioVideosData(postVideoList.get(position));
    }

    @Override
    public int getItemCount() {
        return postVideoList.size();
    }


    class TrainerStudioVideosViewHolder extends RecyclerView.ViewHolder{
        TextView videoTitle;
        public TrainerStudioVideosViewHolder(@NonNull View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.textViewStudioVideoTitle);
        }

        void setTrainerStudioVideosData(PostVideo postVideo){
            videoTitle.setText(postVideo.getVideoTitle());
        }
    }

}
