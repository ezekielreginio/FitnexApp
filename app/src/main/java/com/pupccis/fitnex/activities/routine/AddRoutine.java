package com.pupccis.fitnex.activities.routine;

import static com.pupccis.fitnex.handlers.view.ViewHandler.errorHandler;
import static com.pupccis.fitnex.handlers.view.ViewHandler.uiErrorHandler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.pupccis.fitnex.validation.ValidationResult;
import com.pupccis.fitnex.databinding.ActivityAddRoutineBinding;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.model.Routine;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.viewmodel.RoutineViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class AddRoutine extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private EditText editTextAddRoutineName, editTextAddRoutineReps, editTextAddRoutineSets, editTextAddRoutineWeights, editTextAddRoutineDuration;
    private Spinner editTextAddRoutineCategory;
    private Program program;
    private Button buttonAddRoutineButton;
    private LinearLayout linearLayoutAddRoutineStrengthFields;
    private Routine routine;
    private RoutineViewModel routineViewModel;
    private Routine routine_intent;
    private RoutinePage routinePage;

    private static ActivityAddRoutineBinding binding;


    private String programID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_routine);

        binding.setLifecycleOwner(this);
        binding.setViewModel(new RoutineViewModel());
        binding.executePendingBindings();
        binding.setPresenter(this);
        //Extra intent
         routine_intent = (Routine) getIntent().getSerializableExtra("routine");


        //ViewModel Instantiation
       // routineViewModel = new ViewModelProvider(this).get(RoutineViewModel.class);

        //Extra intents
        program = (Program) getIntent().getSerializableExtra("program");
        //Spinner binding
        Spinner spinner=findViewById(R.id.editTextAddRoutineCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        if(routine_intent != null){
            Log.e("Pumasok and intent", "Pumasok");
            Log.e("Laman ng program intent", program.getProgramID());
            binding.editTextAddRoutineName.setText(routine_intent.getName());
            binding.editTextAddRoutineDuration.setText(routine_intent.getDuration()+"");
            binding.editTextAddRoutineReps.setText(routine_intent.getReps()+"");
            binding.editTextAddRoutineSets.setText(routine_intent.getSets()+"");
            binding.editTextAddRoutineWeights.setText(routine_intent.getWeight()+"");
            binding.buttonAddRoutineButton.setText("Update Routine");
            binding.getViewModel().setRoutineID(routine_intent.getId());
            binding.getViewModel().setProgramID(routine_intent.getProgramID());
        }

    }
    public void setProgramID() {
        binding.getViewModel().setProgramID(program.getProgramID());
        Log.e("Add Routine ProgramID", program.getProgramID());
    }


//    private void setProgramID(){
//        this.programID = routinePage.getProgramID();
//    }
    @Override
    public void onClick(View view) {
       if(view == binding.buttonAddRoutineButton){
           ArrayList<TextInputLayout> textInputLayouts = new ArrayList<>() ;
           textInputLayouts.add(binding.textInputLayoutAddRoutineName);
           textInputLayouts.add(binding.textInputLayoutAddRoutineReps);
           textInputLayouts.add(binding.textInputLayoutAddRoutineSets);
           textInputLayouts.add(binding.textInputLayoutAddRoutineWeights);
           textInputLayouts.add(binding.textInputLayoutAddRoutineDuration);
           //textInputLayouts.add(binding.textInputLayoutAddRoutineCategory);
           boolean isInvalid = false;

           isInvalid = uiErrorHandler(textInputLayouts);
           if(isInvalid)
               Toast.makeText(this, "Some Input Fields Are Invalid, Please Try Again.", Toast.LENGTH_SHORT).show();
           else{
               binding.constraintLayoutRoutineProgressBar.setVisibility(View.VISIBLE);
               if(routine_intent != null){
                   //routine.setRoutineID(routine_intent.getId());
                   setProgramID();
                    MutableLiveData<Routine> routineMutableLiveData = binding.getViewModel().updateRoutine();
                    routineMutableLiveData.observe(binding.getLifecycleOwner(), new Observer<Routine>() {
                        @Override
                        public void onChanged(Routine routine) {
                            Toast.makeText(AddRoutine.this, "Routine Successfully Updated", Toast.LENGTH_SHORT).show();
                            routineMutableLiveData.removeObserver(this::onChanged);
                        }
                    });
               }

               else{
                   setProgramID();
                   MutableLiveData<Routine> routineMutableLiveData =  binding.getViewModel().insertRoutine();
                   routineMutableLiveData.observe(binding.getLifecycleOwner(), new Observer<Routine>() {
                       @Override
                       public void onChanged(Routine routine) {
                           Toast.makeText(AddRoutine.this, "Routine Successfully Registered", Toast.LENGTH_SHORT).show();
                           routineMutableLiveData.removeObserver(this::onChanged);
                       }
                   });
               }
               binding.constraintLayoutRoutineProgressBar.setVisibility(View.GONE);
           }

       }


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        //editTextAddRoutineCategory.setSelection(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @BindingAdapter({"routineValidationData"})
    public static void validateProgramData(View view, HashMap<String, Object> validationData) {
        if (validationData != null) {
            ValidationResult result = (ValidationResult) validationData.get("validationResult");
            com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields field = (com.pupccis.fitnex.validation.validationFields.ProgramFitnessClassRoutineFields) validationData.get("field");
            switch (field) {
                case NAME:
                    errorHandler(binding.textInputLayoutAddRoutineName, result);
                    break;
                case REPS:
                    errorHandler(binding.textInputLayoutAddRoutineReps, result);
                    break;
                case SETS:
                    errorHandler(binding.textInputLayoutAddRoutineSets, result);
                    break;
                case WEIGHT:
                    errorHandler(binding.textInputLayoutAddRoutineWeights, result);
                    break;
                case DURATION:
                    errorHandler(binding.textInputLayoutAddRoutineDuration, result);
                    break;
                case CATEGORY:
                    errorHandler(binding.textInputLayoutAddRoutineCategory, result);
                    break;
            }
        }
    }
}