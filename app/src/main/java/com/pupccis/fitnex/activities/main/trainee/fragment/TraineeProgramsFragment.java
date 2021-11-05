package com.pupccis.fitnex.activities.main.trainee.fragment;

import static com.pupccis.fitnex.handlers.viewmodel.ViewModelHandler.getFirebaseUITraineeProgramOptions;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.adapters.ProgramAdapter;
import com.pupccis.fitnex.api.enums.AccessType;
import com.pupccis.fitnex.databinding.FragmentTraineeProgramsBinding;
import com.pupccis.fitnex.handlers.view.WrapContentLinearLayoutManager;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.viewmodel.ProgramViewModel;


public class TraineeProgramsFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static FragmentTraineeProgramsBinding binding;
    private ProgramViewModel programViewModel = new ProgramViewModel();
    private ProgramAdapter adapter;
    private RecyclerView recyclerView;
    private Context traineeProgramsFragmentContext;

    public TraineeProgramsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TraineeProgramsFragment newInstance(String param1, String param2) {
        TraineeProgramsFragment fragment = new TraineeProgramsFragment();
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
        traineeProgramsFragmentContext = container.getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trainee_programs, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(programViewModel);
        binding.setPresenter(this);
        binding.executePendingBindings();

        FirestoreRecyclerOptions<Program> options = getFirebaseUITraineeProgramOptions();

        recyclerView = binding.traineeProgramsRecyclerView;
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        adapter = new ProgramAdapter(options);
        adapter.setAccessType(AccessType.OWNER);
        adapter.setProgramViewModel(programViewModel);
        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
    if(view == binding.imageViewAddTraineeProgram){
        Navigation.findNavController(view).navigate(R.id.action_traineeProgramsFragment_to_traineeAddProgram);
    }

    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();;
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}