package com.pupccis.fitnex.activities.routine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.activities.main.trainer.AddFitnessClass;
import com.pupccis.fitnex.activities.main.trainer.TrainerDashboard;
import com.pupccis.fitnex.api.adapter.fragmentAdapters.RoutineFragmentAdapter;
import com.pupccis.fitnex.databinding.ActivityRoutineTrackerBinding;
import com.pupccis.fitnex.model.Routine;
import com.pupccis.fitnex.utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.viewmodel.RoutineViewModel;

import java.util.List;

public class RoutineTracker extends AppCompatActivity {
    private ActivityRoutineTrackerBinding binding;
    private RoutineFragmentAdapter routineFragmentAdapter;
    private Intent page_intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_routine_tracker);
        binding.setViewModel(new RoutineViewModel());
        binding.setPresenter(this);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();
        //setContentView(R.layout.activity_routine_tracker);
        String programID = getIntent().getStringExtra(ProgramConstants.KEY_PROGRAM_ID);

        //Observers
        binding.getViewModel().getRoutineData(programID).observe(this, new Observer<List<Routine>>() {
            @Override
            public void onChanged(List<Routine> routines) {
                Log.d("Routine", "Received");
                initializeFragments(routines);
            }
        });
    }

    private void initializeFragments(List<Routine> routines) {
        for(Routine routine: routines)
            binding.tabLayoutRoutineTracker.addTab(binding.tabLayoutRoutineTracker.newTab().setText(routine.getName()));

        FragmentManager fm = getSupportFragmentManager();
        routineFragmentAdapter = new RoutineFragmentAdapter(fm, getLifecycle());
        routineFragmentAdapter.setRoutineList(routines);
        binding.viewPager2Routine.setAdapter(routineFragmentAdapter);
        binding.viewPager2Routine.setCurrentItem(0);

        binding.tabLayoutRoutineTracker.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager2Routine.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.viewPager2Routine.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayoutRoutineTracker.selectTab(binding.tabLayoutRoutineTracker.getTabAt(position));
            }
        });

    }
}