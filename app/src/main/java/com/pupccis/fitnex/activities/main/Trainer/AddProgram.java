package com.pupccis.fitnex.activities.main.Trainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.pupccis.fitnex.databinding.ActivityAddProgramBinding;
import com.pupccis.fitnex.model.DAO.ProgramDAO;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.validation.ValidationModel;
import com.pupccis.fitnex.validation.ValidationResult;
import com.pupccis.fitnex.viewmodel.ProgramViewModel;

import java.util.ArrayList;

public class AddProgram extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Animation rotateAnimation;
    private ImageView imageView;
    private RelativeLayout closeButton;
    private EditText editName, editDescription, editSessionNumber, editDuration;
    private Spinner editCategory;
    private ProgramDAO programDAO = new ProgramDAO();
    private Button addProgram;

    private Program program_intent;
    private ProgramViewModel programViewModel;
    private TextInputLayout til_name, til_description, til_category, til_sessionNumber, til_duration;
    private ArrayList<ValidationModel> fields = new ArrayList<>();
    private Boolean isValid;
    private static ActivityAddProgramBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_program);
        binding.setViewModel(new ProgramViewModel());
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();
        //Extra Intents:
        program_intent = (Program) getIntent().getSerializableExtra("program");

        //setContentView(R.layout.activity_add_program);

        //

//        Spinner spinner=findViewById(R.id.editTextAddProgramCategory);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, R.layout.spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);
//
//        imageView = (ImageView) findViewById(R.id.closeAddProgramButton);
//        closeButton = (RelativeLayout) findViewById(R.id.relativeLayoutAddProgramCloseButton);
//
//        //EditTexts
//        editName = (EditText) findViewById(R.id.editTextAddProgramName);
//        editDescription = (EditText) findViewById(R.id.editTextAddProgramDescription);
//        editCategory = (Spinner) findViewById(R.id.editTextAddProgramCategory);
//        editSessionNumber = (EditText) findViewById(R.id.editTextAddProgramSessionNumber);
//        editDuration = (EditText) findViewById(R.id.editTextAddProgramDuration);
//        addProgram = (Button) findViewById(R.id.buttonAddProgramButton);
//        addProgram.setOnClickListener(this);
//        closeButton.setOnClickListener(this);
//
//        til_name = findViewById(R.id.textInputProgramName);
//        til_description = findViewById(R.id.textInputProgramDescription);
//        til_category = findViewById(R.id.textInputProgramCategory);
//        til_sessionNumber = findViewById(R.id.textInputProgramSessionNumber);
//        til_duration = findViewById(R.id.textInputProgramDuration);
//
//        fields.add(new ValidationModel(til_name, InputType.STRING));
//        fields.add(new ValidationModel(til_description, InputType.STRING));
//        //fields.add(new ValidationModel(til_category, InputType.INT));
//        fields.add(new ValidationModel(til_sessionNumber, InputType.INT));
//        fields.add(new ValidationModel(til_duration, InputType.INT));
//
//        ValidationEventBinder validationEventBinder = new ValidationEventBinder();
//        validationEventBinder.bindEvents(fields, new ProgramFitnessClassValidationService());
//
//        validationEventBinder.getIsValid().observe(this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean aBoolean) {
//                isValid = aBoolean;
//            }
//        });
//
//        //ViewModel Instantiation
//        //programViewModel = new ViewModelProvider(this).get(ProgramViewModel .class);
//        //programViewModel.init(getApplicationContext());
//
//        if(program_intent != null){
//            editName.setText(program_intent.getName());
//            editDescription.setText(program_intent.getDescription());
////            editCategory.setSelection();
////            editCategory.setText(program_intent.getCategory());
//            editSessionNumber.setText(program_intent.getSessionNumber());
//            editDuration.setText(program_intent.getDuration());
//            editCategory.setSelection(Integer.parseInt(program_intent.getCategory()));
//            addProgram.setText("Update Program");
//        }

