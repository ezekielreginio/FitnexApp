package com.pupccis.fitnex.activities.routine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.ActivityRateProgramBinding;

public class RateProgram extends AppCompatActivity {
    private static ActivityRateProgramBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rate_program);
        binding.executePendingBindings();
        binding.setLifecycleOwner(this);
    }
}