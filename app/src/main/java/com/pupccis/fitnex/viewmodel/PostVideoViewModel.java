package com.pupccis.fitnex.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.api.enums.LikeType;
import com.pupccis.fitnex.model.PostVideo;
import com.pupccis.fitnex.model.VideoComment;
import com.pupccis.fitnex.repository.PostedVideosRepository;
import com.pupccis.fitnex.utilities.Constants.VideoCommentConstants;

import java.util.ArrayList;
import java.util.HashMap;

public class PostVideoViewModel extends BaseObservable {
    //Comment This
    MutableLiveData<ArrayList<PostVideo>> postVideos;

    private PostedVideosRepository postedVideosRepository = new PostedVideosRepository();
    private MutableLiveData<HashMap<String, Object>> postVideoUpdate = new MutableLiveData<>();
    private Context context;

    public void init(Context context){

        if(postVideos!=null){
            return;
        }
        this.context = context;
        postVideos = PostedVideosRepository.getInstance().getVideos();
        postVideoUpdate = PostedVideosRepository.getInstance().updateVideos();
    }

    public LiveData<ArrayList<PostVideo>> getLiveDataPostVideos(){
        postVideos = PostedVideosRepository.getInstance().getVideos();
        return postVideos;
    }

    public MutableLiveData<HashMap<String, Object>> getLiveDataPostVideoUpdate(){
        return postVideoUpdate;
    }

    public void uploadVideo(PostVideo postVideo){
        PostedVideosRepository.getInstance().uploadVideo(postVideo);
    }
    //Comment This
    //Bindable Attributes
    @Bindable
    private String commentSectionAddComment;
    @Bindable
    private String commentSectionReplyComment;

    //Live Data
    private MutableLiveData<PostVideo> itemPostVideoMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<VideoComment> videoCommentMutableLiveData = new MutableLiveData<>();

    private VideoComment videoComment;

    //Getter Methods
    public String getCommentSectionAddComment() {
        return commentSectionAddComment;
    }
    public String getCommentSectionReplyComment() {
        return commentSectionReplyComment;
    }
    public VideoComment getVideoComment() {
        return videoComment;
    }

    //Setter Methods
    public void setCommentSectionAddComment(String commentSectionAddComment) {
        this.commentSectionAddComment = commentSectionAddComment;
        notifyPropertyChanged(BR.commentSectionAddComment);
    }

    public void setCommentSectionReplyComment(String commentSectionReplyComment) {
        this.commentSectionReplyComment = commentSectionReplyComment;
        notifyPropertyChanged(BR.commentSectionReplyComment);
    }

    public void setVideoComment(VideoComment videoComment) {
        this.videoComment = videoComment;
    }

    //Mutable Live Data Getters
    public MutableLiveData<HashMap<String, Object>> getVideoLikesData(String videoID){
        return postedVideosRepository.getVideoLikesData(videoID);
    }

    public MutableLiveData<HashMap<String, Boolean>> getUserLikeStatus(String videoID){
        return postedVideosRepository.getUserLikeStatus(videoID);
    }

    public MutableLiveData<VideoComment> videoCommentReplyObserver(){
        return videoCommentMutableLiveData;
    }

    public MutableLiveData<PostVideo> itemContainerClickObserver(){
        return itemPostVideoMutableLiveData;
    }

    public MutableLiveData<HashMap<String, Object>> videoCommentCounterObserver(VideoComment model) {
        return PostedVideosRepository.getInstance().getRepliesCounter(model);
    }

    //Mutable Live Data Triggers
    public void triggerContainerObserver(PostVideo postVideo){
        itemPostVideoMutableLiveData.postValue(postVideo);
    }

    public void triggerReplyClickObserver(VideoComment comment){
        videoCommentMutableLiveData.postValue(comment);
    }

    //Public Methods
    public void likeVideo(String videoID, String liked){
        postedVideosRepository.likeVideo(videoID, liked);
    }

    public void likeComment(VideoComment comment, LikeType likeType){
        Log.d("Comment", comment.getCommentId());
        postedVideosRepository.likeComment(comment, likeType);
    }

    public void postComment(String videoID, String userName) {
        VideoComment videoComment = new VideoComment.VideoCommentBuilder(
                FirebaseAuth.getInstance().getUid(),
                userName,
                System.currentTimeMillis(),
                getCommentSectionAddComment(),
                VideoCommentConstants.KEY_VIDEO_COMMENT
                )
                .videoId(videoID)
                .build();
        postedVideosRepository.postComment(videoComment);
    }

    public void postReply(String userName) {
        VideoComment videoComment = new VideoComment.VideoCommentBuilder(
                FirebaseAuth.getInstance().getUid(),
                userName,
                System.currentTimeMillis(),
                getCommentSectionReplyComment(),
                VideoCommentConstants.KEY_VIDEO_REPLY
        )
                .videoId(this.videoComment.getVideoId())
                .parentCommentId(this.videoComment.getCommentId())
                .build();
        postedVideosRepository.postReply(videoComment);
    }
}
