package com.pupccis.fitnex.activities.healthassessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.ActivityHealthAssessmentBinding;
import com.pupccis.fitnex.viewmodel.HealthAssessmentViewModel;
import com.pupccis.fitnex.viewmodel.LoginViewModel;

public class HealthAssessment extends AppCompatActivity {
    private HealthAssessmentViewModel viewModel;
    private static ActivityHealthAssessmentBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_health_assessment);
        binding.setViewModel(new HealthAssessmentViewModel());
        binding.setLifecycleOwner(this);
        binding.setPresenter(this);
        binding.executePendingBindings();
    }
    public HealthAssessmentViewModel getViewModel(){
        return binding.getViewModel();
    }
}