package com.pupccis.fitnex.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pupccis.fitnex.Model.PostVideo;
import com.pupccis.fitnex.Repository.Repo;

import java.util.ArrayList;
import java.util.HashMap;

public class PostVideoViewModel extends ViewModel {
    MutableLiveData<ArrayList<PostVideo>> postVideos;
<<<<<<< HEAD

    private PostedVideosRepository postedVideosRepository;
    private MutableLiveData<HashMap<String, Object>> postVideoUpdate = new MutableLiveData<>();
    private Context context;
=======
>>>>>>> parent of 330d067 (mvvm 9-16 7:53pm)

    public void init(Context context){

        if(postVideos!=null){
            return;
        }
<<<<<<< HEAD
        this.context = context;
        postVideos = PostedVideosRepository.getInstance().getVideos();
        postVideoUpdate = PostedVideosRepository.getInstance().updateVideos();
=======

        postVideos = Repo.getInstance(context).getPostVideos();
>>>>>>> parent of 330d067 (mvvm 9-16 7:53pm)
    }

    public LiveData<ArrayList<PostVideo>> getLiveDataPostVideos(){
        return postVideos;
    }
<<<<<<< HEAD

    public MutableLiveData<HashMap<String, Object>> getLiveDataPostVideoUpdate(){
        return postVideoUpdate;
    }

    public void uploadVideo(PostVideo postVideo){
        PostedVideosRepository.getInstance().uploadVideo(postVideo);
    }
=======
>>>>>>> parent of 330d067 (mvvm 9-16 7:53pm)
}
