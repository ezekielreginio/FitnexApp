package com.pupccis.fitnex.adapters;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.api.enums.LikeType;
import com.pupccis.fitnex.model.VideoComment;
import com.pupccis.fitnex.viewmodel.PostVideoViewModel;

import java.util.HashMap;
import java.util.List;

public class VideoCommentAdapter extends FirestoreRecyclerAdapter<VideoComment, VideoCommentAdapter.VideoCommentHolder> {

    //Private Attributes
    private PostVideoViewModel postVideoViewModel;
    private LifecycleOwner lifecycleOwner;

    public VideoCommentAdapter(@NonNull FirestoreRecyclerOptions<VideoComment> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull VideoCommentHolder holder, int position, @NonNull VideoComment model) {
        model.setCommentId(this.getSnapshots().getSnapshot(position).getId());
        holder.setVideoCommentData(model);
    }

    @NonNull
    @Override
    public VideoCommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("Comment Create View", "Triggered");
        return new VideoCommentAdapter.VideoCommentHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_comment,
                        parent,
                        false
                )
        );
    }

    public void setPostVideoViewModel(PostVideoViewModel postVideoViewModel) {
        this.postVideoViewModel = postVideoViewModel;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    class VideoCommentHolder extends RecyclerView.ViewHolder{
        TextView commentUsername, commentContent, commentDate, commentLikesCounter, commentDislikesCounter, commentReplyCounter;
        ImageView buttonCommentLike, buttonCommentDislike, buttonCommentReply;
        ConstraintLayout constraintLayoutCommentReplyLink;
        public VideoCommentHolder(@NonNull View itemView) {
            super(itemView);
            commentUsername = itemView.findViewById(R.id.commentContainerUsername);
            commentContent = itemView.findViewById(R.id.commentContainerContent);
            commentDate = itemView.findViewById(R.id.commentContainerDate);
            commentLikesCounter = itemView.findViewById(R.id.commentContainerLikesCounter);
            commentDislikesCounter = itemView.findViewById(R.id.commentContainerDislikesCounter);
            commentReplyCounter = itemView.findViewById(R.id.commentReplyCounter);

            buttonCommentLike = itemView.findViewById(R.id.buttonCommentLike);
            buttonCommentDislike = itemView.findViewById(R.id.buttonCommentDislike);
            buttonCommentReply = itemView.findViewById(R.id.buttonCommentReply);

            constraintLayoutCommentReplyLink = itemView.findViewById(R.id.constraintLayoutCommentReplyLink);
        }

        void setVideoCommentData(VideoComment model){
            PostVideoViewModel viewModel = new PostVideoViewModel();
            String dateRelative = (String) DateUtils.getRelativeTimeSpanString(model.getDateCreated());
            List<String> likes = model.getLikes();
            List<String> dislikes = model.getDislikes();
            int likesCtr = 0;
            int dislikesCtr = 0;
            boolean isLiked = false;
            boolean isDisliked = false;

            if(likes != null){
                likesCtr = likes.size();
                if (likes.contains(FirebaseAuth.getInstance().getUid())){
                    isLiked = true;
                }
            }

            if(dislikes != null){
                dislikesCtr = dislikes.size();
                if (dislikes.contains(FirebaseAuth.getInstance().getUid())){
                    isDisliked = true;
                }
            }

            if(dateRelative.equals("0 minutes ago"))
                dateRelative = "A Few Seconds Ago";
            commentUsername.setText(model.getUserName());
            commentContent.setText(model.getComment());
            commentDate.setText(dateRelative);
            commentLikesCounter.setText(likesCtr+"");
            commentDislikesCounter.setText(dislikesCtr+"");
            if(isLiked)
                buttonCommentLike.setImageResource(R.drawable.ic_baseline_thumb_up_24);
            else
                buttonCommentLike.setImageResource(R.drawable.ic_outline_thumb_up_alt_24);
            if(isDisliked)
                buttonCommentDislike.setImageResource(R.drawable.ic_baseline_thumb_down_24);
            else
                buttonCommentDislike.setImageResource(R.drawable.ic_outline_thumb_down_alt_24);

            //Observers
            viewModel.videoCommentCounterObserver(model).observe(lifecycleOwner, new Observer<HashMap<String, Object>>() {
                @Override
                public void onChanged(HashMap<String, Object> map) {
                    Integer replyCtr =(Integer) map.get("replyCount");
                    String commentID = map.get("commentID").toString();
                    if(replyCtr != 0 && replyCtr != null && commentID.equals(model.getCommentId())){
                        Log.d("Comment ID", model.getCommentId());
                        constraintLayoutCommentReplyLink.setVisibility(View.VISIBLE);
                        commentReplyCounter.setText(replyCtr+"");
                    }
                    else
                        constraintLayoutCommentReplyLink.setVisibility(View.GONE);
                }
            });

            buttonCommentLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    postVideoViewModel.likeComment(model, LikeType.LIKE);
                }
            });

            buttonCommentDislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    postVideoViewModel.likeComment(model, LikeType.DISLIKE);
                }
            });

            constraintLayoutCommentReplyLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    postVideoViewModel.triggerReplyClickObserver(model);
                }
            });

            buttonCommentReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    postVideoViewModel.triggerReplyClickObserver(model);
                }
            });


        }
    }
}
