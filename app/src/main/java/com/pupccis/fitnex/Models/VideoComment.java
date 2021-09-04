package com.pupccis.fitnex.Models;

public class VideoComment {
    private final String trainerID;
    private final String trainerName;
    private final String dateCreated;
    private final String comment;

    private int likes;
    private int dislikes;

    public VideoComment(VideoCommentBuilder builder) {
        this.trainerID = builder.trainerID;
        this.trainerName = builder.trainerName;
        this.dateCreated = builder.dateCreated;
        this.comment = builder.comment;

        this.likes = builder.likes;
        this.dislikes = builder.dislikes;
    }

    public String getTrainerID() {
        return trainerID;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getComment() {
        return comment;
    }

    public static class VideoCommentBuilder{
        private final String trainerID;
        private final String trainerName;
        private final String dateCreated;
        private final String comment;
        private int likes;
        private int dislikes;


        public VideoCommentBuilder(String trainerID, String trainerName, String dateCreated, String comment) {
            this.trainerID = trainerID;
            this.trainerName = trainerName;
            this.dateCreated = dateCreated;
            this.comment = comment;
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
