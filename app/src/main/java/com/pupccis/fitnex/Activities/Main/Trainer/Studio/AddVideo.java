package com.pupccis.fitnex.Activities.Main.Trainer.Studio;

import static com.pupccis.fitnex.Utilities.Globals.RotateAnimation.rotateAnimation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.pupccis.fitnex.Models.FitnessClass;
import com.pupccis.fitnex.R;

import java.util.Date;

public class AddVideo extends AppCompatActivity implements View.OnClickListener {
    //Private Layout Attributes
    private ImageView btnClose;
    private Button btnUploadVideo;
    private ConstraintLayout videoUploadContainer;
    private VideoView videoVIewUploadedVideo;

    //Video Attributes
    private VideoView videoView;
    private Uri videoUri;
    private MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);

        //Layout Binding
        btnClose = findViewById(R.id.closeAddClassButton);
        btnUploadVideo = (Button) findViewById(R.id.btnAddVideoFile);
        videoUploadContainer = (ConstraintLayout) findViewById(R.id.constraintLayoutAddVideo);
        videoVIewUploadedVideo = (VideoView) findViewById(R.id.videoViewUploadedVideo);

        //MediaController
        mediaController = new MediaController(this);
        videoVIewUploadedVideo.setMediaController(mediaController);

        //Rotate Animation
        rotateAnimation(this, btnClose);

        //Event Bindings
        btnUploadVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.btnAddVideoFile):
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent();
                                intent.setType("video/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intent, 101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101 && resultCode==RESULT_OK){
            videoUploadContainer.setVisibility(View.GONE);
            videoVIewUploadedVideo.setVisibility(View.VISIBLE);
            videoVIewUploadedVideo.start();
            videoUri = data.getData();
            videoVIewUploadedVideo.setVideoURI(videoUri);
        }
    }
}