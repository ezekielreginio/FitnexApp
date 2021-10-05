package com.pupccis.fitnex.activities.videoplayer;

import static com.pupccis.fitnex.api.NumberFormatter.dateFormatter;
import static com.pupccis.fitnex.api.NumberFormatter.numberFormatter;
import static com.pupccis.fitnex.handlers.viewmodel.ViewModelHandler.getFirebaseUIVideoCommentOptions;
import static com.pupccis.fitnex.handlers.viewmodel.ViewModelHandler.getFirebaseUIVideoCommentReplyOptions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
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
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.pupccis.fitnex.adapters.VideoCommentAdapter;
import com.pupccis.fitnex.api.enums.VideoPlayerNavigation;
import com.pupccis.fitnex.databinding.ActivityVideoPlayerBinding;
import com.pupccis.fitnex.handlers.view.WrapContentLinearLayoutManager;
import com.pupccis.fitnex.model.DAO.PostVideoDAO;
import com.pupccis.fitnex.model.DAO.VideoCommentDAO;
import com.pupccis.fitnex.model.PostVideo;
import com.pupccis.fitnex.model.VideoComment;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.utilities.Constants.PostVideoConstants;
import com.pupccis.fitnex.utilities.Constants.UserConstants;
import com.pupccis.fitnex.utilities.Preferences.UserPreferences;
import com.pupccis.fitnex.utilities.VideoConferencingConstants;
import com.pupccis.fitnex.viewmodel.PostVideoViewModel;
import com.pupccis.fitnex.viewmodel.ProgramViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class TrainingVideoPlayer extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
    private UserPreferences userPreferences;
    private SimpleExoPlayer simpleExoPlayer;
    private PostVideo postVideo;

    private VideoCommentAdapter adapter, replyAdapter;
    private ActivityVideoPlayerBinding binding;
    private PostVideoViewModel postVideoViewModel = new PostVideoViewModel();
    private List<VideoPlayerNavigation> navigationHistory = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_player);
        binding.setLifecycleOwner(this);
        binding.setPresenter(this);
        binding.setViewModel(postVideoViewModel);

        //Extra Intent
        userPreferences = new UserPreferences(getApplicationContext());
        postVideo = (PostVideo) getIntent().getSerializableExtra("PostVideo");


        binding.textViewVideoPlayerTitle.setText(postVideo.getVideoTitle());

        binding.editTextWriteComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    binding.getViewModel().postComment(postVideo.getPostVideoID(), userPreferences.getString(UserConstants.KEY_USER_NAME));
                    handled = true;
                    hideCommentInput();
                }
                return handled;
            }
        });

        binding.editTextVideoCommentReply.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    binding.getViewModel().postReply(userPreferences.getString(UserConstants.KEY_USER_NAME));
                    handled = true;
                    hideReplyInput();
                }
                return handled;
            }
        });

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
        binding.videoPlayer.setPlayer(simpleExoPlayer);
        binding.videoPlayer.setKeepScreenOn(true);
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
                    binding.progressBar.setVisibility(View.VISIBLE);
                } else if (playbackState == Player.STATE_READY) {
                    binding.progressBar.setVisibility(View.GONE);
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

        //Comment RecyclerView Firebase UI
        FirestoreRecyclerOptions<VideoComment> options = getFirebaseUIVideoCommentOptions(postVideo.getPostVideoID());
        //binding.recyclerViewVideoComments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerViewVideoComments.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new VideoCommentAdapter(options);
        adapter.setPostVideoViewModel(postVideoViewModel);
        adapter.setLifecycleOwner(this);
        binding.recyclerViewVideoComments.setAdapter(adapter);

        //Observers
        binding.getViewModel().getVideoLikesData(postVideo.getPostVideoID()).observe(binding.getLifecycleOwner(), new Observer<HashMap<String, Object>>() {
            @Override
            public void onChanged(HashMap<String, Object> videoLikesData) {
                String likes = numberFormatter(((Number) videoLikesData.get(PostVideoConstants.KEY_POST_VIDEO_LIKES)).longValue());
                String dislikes = numberFormatter(((Number) videoLikesData.get(PostVideoConstants.KEY_POST_VIDEO_DISLIKES)).longValue());
                binding.textViewVideoPlayerLikeCounter.setText(likes);
                binding.textViewVideoPlayerDislikeCounter.setText(dislikes);
            }
        });

        binding.getViewModel().getUserLikeStatus(postVideo.getPostVideoID()).observe(binding.getLifecycleOwner(), new Observer<HashMap<String, Boolean>>() {
            @Override
            public void onChanged(HashMap<String, Boolean> stringBooleanHashMap) {
                Log.d("Like Event", "Triggered");
                if(stringBooleanHashMap.get("liked"))
                    binding.buttonVideoLike.setImageResource(R.drawable.ic_baseline_thumb_up_24);
                else
                    binding.buttonVideoLike.setImageResource(R.drawable.ic_outline_thumb_up_alt_24);

                if(stringBooleanHashMap.get("disliked"))
                    binding.buttonVideoDislike.setImageResource(R.drawable.ic_baseline_thumb_down_24);
                else
                    binding.buttonVideoDislike.setImageResource(R.drawable.ic_outline_thumb_down_alt_24);
            }
        });

        binding.getViewModel().videoCommentReplyObserver().observe(binding.getLifecycleOwner(), new Observer<VideoComment>() {
            @Override
            public void onChanged(VideoComment comment) {
                Log.d("Reply", "clicked");
                binding.constraintLayoutReplySection.setVisibility(View.VISIBLE);
                Animation slideLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left);
                binding.constraintLayoutReplySection.startAnimation(slideLeft);

                binding.getViewModel().setVideoComment(comment);

                FirestoreRecyclerOptions<VideoComment> replyOptions = getFirebaseUIVideoCommentReplyOptions(comment);
                replyAdapter  = new VideoCommentAdapter(replyOptions);
                replyAdapter.startListening();
                replyAdapter.setPostVideoViewModel(postVideoViewModel);
                replyAdapter.setLifecycleOwner(TrainingVideoPlayer.this);
                binding.recyclerViewCommentReplies.setLayoutManager(new WrapContentLinearLayoutManager(TrainingVideoPlayer.this, LinearLayoutManager.VERTICAL, false));
                binding.recyclerViewCommentReplies.setAdapter(replyAdapter);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Start", "Activity");
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onClick(View view) {
        if(view == binding.buttonVideoLike)
            binding.getViewModel().likeVideo(postVideo.getPostVideoID(), "liked");

        else if(view == binding.buttonVideoDislike)
            binding.getViewModel().likeVideo(postVideo.getPostVideoID(), "disliked");

        else if(view == binding.constraintLayoutVideoCommentBox){
            binding.constraintLayoutCommentsSection.setVisibility(View.VISIBLE);
            Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
            binding.constraintLayoutCommentsSection.startAnimation(slideUp);
        }

        else if(view == binding.buttonUploadVideoCommentReply)
            binding.getViewModel().postReply(userPreferences.getString(UserConstants.KEY_USER_NAME));

        else if(view == binding.buttonCloseCommentsSection){
            binding.constraintLayoutCommentsSection.setVisibility(View.GONE);
            Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
            binding.constraintLayoutCommentsSection.startAnimation(slideDown);
        }

        else if(view==binding.buttonReplySectionBack){
            binding.constraintLayoutReplySection.setVisibility(View.GONE);
            Animation slideRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right);
            binding.constraintLayoutReplySection.startAnimation(slideRight);
        }

        else if(view == binding.editTextWriteComment1){
            binding.constraintLayoutTypeVideoCommentBox.setVisibility(View.VISIBLE);
            binding.editTextWriteComment.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            navigationHistory.add(VideoPlayerNavigation.COMMENT_INPUT);
        }

        else if(view == binding.constraintLayoutTypeVideoCommentBox){
            hideCommentInput();
        }

        else if(view == binding.editTextVideoCommentReply1){
            binding.constraintLayoutTypeVideoCommentReplyBox.setVisibility(View.VISIBLE);
            binding.editTextVideoCommentReply.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            navigationHistory.add(VideoPlayerNavigation.REPLY_INPUT);
        }
        else if(view == binding.constraintLayoutTypeVideoCommentReplyBox){
            hideReplyInput();
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {

        return false;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(navigationHistory.contains(VideoPlayerNavigation.COMMENT_INPUT)){
            hideCommentInput();
            navigationHistory.remove(VideoPlayerNavigation.COMMENT_INPUT);
        }
        else if(navigationHistory.contains(VideoPlayerNavigation.REPLY_INPUT)){
            hideReplyInput();
            navigationHistory.remove(VideoPlayerNavigation.REPLY_INPUT);
        }
        else
            finish();
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

    void hideCommentInput(){
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.editTextWriteComment.getWindowToken(), 0);
        binding.constraintLayoutTypeVideoCommentBox.setVisibility(View.GONE);
        binding.editTextWriteComment.setText("");
        navigationHistory.remove(VideoPlayerNavigation.COMMENT_INPUT);
    }

    private void hideReplyInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.editTextWriteComment.getWindowToken(), 0);
        binding.constraintLayoutTypeVideoCommentReplyBox.setVisibility(View.GONE);
        binding.editTextVideoCommentReply.setText("");
        navigationHistory.remove(VideoPlayerNavigation.REPLY_INPUT);
    }

}