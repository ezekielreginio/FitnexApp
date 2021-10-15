package com.pupccis.fitnex.activities.routine;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.adapters.RoutineTrackerAdapter;
import com.pupccis.fitnex.databinding.FragmentRoutineBinding;
import com.pupccis.fitnex.handlers.view.WrapContentLinearLayoutManager;
import com.pupccis.fitnex.model.Routine;
import com.pupccis.fitnex.model.RoutineData;

import java.util.ArrayList;
import java.util.List;

public class RoutineFragment extends Fragment implements View.OnClickListener {



    private Routine routine;

    private FragmentRoutineBinding binding;

    private long pauseOffset;

    private RoutineTrackerAdapter adapter;
    private List<RoutineData> routineDataList;
    private int position = 0;
    private String programID;

    public RoutineFragment() {
        // Required empty public constructor
    }

    public RoutineFragment(Routine routine) {
        this.routine = routine;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        routineDataList = new ArrayList<>();
        programID = ((RoutineTracker) getActivity()).getProgramID();
        RoutineData routineData = binding.getViewModel().setRoutineData(routine);
        for(int i=0;i<routine.getSets();i++){
            routineDataList.add(routineData);
        }
        routine.setUserID(((RoutineTracker) getActivity()).getUserID());



        binding.recyclerViewRoutinePage.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new RoutineTrackerAdapter(routineDataList, routine);
        adapter.setRoutineViewModel(binding.getViewModel());
        binding.recyclerViewRoutinePage.setAdapter(adapter);

        binding.getViewModel().observeRoutineRealtime(routine).observe(binding.getLifecycleOwner(), new Observer<List<RoutineData>>() {
            @Override
            public void onChanged(List<RoutineData> routineDataList) {
                for(RoutineData routineData1: routineDataList){
                    RoutineFragment.this.routineDataList.set(routineData1.getPosition(), routineData1);
                    adapter.notifyItemChanged(routineData1.getPosition());

                }
            }
        });

        binding.buttonLogRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int routineCount = ((RoutineTracker) getActivity()).getRoutineCount();
                int currentRoutine = ((RoutineTracker) getActivity()).getSelectedRoutinePosition()+1;

                if(position<routine.getSets()){
                    ((RoutineTracker) getActivity()).showRoutineRestTimer();
                    RoutineData newRoutineData = binding.getViewModel().setRoutineDataList(routine, position, programID);
                    routineDataList.set(position, newRoutineData);
                    //routineDataList.get(position).setCompleted(true);
                    adapter.notifyItemChanged(position);

                    position=position+1;
                }
                else{
                    if(currentRoutine == routineCount)
                        ((RoutineTracker) getActivity()).showRoutineFinishTimer();

                    else{
                        ((RoutineTracker) getActivity()).showRoutineRestTimer();
                        ((RoutineTracker) getActivity()).nextRoutine();
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_routine, container, false);
        binding.setLifecycleOwner(this);
        binding.setPresenter(this);
        binding.setViewModel(((RoutineTracker) getActivity()).getViewModel());
        binding.executePendingBindings();

        //View view =  inflater.inflate(R.layout.fragment_routine, container, false);
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {


    }

    @Override
    public void onStart() {
        super.onStart();
        startRoutineTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        startRoutineTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.chronometerRoutineTimer.stop();
        pauseRoutineTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseRoutineTimer();
    }

    private void startRoutineTimer() {
        binding.chronometerRoutineTimer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
        binding.chronometerRoutineTimer.start();
    }

    private void pauseRoutineTimer(){
        binding.chronometerRoutineTimer.stop();
        pauseOffset = SystemClock.elapsedRealtime() - binding.chronometerRoutineTimer.getBase();
    }


}