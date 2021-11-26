package com.pupccis.fitnex.activities.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.pupccis.fitnex.api.JavaMailAPI;
import com.pupccis.fitnex.api.MailBody;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.ActivityFitnexTrainerApplicationBinding;
import com.pupccis.fitnex.viewmodel.RoutineViewModel;
import com.pupccis.fitnex.viewmodel.UserViewModel;

public class FitnexTrainerApplication extends AppCompatActivity implements View.OnClickListener{
    private EditText recipient;
    private TextView applyButton;
    private ActivityFitnexTrainerApplicationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitnex_trainer_application);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fitnex_trainer_application);
        binding.setLifecycleOwner(this);
        binding.setViewModel(new UserViewModel());
        binding.executePendingBindings();
        binding.setPresenter(this);
//        recipient = (EditText) findViewById(R.id.editTextApplicationEmail);
//        applyButton = (Button) findViewById(R.id.buttonRegisterButton);
//        applyButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) { sendClick(); }
//        });
    }

    @Override
    public void onClick(View view) {
        //Button Add Profile Picture
        if(view == binding.btnAddApplicantProfilePicture){
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
        }

        //Button Add Resume
        else if(view == binding.btnAddApplicantResume){
            Dexter.withContext(getApplicationContext())
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Intent intent = new Intent();
                            intent.setType("application/pdf");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intent, 103);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).check();
        }

        else if(view == binding.buttonRegisterButton){
            binding.constraintLayoutApplyTrainerProgressBar.setVisibility(View.VISIBLE);
            binding.getViewModel().applyTrainer().observe(binding.getLifecycleOwner(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if(aBoolean == true){
                        binding.constraintLayoutApplyTrainerProgressBar.setVisibility(View.GONE);
                        sendClick();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            if(requestCode == 102){
                binding.constraintLayoutContainerApplicantProfile.setVisibility(View.GONE);
                binding.imageViewApplicantProfilePicture.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext())
                        .load(data.getData()).into(binding.imageViewApplicantProfilePicture);
                binding.getViewModel().setApplicantProfilePicture(data.getData());
                binding.getViewModel().setApplicantProfilePictureFileType(getContentResolver().getType(data.getData()));
//            binding.constraintLayoutContainerRoutineGuide.setVisibility(View.GONE);
//
//            binding.getViewModel().setRoutineGuideUri(data.getData());
//            binding.getViewModel().setRoutineGuideFileType(getContentResolver().getType(data.getData()));
//            Glide.with(getApplicationContext())
//                    .load(binding.getViewModel().getRoutineGuideUri()).into(binding.imageViewRoutineGuide);
//            binding.imageViewRoutineGuide.setVisibility(View.VISIBLE);
////            binding.imageViewRoutineGuide.setImageURI(routineGuideUri);
            }
            else if(requestCode == 103){
                Log.e("PDF", "uploaded");
                binding.getViewModel().setApplicantResume(data.getData());
                binding.getViewModel().setApplicantResumeFileType(getContentResolver().getType(data.getData()));
            }
        }
    }
    private void sendClick(){
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, binding.getViewModel().getApplicantEmail(), MailBody.getSubject(), MailBody.getBody());
        javaMailAPI.execute();
    }
    public void onLoginClick (View view){
        startActivity(new Intent(this, FitnexLogin.class ));
        overridePendingTransition(R.anim.from_left, android.R.anim.slide_out_right);
    }
}