//        rotateAnimation();
//        closeButton.setVisibility(View.VISIBLE);

    }





//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case(R.id.relativeLayoutAddProgramCloseButton):
//                closeForm();
//                break;
//            case(R.id.buttonAddProgramButton):
//                //addClass();
//                break;
//
//        }
//
//    }

//    private void addClass(){
//        for(ValidationModel field: fields){
//            String input = field.getTextInputLayout().getEditText().getText().toString();
//            if(input == null || input.equals("")){
//                TextInputLayout textInputLayout = field.getTextInputLayout();
//                textInputLayout.setErrorEnabled(true);
//                textInputLayout.setError("This Field is required");
//                isValid = false;
//            }
//        }
//        if(isValid && this.isValid){
//            String name = editName.getText().toString();
//            String description = editDescription.getText().toString();
//            String category = "" + editCategory.getSelectedItemPosition();
//            String sessionNumber = editSessionNumber.getText().toString();
//            String duration = editDuration.getText().toString();
//            Program program = new Program.Builder(name, description, category, sessionNumber, duration, FirebaseAuth.getInstance().getCurrentUser().getUid()).build();
//
//            if(program_intent != null){
//                Program updatedProgram = new Program.Builder(program)
//                        .setProgramID(program_intent.getProgramID())
//                        .setTrainerID(program_intent.getTrainerID())
//                        .build();
//                programViewModel.updateProgram(updatedProgram);
//                //program.setTrainerID(program_intent.getTrainerID());
//                //program.setProgramID(program_intent.getProgramID());
//                //programDAO.updateProgram(program);
//                Toast.makeText(this, "Program Successfully Updated", Toast.LENGTH_SHORT).show();
//            }
//
//            else{
//                programViewModel.insertProgram(program);
//                //programDAO.createProgram(program);
//                Toast.makeText(this, "Program Successfully Created", Toast.LENGTH_SHORT).show();
//            }
//            closeForm();
////            UserViewModel.registerUser(user).observe(this, new Observer<User>() {
////                @Override
////                public void onChanged(User user) {
////                    if(user != null)
////                        Toast.makeText(FitnexRegister.this, "Registration Successful, Welcome to Fitnex!", Toast.LENGTH_LONG).show();
////                }
////            });
//        }
//        else{
//            Toast.makeText(AddProgram.this, "Failed to register, please check your inputs", Toast.LENGTH_LONG).show();
//        }
//    }

//    private void closeForm(){
//        startActivity(new Intent(AddProgram.this, TrainerDashboard.class));
//        overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
//    }

//    private void rotateAnimation() {
//        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
//        imageView.setImageResource(R.drawable.ic_close_button);
//        imageView.startAnimation(rotateAnimation);
//    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        editCategory.setSelection(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    //Binding Adapters
    @BindingAdapter({"validationResultProgramName"})
    public static void validateName(View view, ValidationResult result){
        Log.d("Trigger", "Triggered");
        binding.textInputProgramName.setErrorEnabled(!result.isValid());
        binding.textInputProgramName.setError(result.getErrorMsg());
    }
    @BindingAdapter({"validationResultProgramDescription"})
    public static void validateDescription(View view, ValidationResult result){
        binding.textInputProgramDescription.setErrorEnabled(!result.isValid());
        binding.textInputProgramDescription.setError(result.getErrorMsg());
    }
    @BindingAdapter({"validationResultProgramSessionNumber"})
    public static void validateSessionNumber(View view, ValidationResult result){
        binding.textInputProgramSessionNumber.setErrorEnabled(!result.isValid());
        binding.textInputProgramSessionNumber.setError(result.getErrorMsg());
    }
    @BindingAdapter({"validationResultProgramDuration"})
    public static void validateDuration(View view, ValidationResult result){
        binding.textInputProgramDuration.setErrorEnabled(!result.isValid());
        binding.textInputProgramDuration.setError(result.getErrorMsg());
    }
}
