package com.pupccis.fitnex.activities.routine;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.ActivityRateProgramBinding;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.utilities.Constants.UserConstants;
import com.pupccis.fitnex.utilities.Preferences.UserPreferences;
import com.pupccis.fitnex.viewmodel.ProgramRatingViewModel;

public class RateProgram extends AppCompatActivity implements View.OnClickListener{
    private static ActivityRateProgramBinding binding;
    private Program programIntent;
    private UserPreferences userPreferences;
    private ProgramRatingViewModel viewModel = new ProgramRatingViewModel();

    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rate_program);
        binding.setLifecycleOwner(this);
        binding.setPresenter(this);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        programIntent = (Program) getIntent().getSerializableExtra("programData");
        userPreferences = new UserPreferences(getApplicationContext());
        userName = userPreferences.getString(UserConstants.KEY_USER_NAME);

        binding.textViewRateProgramName.setText(programIntent.getName());
    }

    @Override
    public void onClick(View view) {
        if(view == binding.btnAddReviewPhoto){
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
        else if(view == binding.buttonRateProgramSubmit){
            binding.getViewModel().setRating(binding.ratingBar.getRating());
            binding.getViewModel().setRateProgramImageFiletype(getContentResolver().getType(binding.getViewModel().getRateProgramImage()));
            binding.getViewModel().saveRating(programIntent, userName);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 102 && resultCode==RESULT_OK){
            Glide.with(getApplicationContext())
                    .load(data.getData()).into(binding.imageViewRateProgramPhoto);
            viewModel.setRateProgramImage(data.getData());
            binding.constraintLayoutContainerReviewPhoto.setVisibility(View.GONE);
            binding.imageViewRateProgramPhoto.setVisibility(View.VISIBLE);
        }
    }
}