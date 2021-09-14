package com.pupccis.fitnex.Activities.VideoPlayer;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.pupccis.fitnex.Models.VideoComment;
import com.pupccis.fitnex.R;

public class TrainingVideoCommentReply {
    public static void initiateReplyView(Context context, VideoComment videoComment){
        //Attributes
        ConstraintLayout constraintLayoutReplySection;
        TextView replySectionCommentContent;

        //Layout Binding
        constraintLayoutReplySection = ((Activity) context).findViewById(R.id.constraintLayoutReplySection);
        replySectionCommentContent = ((Activity) context).findViewById(R.id.replySectionCommentContent);

        //Layout Setting
        replySectionCommentContent.setText(videoComment.getComment());

        //Event Binding
        constraintLayoutReplySection.setVisibility(View.VISIBLE);
        Animation slideLeft = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.slide_left);
        constraintLayoutReplySection.startAnimation(slideLeft);
    }
}
