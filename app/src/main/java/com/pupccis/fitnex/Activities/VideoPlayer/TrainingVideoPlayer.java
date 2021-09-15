package com.pupccis.fitnex.Activities.VideoPlayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.pupccis.fitnex.API.adapter.VideoCommentsAdapter;
import com.pupccis.fitnex.Models.DAO.VideoCommentDAO;
import com.pupccis.fitnex.Models.PostVideo;
import com.pupccis.fitnex.Models.VideoComment;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.Preferences.UserPreferences;
import com.pupccis.fitnex.Utilities.VideoConferencingConstants;

import org.webrtc.MediaSource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TrainingVideoPlayer extends AppCompatActivity implements View.OnClickListener {
    private UserPreferences userPreferences;
    private PlayerView playerView;
    private ProgressBar progressBar;
    private SimpleExoPlayer simpleExoPlayer;

    private ConstraintLayout constraintLayoutVideoCommentBox, constraintLayoutCommentsSection;
    private ImageView btFullScreen, buttonUploadVideoComment;
    private EditText editTextWriteComment;
    private RecyclerView videoComments;

    boolean flag = false;
    private Calendar calendar = Calendar.getInstance();
    private PostVideo postVideo;
    private VideoCommentsAdapter videoCommentsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        //Extra Intent
        userPreferences = new UserPreferences(getApplicationContext());
        postVideo = (PostVideo) getIntent().getSerializableExtra("PostVideo");

        playerView = findViewById(R.id.video_player);

        progressBar = findViewById(R.id.progress_bar);
        btFullScreen = playerView.findViewById(R.id.bt_fullscreen);

        constraintLayoutVideoCommentBox = findViewById(R.id.constraintLayoutVideoCommentBox);
        constraintLayoutCommentsSection = findViewById(R.id.constraintLayoutCommentsSection);
        editTextWriteComment = findViewById(R.id.editTextWriteComment);
        buttonUploadVideoComment = findViewById(R.id.buttonUploadVideoComment);

        //Event Bindings
        btFullScreen.setOnClickListener(this);
        constraintLayoutVideoCommentBox.setOnClickListener(this);
        buttonUploadVideoComment.setOnClickListener(this);

        //Video Url
        Uri videoUrl = Uri.parse(postVideo.getVideoURL());

        //Video Player Initializations
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        LoadControl loadControl = new DefaultLoadControl();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(
            new AdaptiveTrackSelection.Factory(bandwidthMeter)
        );
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                TrainingVideoPlayer.this, trackSelector, loadControl
        );
        DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory(
                "exoplayer_video"
        );
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        ExtractorMediaSource mediaSource = new ExtractorMediaSource(videoUrl, factory, extractorsFactory, null, null);

        //Set Player Settings and Parameters
        playerView.setPlayer(simpleExoPlayer);
        playerView.setKeepScreenOn(true);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.DefaultEventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
                super.onTimelineChanged(timeline, manifest, reason);
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                super.onTracksChanged(trackGroups, trackSelections);
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                super.onLoadingChanged(isLoading);
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                super.onPlayerStateChanged(playWhenReady, playbackState);
                if(playbackState == Player.STATE_BUFFERING){
                    progressBar.setVisibility(View.VISIBLE);
                } else if (playbackState == Player.STATE_READY) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {
                super.onRepeatModeChanged(repeatMode);
            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
                super.onShuffleModeEnabledChanged(shuffleModeEnabled);
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                super.onPlayerError(error);
            }

            @Override
            public void onPositionDiscontinuity(int reason) {
                super.onPositionDiscontinuity(reason);
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                super.onPlaybackParametersChanged(playbackParameters);
            }

            @Override
            public void onSeekProcessed() {
                super.onSeekProcessed();
            }

            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {
                super.onTimelineChanged(timeline, manifest);
            }
        });

        //RecyclerView Initialization
        videoComments = (RecyclerView) findViewById(R.id.recyclerViewVideoComments);
        videoComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //RecyclerView and List Instance Read Query and Binding
        VideoCommentDAO videoCommentDAO = new VideoCommentDAO.VideoCommentDAOBuilder()
                .context(TrainingVideoPlayer.this)
                .recyclerViewVideoComments(videoComments)
                .build();
        videoCommentDAO.queryCommentsList(postVideo.getPostVideoID());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_fullscreen:
                if(flag){
                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    flag = false;
                }else{
                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    flag = true;
                }
                break;
            case R.id.constraintLayoutVideoCommentBox:
                constraintLayoutCommentsSection.setVisibility(View.VISIBLE);
                Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                constraintLayoutCommentsSection.startAnimation(slideUp);
                break;
            case R.id.buttonUploadVideoComment:
                Date currentTime = calendar.getTime();
                String comment = editTextWriteComment.getText().toString();
                String postVideoID = postVideo.getPostVideoID();
                VideoComment videoComment = new VideoComment
                        .VideoCommentBuilder(
                                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                userPreferences.getString(VideoConferencingConstants.KEY_FULLNAME),
                                currentTime.toString(),
                                comment
                            )
                        .build();
                VideoCommentDAO.postComment(videoComment, postVideoID);
                break;
        }
    }




    @Override
    protected void onPause() {
        super.onPause();
        simpleExoPlayer.setPlayWhenReady(false);
        simpleExoPlayer.getPlaybackState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.getPlaybackState();
    }

}