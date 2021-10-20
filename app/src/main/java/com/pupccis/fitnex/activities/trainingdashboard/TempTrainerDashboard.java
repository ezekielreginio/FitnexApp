package com.pupccis.fitnex.activities.trainingdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.ActivityTempTrainerDashboardBinding;

public class TempTrainerDashboard extends AppCompatActivity {
    private static ActivityTempTrainerDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_trainer_dashboard);

        //View navHostFragment = binding.getRoot().findViewById(R.id.navHostFragmentTrainerDashboard);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationTrainerDashboard);
        NavController navController = Navigation.findNavController(this, R.id.navHostFragmentTrainerDashboard);
        NavigationUI.setupWithNavController(binding.bottomNavigationTrainerDashboard, navController);
    }
}