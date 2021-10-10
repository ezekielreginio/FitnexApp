package com.pupccis.fitnex.activities.main.trainee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pupccis.fitnex.activities.healthassessment.HealthAssessment;
import com.pupccis.fitnex.activities.searchengine.SearchEngine;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.activities.trainingdashboard.TrainerDashboard;

public class TraineeDashboard extends AppCompatActivity {
    private ConstraintLayout buttonSearch, constraintLayoutDashboard;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_home);

        buttonSearch = findViewById(R.id.layoutTraineeSearch);
        constraintLayoutDashboard = findViewById(R.id.constraintLayoutDashboard);

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

        button = findViewById(R.id.buttonQuickAssess);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TraineeDashboard.this, HealthAssessment.class);
                startActivity(intent);
            }
        });
    }
}