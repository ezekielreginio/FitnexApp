package com.pupccis.fitnex.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pupccis.fitnex.databinding.ItemContainerCommentBinding;
import com.pupccis.fitnex.model.VideoComment;
import com.pupccis.fitnex.viewmodel.PostVideoViewModel;

public class VideoCommentAdapter extends FirestoreRecyclerAdapter<VideoComment, VideoCommentAdapter.VideoCommentHolder> {

    //Private Attributes
    private ItemContainerCommentBinding binding;
    private PostVideoViewModel postVideoViewModel =  new PostVideoViewModel();

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

        return new VideoCommentHolder(binding.getRoot());
    }

    public PostVideoViewModel getPostVideoViewModel() {
        return postVideoViewModel;
    }

    class VideoCommentHolder extends RecyclerView.ViewHolder{

        public VideoCommentHolder(@NonNull View itemView) {
            super(itemView);
        }

        void setVideoCommentData(VideoComment model){
            binding.commentUsername.setText(model.getUserName());
            binding.commentContent.setText(model.getComment());
        }
    }
}
