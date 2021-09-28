package com.pupccis.fitnex.activities.main.trainer;

import static com.pupccis.fitnex.handlers.view.ViewHandler.errorHandler;
import static com.pupccis.fitnex.handlers.view.ViewHandler.rotateAnimation;
import static com.pupccis.fitnex.handlers.view.ViewHandler.setDropdown;
import static com.pupccis.fitnex.handlers.view.ViewHandler.uiErrorHandler;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.pupccis.fitnex.databinding.ActivityAddClassBinding;
import com.pupccis.fitnex.model.FitnessClass;
import com.pupccis.fitnex.model.DAO.FitnessClassDAO;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.validation.Services.FitnessClassValidationService;
import com.pupccis.fitnex.validation.Services.ValidationEventBinder;
import com.pupccis.fitnex.validation.ValidationModel;
import com.pupccis.fitnex.validation.ValidationResult;
import com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassFields;
import com.pupccis.fitnex.viewmodel.FitnessClassViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class AddFitnessClass extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    //Private Class Attributes
    private String timeStartHolder, timeEndHolder;
    private Calendar calendar = Calendar.getInstance();
    private FitnessClass fitness_intent;
    private static ActivityAddClassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_class);
        binding.setViewModel(new FitnessClassViewModel());
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();
        binding.setPresenter(this);

        //Extra Intent
        fitness_intent = (FitnessClass) getIntent().getSerializableExtra("fitnessClass");

        //Time Selector Focusable False
        binding.editTextTimeStart.setFocusable(false);
        binding.editTextTimeEnd.setFocusable(false);


        rotateAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate), binding.closeAddClassButton);
        setDropdown(binding.editTextAddFitnessClassCategory, this);
        binding.editTextAddFitnessClassCategory.setOnItemSelectedListener(this);

        //Fitness Intent Information for Update
        if(fitness_intent != null){
            Log.d("Fitness INtent ID", fitness_intent.getClassID());
            binding.editTextAddClassName.setText(fitness_intent.getClassName());
            binding.editTextAddFitnessClassCategory.setSelection(fitness_intent.getCategory());
            binding.editTextAddClassSessionNumber.setText(fitness_intent.getSessionNo());
            binding.editTextTimeStart.setText(fitness_intent.getTimeStart());
            binding.editTextTimeEnd.setText(fitness_intent.getTimeEnd());
            binding.editTextAddFitnessClassDescription.setText(fitness_intent.getDescription());
            binding.editTextAddClassDuration.setText(fitness_intent.getDuration());
            //fitnessClassCategory.setSelection(fitness_intent.getCategory());
            binding.getViewModel().setFitnessClassID(fitness_intent.getClassID());
            binding.buttonAddClassButton.setText("Update Class");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        if(view == binding.buttonAddClassButton){
            Log.d("Register", "clicked");
            ArrayList<TextInputLayout> textInputLayouts = new ArrayList<>() ;
            textInputLayouts.add(binding.textInputClassName);
            textInputLayouts.add(binding.textInputClassDescription);
            textInputLayouts.add(binding.textInputClassSessionNumber);
            textInputLayouts.add(binding.textInputClassTimeStart);
            textInputLayouts.add(binding.textInputClassTimeEnd);
            textInputLayouts.add(binding.textInputClassDuration);
            boolean isInvalid = false;

            isInvalid = uiErrorHandler(textInputLayouts);

            if(binding.editTextAddFitnessClassCategory.getSelectedItemPosition() < 1){
                binding.textInputClassCategory.setErrorEnabled(true);
                binding.textInputClassCategory.setError("Please Select a Category");
                isInvalid = true;
            }


            if(isInvalid)
                Toast.makeText(this, "Some Input Fields Are Invalid, Please Try Again.", Toast.LENGTH_SHORT).show();
            else{
                binding.constraintLayoutFitnessClassProgressBar.setVisibility(View.VISIBLE);
                if(fitness_intent != null){
                    MutableLiveData<FitnessClass> fitnessClassMutableLiveData = binding.getViewModel().updateFitnessClass();
                    fitnessClassMutableLiveData.observe(binding.getLifecycleOwner(), new Observer<FitnessClass>() {
                        @Override
                        public void onChanged(FitnessClass fitnessClass) {

                            fitnessClassMutableLiveData.removeObserver(this::onChanged);
                        }
                    });
                }
                else{
                    MutableLiveData<FitnessClass> fitnessClassMutableLiveData = binding.getViewModel().insertFitnessClass();
                    fitnessClassMutableLiveData.observe(binding.getLifecycleOwner(), new Observer<FitnessClass>() {
                        @Override
                        public void onChanged(FitnessClass fitnessClass) {
                            if(fitnessClass!= null)
                                Toast.makeText(AddFitnessClass.this, "Program Successfully Registered", Toast.LENGTH_SHORT).show();

                            fitnessClassMutableLiveData.removeObserver(this::onChanged);
                        }
                    });
                }
                binding.constraintLayoutFitnessClassProgressBar.setVisibility(View.GONE);
                closeForm();
            }
        }
        else if (view == binding.relativeLayoutAddClassCloseButton){
            closeForm();
        }
        else if (view == binding.editTextTimeStart)
            showTimeDialog(binding.editTextTimeStart, 0);
        else if(view == binding.editTextTimeEnd)
            showTimeDialog(binding.editTextTimeStart, 1);

    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        binding.editTextAddFitnessClassCategory.setSelection(i);
        binding.getViewModel().setAddFitnessClassCategory(i);
        binding.textInputClassCategory.setErrorEnabled(false);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //Binding Adapters
    @BindingAdapter({"fitnessClassValidationData"})
    public static void validateFitnessClassData(View view, HashMap<String, Object> validationData){
        if (validationData != null) {
            ValidationResult result = (ValidationResult) validationData.get("validationResult");
            ProgramFitnessClassFields field = (ProgramFitnessClassFields) validationData.get("field");
            switch (field) {
                case NAME:
                    errorHandler(binding.textInputClassName, result);
                    break;
                case DESCRIPTION:
                    errorHandler(binding.textInputClassDescription, result);
                    break;
                case SESSION_NUMBER:
                    errorHandler(binding.textInputClassSessionNumber, result);
                    break;
                case DURATION:
                    errorHandler(binding.textInputClassDuration, result);
                    break;
                case TIME_START:
                    errorHandler(binding.textInputClassTimeStart, result);
                    break;
                case TIME_END:
                    errorHandler(binding.textInputClassTimeEnd, result);
                    break;
            }
        }
    }

    //Class Methods
    private void closeForm() {
        Log.d("close", "close");
        Intent intent = new Intent(AddFitnessClass.this, TrainerDashboard.class);
        intent.putExtra("page", 1);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
    }


    private String showTimeDialog(EditText time, int setter) {

        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int mins = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddFitnessClass.this, R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, i);
                c.set(Calendar.MINUTE, i1);
                c.setTimeZone(TimeZone.getDefault());
                SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
                String time = format.format(c.getTime());
                if (setter ==0){
                    binding.editTextTimeStart.setText(time);
                    timeStartHolder = binding.editTextTimeStart.getText().toString();
                }
                else{
                    binding.editTextTimeEnd.setText(time);
                    timeEndHolder = binding.editTextTimeEnd.getText().toString();
                }
            }
        },hours, mins, false);
        timePickerDialog.show();
        return time.toString();
    }
}
