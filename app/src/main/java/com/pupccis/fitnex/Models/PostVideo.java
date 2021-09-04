package com.pupccis.fitnex.Models;

import com.pupccis.fitnex.Models.DAO.PostVideoDAO;

import java.io.Serializable;

public class PostVideo implements Serializable {

    private final String videoTitle;
    private final String category;
    private final String description;
    private final String trainerID;
    private String videoURL;
    private String thumbnailURL;
    private String postVideoID;

    private PostVideo(PostVideoBuilder builder) {
        this.videoTitle = builder.videoTitle;
        this.category = builder.category;
        this.description = builder.description;
        this.trainerID = builder.trainerID;
        this.videoURL = builder.videoURL;
        this.thumbnailURL = builder.thumbnailURL;
        this.postVideoID = builder.postVideoID;
    }
    public String getVideoTitle() {
        return videoTitle;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL){
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getTrainerID() {
        return trainerID;
    }

    public String getPostVideoID() {
        return postVideoID;
    }

    public static class PostVideoBuilder{

        private final String videoTitle;
        private final String category;
        private final String description;
        private final String trainerID;
        private String videoURL;
        private String thumbnailURL;
        private String postVideoID;


        public PostVideoBuilder(String videoTitle, String category, String description, String trainerID){
            this.videoTitle = videoTitle;
            this.category = category;
            this.description = description;
            this.trainerID = trainerID;
        }

        public PostVideoBuilder videoUri(String videoURL){
            this.videoURL = videoURL;
            return this;
        }

        public PostVideoBuilder thumbnailURL(String thumbnailURL){
            this.thumbnailURL = thumbnailURL;
            return this;
        }

        public PostVideoBuilder postVideoID(String postVideoID){
            this.postVideoID = postVideoID;
            return this;
        }

        public PostVideo build(){
            PostVideo postVideo = new PostVideo(this);
            validateObject(postVideo);
            return  postVideo;
        }

        private boolean validateObject(PostVideo postVideo){

            return true;
        }
    }
}
