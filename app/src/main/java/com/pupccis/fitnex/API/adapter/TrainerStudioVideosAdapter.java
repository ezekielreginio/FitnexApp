package com.pupccis.fitnex.API.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pupccis.fitnex.Models.PostVideo;
import com.pupccis.fitnex.R;

import java.util.List;

public class TrainerStudioVideosAdapter extends RecyclerView.Adapter<TrainerStudioVideosAdapter.TrainerStudioVideosViewHolder>{
    private List<PostVideo> postVideoList;
    private Context context;

    public TrainerStudioVideosAdapter(List<PostVideo> postVideoList, Context context) {
        this.postVideoList = postVideoList;
        this.context = context;
    }

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
        ImageView videoThumbnail;
        ConstraintLayout studioVideoContainer;
        public TrainerStudioVideosViewHolder(@NonNull View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.textViewStudioVideoTitle);
            videoThumbnail = itemView.findViewById(R.id.imageViewVideoThumbnail);
            studioVideoContainer = itemView.findViewById(R.id.constraintLayoutStudioVideoContainer);
        }

        void setTrainerStudioVideosData(PostVideo postVideo){
            Log.d("URL", postVideo.getThumbnailURL());
            studioVideoContainer.setVisibility(View.GONE);
            Uri videoThumbnailUri = Uri.parse(postVideo.getThumbnailURL());
            videoTitle.setText(postVideo.getVideoTitle());
            //Glide.with(videoThumbnail.getContext()).load(postVideo.getThumbnailURL()).placeholder(R.drawable.gif_thumbnail_loading).into(videoThumbnail);
            Glide.with(videoThumbnail.getContext())
                    .load(postVideo.getThumbnailURL())
                    .placeholder(R.drawable.gif_thumbnail_loading)
                    .crossFade()
                    .into(videoThumbnail);
            studioVideoContainer.setVisibility(View.VISIBLE);
        }
    }

}
