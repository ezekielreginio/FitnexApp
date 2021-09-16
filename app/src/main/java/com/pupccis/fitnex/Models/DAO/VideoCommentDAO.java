package com.pupccis.fitnex.Models.DAO;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pupccis.fitnex.API.adapter.VideoCommentsAdapter;
import com.pupccis.fitnex.Activities.VideoPlayer.TrainingVideoPlayer;
import com.pupccis.fitnex.Models.PostVideo;
import com.pupccis.fitnex.Models.VideoComment;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.Constants.PostVideoConstants;
import com.pupccis.fitnex.Utilities.Constants.VideoCommentConstants;

import java.util.ArrayList;
import java.util.List;

public class VideoCommentDAO {
    private Context context;
    private RecyclerView recyclerViewVideoComments;
    private VideoCommentsAdapter videoCommentsAdapter;
    private String commentId;

    public VideoCommentDAO(VideoCommentDAOBuilder builder) {
        this.context = builder.context;
        this.recyclerViewVideoComments = builder.recyclerViewVideoComments;
        this.commentId = builder.commentId;
    }

    public static void postComment(VideoComment videoComment, String postVideoID, String type){
        DatabaseReference commentReference = null;
        if(type.equals("comment"))
            commentReference = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO)
                    .child(postVideoID)
                    .child(VideoCommentConstants.KEY_COLLECTION_COMMENTS);
        else if(type.equals("reply"))
            commentReference = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO)
                    .child(postVideoID)
                    .child(VideoCommentConstants.KEY_COLLECTION_COMMENTS)
                    .child(videoComment.getCommentId())
                    .child(VideoCommentConstants.KEY_VIDEO_REPLIES);
        commentReference.push().setValue(videoComment);
    }

    public void likeEventComment(VideoComment videoComment, String userId, boolean liked){
        DatabaseReference query = null;
        if(videoComment.getType().equals(VideoCommentConstants.KEY_VIDEO_COMMENT))
            query = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO)
                    .child(videoComment.getVideoId())
                    .child(VideoCommentConstants.KEY_COLLECTION_COMMENTS)
                    .child(commentId);

        else if(videoComment.getType().equals(VideoCommentConstants.KEY_VIDEO_REPLIES))
            query = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO)
                    .child(videoComment.getVideoId())
                    .child(VideoCommentConstants.KEY_COLLECTION_COMMENTS)
                    .child(videoComment.getParentCommentId())
                    .child(VideoCommentConstants.KEY_VIDEO_REPLIES)
                    .child(commentId);

        if(liked){
            query.child(VideoCommentConstants.KEY_VIDEO_COMMENT_LIKES)
            .get().addOnCompleteListener(task -> {
                if(task.getResult().hasChild(userId))
                    task.getResult().child(userId).getRef().removeValue();
                else
                    task.getResult().child(userId).getRef().setValue(true);
            });

            query.child(VideoCommentConstants.KEY_VIDEO_COMMENT_DISLIKES)
                    .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    task.getResult().child(userId).getRef().removeValue();
                }
            });

        }
        else{
            query.child(VideoCommentConstants.KEY_VIDEO_COMMENT_DISLIKES)
            .get().addOnCompleteListener(task -> {
                if(task.getResult().hasChild(userId))
                    task.getResult().child(userId).getRef().removeValue();
                else
                    task.getResult().child(userId).getRef().setValue(true);
            });

            query.child(VideoCommentConstants.KEY_VIDEO_COMMENT_LIKES)
                    .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    task.getResult().child(userId).getRef().removeValue();
                }
            });
        }
    }

    public List<VideoComment> queryCommentsList(String postVideoID, String type){
        List<VideoComment> commentsList = new ArrayList<>();
        Query query = null;
        if(type.equals("comment"))
            query = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO).child(postVideoID).child(VideoCommentConstants.KEY_COLLECTION_COMMENTS);
        else if(type.equals("reply"))
            query = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO)
                    .child(postVideoID)
                    .child(VideoCommentConstants.KEY_COLLECTION_COMMENTS)
                    .child(commentId)
                    .child(VideoCommentConstants.KEY_VIDEO_REPLIES);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    VideoComment comment = new VideoComment.VideoCommentBuilder(
                            dataSnapshot.child(VideoCommentConstants.KEY_VIDEO_COMMENT_TRAINER_ID).getValue().toString(),
                            dataSnapshot.child(VideoCommentConstants.KEY_VIDEO_COMMENT_TRAINER_NAME).getValue().toString(),
                            (long) dataSnapshot.child(VideoCommentConstants.KEY_VIDEO_COMMENT_DATE_CREATED).getValue(),
                            dataSnapshot.child(VideoCommentConstants.KEY_VIDEO_COMMENT).getValue().toString(),
                            dataSnapshot.child(VideoCommentConstants.KEY_VIDEO_COMMENT_TYPE).getValue().toString()
                    )
                            .commentId(dataSnapshot.getKey())
                            .parentCommentId(commentId)
                            .videoId(postVideoID)
                            .build();
                    commentsList.add(comment);
                }
                if(recyclerViewVideoComments != null){
                    videoCommentsAdapter = new VideoCommentsAdapter(commentsList, context);
                    videoCommentsAdapter.setHasStableIds(true);
                    recyclerViewVideoComments.setAdapter(videoCommentsAdapter);
                    Parcelable recyclerViewState;
                    recyclerViewState = recyclerViewVideoComments.getLayoutManager().onSaveInstanceState();

                    videoCommentsAdapter.notifyDataSetChanged();
                    recyclerViewVideoComments.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return commentsList;
    }

    public static void setReplyCount(VideoComment videoComment, View itemView, String userId){
        Query queryLike = null;
        Query queryDislike = null;
        TextView commentReplyCounter = itemView.findViewById(R.id.commentReplyCounter);
        TextView commentLikesCounter = itemView.findViewById(R.id.commentLikesCounter);
        TextView commentDislikesCounter = itemView.findViewById(R.id.commentDislikesCounter);
        ImageView buttonCommentLike = itemView.findViewById(R.id.buttonCommentLike);
        ImageView buttonCommentDislike = itemView.findViewById(R.id.buttonCommentDislike);

        ConstraintLayout constraintLayoutCommentReplyLink = itemView.findViewById(R.id.constraintLayoutCommentReplyLink);
        FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO)
                .child(videoComment.getVideoId())
                .child(VideoCommentConstants.KEY_COLLECTION_COMMENTS)
                .child(videoComment.getCommentId())
                .child(VideoCommentConstants.KEY_VIDEO_REPLIES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            long replyCount = task.getResult().getChildrenCount();

                            if(replyCount == 0)
                                constraintLayoutCommentReplyLink.setVisibility(View.GONE);
                            else
                                commentReplyCounter.setText(replyCount+"");
                        }
                    }
                });
        //Load Like and Dislike Stats
        if(videoComment.getType().equals(VideoCommentConstants.KEY_VIDEO_COMMENT)){
            DatabaseReference commentReference = FirebaseDatabase
                    .getInstance()
                    .getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO)
                    .child(videoComment.getVideoId())
                    .child(VideoCommentConstants.KEY_COLLECTION_COMMENTS)
                    .child(videoComment.getCommentId());
            queryLike = commentReference.child(VideoCommentConstants.KEY_VIDEO_COMMENT_LIKES);
            queryDislike =commentReference.child(VideoCommentConstants.KEY_VIDEO_COMMENT_DISLIKES);
        }


        else{
            DatabaseReference commentReference = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO)
                    .child(videoComment.getVideoId())
                    .child(VideoCommentConstants.KEY_COLLECTION_COMMENTS)
                    .child(videoComment.getParentCommentId())
                    .child(VideoCommentConstants.KEY_VIDEO_REPLIES)
                    .child(videoComment.getCommentId());
            queryLike = commentReference.child(VideoCommentConstants.KEY_VIDEO_COMMENT_LIKES);
            queryDislike =commentReference.child(VideoCommentConstants.KEY_VIDEO_COMMENT_DISLIKES);
        }

        queryLike.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long likesCount = snapshot.getChildrenCount();
                    commentLikesCounter.setText(likesCount+"");

                    if(snapshot.hasChild(userId)){
                        buttonCommentLike.setImageResource(R.drawable.ic_baseline_thumb_up_24);
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        queryDislike.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long dislikesCount = snapshot.getChildrenCount();
                commentDislikesCounter.setText(dislikesCount+"");

                if(snapshot.hasChild(userId)){
                    buttonCommentDislike.setImageResource(R.drawable.ic_baseline_thumb_up_24);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static class VideoCommentDAOBuilder{
        private Context context;
        private RecyclerView recyclerViewVideoComments;
        private String commentId;

        public VideoCommentDAOBuilder() {
        }

        public VideoCommentDAOBuilder context(Context context){
            this.context = context;
            return this;
        }

        public VideoCommentDAOBuilder recyclerViewVideoComments(RecyclerView recyclerViewVideoComments){
            this.recyclerViewVideoComments = recyclerViewVideoComments;
            return this;
        }

        public VideoCommentDAOBuilder commentId(String commentId){
            this.commentId = commentId;
            return this;
        }

        public VideoCommentDAO build(){
            VideoCommentDAO  videoCommentDAO = new VideoCommentDAO(this);
            return  videoCommentDAO;
        }
    }
}
