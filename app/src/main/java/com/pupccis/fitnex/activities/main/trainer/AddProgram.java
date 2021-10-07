package com.pupccis.fitnex.activities.main.trainer;

import static com.pupccis.fitnex.handlers.view.ViewHandler.errorHandler;
import static com.pupccis.fitnex.handlers.view.ViewHandler.rotateAnimation;
import static com.pupccis.fitnex.handlers.view.ViewHandler.setDropdown;
import static com.pupccis.fitnex.handlers.view.ViewHandler.uiErrorHandler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.pupccis.fitnex.databinding.ActivityAddProgramBinding;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.validation.ValidationResult;
import com.pupccis.fitnex.viewmodel.ProgramViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class AddProgram extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    //Private Class Attributes
    private Program program_intent;
    private static ActivityAddProgramBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Extra Intents:
        program_intent = (Program) getIntent().getSerializableExtra("program");

        //Binding Initialization
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_program);
        binding.setViewModel(new ProgramViewModel());
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();
        binding.setPresenter(this);

        rotateAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate), binding.closeAddProgramButton);
        setDropdown(binding.editTextAddProgramCategory, this);
        binding.editTextAddProgramCategory.setOnItemSelectedListener(this);

        if(program_intent!=null){
            binding.editTextAddProgramName.setText(program_intent.getName());
            binding.editTextAddProgramDescription.setText(program_intent.getDescription());
            binding.editTextAddProgramSessionNumber.setText(program_intent.getSessionNumber());
            binding.editTextAddProgramDuration.setText(program_intent.getDuration());
            binding.editTextAddProgramCategory.setSelection(program_intent.getCategory());
            binding.getViewModel().setProgramID(program_intent.getProgramID());
        }
    }


    private void closeForm(){
        startActivity(new Intent(AddProgram.this, TrainerDashboard.class));
        overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        binding.editTextAddProgramCategory.setSelection(i);
        binding.getViewModel().setAddProgramCategory(i);
        binding.textInputProgramCategory.setErrorEnabled(false);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @BindingAdapter({"programValidationData"})
    public static void validateProgramData(View view, HashMap<String, Object> validationData) {
        if (validationData != null) {
            ValidationResult result = (ValidationResult) validationData.get("validationResult");
            com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields field = (com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields) validationData.get("field");
            switch (field) {
                case NAME:
                    errorHandler(binding.textInputProgramName, result);
                    break;
                case DESCRIPTION:
                    errorHandler(binding.textInputProgramDescription, result);
                    break;
                case SESSION_NUMBER:
                    errorHandler(binding.textInputProgramSessionNumber, result);
                    break;
                case DURATION:
                    errorHandler(binding.textInputProgramDuration, result);
            }
        }
    }
    @Override
    public void onClick(View view) {
        if(view == binding.buttonAddProgramButton){
            ArrayList<TextInputLayout> textInputLayouts = new ArrayList<>() ;
            textInputLayouts.add(binding.textInputProgramName);
            textInputLayouts.add(binding.textInputProgramDescription);
            textInputLayouts.add(binding.textInputProgramSessionNumber);
            textInputLayouts.add(binding.textInputProgramDuration);
            boolean isInvalid = false;

            isInvalid = uiErrorHandler(textInputLayouts);

            if(binding.editTextAddProgramCategory.getSelectedItemPosition() < 1){
                binding.textInputProgramCategory.setErrorEnabled(true);
                binding.textInputProgramCategory.setError("Please Select a Category");
                isInvalid = true;
            }

            if(isInvalid)
                Toast.makeText(this, "Some Input Fields Are Invalid, Please Try Again.", Toast.LENGTH_SHORT).show();
            else{
                binding.constraintLayoutProgramProgressBar.setVisibility(View.VISIBLE);
                if(program_intent!= null){
                    MutableLiveData<Program> programMutableLiveData = binding.getViewModel().updateProgram();

                    programMutableLiveData.observe(binding.getLifecycleOwner(), new Observer<Program>() {
                        @Override
                        public void onChanged(Program program) {
                            if(program!= null)
                                Toast.makeText(AddProgram.this, "Program Successfully Updated", Toast.LENGTH_SHORT).show();
                            binding.constraintLayoutProgramProgressBar.setVisibility(View.GONE);
                            programMutableLiveData.removeObserver(this::onChanged);
                            closeForm();
                        }
                    });
                }
                else{
                    MutableLiveData<Program> programMutableLiveData = binding.getViewModel().insertProgram();
                    programMutableLiveData.observe(binding.getLifecycleOwner(), new Observer<Program>() {
                        @Override
                        public void onChanged(Program program) {
                            if(program!= null)
                                Toast.makeText(AddProgram.this, "Program Successfully Added!", Toast.LENGTH_SHORT).show();
                            binding.constraintLayoutProgramProgressBar.setVisibility(View.GONE);
                            programMutableLiveData.removeObserver(this::onChanged);
                            closeForm();
                        }
                    });
                }
            }
        }
        else if(view == binding.relativeLayoutAddProgramCloseButton)
            closeForm();
    }
}
