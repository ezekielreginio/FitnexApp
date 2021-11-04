package com.pupccis.fitnex.activities.main.trainee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.pupccis.fitnex.activities.healthassessment.HealthAssessment;
import com.pupccis.fitnex.activities.nutritiontracking.NutritionTrackingMain;
import com.pupccis.fitnex.activities.searchengine.SearchEngine;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.activities.trainingdashboard.TrainerDashboard;
import com.pupccis.fitnex.utilities.Constants.UserConstants;
import com.pupccis.fitnex.utilities.Preferences.UserPreferences;

public class TraineeDashboard extends AppCompatActivity {
    private ConstraintLayout buttonSearch, constraintLayoutDashboard, constraintLayoutTraineeStudioNutritionTracker;
    private TextView textViewUserNameHeader;
    private UserPreferences userPreferences;
    private Button button;
    private Context traineeDashboardContext = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_home);
        traineeDashboardContext = getApplicationContext();
        userPreferences = new UserPreferences(getApplicationContext());


//        buttonSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent( TraineeDashboard.this, SearchEngine.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_bottom, R.anim.stay);
//            }
//        });
//
//        constraintLayoutDashboard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), TrainerDashboard.class));
//            }
//        });
//        constraintLayoutTraineeStudioNutritionTracker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), NutritionTrackingMain.class));
//            }
//        });

//        button = findViewById(R.id.buttonQuickAssess);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(TraineeDashboard.this, HealthAssessment.class);
//                startActivity(intent);
//            }
//        });
    }
    public Context getTraineeDashboardContext(){

        return traineeDashboardContext;
    }
}