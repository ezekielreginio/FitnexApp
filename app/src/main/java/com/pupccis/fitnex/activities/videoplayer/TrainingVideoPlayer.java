package com.pupccis.fitnex.activities.videoplayer;

import static com.pupccis.fitnex.api.NumberFormatter.dateFormatter;
import static com.pupccis.fitnex.api.NumberFormatter.numberFormatter;
import static com.pupccis.fitnex.handlers.viewmodel.ViewModelHandler.getFirebaseUIVideoCommentOptions;
import static com.pupccis.fitnex.handlers.viewmodel.ViewModelHandler.getFirebaseUIVideoCommentReplyOptions;

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
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import java.util.Calendar;
import java.util.HashMap;

public class TrainingVideoPlayer extends AppCompatActivity implements View.OnClickListener {
    private UserPreferences userPreferences;
    private SimpleExoPlayer simpleExoPlayer;

    private ConstraintLayout constraintLayoutVideoCommentBox, constraintLayoutCommentsSection;
    private ImageView buttonUploadVideoComment, buttonVideoLike, buttonVideoDislike, buttonCloseCommentsSection;
    private EditText editTextWriteComment;
    private RecyclerView videoComments;
    private TextView textViewVideoPlayerTitle, textViewVideoPlayerLikeCounter, textViewVideoPlayerDislikeCounter, textViewVideoPlayerViewCounter, textViewVideoPlayerTimestamp;

    boolean flag = false;
    private Calendar calendar = Calendar.getInstance();
    private PostVideo postVideo;

    private VideoCommentAdapter adapter, replyAdapter;
    private ActivityVideoPlayerBinding binding;
    private PostVideoViewModel postVideoViewModel = new PostVideoViewModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_player);
        binding.setLifecycleOwner(this);
        binding.setPresenter(this);
        binding.setViewModel(postVideoViewModel);

//        //ViewModel Instantiation
//        postVideoViewModel = new ViewModelProvider(TrainingVideoPlayer.this).get(PostVideoViewModel.class);
//        postVideoViewModel.init(TrainingVideoPlayer.this);

        //Extra Intent
        userPreferences = new UserPreferences(getApplicationContext());
        postVideo = (PostVideo) getIntent().getSerializableExtra("PostVideo");

        //Layout Bindings
        //btFullScreen = findViewById(R.id.bt_fullscreen);

//        constraintLayoutVideoCommentBox = findViewById(R.id.constraintLayoutVideoCommentBox);
//        constraintLayoutCommentsSection = findViewById(R.id.constraintLayoutCommentsSection);
//        editTextWriteComment = findViewById(R.id.editTextWriteComment);
//        buttonUploadVideoComment = findViewById(R.id.buttonUploadVideoComment);
//        buttonVideoLike = findViewById(R.id.buttonVideoLike);
//        buttonVideoDislike = findViewById(R.id.buttonVideoDislike);
//        textViewVideoPlayerTitle = findViewById(R.id.textViewVideoPlayerTitle);
//        textViewVideoPlayerLikeCounter = findViewById(R.id.textViewVideoPlayerLikeCounter);
//        textViewVideoPlayerDislikeCounter = findViewById(R.id.textViewVideoPlayerDislikeCounter);
//        textViewVideoPlayerViewCounter = findViewById(R.id.textViewVideoPlayerViewCounter);
//        textViewVideoPlayerTimestamp = findViewById(R.id.textViewVideoPlayerTimestamp);
//        buttonCloseCommentsSection  = findViewById(R.id.buttonCloseCommentsSection);

        //Event Bindings
        //btFullScreen.setOnClickListener(this);
//        constraintLayoutVideoCommentBox.setOnClickListener(this);
//        buttonUploadVideoComment.setOnClickListener(this);
//        buttonVideoLike.setOnClickListener(this);
//        buttonVideoDislike.setOnClickListener(this);
//        buttonCloseCommentsSection.setOnClickListener(this);

        //Load Video Data to Context
        //PostVideoDAO.loadVideoData(postVideo, getBaseContext(), FirebaseAuth.getInstance().getUid());

        //DateUtils.getRelativeTimeSpanString();
        binding.textViewVideoPlayerTitle.setText(postVideo.getVideoTitle());
        //PostVideoDAO.incrementViews(postVideo.getPostVideoID());

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

        //RecyclerView Initialization
