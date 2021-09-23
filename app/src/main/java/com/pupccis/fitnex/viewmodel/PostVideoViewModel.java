package com.pupccis.fitnex.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pupccis.fitnex.Model.PostVideo;
import com.pupccis.fitnex.Repository.PostedVideosRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class PostVideoViewModel extends ViewModel {
    MutableLiveData<ArrayList<PostVideo>> postVideos;

    private PostedVideosRepository postedVideosRepository;
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
}
