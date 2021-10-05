package com.pupccis.fitnex.activities.videoplayer;

import static com.pupccis.fitnex.model.DAO.VideoCommentDAO.postComment;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.pupccis.fitnex.model.DAO.VideoCommentDAO;
import com.pupccis.fitnex.model.VideoComment;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.utilities.Preferences.UserPreferences;
import com.pupccis.fitnex.utilities.VideoConferencingConstants;

public class TrainingVideoCommentReply {
    public static void initiateReplyView(Context context, VideoComment videoComment){
        //Attributes
        ConstraintLayout constraintLayoutReplySection, constraintLayoutReplySectionBack, constraintLayoutCommentsSection;
        RecyclerView recyclerViewCommentReplies;
        TextView replySectionCommentContent;
        EditText editTextVideoCommentReply;
        ImageView  buttonCloseReplySection, buttonUploadVideoCommentReply;

        //Extra Intent
        UserPreferences userPreferences = new UserPreferences(((Activity)context).getApplicationContext());

        //Layout Binding
        constraintLayoutReplySection = ((Activity) context).findViewById(R.id.constraintLayoutReplySection);
        replySectionCommentContent = ((Activity) context).findViewById(R.id.replySectionCommentContent);
        constraintLayoutReplySectionBack = ((Activity) context).findViewById(R.id.constraintLayoutReplySectionBack);
        buttonCloseReplySection = ((Activity) context).findViewById(R.id.buttonCloseReplySection);
        constraintLayoutCommentsSection = ((Activity) context).findViewById(R.id.constraintLayoutCommentsSection);
        editTextVideoCommentReply = ((Activity) context).findViewById(R.id.editTextVideoCommentReply);
        buttonUploadVideoCommentReply = ((Activity) context).findViewById(R.id.buttonUploadVideoCommentReply);
        recyclerViewCommentReplies = ((Activity) context).findViewById(R.id.recyclerViewCommentReplies);


        //Layout Setting
        replySectionCommentContent.setText(videoComment.getComment());

        //Event Binding
        constraintLayoutReplySection.setVisibility(View.VISIBLE);
        Animation slideLeft = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.slide_left);
        constraintLayoutReplySection.startAnimation(slideLeft);

        constraintLayoutReplySectionBack.setOnClickListener(view -> {
            constraintLayoutReplySection.setVisibility(View.GONE);
            Animation slideRight = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.slide_right);
            constraintLayoutReplySection.startAnimation(slideRight);
        });

        buttonCloseReplySection.setOnClickListener(view -> {
            constraintLayoutReplySection.setVisibility(View.GONE);
            constraintLayoutCommentsSection.setVisibility(View.GONE);
            Animation slideDown = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.slide_down);
            constraintLayoutReplySection.startAnimation(slideDown);
        });

        buttonUploadVideoCommentReply.setOnClickListener(view -> {
            VideoComment videoCommentReply = new
                    VideoComment.VideoCommentBuilder(
                            FirebaseAuth.getInstance().getUid(),
                            userPreferences.getString(VideoConferencingConstants.KEY_FULLNAME),
                            System.currentTimeMillis(),
                            editTextVideoCommentReply.getText().toString(),
                            "reply"
                    )
                    .commentId(videoComment.getCommentId())
                    .build();
            postComment(videoCommentReply, videoComment.getVideoId(), "reply");
        });

        //RecyclerView Binding
        recyclerViewCommentReplies.setLayoutManager(new LinearLayoutManager(((Activity) context).getApplicationContext()));
        VideoCommentDAO videoCommentDAO = new VideoCommentDAO.VideoCommentDAOBuilder()
                .context(((Activity) context))
                .recyclerViewVideoComments(recyclerViewCommentReplies)
                .commentId(videoComment.getCommentId())
                .build();
        videoCommentDAO.queryCommentsList(videoComment.getVideoId(), "reply");
    }
}
