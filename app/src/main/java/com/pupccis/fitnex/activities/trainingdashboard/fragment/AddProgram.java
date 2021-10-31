package com.pupccis.fitnex.activities.trainingdashboard.fragment;

import static com.pupccis.fitnex.activities.trainingdashboard.fragment.ProgramsFragment.getProgramViewModel;
import static com.pupccis.fitnex.handlers.view.ViewHandler.errorHandler;
import static com.pupccis.fitnex.handlers.view.ViewHandler.setDropdown;
import static com.pupccis.fitnex.handlers.view.ViewHandler.uiErrorHandler;

import android.os.Bundle;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.FragmentAddProgramBinding;
import com.pupccis.fitnex.databinding.FragmentProgramsBinding;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.validation.ValidationResult;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProgram#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProgram extends Fragment implements View.OnClickListener{
    private Program program_intent;
    private static FragmentProgramsBinding fragmentProgramsBinding;
    private static FragmentAddProgramBinding fragmentAddProgramBinding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddProgram() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddProgram newInstance(String param1, String param2) {
        AddProgram fragment = new AddProgram();
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
        // Inflate the layout for this fragment


        //Binding Initialization
        fragmentAddProgramBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_program, container, false);
        fragmentAddProgramBinding.setViewModel(getProgramViewModel());
        fragmentAddProgramBinding.setLifecycleOwner(this);
        fragmentAddProgramBinding.setPresenter(this);
        fragmentAddProgramBinding.executePendingBindings();
        program_intent = fragmentAddProgramBinding.getViewModel().getIntent();

        if(program_intent!=null){
            Log.e("May laman ang intent", program_intent.getName());
            fragmentAddProgramBinding.editTextAddProgramName.setText(program_intent.getName());
            fragmentAddProgramBinding.editTextAddProgramDescription.setText(program_intent.getDescription());
            fragmentAddProgramBinding.editTextAddProgramSessionNumber.setText(program_intent.getSessionNumber());
            fragmentAddProgramBinding.editTextAddProgramDuration.setText(program_intent.getDuration());
            fragmentAddProgramBinding.editTextAddProgramCategory.setSelection(program_intent.getCategory());
            fragmentAddProgramBinding.buttonAddProgramButton.setText("Update Program");
            fragmentAddProgramBinding.getViewModel().setProgramID(program_intent.getProgramID());
        }
//        program_intent = (Program) getActivity().getIntent().getSerializableExtra("program");

        setDropdown(fragmentAddProgramBinding.editTextAddProgramCategory, getContext(), GlobalConstants.spinner_category);
        setDropdown(fragmentAddProgramBinding.spinnerAddProgramPatronLevel, getContext(), GlobalConstants.spinner_patron);
        fragmentAddProgramBinding.editTextAddProgramCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fragmentAddProgramBinding.editTextAddProgramCategory.setSelection(i);
                fragmentAddProgramBinding.getViewModel().setAddProgramCategory(i);
                fragmentAddProgramBinding.textInputProgramCategory.setErrorEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        fragmentAddProgramBinding.spinnerAddProgramPatronLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fragmentAddProgramBinding.spinnerAddProgramPatronLevel.setSelection(i);
                fragmentAddProgramBinding.getViewModel().setAddProgramPatronLevel(i);
                fragmentAddProgramBinding.textInputProgramPatronLevel.setErrorEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


//        setProgramUpdateFields(program_intent);

        return fragmentAddProgramBinding.getRoot();
    }
    @BindingAdapter({"programValidationData"})
    public static void validateProgramData(View view, HashMap<String, Object> validationData) {
        if (validationData != null) {
            ValidationResult result = (ValidationResult) validationData.get("validationResult");
            com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields field = (com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields) validationData.get("field");
            switch (field) {
                case NAME:
                    errorHandler(fragmentAddProgramBinding.textInputProgramName, result);
                    break;
                case DESCRIPTION:
                    errorHandler(fragmentAddProgramBinding.textInputProgramDescription, result);
                    break;
                case SESSION_NUMBER:
                    errorHandler(fragmentAddProgramBinding.textInputProgramSessionNumber, result);
                    break;
                case DURATION:
                    errorHandler(fragmentAddProgramBinding.textInputProgramDuration, result);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view == fragmentAddProgramBinding.buttonAddProgramButton){
            ArrayList<TextInputLayout> textInputLayouts = new ArrayList<>();
            textInputLayouts.add(fragmentAddProgramBinding.textInputProgramName);
            textInputLayouts.add(fragmentAddProgramBinding.textInputProgramDescription);
            textInputLayouts.add(fragmentAddProgramBinding.textInputProgramSessionNumber);
            textInputLayouts.add(fragmentAddProgramBinding.textInputProgramDuration);
            boolean isInvalid = false;

            isInvalid = uiErrorHandler(textInputLayouts);

            if(fragmentAddProgramBinding.editTextAddProgramCategory.getSelectedItemPosition() < 1){
                fragmentAddProgramBinding.textInputProgramCategory.setErrorEnabled(true);
                fragmentAddProgramBinding.textInputProgramCategory.setError("Please Select an Option");
                isInvalid = true;
            }

            if(fragmentAddProgramBinding.spinnerAddProgramPatronLevel.getSelectedItemPosition() < 1){
                fragmentAddProgramBinding.textInputProgramPatronLevel.setErrorEnabled(true);
                fragmentAddProgramBinding.textInputProgramPatronLevel.setError("Please Select an Option");
                isInvalid = true;
            }

            if(isInvalid)
                Toast.makeText(getContext(), "Some Input Fields Are Invalid, Please Try Again.", Toast.LENGTH_SHORT).show();
            else{
                fragmentAddProgramBinding.constraintLayoutProgramProgressBar.setVisibility(View.VISIBLE);
                if(program_intent!= null){
                    MutableLiveData<Program> programMutableLiveData = fragmentAddProgramBinding.getViewModel().updateProgram();

                    programMutableLiveData.observe(fragmentAddProgramBinding.getLifecycleOwner(), new Observer<Program>() {
                        @Override
                        public void onChanged(Program program) {
                            if(program!= null)
                                Toast.makeText(getContext(), "Program Successfully Updated", Toast.LENGTH_SHORT).show();
                            fragmentAddProgramBinding.constraintLayoutProgramProgressBar.setVisibility(View.GONE);
                            programMutableLiveData.removeObserver(this::onChanged);
                            Navigation.findNavController(view).navigate(R.id.action_addProgram_to_programsFragment);
                        }
                    });
                }
                else{
                    MutableLiveData<Program> programMutableLiveData = fragmentAddProgramBinding.getViewModel().insertProgram();
                    programMutableLiveData.observe(fragmentAddProgramBinding.getLifecycleOwner(), new Observer<Program>() {
                        @Override
                        public void onChanged(Program program) {
                            if(program!= null)
                                Toast.makeText(getContext(), "Program Successfully Added!", Toast.LENGTH_SHORT).show();
                            fragmentAddProgramBinding.constraintLayoutProgramProgressBar.setVisibility(View.GONE);
                            programMutableLiveData.removeObserver(this::onChanged);
                            Navigation.findNavController(view).navigate(R.id.action_addProgram_to_programsFragment);
                        }
                    });
                }
            }
        }
        else if(view == fragmentAddProgramBinding.imageViewAddProgramBackButton)
            Navigation.findNavController(view).navigate(R.id.action_addProgram_to_programsFragment);
    }

}