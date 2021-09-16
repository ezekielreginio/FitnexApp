package com.pupccis.fitnex.Model;

import android.net.Uri;

import com.pupccis.fitnex.Utilities.Constants.PostVideoConstants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PostVideo implements Serializable {

    private final String videoTitle;
    private final String category;
    private final String description;
    private final String trainerID;
    private String videoURL;
    private String thumbnailURL;
    private String postVideoID;
    private String thumbnailFiletype;
    private String videoFiletype;
    private Uri videoUri, thumbnailUri;
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
        this.thumbnailFiletype = builder.thumbnailFiletype;
        this.videoFiletype = builder.videoFiletype;
        this.likes = builder.likes;
        this.dislikes = builder.dislikes;
        this.views = builder.views;
        this.date_posted = builder.date_posted;
        this.videoUri = builder.videoUri;
        this.thumbnailUri = builder.thumbnailUri;
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

    public Uri getVideoUri() {
        return videoUri;
    }

    public Uri getThumbnailUri() {
        return thumbnailUri;
    }

    public String getThumbnailFiletype() {
        return thumbnailFiletype;
    }

    public String getVideoFiletype() {
        return videoFiletype;
    }

    public Map<String, Object> map(){
        Map<String, Object> video = new HashMap<>();
        video.put(PostVideoConstants.KEY_POST_VIDEO_CATEGORY, this.category);
        video.put(PostVideoConstants.KEY_POST_VIDEO_DATE_POSTED, this.date_posted);
        video.put(PostVideoConstants.KEY_POST_VIDEO_DESCRIPTION, this.description);
        video.put(PostVideoConstants.KEY_POST_VIDEO_DISLIKES, this.dislikes);
        video.put(PostVideoConstants.KEY_POST_VIDEO_LIKES, this.likes);
        video.put(PostVideoConstants.KEY_POST_VIDEO_THUMBNAIL_URL, this.thumbnailURL);
        video.put(PostVideoConstants.KEY_POST_VIDEO_TRAINER_ID, this.trainerID);
        video.put(PostVideoConstants.KEY_POST_VIDEO_TITLE, this.videoTitle);
        video.put(PostVideoConstants.KEY_POST_VIDEO_URL, this.videoURL);
        video.put(PostVideoConstants.KEY_POST_VIDEO_VIEWS, this.views);
        return video;
    }

    public static class PostVideoBuilder{

        private String videoTitle;
        private String category;
        private String description;
        private String trainerID;
        private String videoURL;
        private String thumbnailURL;
        private String postVideoID;
        private String thumbnailFiletype;
        private String videoFiletype;
        private Uri videoUri, thumbnailUri;
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

        public PostVideoBuilder videoURL(String videoURL){
            this.videoURL = videoURL;
            return this;
        }

        public PostVideoBuilder thumbnailURL(String thumbnailURL){
            this.thumbnailURL = thumbnailURL;
            return this;
        }

        public PostVideoBuilder thumbnailFiletype(String thumbnailFiletype){
            this.thumbnailFiletype = thumbnailFiletype;
            return this;
        }

        public PostVideoBuilder videoFiletype(String videoFiletype){
            this.videoFiletype = videoFiletype;
            return this;
        }

        public PostVideoBuilder videoUri(Uri videoUri){
            this.videoUri = videoUri;
            return this;
        }

        public PostVideoBuilder thumbnailUri(Uri thumbnailUri){
            this.thumbnailUri = thumbnailUri;
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
