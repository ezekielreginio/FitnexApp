package com.pupccis.fitnex.API.adapter;

import static com.pupccis.fitnex.Activities.VideoPlayer.TrainingVideoCommentReply.initiateReplyView;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.pupccis.fitnex.Models.VideoComment;
import com.pupccis.fitnex.R;

import java.util.List;

public class VideoCommentsAdapter extends RecyclerView.Adapter<VideoCommentsAdapter.VideoCommentViewHolder> {
    private List<VideoComment> videoComments;
    private Context context;

    public VideoCommentsAdapter(List<VideoComment> videoComments, Context context) {
        this.videoComments = videoComments;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoCommentViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_comment,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull VideoCommentViewHolder holder, int position) {
        holder.setVideoCommentData(videoComments.get(position));
    }

    @Override
    public int getItemCount() {
        return videoComments.size();
    }

    class VideoCommentViewHolder extends RecyclerView.ViewHolder{
        private VideoComment videoComment;
        private String commentId;
        private ImageView buttonCommentLike, buttonCommentDislike;
        private TextView commentUsername, commentDate, commentContent, commentLikesCounter, commentDislikesCounter;
        private ConstraintLayout constraintLayoutReplySection, constraintLayoutCommentReplyLink;

        public VideoCommentViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonCommentLike = itemView.findViewById(R.id.buttonCommentLike);
            buttonCommentDislike = itemView.findViewById(R.id.buttonCommentDislike);
            commentUsername = itemView.findViewById(R.id.commentUsername);
            commentDate = itemView.findViewById(R.id.commentDate);
            commentContent = itemView.findViewById(R.id.commentContent);
            commentLikesCounter = itemView.findViewById(R.id.commentLikesCounter);
            commentDislikesCounter = itemView.findViewById(R.id.commentDislikesCounter);
            constraintLayoutCommentReplyLink = itemView.findViewById(R.id.constraintLayoutCommentReplyLink);


            constraintLayoutCommentReplyLink.setOnClickListener(view -> {
                initiateReplyView(context, videoComment);
            });
        }

        void setVideoCommentData(VideoComment videoComment){
            this.videoComment = videoComment;
            commentUsername.setText(videoComment.getTrainerName());
            commentContent.setText(videoComment.getComment());
            commentId = videoComment.getCommentId();

            String dateRelative = (String) DateUtils.getRelativeTimeSpanString(videoComment.getDateCreated());
            if(dateRelative.equals("0 minutes ago"))
                dateRelative = "A Few Seconds Ago";

            commentDate.setText(dateRelative);
            //commentId = videoComment.get
        }
    }
}
