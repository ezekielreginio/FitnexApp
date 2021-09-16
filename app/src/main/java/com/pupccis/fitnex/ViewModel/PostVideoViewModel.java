package com.pupccis.fitnex.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pupccis.fitnex.Model.PostVideo;
import com.pupccis.fitnex.Repository.Repo;

import java.util.ArrayList;

public class PostVideoViewModel extends ViewModel {
    MutableLiveData<ArrayList<PostVideo>> postVideos;

    public void init(Context context){

        if(postVideos!=null){
            return;
        }

        postVideos = Repo.getInstance(context).getPostVideos();
    }

    public LiveData<ArrayList<PostVideo>> getPostVideos(){

        return postVideos;
    }
}
