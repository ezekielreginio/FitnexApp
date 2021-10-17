package com.pupccis.fitnex.activities.healthassessment;

import static com.pupccis.fitnex.handlers.view.ViewHandler.errorHandler;
import static com.pupccis.fitnex.handlers.view.ViewHandler.uiErrorHandler;

import android.os.Bundle;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.FragmentStep1Binding;
import com.pupccis.fitnex.validation.ValidationResult;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Step1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Step1 extends Fragment implements View.OnClickListener{
    private static FragmentStep1Binding binding;
    private ImageView button;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Step1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Step1.
     */
    // TODO: Rename and change types and number of parameters
    public static Step1 newInstance(String param1, String param2) {
        Step1 fragment = new Step1();
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_step1, container, false);
        binding.setViewModel(((HealthAssessment)getActivity()).getViewModel());
        binding.setPresenter(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if(view == binding.imageViewOneNext){
            ArrayList<TextInputLayout> textInputLayouts = new ArrayList<>() ;
            textInputLayouts.add(binding.textInputHAHeight);
            textInputLayouts.add(binding.textInputHAWeight);
            boolean isInvalid = false;

            isInvalid = uiErrorHandler(textInputLayouts);

            if(isInvalid)
                Toast.makeText(getContext(), "Some Input Fields Are Invalid, Please Try Again.", Toast.LENGTH_SHORT).show();
            else
                Navigation.findNavController(view).navigate(R.id.action_step1_to_step2);
            binding.getViewModel();

        }
        if (view == binding.imageViewOneBack){
            Navigation.findNavController(view).navigate(R.id.action_step1_to_privacyTerms);
        }
    }
    @BindingAdapter({"healthAssessmentValidationData"})
    public static void validateHealthAssessmentData(View view, HashMap<String, Object> validationData){
        if (validationData != null) {
            ValidationResult result = (ValidationResult) validationData.get("validationResult");
            com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields field = (com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields) validationData.get("field");
            switch (field) {
                case HEIGHT:
                    errorHandler(binding.textInputHAHeight, result);
                    break;
                case WEIGHT:
                    errorHandler(binding.textInputHAWeight, result);
                    break;
            }
        }
    }
}