package com.pupccis.fitnex.Models;

import com.pupccis.fitnex.Models.DAO.PostVideoDAO;

public class PostVideo {

    private final String videoTitle;
    private final String category;
    private final String description;
    private String videoURL;

    private PostVideo(PostVideoBuilder builder) {
        this.videoTitle = builder.videoTitle;
        this.category = builder.category;
        this.description = builder.description;
        this.videoURL = builder.videoTitle;
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

    public static class PostVideoBuilder{

        private final String videoTitle;
        private final String category;
        private final String description;
        private String videoURL;

        public PostVideoBuilder(PostVideo postVideo){
            this.videoTitle = postVideo.getVideoTitle();
            this.category = postVideo.getCategory();
            this.description = postVideo.getDescription();
        }

        public PostVideoBuilder(String videoTitle, String category, String description){
            this.videoTitle = videoTitle;
            this.category = category;
            this.description = description;
        }

        public PostVideoBuilder videoUri(String videoURL){
            this.videoURL = videoURL;
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
