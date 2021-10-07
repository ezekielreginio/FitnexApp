package com.pupccis.fitnex.activities.routine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.api.adapter.fragmentAdapters.RoutineFragmentAdapter;
import com.pupccis.fitnex.databinding.ActivityRoutineTrackerBinding;
import com.pupccis.fitnex.handlers.view.TimerViewHandler;
import com.pupccis.fitnex.model.Routine;
import com.pupccis.fitnex.utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.viewmodel.RoutineViewModel;

import java.util.List;
import java.util.concurrent.Callable;

public class RoutineTracker extends AppCompatActivity implements View.OnClickListener{
    private ActivityRoutineTrackerBinding binding;
    private RoutineFragmentAdapter routineFragmentAdapter;

    private TimerViewHandler timerViewHandler;
    private RoutineViewModel routineViewModel = new RoutineViewModel();
    private String programID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Binding Instantiation
        binding = DataBindingUtil.setContentView(this, R.layout.activity_routine_tracker);
        binding.setViewModel(routineViewModel);
        binding.setPresenter(this);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        //Extra Intents
        programID = getIntent().getStringExtra(ProgramConstants.KEY_PROGRAM_ID);

        //TimerViewHandler Instantiation
        timerViewHandler = new TimerViewHandler(60000, binding.timerProgressBar, binding.textViewTimer, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                executeOnFinish();
                return null;
            }
        });

        //Observers
        binding.getViewModel().getRoutineData(programID).observe(this, new Observer<List<Routine>>() {
            @Override
            public void onChanged(List<Routine> routines) {
                Log.d("Routine", "Received");
                initializeFragments(routines);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == binding.buttonSkipRest){
            hideRoutineRestTimer();
        }
    }

    //Private Methods
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

    //Public Methods
    public void showRoutineRestTimer(){
        binding.constraintLayoutRoutineCountdown.setVisibility(View.VISIBLE);
        timerViewHandler.startTimer(true);
    }

    public void showRoutineFinishTimer(){
        binding.linearLayoutRestTitle.setVisibility(View.GONE);
        binding.linearLayoutFinished.setVisibility(View.VISIBLE);
        binding.constraintLayoutRoutineCountdown.setVisibility(View.VISIBLE);
        timerViewHandler.startTimer(true);
    }

    public void hideRoutineRestTimer(){
        binding.constraintLayoutRoutineCountdown.setVisibility(View.GONE);
        timerViewHandler.stopTimer();
    }

    public void executeOnFinish(){
        hideRoutineRestTimer();
        int routineCount = getRoutineCount();
        int currentRoutine = getSelectedRoutinePosition()+1;
        if(routineCount == currentRoutine)
            finish();
    }

    public String getProgramID(){
        return programID;
    }

    public int getRoutineCount(){
        int routineCount = binding.tabLayoutRoutineTracker.getTabCount();
        return routineCount;
    }

    public int getSelectedRoutinePosition(){
        int routinePosition = binding.tabLayoutRoutineTracker.getSelectedTabPosition();
        return routinePosition;
    }

    public void nextRoutine(){
        binding.viewPager2Routine.setCurrentItem(binding.viewPager2Routine.getCurrentItem()+1);
        binding.tabLayoutRoutineTracker.selectTab(binding.tabLayoutRoutineTracker.getTabAt(binding.viewPager2Routine.getCurrentItem()));
    }

    public RoutineViewModel getViewModel(){
        return routineViewModel;
    }
}