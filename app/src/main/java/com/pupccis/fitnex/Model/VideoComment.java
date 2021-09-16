package com.pupccis.fitnex.Model;

public class VideoComment {
    private final String trainerID, comment, trainerName, type;
    private final long dateCreated;
    private String commentId, videoId, parentCommentId;
    private int likes, dislikes;

    public VideoComment(VideoCommentBuilder builder) {
        this.trainerID = builder.trainerID;
        this.trainerName = builder.trainerName;
        this.dateCreated = builder.dateCreated;
        this.comment = builder.comment;
        this.type = builder.type;

        this.likes = builder.likes;
        this.dislikes = builder.dislikes;
        this.commentId = builder.commentId;
        this.videoId = builder.videoId;
        this.parentCommentId = builder.parentCommentId;
    }

    public String getTrainerID() {
        return trainerID;
    }

    public String getTrainerName() {
        return trainerName;
    }

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

    public static class VideoCommentBuilder{
        private final String trainerID, comment, trainerName, type;
        private final long dateCreated;
        private String commentId, videoId, parentCommentId;
        private int likes, dislikes;


        public VideoCommentBuilder(String trainerID, String trainerName, long dateCreated, String comment, String type) {
            this.trainerID = trainerID;
            this.trainerName = trainerName;
            this.dateCreated = dateCreated;
            this.comment = comment;
            this.type = type;
        }

        public VideoCommentBuilder initializeData(){
            this.likes =  0;
            this.dislikes = 0;
            return this;
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

        public VideoCommentBuilder likes(int likes){
            this.likes = likes;
            return this;
        }

        public VideoCommentBuilder dislikes(int dislikes){
            this.dislikes = dislikes;
            return this;
        }

        public VideoComment build(){
            VideoComment videoComment = new VideoComment(this);
            return videoComment;
        }
    }
}
