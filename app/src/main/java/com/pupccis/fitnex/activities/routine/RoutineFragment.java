package com.pupccis.fitnex.activities.routine;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoutineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoutineFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RoutineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoutineFragment newInstance(String param1, String param2) {
        RoutineFragment fragment = new RoutineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

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

        binding.recyclerViewRoutinePage.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new RoutineTrackerAdapter(routineDataList, routine);
        adapter.setRoutineViewModel(binding.getViewModel());
        binding.recyclerViewRoutinePage.setAdapter(adapter);

        binding.buttonLogRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int routineCount = ((RoutineTracker) getActivity()).getRoutineCount();
                int currentRoutine = ((RoutineTracker) getActivity()).getSelectedRoutinePosition()+1;

                if(position<routine.getSets()){
                    ((RoutineTracker) getActivity()).showRoutineRestTimer();
                    RoutineData newRoutineData = binding.getViewModel().setRoutineDataList(routine, position, programID);
                    routineDataList.set(position, newRoutineData);
                    Log.d("Reps", newRoutineData.getReps()+"");
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