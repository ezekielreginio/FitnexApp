package com.pupccis.fitnex.activities.trainingdashboard.fragment;

import static com.pupccis.fitnex.activities.trainingdashboard.TrainerDashboard.getIntentFromFragment;
import static com.pupccis.fitnex.handlers.viewmodel.ViewModelHandler.getFirebaseUIProgramOptions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pupccis.fitnex.activities.patron.PatronInitialActivity;
import com.pupccis.fitnex.activities.routine.RoutinePage;
import com.pupccis.fitnex.activities.trainingdashboard.MySwipeHelper;
import com.pupccis.fitnex.activities.trainingdashboard.TrainerDashboard;
import com.pupccis.fitnex.adapters.ProgramAdapter;
import com.pupccis.fitnex.api.enums.AccessType;
import com.pupccis.fitnex.databinding.FragmentAddProgramBinding;
import com.pupccis.fitnex.databinding.FragmentProgramsBinding;
import com.pupccis.fitnex.handlers.view.WrapContentLinearLayoutManager;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.utilities.Constants.PatronConstants;
import com.pupccis.fitnex.utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.utilities.Constants.UserConstants;
import com.pupccis.fitnex.utilities.Preferences.UserPreferences;
import com.pupccis.fitnex.validation.Services.MyButtonClickListener;
import com.pupccis.fitnex.viewmodel.ProgramViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgramsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgramsFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private UserPreferences userPreferences;
    private String userType;
    private boolean patronSet;
    private static FragmentProgramsBinding fragmentProgramsBinding;
    private ProgramViewModel programViewModel = new ProgramViewModel();
    private ProgramAdapter adapter;
    private RecyclerView recyclerView;
    public ProgramsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProgramsFragment newInstance(String param1, String param2) {
        ProgramsFragment fragment = new ProgramsFragment();
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
        userPreferences = new UserPreferences(getActivity().getApplicationContext());
        patronSet = userPreferences.getBoolean(PatronConstants.KEY_PATRON_SET);
        userType = userPreferences.getString(UserConstants.KEY_USER_TYPE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d("ViewCreate", "onViewCreateExecuted");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Started", "onStartExecuted");
        adapter.startListening();;
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("OnCreateView", "executed");
        // Inflate the layout for this fragment
        fragmentProgramsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_programs, container, false);
        fragmentProgramsBinding.setLifecycleOwner(this);
        fragmentProgramsBinding.setViewModel(programViewModel);
        fragmentProgramsBinding.setPresenter(this);
        fragmentProgramsBinding.executePendingBindings();

        //Get FirestoreOptions From ViewModel
        FirestoreRecyclerOptions<Program> options = getFirebaseUIProgramOptions();

        //Instantiate and Set RecyclerView Settings
        recyclerView = fragmentProgramsBinding.programsRecyclerView;
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        //Instantiate Adapter and Bind to RecyclerView
        adapter = new ProgramAdapter(options);
        adapter.setAccessType(AccessType.OWNER);
        adapter.setProgramViewModel(programViewModel);
        recyclerView.setAdapter(adapter);

        //ViewModel Observers
        fragmentProgramsBinding.getViewModel().routineObserver().observe(fragmentProgramsBinding.getLifecycleOwner(), new Observer<Program>() {
            @Override
            public void onChanged(Program program) {
                Intent intent = new Intent(getContext(), RoutinePage.class);
                intent.putExtra("program", program);
                startActivity(intent);
            }
        });
        fragmentProgramsBinding.getViewModel().updateObserver().observe(fragmentProgramsBinding.getLifecycleOwner(), new Observer<Program>() {
            @Override
            public void onChanged(Program program) {
                Intent intent= new Intent();
                intent.putExtra("program", program);
                Log.e("Program intent from program fragment", program.getProgramID());
                fragmentProgramsBinding.getViewModel().setIntent((Program) intent.getSerializableExtra("program"));
                Navigation.findNavController(getView()).navigate(R.id.action_programsFragment_to_addProgram);
            }
        });

        fragmentProgramsBinding.getViewModel().deleteObserver().observe(fragmentProgramsBinding.getLifecycleOwner(), new Observer<Program>() {
            @Override
            public void onChanged(Program program) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Log.d("Delete", "Clicked");
                                fragmentProgramsBinding.getViewModel().deleteProgram(program.getProgramID());
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you wish to delete this program?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        //Get the View from binding and return its value *REQUIRED

        return fragmentProgramsBinding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if(view == fragmentProgramsBinding.imageViewAddProgram){
            if(patronSet){
                fragmentProgramsBinding.getViewModel().clearIntent();
                Navigation.findNavController(view).navigate(R.id.action_programsFragment_to_addProgram);
            }
            else
                fragmentProgramsBinding.constraintLayoutPatronNotSetModal.setVisibility(View.VISIBLE);
        }
        if(view == fragmentProgramsBinding.buttonSetPatronData){
            startActivity(new Intent(getContext(), PatronInitialActivity.class));
        }
    }

    public static ProgramViewModel getProgramViewModel(){
        return fragmentProgramsBinding.getViewModel();
    }
}