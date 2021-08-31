package com.pupccis.fitnex.Models;

public class PostVideo {

    private final String videoTitle;
    private final String category;

    private PostVideo(PostVideoBuilder builder) {
        this.videoTitle = builder.videoTitle;
        this.category = builder.category;
    }

    public static class PostVideoBuilder{
        private final String videoTitle;
        private final String category;
        private String trainerID;
        public PostVideoBuilder(String videoTitle, String category){
            this.videoTitle = videoTitle;
            this.category = category;
        }

        public PostVideoBuilder trainerID(String trainerID){
            this.trainerID = trainerID;
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
