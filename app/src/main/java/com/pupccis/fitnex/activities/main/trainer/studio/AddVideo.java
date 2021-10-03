package com.pupccis.fitnex.activities.main.trainer.studio;

import static com.pupccis.fitnex.utilities.Globals.RotateAnimation.rotateAnimation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.pupccis.fitnex.model.PostVideo;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.viewmodel.PostVideoViewModel;

public class AddVideo extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    //Private Layout Attributes
    private EditText editTextVideoTitle, editTextVideoDescription;
    private ImageView btnClose;
    private Button btnUploadVideo, btnAddVideo,btnAddThumbnail;
    private ConstraintLayout videoUploadContainer,videoThumbnailContainer;
    private VideoView videoVIewUploadedVideo;
    private ImageView videoThumbnail;
    private Uri thumbnailUri;

    private PostVideoViewModel postVideoViewModel;

    //Spinner Attribute
    private Spinner spinnerVideoCategory;

    //Video Attributes
    private VideoView videoView;
    private Uri videoUri;
    private MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);

        //Layout Binding
        editTextVideoTitle = findViewById(R.id.editTextVideoTitle);
        editTextVideoDescription = (EditText) findViewById(R.id.editTextVideoDescription);
        spinnerVideoCategory = (Spinner) findViewById(R.id.editTextPostVideoCategory);
        btnClose = findViewById(R.id.closeAddClassButton);
        btnAddVideo = (Button) findViewById(R.id.btnAddVideoFile);
        btnUploadVideo = (Button) findViewById(R.id.buttonUploadVideo);
        btnAddThumbnail = (Button) findViewById(R.id.btnAddVideoThumbnail);
        videoUploadContainer = (ConstraintLayout) findViewById(R.id.constraintLayoutAddVideo);
        videoThumbnailContainer = (ConstraintLayout) findViewById(R.id.constraintLayoutAddVideoThumbnail);
        videoVIewUploadedVideo = (VideoView) findViewById(R.id.videoViewUploadedVideo);
        videoThumbnail = (ImageView) findViewById(R.id.imageViewVideoThumbnail);

        //Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVideoCategory.setAdapter(adapter);


        //MediaController
        mediaController = new MediaController(this);
        videoVIewUploadedVideo.setMediaController(mediaController);

        //Rotate Animation
        rotateAnimation(this, btnClose);

        //ViewModel Instantiation
//        postVideoViewModel = new ViewModelProvider(AddVideo.this).get(PostVideoViewModel.class);
//        postVideoViewModel.init(AddVideo.this);

        //Event Bindings
        btnAddVideo.setOnClickListener(this);
        btnUploadVideo.setOnClickListener(this);
        spinnerVideoCategory.setOnItemSelectedListener(this);
        btnAddThumbnail.setOnClickListener(this);
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

            case (R.id.btnAddVideoThumbnail):
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intent, 102);
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

            case(R.id.buttonUploadVideo):
                String videoTitle = editTextVideoTitle.getText().toString();
                String videoDescription = editTextVideoDescription.getText().toString();
                String videoCategory= spinnerVideoCategory.getSelectedItemPosition() + "";
                String videoFiletype = getContentResolver().getType(videoUri);
                String thumbnailFiletype = getContentResolver().getType(thumbnailUri);
                Intent intent = new Intent(AddVideo.this, TrainerStudio.class);

                Toast.makeText(this, "Uploading the Video, Please Wait...", Toast.LENGTH_SHORT).show();
                PostVideo postVideo = new PostVideo.PostVideoBuilder(
                            videoTitle,
                            videoCategory,
                            videoDescription,
                            FirebaseAuth.getInstance().getCurrentUser().getUid(),
                            System.currentTimeMillis())
                        .videoFiletype(videoFiletype)
                        .videoUri(videoUri)
                        .thumbnailUri(thumbnailUri)
                        .thumbnailFiletype(thumbnailFiletype)
                        .initializeData()
                        .build();
                PostVideoViewModel postModel = new PostVideoViewModel();
                postModel.uploadVideo(postVideo);
//                PostVideoDAO postVideoDAO = new PostVideoDAO.PostVideoDAOBuilder(filetype, videoUri, thumbnailFiletype, imageUri, getApplicationContext()).build();
//                PostVideo postVideo = new PostVideo.PostVideoBuilder(videoTitle,videoCategory, videoDescription, FirebaseAuth.getInstance().getCurrentUser().getUid(), System.currentTimeMillis()).initializeData().build();
//                postVideoDAO.postVideo(postVideo);

                overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
                intent.putExtra("access_type", GlobalConstants.KEY_ACCESS_TYPE_OWNER);
                startActivity(intent);
                //PostVideo postVideo = PostVideo.PostVideoBuilder(videoTitle, videoCategory, videoDescription,);
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
        else if(requestCode == 102 && resultCode==RESULT_OK){
            videoThumbnailContainer.setVisibility(View.GONE);
            videoThumbnail.setVisibility(View.VISIBLE);
            thumbnailUri = data.getData();
            videoThumbnail.setImageURI(thumbnailUri);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        spinnerVideoCategory.setSelection(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}