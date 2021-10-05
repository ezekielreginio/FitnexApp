package com.pupccis.fitnex.model;

public class VideoThumbnail {
    private String videoTitle, thumbnailURL;

    public VideoThumbnail() {
    }

    public VideoThumbnail(String videoTitle, String thumbnailURL) {
        this.videoTitle = videoTitle;
        this.thumbnailURL = thumbnailURL;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
