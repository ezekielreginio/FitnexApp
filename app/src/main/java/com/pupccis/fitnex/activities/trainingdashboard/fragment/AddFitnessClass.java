package com.pupccis.fitnex.activities.trainingdashboard.fragment;

import static com.pupccis.fitnex.activities.trainingdashboard.fragment.ClassFragment.getFitnessClassViewModel;
import static com.pupccis.fitnex.handlers.view.ViewHandler.errorHandler;
import static com.pupccis.fitnex.handlers.view.ViewHandler.setDropdown;
import static com.pupccis.fitnex.handlers.view.ViewHandler.uiErrorHandler;

import android.app.TimePickerDialog;
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
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.FragmentAddFitnessClassBinding;
import com.pupccis.fitnex.model.FitnessClass;
import com.pupccis.fitnex.utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.validation.ValidationResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFitnessClass#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFitnessClass extends Fragment implements View.OnClickListener , AdapterView.OnItemSelectedListener{
    private static FragmentAddFitnessClassBinding fragmentAddFitnessClassBinding;
    private FitnessClass fitness_intent;
    private Calendar calendar = Calendar.getInstance();
    private String timeStartHolder, timeEndHolder;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFitnessClass() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFitnessClass.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFitnessClass newInstance(String param1, String param2) {
        AddFitnessClass fragment = new AddFitnessClass();
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
        fragmentAddFitnessClassBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_fitness_class, container, false);
        fragmentAddFitnessClassBinding.setViewModel(getFitnessClassViewModel());
        fragmentAddFitnessClassBinding.setLifecycleOwner(this);
        fragmentAddFitnessClassBinding.setPresenter(this);
        fragmentAddFitnessClassBinding.executePendingBindings();
        fitness_intent = fragmentAddFitnessClassBinding.getViewModel().getIntent();

        //Time Selector Focusable False
        fragmentAddFitnessClassBinding.editTextTimeStart.setFocusable(false);
        fragmentAddFitnessClassBinding.editTextTimeEnd.setFocusable(false);

        setDropdown(fragmentAddFitnessClassBinding.editTextAddFitnessClassCategory, getContext(), GlobalConstants.spinner_category);
        fragmentAddFitnessClassBinding.editTextAddFitnessClassCategory.setOnItemSelectedListener(this);

        if(fitness_intent != null){
            Log.e("Fitness ID",fitness_intent.getClassID());
            fragmentAddFitnessClassBinding.editTextAddClassName.setText(fitness_intent.getClassName());
            fragmentAddFitnessClassBinding.editTextAddFitnessClassCategory.setSelection(fitness_intent.getCategory());
            fragmentAddFitnessClassBinding.editTextAddClassSessionNumber.setText(fitness_intent.getSessionNo());
            fragmentAddFitnessClassBinding.editTextTimeStart.setText(fitness_intent.getTimeStart());
            fragmentAddFitnessClassBinding.editTextTimeEnd.setText(fitness_intent.getTimeEnd());
            fragmentAddFitnessClassBinding.editTextAddFitnessClassDescription.setText(fitness_intent.getDescription());
            fragmentAddFitnessClassBinding.editTextAddClassDuration.setText(fitness_intent.getDuration());
            //fitnessClassCategory.setSelection(fitness_intent.getCategory());
            fragmentAddFitnessClassBinding.getViewModel().setFitnessClassID(fitness_intent.getClassID());
            fragmentAddFitnessClassBinding.buttonAddClassButton.setText("Update Class");
        }
        return fragmentAddFitnessClassBinding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if(view == fragmentAddFitnessClassBinding.buttonAddClassButton){
            Log.d("Register", "clicked");
            ArrayList<TextInputLayout> textInputLayouts = new ArrayList<>() ;
            textInputLayouts.add(fragmentAddFitnessClassBinding.textInputClassName);
            textInputLayouts.add(fragmentAddFitnessClassBinding.textInputClassDescription);
            textInputLayouts.add(fragmentAddFitnessClassBinding.textInputClassSessionNumber);
            textInputLayouts.add(fragmentAddFitnessClassBinding.textInputClassTimeStart);
            textInputLayouts.add(fragmentAddFitnessClassBinding.textInputClassTimeEnd);
            textInputLayouts.add(fragmentAddFitnessClassBinding.textInputClassDuration);
            boolean isInvalid = false;

            isInvalid = uiErrorHandler(textInputLayouts);

            if(fragmentAddFitnessClassBinding.editTextAddFitnessClassCategory.getSelectedItemPosition() < 1){
                fragmentAddFitnessClassBinding.textInputClassCategory.setErrorEnabled(true);
                fragmentAddFitnessClassBinding.textInputClassCategory.setError("Please Select a Category");
                isInvalid = true;
            }


            if(isInvalid)
                Toast.makeText(getContext(), "Some Input Fields Are Invalid, Please Try Again.", Toast.LENGTH_SHORT).show();
            else{
                fragmentAddFitnessClassBinding.constraintLayoutFitnessClassProgressBar.setVisibility(View.VISIBLE);
                if(fitness_intent != null){
                    MutableLiveData<FitnessClass> fitnessClassMutableLiveData = fragmentAddFitnessClassBinding.getViewModel().updateFitnessClass();
                    fitnessClassMutableLiveData.observe(fragmentAddFitnessClassBinding.getLifecycleOwner(), new Observer<FitnessClass>() {
                        @Override
                        public void onChanged(FitnessClass fitnessClass) {

                            fitnessClassMutableLiveData.removeObserver(this::onChanged);
                        }
                    });
                }
                else{
                    MutableLiveData<FitnessClass> fitnessClassMutableLiveData = fragmentAddFitnessClassBinding.getViewModel().insertFitnessClass();
                    fitnessClassMutableLiveData.observe(fragmentAddFitnessClassBinding.getLifecycleOwner(), new Observer<FitnessClass>() {
                        @Override
                        public void onChanged(FitnessClass fitnessClass) {
                            if(fitnessClass!= null)
                                Toast.makeText(getContext(), "Program Successfully Registered", Toast.LENGTH_SHORT).show();

                            fitnessClassMutableLiveData.removeObserver(this::onChanged);
                        }
                    });
                }
                fragmentAddFitnessClassBinding.constraintLayoutFitnessClassProgressBar.setVisibility(View.GONE);
                Navigation.findNavController(view).navigate(R.id.action_addFitnessClass_to_classFragment);
            }
        }
        else if (view == fragmentAddFitnessClassBinding.imageViewAddFitnessClassBackButton){
            Navigation.findNavController(view).navigate(R.id.action_addFitnessClass_to_classFragment);
        }
        else if (view == fragmentAddFitnessClassBinding.editTextTimeStart)
            showTimeDialog(fragmentAddFitnessClassBinding.editTextTimeStart, 0);
        else if(view == fragmentAddFitnessClassBinding.editTextTimeEnd)
            showTimeDialog(fragmentAddFitnessClassBinding.editTextTimeStart, 1);

    }
    private String showTimeDialog(EditText time, int setter) {

        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int mins = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, i);
                c.set(Calendar.MINUTE, i1);
                c.setTimeZone(TimeZone.getDefault());
                SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
                String time = format.format(c.getTime());
                if (setter ==0){
                    fragmentAddFitnessClassBinding.editTextTimeStart.setText(time);
                    timeStartHolder = fragmentAddFitnessClassBinding.editTextTimeStart.getText().toString();
                }
                else{
                    fragmentAddFitnessClassBinding.editTextTimeEnd.setText(time);
                    timeEndHolder = fragmentAddFitnessClassBinding.editTextTimeEnd.getText().toString();
                }
            }
        },hours, mins, false);
        timePickerDialog.show();
        return time.toString();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        fragmentAddFitnessClassBinding.editTextAddFitnessClassCategory.setSelection(i);
        fragmentAddFitnessClassBinding.getViewModel().setAddFitnessClassCategory(i);
        fragmentAddFitnessClassBinding.textInputClassCategory.setErrorEnabled(false);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @BindingAdapter({"fitnessClassValidationData"})
    public static void validateFitnessClassData(View view, HashMap<String, Object> validationData){
        if (validationData != null) {
            ValidationResult result = (ValidationResult) validationData.get("validationResult");
            com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields field = (com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields) validationData.get("field");
            switch (field) {
                case NAME:
                    errorHandler(fragmentAddFitnessClassBinding.textInputClassName, result);
                    break;
                case DESCRIPTION:
                    errorHandler(fragmentAddFitnessClassBinding.textInputClassDescription, result);
                    break;
                case SESSION_NUMBER:
                    errorHandler(fragmentAddFitnessClassBinding.textInputClassSessionNumber, result);
                    break;
                case DURATION:
                    errorHandler(fragmentAddFitnessClassBinding.textInputClassDuration, result);
                    break;
                case TIME_START:
                    errorHandler(fragmentAddFitnessClassBinding.textInputClassTimeStart, result);
                    break;
                case TIME_END:
                    errorHandler(fragmentAddFitnessClassBinding.textInputClassTimeEnd, result);
                    break;
            }
        }
    }
}