package com.pupccis.fitnex.activities.patron;

import static androidx.core.content.ContextCompat.getSystemService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.activities.trainingdashboard.TrainerDashboard;
import com.pupccis.fitnex.databinding.ActivityPatronMainBinding;
import com.pupccis.fitnex.utilities.Constants.PatronConstants;
import com.pupccis.fitnex.utilities.Preferences.UserPreferences;
import com.pupccis.fitnex.viewmodel.PatronViewModel;

public class PatronInitialActivity extends AppCompatActivity {
    private PatronViewModel patronViewModel = new PatronViewModel();
    private ActivityPatronMainBinding binding;
    private UserPreferences userPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_patron_main);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();
        userPreferences = new UserPreferences(getApplicationContext());

    }

    public PatronViewModel getPatronViewModel() {
        return patronViewModel;
    }

    public void showProgressBar(int view){
        binding.constraintLayoutPatronProgressBar.setVisibility(view);
    }

    public void showPatronPage(){
        userPreferences.putBoolean(PatronConstants.KEY_PATRON_SET, true);
        Intent intent = new Intent(getApplicationContext(), TrainerPatronPage.class);
        startActivity(intent);
        finish();
    }


}