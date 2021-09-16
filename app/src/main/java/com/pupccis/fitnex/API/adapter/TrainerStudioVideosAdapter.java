package com.pupccis.fitnex.API.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pupccis.fitnex.Activities.VideoPlayer.TrainingVideoPlayer;
import com.pupccis.fitnex.Model.PostVideo;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.Constants.GlobalConstants;

import java.util.ArrayList;
import java.util.List;

public class TrainerStudioVideosAdapter extends RecyclerView.Adapter<TrainerStudioVideosAdapter.TrainerStudioVideosViewHolder> {
    private ArrayList<PostVideo> postVideoList;
    private Context context;
    private String access_type;

    public TrainerStudioVideosAdapter(ArrayList<PostVideo> postVideoList, Context context, String access_type) {
        this.postVideoList = postVideoList;
        this.context = context;
        this.access_type = access_type;
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
        ConstraintLayout studioVideoContainer, constraintLayoutVideoThumbnailEdit, constraintLayoutVideoThumbnailDelete;
        LinearLayout linearLayoutAddVideoButton;
        public TrainerStudioVideosViewHolder(@NonNull View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.textViewStudioVideoTitle);
            videoThumbnail = itemView.findViewById(R.id.imageViewVideoThumbnail);
            studioVideoContainer = itemView.findViewById(R.id.constraintLayoutStudioVideoContainer);
            constraintLayoutVideoThumbnailEdit = itemView.findViewById(R.id.constraintLayoutVideoThumbnailEdit);
            constraintLayoutVideoThumbnailDelete = itemView.findViewById(R.id.constraintLayoutVideoThumbnailDelete);

        }

        void setTrainerStudioVideosData(PostVideo postVideo){
            if(access_type.equals(GlobalConstants.KEY_ACCESS_TYPE_VIEW)){
                constraintLayoutVideoThumbnailEdit.setVisibility(View.GONE);
                constraintLayoutVideoThumbnailDelete.setVisibility(View.GONE);


            }
            Log.d("URL", postVideo.getThumbnailURL());
            studioVideoContainer.setVisibility(View.GONE);
            Uri videoThumbnailUri = Uri.parse(postVideo.getThumbnailURL());
            videoTitle.setText(postVideo.getVideoTitle());
            //Glide.with(videoThumbnail.getContext()).load(postVideo.getThumbnailURL()).placeholder(R.drawable.gif_thumbnail_loading).into(videoThumbnail);
            Glide.with(videoThumbnail.getContext())
                    .load(postVideo.getThumbnailURL())
                    .into(videoThumbnail);
            studioVideoContainer.setVisibility(View.VISIBLE);

            studioVideoContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Video Url from adapter", postVideo.getVideoURL());
                    Intent intent= new Intent(context, TrainingVideoPlayer.class);
                    intent.putExtra("PostVideo", postVideo);
                    context.startActivity(intent);
                }
            });
        }
    }

}
