package com.pupccis.fitnex.model;

import com.pupccis.fitnex.utilities.Constants.VideoCommentConstants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class VideoComment implements Serializable {
    private String userID, comment, userName, type;
    private long dateCreated;
    private String commentId, videoId, parentCommentId;
    private List<String> likes, dislikes;
    public VideoComment(){

    }

    public VideoComment(VideoCommentBuilder builder) {
        this.userID = builder.userID;
        this.userName = builder.userName;
        this.dateCreated = builder.dateCreated;
        this.comment = builder.comment;
        this.type = builder.type;

        this.commentId = builder.commentId;
        this.videoId = builder.videoId;
        this.parentCommentId = builder.parentCommentId;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() { return userName; }

    public long getDateCreated() {
        return dateCreated;
    }

    public String getComment() {
        return comment;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getType() {
        return type;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public List<String> getLikes() {
        return likes;
    }

    public List<String> getDislikes() {
        return dislikes;
    }

    public HashMap<String, Object> map(){
        HashMap<String, Object> map = new HashMap<>();
        map.put(VideoCommentConstants.KEY_VIDEO_COMMENT, getComment());
        map.put(VideoCommentConstants.KEY_VIDEO_COMMENT_DATE_CREATED, getDateCreated());
        map.put(VideoCommentConstants.KEY_VIDEO_COMMENT_USER_ID, getUserID());
        map.put(VideoCommentConstants.KEY_VIDEO_COMMENT_USER_NAME, getUserName());
        map.put(VideoCommentConstants.KEY_VIDEO_COMMENT_TYPE, getType());
        map.put(VideoCommentConstants.KEY_VIDEO_COMMENT_VIDEO_ID, getVideoId());
        map.put(VideoCommentConstants.KEY_VIDEO_COMMENT_PARENT_COMMENT_ID, getParentCommentId());
        return map;
    }

    public static class VideoCommentBuilder{
        private final String userID, comment, userName, type;
        private final long dateCreated;
        private String commentId, videoId, parentCommentId;


        public VideoCommentBuilder(String userID, String userName, long dateCreated, String comment, String type) {
            this.userID = userID;
            this.userName = userName;
            this.dateCreated = dateCreated;
            this.comment = comment;
            this.type = type;
        }


        public VideoCommentBuilder commentId(String commentId){
            this.commentId = commentId;
            return this;
        }

        public VideoCommentBuilder parentCommentId(String parentCommentId){
            this.parentCommentId = parentCommentId;
            return this;
        }

        public VideoCommentBuilder videoId(String videoId){
            this.videoId = videoId;
            return this;
        }

        public VideoComment build(){
            VideoComment videoComment = new VideoComment(this);
            return videoComment;
        }
    }
}
