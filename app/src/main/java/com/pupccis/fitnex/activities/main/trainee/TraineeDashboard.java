package com.pupccis.fitnex.activities.main.trainee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import com.pupccis.fitnex.activities.healthassessment.HealthAssessment;
import com.pupccis.fitnex.activities.nutritiontracking.NutritionTrackingMain;
import com.pupccis.fitnex.activities.searchengine.SearchEngine;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.activities.trainingdashboard.TrainerDashboard;
import com.pupccis.fitnex.databinding.ActivityTraineeHomeBinding;
import com.pupccis.fitnex.utilities.Constants.UserConstants;
import com.pupccis.fitnex.utilities.Preferences.UserPreferences;
import com.pupccis.fitnex.viewmodel.TraineeDashboardViewModel;

public class TraineeDashboard extends AppCompatActivity {
    private ConstraintLayout buttonSearch, constraintLayoutDashboard, constraintLayoutTraineeStudioNutritionTracker;
    private TextView textViewUserNameHeader;
    private UserPreferences userPreferences;
    private ImageView topupbtn;
    private Button button;
    private static ActivityTraineeHomeBinding binding;
    private TraineeDashboardViewModel traineeDashboardViewModel = new TraineeDashboardViewModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trainee_home);
        binding.setLifecycleOwner(this);
        binding.setViewModel(traineeDashboardViewModel);
        binding.setPresenter(this);
        binding.executePendingBindings();

        userPreferences = new UserPreferences(getApplicationContext());

        buttonSearch = findViewById(R.id.layoutTraineeSearch);
        topupbtn = findViewById(R.id.imageViewTraineeCoin);
        constraintLayoutDashboard = findViewById(R.id.constraintLayoutDashboard);
        constraintLayoutTraineeStudioNutritionTracker = findViewById(R.id.constraintLayoutTraineeStudioNutritionTracker);
        textViewUserNameHeader = findViewById(R.id.textViewUserNameHeader);
        textViewUserNameHeader.setText("Welcome, \n"+userPreferences.getString(UserConstants.KEY_USER_NAME));

        //Observers
        binding.getViewModel().getMldUserTraineeCoins().observe(this, string->{});

        topupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TraineeDashboard.this, TopupCoins.class));
            }
        });
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent( TraineeDashboard.this, SearchEngine.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.stay);
            }
        });

        constraintLayoutDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TrainerDashboard.class));
            }
        });
        constraintLayoutTraineeStudioNutritionTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NutritionTrackingMain.class));
            }
        });

//        button = findViewById(R.id.buttonQuickAssess);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(TraineeDashboard.this, HealthAssessment.class);
//                startActivity(intent);
//            }
//        });
    }
}