package com.pupccis.fitnex.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pupccis.fitnex.model.PostVideo;
import com.pupccis.fitnex.repository.PostedVideosRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class PostVideoViewModel extends ViewModel {
    //Comment This
    MutableLiveData<ArrayList<PostVideo>> postVideos;

    private PostedVideosRepository postedVideosRepository = new PostedVideosRepository();
    private MutableLiveData<HashMap<String, Object>> postVideoUpdate = new MutableLiveData<>();
    private Context context;

    public void init(Context context){

        if(postVideos!=null){
            return;
        }
        this.context = context;
        postVideos = PostedVideosRepository.getInstance().getVideos();
        postVideoUpdate = PostedVideosRepository.getInstance().updateVideos();
    }

    public LiveData<ArrayList<PostVideo>> getLiveDataPostVideos(){
        return postVideos;
    }

    public MutableLiveData<HashMap<String, Object>> getLiveDataPostVideoUpdate(){
        return postVideoUpdate;
    }

    public void uploadVideo(PostVideo postVideo){
        PostedVideosRepository.getInstance().uploadVideo(postVideo);
    }
    //Comment This


    public MutableLiveData<HashMap<String, Object>> getVideoLikesData(String videoID){
        return postedVideosRepository.getVideoLikesData(videoID);
    }

    public MutableLiveData<String> getUserLikeStatus(String videoID){
        return postedVideosRepository.getUserLikeStatus(videoID);
    }

    public void likeVideo(String videoID, String liked){
        postedVideosRepository.likeVideo(videoID, liked);
    }

}
