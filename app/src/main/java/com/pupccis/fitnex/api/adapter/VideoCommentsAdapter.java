package com.pupccis.fitnex.api.adapter;

import static com.pupccis.fitnex.activities.videoplayer.TrainingVideoCommentReply.initiateReplyView;
import static com.pupccis.fitnex.model.DAO.VideoCommentDAO.setReplyCount;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.pupccis.fitnex.model.DAO.VideoCommentDAO;
import com.pupccis.fitnex.model.VideoComment;
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
        private ImageView buttonCommentLike, buttonCommentDislike, buttonCommentReply;
        private TextView commentUsername, commentDate, commentContent, commentLikesCounter, commentDislikesCounter;
        private ConstraintLayout constraintLayoutReplySection, constraintLayoutCommentReplyLink;
        private View itemView;

        public VideoCommentViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            buttonCommentLike = itemView.findViewById(R.id.buttonCommentLike);
            buttonCommentDislike = itemView.findViewById(R.id.buttonCommentDislike);
            commentUsername = itemView.findViewById(R.id.commentUsername);
            commentDate = itemView.findViewById(R.id.commentDate);
            commentContent = itemView.findViewById(R.id.commentContent);
            commentLikesCounter = itemView.findViewById(R.id.commentLikesCounter);
            commentDislikesCounter = itemView.findViewById(R.id.commentDislikesCounter);
            constraintLayoutCommentReplyLink = itemView.findViewById(R.id.constraintLayoutCommentReplyLink);
            buttonCommentReply = itemView.findViewById(R.id.buttonCommentReply);

            constraintLayoutCommentReplyLink.setOnClickListener(view -> {
                initiateReplyView(context, videoComment);
            });

            buttonCommentReply.setOnClickListener(view -> {
                initiateReplyView(context, videoComment);
            });

        }

        void setVideoCommentData(VideoComment videoComment){
            this.videoComment = videoComment;
            commentUsername.setText(videoComment.getTrainerName());
            commentContent.setText(videoComment.getComment());
            commentId = videoComment.getCommentId();

            //Set Reply Link
            setReplyCount(videoComment, itemView, FirebaseAuth.getInstance().getUid());

            String dateRelative = (String) DateUtils.getRelativeTimeSpanString(videoComment.getDateCreated());
            if(dateRelative.equals("0 minutes ago"))
                dateRelative = "A Few Seconds Ago";

            commentDate.setText(dateRelative);
            setReplyCount(videoComment, itemView, FirebaseAuth.getInstance().getUid());

            //Video Comment DAO Instance
            VideoCommentDAO videoCommentDAO = new VideoCommentDAO.VideoCommentDAOBuilder()
                    .commentId(videoComment.getCommentId())
                    .context(context)
                    .build();

            buttonCommentLike.setOnClickListener(view -> {
                videoCommentDAO.likeEventComment(videoComment, FirebaseAuth.getInstance().getUid(), true);
            });

            buttonCommentDislike.setOnClickListener(view -> {
                videoCommentDAO.likeEventComment(videoComment, FirebaseAuth.getInstance().getUid(), false);
            });
        }
    }
}
