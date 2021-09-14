package com.pupccis.fitnex.Models;

public class VideoComment {
    private final String trainerID, comment, trainerName;
    private final long dateCreated;
    private String commentId;
    private int likes, dislikes;

    public VideoComment(VideoCommentBuilder builder) {
        this.trainerID = builder.trainerID;
        this.trainerName = builder.trainerName;
        this.dateCreated = builder.dateCreated;
        this.comment = builder.comment;

        this.likes = builder.likes;
        this.dislikes = builder.dislikes;
        this.commentId = builder.commentId;
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

    public static class VideoCommentBuilder{
        private final String trainerID, comment, trainerName;
        private final long dateCreated;
        private String commentId;
        private int likes, dislikes;


        public VideoCommentBuilder(String trainerID, String trainerName, long dateCreated, String comment) {
            this.trainerID = trainerID;
            this.trainerName = trainerName;
            this.dateCreated = dateCreated;
            this.comment = comment;
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
