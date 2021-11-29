package com.pupccis.fitnex.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.activities.videoplayer.TrainingVideoPlayer;
import com.pupccis.fitnex.api.enums.AccessType;
import com.pupccis.fitnex.databinding.ItemContainerStudioVideoBinding;
import com.pupccis.fitnex.model.PostVideo;
import com.pupccis.fitnex.viewmodel.PostVideoViewModel;

public class TrainerStudioVideosAdapter extends FirestoreRecyclerAdapter<PostVideo, TrainerStudioVideosAdapter.PostVideoHolder> {
    private ItemContainerStudioVideoBinding binding;
    private PostVideoViewModel postVideoViewModel = new PostVideoViewModel();
    private AccessType accessType;
    private Activity activity;

    public TrainerStudioVideosAdapter(@NonNull FirestoreRecyclerOptions<PostVideo> options) {
        super(options);
        Log.d("Adapter", "Instantiated");
    }

    @Override
    protected void onBindViewHolder(@NonNull PostVideoHolder holder, int position, @NonNull PostVideo model) {
        Log.d("BindViewHolder", "Triggered");
        model.setPostVideoID(this.getSnapshots().getSnapshot(position).getId());
        holder.setPostVideoData(model);
    }

    @NonNull
    @Override
    public PostVideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CreateViewHolder", "Triggered");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemContainerStudioVideoBinding.inflate(inflater, parent, false);
        binding.setViewModel(postVideoViewModel);

        return new PostVideoHolder(binding.getRoot());
    }

    public PostVideoViewModel getPostVideoViewModel() {
        return postVideoViewModel;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }


    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    class PostVideoHolder extends RecyclerView.ViewHolder{

        public PostVideoHolder(@NonNull View itemView) {
            super(itemView);
        }

        void setPostVideoData(PostVideo model){
            binding.textViewStudioVideoTitle.setText(model.getVideoTitle());

            Glide.with(binding.imageViewVideoThumbnail.getContext())
                    .load(model.getThumbnailURL())
                    .into(binding.imageViewVideoThumbnail);

            binding.setVariable(BR.thumbnailPostVideoInstance, model);
            switch (accessType){
                case VIEW:
                    binding.constraintLayoutEditDelete.setVisibility(View.GONE);
                    break;
            }

            binding.cardViewVideoThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(activity.getApplicationContext(), TrainingVideoPlayer.class);
                    intent.putExtra("PostVideo", model);
                    activity.startActivity(intent);
                }
            });
        }
    }
}