//        videoComments = (RecyclerView) findViewById(R.id.recyclerViewVideoComments);
//        videoComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        videoComments.setItemAnimator(null);

        //Comment RecyclerView and List Instance Read Query and Binding
        //VideoCommentDAO videoCommentDAO = new VideoCommentDAO.VideoCommentDAOBuilder()
//                .context(TrainingVideoPlayer.this)
//                .recyclerViewVideoComments(videoComments)
//                .build();
        //videoCommentDAO.queryCommentsList(postVideo.getPostVideoID(), "comment");

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
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

//    private BroadcastReceiver postedVideoBroadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Log.d("Broadcast Received", "received");
//            PostVideo postVideo = (PostVideo) intent.getSerializableExtra("videoData");
//            String postLikes = numberFormatter((long) postVideo.getLikes());
//            textViewVideoPlayerLikeCounter.setText(postLikes);
//            textViewVideoPlayerDislikeCounter.setText(postVideo.getDislikes()+"");
//            textViewVideoPlayerViewCounter.setText(String.format("%,d", postVideo.getViews())+" views");
//            textViewVideoPlayerTimestamp.setText(dateFormatter("MMM dd, y", postVideo.getDate_posted()));
//            //Log.d("Relative Date", DateUtils.getRelativeTimeSpanString(postVideo.getDate_posted())+"");
//
//            boolean liked = intent.getBooleanExtra("liked", false);
//            boolean disliked = intent.getBooleanExtra("disliked", false);
//
//            if(liked){
//                buttonVideoLike.setImageResource(R.drawable.ic_baseline_thumb_up_24);
//            }
//            else{
//                buttonVideoLike.setImageResource(R.drawable.ic_outline_thumb_up_alt_24);
//            }
//            if(disliked){
//                buttonVideoDislike.setImageResource(R.drawable.ic_baseline_thumb_down_24);
//            }
//            else
//                buttonVideoDislike.setImageResource(R.drawable.ic_outline_thumb_down_alt_24);
//
//        }
//    };

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
        else if(view == binding.buttonUploadVideoComment)
            binding.getViewModel().postComment(postVideo.getPostVideoID(), userPreferences.getString(UserConstants.KEY_USER_NAME));
        else if(view == binding.buttonUploadVideoCommentReply)
            binding.getViewModel().postReply(userPreferences.getString(UserConstants.KEY_USER_NAME));
        else if(view==binding.buttonReplySectionBack){
            binding.constraintLayoutReplySection.setVisibility(View.GONE);
            Animation slideRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right);
            binding.constraintLayoutReplySection.startAnimation(slideRight);
        }

//        switch (view.getId()){
//            case R.id.bt_fullscreen:
////                if(flag){
////                    binding..setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
////                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
////                    flag = false;
////                }else{
////                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen_exit));
////                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
////                    flag = true;
////                }
//                break;
//            case R.id.constraintLayoutVideoCommentBox:
//                constraintLayoutCommentsSection.setVisibility(View.VISIBLE);
//                Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
//                constraintLayoutCommentsSection.startAnimation(slideUp);
//                break;
//            case R.id.buttonCloseCommentsSection:
//                constraintLayoutCommentsSection.setVisibility(View.GONE);
//                Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
//                constraintLayoutCommentsSection.startAnimation(slideDown);
//                break;
//            case R.id.buttonUploadVideoComment:
//                String comment = editTextWriteComment.getText().toString();
//                String postVideoID = postVideo.getPostVideoID();
//                VideoComment videoComment = new VideoComment
//                        .VideoCommentBuilder(
//                                FirebaseAuth.getInstance().getCurrentUser().getUid(),
//                                userPreferences.getString(VideoConferencingConstants.KEY_FULLNAME),
//                                System.currentTimeMillis(),
//                                comment,
//                                "comment"
//                            )
//                        .initializeData()
//                        .build();
//                VideoCommentDAO.postComment(videoComment, postVideoID, "comment");
//                break;
//            case R.id.buttonVideoLike:
//                PostVideoDAO.likeEventVideo(postVideo.getPostVideoID() ,FirebaseAuth.getInstance().getCurrentUser().getUid(), true);
//                break;
//            case R.id.buttonVideoDislike:
//                PostVideoDAO.likeEventVideo(postVideo.getPostVideoID() ,FirebaseAuth.getInstance().getCurrentUser().getUid(), false);
//                break;
//        }
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