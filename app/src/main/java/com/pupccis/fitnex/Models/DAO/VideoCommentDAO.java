package com.pupccis.fitnex.Models.DAO;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

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
                            dataSnapshot.child(VideoCommentConstants.KEY_VIDEO_COMMENT).getValue().toString()
                    )
                            .commentId(dataSnapshot.getKey())
                            .videoId(postVideoID)
                            .build();
                    commentsList.add(comment);
                }
                if(recyclerViewVideoComments != null){
                    videoCommentsAdapter = new VideoCommentsAdapter(commentsList, context);
                    recyclerViewVideoComments.setAdapter(videoCommentsAdapter);
                    videoCommentsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return commentsList;
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
