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
    private int likes,dislikes;
    private long views, date_posted;

    private PostVideo(PostVideoBuilder builder) {
        this.videoTitle = builder.videoTitle;
        this.category = builder.category;
        this.description = builder.description;
        this.trainerID = builder.trainerID;
        this.videoURL = builder.videoURL;
        this.thumbnailURL = builder.thumbnailURL;
        this.postVideoID = builder.postVideoID;
        this.likes = builder.likes;
        this.dislikes = builder.dislikes;
        this.views = builder.views;
        this.date_posted = builder.date_posted;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public String getPostVideoID() {
        return postVideoID;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getDate_posted() {
        return date_posted;
    }

    public static class PostVideoBuilder{

        private String videoTitle;
        private String category;
        private String description;
        private String trainerID;
        private String videoURL;
        private String thumbnailURL;
        private String postVideoID;
        private int likes,dislikes;
        private long views, date_posted;

        public PostVideoBuilder(){

        }

        public PostVideoBuilder(String videoTitle, String category, String description, String trainerID, long date_posted){
            this.videoTitle = videoTitle;
            this.category = category;
            this.description = description;
            this.trainerID = trainerID;
            this.date_posted = date_posted;
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

        public PostVideoBuilder date_posted(long date_posted){
            this.date_posted = date_posted;
            return this;
        }

        public PostVideoBuilder initializeData(){
            this.likes = 0;
            this.dislikes = 0;
            this.views = 0;
            return this;
        }

        public PostVideoBuilder likes(int likes){
            this.likes = likes;
            return this;
        }

        public PostVideoBuilder dislikes(int dislikes){
            this.dislikes = dislikes;
            return this;
        }

        public PostVideoBuilder views(long views){
            this.views = views;
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
