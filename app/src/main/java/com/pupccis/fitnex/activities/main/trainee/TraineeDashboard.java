package com.pupccis.fitnex.activities.main.trainee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.ActivityTraineeDashboardBinding;
import com.pupccis.fitnex.utilities.Preferences.UserPreferences;

public class TraineeDashboard extends AppCompatActivity {
    private ConstraintLayout buttonSearch, constraintLayoutDashboard, constraintLayoutTraineeStudioNutritionTracker;
    private TextView textViewUserNameHeader;
    private UserPreferences userPreferences;
    private Button button;
    private Context traineeDashboardContext = null;
    private static ActivityTraineeDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trainee_dashboard);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();
        traineeDashboardContext = getApplicationContext();
        userPreferences = new UserPreferences(getApplicationContext());

        View navHostFragment = binding.getRoot().findViewById(R.id.navHostFragmentTraineeDashboard);
        NavController navController = Navigation.findNavController(this, R.id.navHostFragmentTraineeDashboard);
        NavigationUI.setupWithNavController(binding.bottomNavigationTraineeDashboard, navController);

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

}