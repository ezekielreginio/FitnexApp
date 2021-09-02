package com.pupccis.fitnex.Activities.Main.Trainee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pupccis.fitnex.Activities.SearchEngine.SearchEngine;
import com.pupccis.fitnex.R;

public class TraineeDashboard extends AppCompatActivity {
    private ConstraintLayout buttonSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_dashboard);

        buttonSearch = findViewById(R.id.layoutTraineeSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent( TraineeDashboard.this, SearchEngine.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.stay);
            }
        });
    }
}