package com.pupccis.fitnex.activities.main.trainee.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.FragmentTraineeAddProgramBinding;
import com.pupccis.fitnex.viewmodel.ProgramViewModel;

public class TraineeAddProgram extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static FragmentTraineeAddProgramBinding binding;
    public TraineeAddProgram() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static TraineeAddProgram newInstance(String param1, String param2) {
        TraineeAddProgram fragment = new TraineeAddProgram();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trainee_add_program, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(new ProgramViewModel());
        binding.setPresenter(this);
        binding.executePendingBindings();
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if(view == binding.buttonAddTraineeProgramButton){
            binding.getViewModel().insertTraineeProgram(getView());
        }
        if(view == binding.imageViewAddTraineeProgramBackButton){
            Navigation.findNavController(view).navigate(R.id.action_traineeAddProgram_to_traineeProgramsFragment);
        }
    }
}