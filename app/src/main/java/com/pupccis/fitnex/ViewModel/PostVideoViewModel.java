package com.pupccis.fitnex.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pupccis.fitnex.Model.PostVideo;
import com.pupccis.fitnex.Repository.PostedVideosRepository;

import java.util.ArrayList;

public class PostVideoViewModel extends ViewModel {
    MutableLiveData<ArrayList<PostVideo>> postVideos;
    private PostedVideosRepository postedVideosRepository;

    private Context context;

    public void init(Context context){

        if(postVideos!=null){
            return;
        }
        this.context = context;
        postVideos = PostedVideosRepository.getInstance(context).getVideos();
    }

    public LiveData<ArrayList<PostVideo>> getPostVideos(){

        return postVideos;
    }

    public void uploadVideo(PostVideo postVideo){
        PostedVideosRepository.getInstance(context).uploadVideo(postVideo);
    }
}
