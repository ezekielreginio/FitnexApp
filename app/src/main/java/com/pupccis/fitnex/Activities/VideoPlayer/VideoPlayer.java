package com.pupccis.fitnex.Activities.VideoPlayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.ui.PlayerView;
import com.pupccis.fitnex.R;

public class VideoPlayer extends AppCompatActivity {
    PlayerView playerView;
    ProgressBar progressBar;
    ImageView btFullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        playerView = findViewById(R.id.video_player);
        progressBar = findViewById(R.id.progress_bar);
        btFullScreen = playerView.findViewById(R.id.bt_fullscreen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
            ,WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }
}