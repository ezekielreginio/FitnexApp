package com.pupccis.fitnex.adapters;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.databinding.ItemContainerCommentBinding;
import com.pupccis.fitnex.model.VideoComment;
import com.pupccis.fitnex.utilities.Constants.VideoCommentConstants;
import com.pupccis.fitnex.viewmodel.PostVideoViewModel;

import java.util.HashMap;
import java.util.List;

public class VideoCommentAdapter extends FirestoreRecyclerAdapter<VideoComment, VideoCommentAdapter.VideoCommentHolder> {

    //Private Attributes
    private ItemContainerCommentBinding binding;
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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemContainerCommentBinding.inflate(inflater, parent, false);
        binding.setViewModel(postVideoViewModel);
        binding.setLifecycleOwner(lifecycleOwner);
        return new VideoCommentHolder(binding.getRoot());
    }

    public void setPostVideoViewModel(PostVideoViewModel postVideoViewModel) {
        this.postVideoViewModel = postVideoViewModel;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    class VideoCommentHolder extends RecyclerView.ViewHolder{

        public VideoCommentHolder(@NonNull View itemView) {
            super(itemView);
        }

        void setVideoCommentData(VideoComment model){
            String dateRelative = (String) DateUtils.getRelativeTimeSpanString(model.getDateCreated());
            List<String> likes = model.getLikes();
            List<String> dislikes = model.getDislikes();
            int likesCtr = 0;
            int dislikesCtr = 0;

            if(likes != null)
                likesCtr = likes.size();
            if(dislikes != null)
                dislikesCtr = dislikes.size();
            Log.d("Likes", likesCtr+"");

            if(dateRelative.equals("0 minutes ago"))
                dateRelative = "A Few Seconds Ago";

            binding.commentUsername.setText(model.getUserName());
            binding.commentContent.setText(model.getComment());
            binding.commentDate.setText(dateRelative);
            binding.commentLikesCounter.setText(likesCtr+"");
            binding.commentDislikesCounter.setText(dislikesCtr+"");
            binding.setVariable(BR.videoCommentInstance, model);

//            binding.getViewModel().likesCounterObserver(model).observe(binding.getLifecycleOwner(), new Observer<HashMap<String, Object>>() {
//                @Override
//                public void onChanged(HashMap<String, Object> stringObjectHashMap) {
//                    Log.d("Observer Triggered", "triggered");
//                    if(stringObjectHashMap != null){
//                        String likes = "0";
//                        String dislikes = "0";
//                        if(stringObjectHashMap.get(VideoCommentConstants.KEY_VIDEO_COMMENT_LIKES).toString() != null || stringObjectHashMap.get(VideoCommentConstants.KEY_VIDEO_COMMENT_LIKES).toString().isEmpty())
//                            likes = stringObjectHashMap.get(VideoCommentConstants.KEY_VIDEO_COMMENT_LIKES).toString();
//                        if(stringObjectHashMap.get(VideoCommentConstants.KEY_VIDEO_COMMENT_DISLIKES).toString() != null || stringObjectHashMap.get(VideoCommentConstants.KEY_VIDEO_COMMENT_DISLIKES).toString().isEmpty())
//                            dislikes = stringObjectHashMap.get(VideoCommentConstants.KEY_VIDEO_COMMENT_DISLIKES).toString();
//                        Log.d("Likes Counter", likes);
//                        binding.commentLikesCounter.setText(likes);
//                        binding.commentDislikesCounter.setText(dislikes);
//                    }
//                }
//            });
        }
    }
}
