package com.pupccis.fitnex.activities.main.trainer;

import static com.pupccis.fitnex.handlers.view.ViewHandler.errorHandler;
import static com.pupccis.fitnex.handlers.view.ViewHandler.uiErrorHandler;
import static com.pupccis.fitnex.viewmodel.FitnessClassViewModel.updateFitnessClass;

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

public class AddFitnessClass extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, AdapterView.OnItemSelectedListener{
    private Animation rotateAnimation;
    private ImageView imageView;
    private EditText editTimeStart, editTimeEnd, editName, editSessionNumber, editDescription, editDuration;
    private Spinner fitnessClassCategory;
    private TimePickerDialog timePickerDialog;
    private RelativeLayout closeButton;
    private Button addClass;
    private String timeStartHolder, timeEndHolder;
    private FitnessClassDAO fitnessClassDAO = new FitnessClassDAO();
    private Calendar calendar = Calendar.getInstance();
    private FitnessClass fitness_intent;
    private FitnessClassViewModel fitnessClassViewModel;
    private TextInputLayout til_name, til_description, til_category,til_timeStart, til_timeEnd, til_sessionNumber, til_duration;
    private ArrayList<ValidationModel> fields = new ArrayList<>();
    private Boolean isValid;
    private static ActivityAddClassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_class);
        binding.setViewModel(new FitnessClassViewModel());
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();
        binding.setPresenter(this);

        //ViewModel Instantiation
//        fitnessClassViewModel = new ViewModelProvider(this).get(FitnessClassViewModel.class);
//        fitnessClassViewModel.init(getApplicationContext());

        //Extra Intent
        fitness_intent = (FitnessClass) getIntent().getSerializableExtra("fitness");

//        closeButton = (RelativeLayout) findViewById(R.id.relativeLayoutAddClassCloseButton);
//        imageView = (ImageView) findViewById(R.id.closeAddClassButton);
//
//        editName = (EditText) findViewById(R.id.editTextAddClassName);
//        editDescription = (EditText) findViewById(R.id.editTextAddFitnessClassDescription);
//        editTimeStart = (EditText) findViewById(R.id.editTextTimeStart);
//        editTimeEnd = (EditText) findViewById(R.id.editTextTimeEnd);
//        editSessionNumber = (EditText) findViewById(R.id.editTextAddClassSessionNumber);
//        editDuration = (EditText) findViewById(R.id.editTextAddClassDuration);
//
//        til_name = findViewById(R.id.textInputClassName);
//        til_description = findViewById(R.id.textInputClassDescription);
//        til_category = findViewById(R.id.textInputClassCategory);
//        til_timeStart = findViewById(R.id.textInputClassTimeStart);
//        til_timeEnd = findViewById(R.id.textInputClassTimeEnd);
//        til_sessionNumber = findViewById(R.id.textInputClassSessionNumber);
//        til_duration = findViewById(R.id.textInputClassDuration);
//
//        fields.add(new ValidationModel(til_name, com.pupccis.fitnex.validation.InputType.STRING));
//        fields.add(new ValidationModel(til_description, com.pupccis.fitnex.validation.InputType.STRING));
//        //fields.add(new ValidationModel(til_category, com.pupccis.fitnex.Validation.InputType.CATEGORY));
//        fields.add(new ValidationModel(til_timeStart, com.pupccis.fitnex.validation.InputType.TIME));
//        fields.add(new ValidationModel(til_timeEnd, com.pupccis.fitnex.validation.InputType.TIME));
//        fields.add(new ValidationModel(til_sessionNumber, com.pupccis.fitnex.validation.InputType.INT));
//        fields.add(new ValidationModel(til_duration, com.pupccis.fitnex.validation.InputType.INT));
//
//        ValidationEventBinder validationEventBinder = new ValidationEventBinder();
//        validationEventBinder.bindEvents(fields, new FitnessClassValidationService());
//
//        validationEventBinder.getIsValid().observe(this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean aBoolean) {
//                isValid = aBoolean;
//            }
//        });
//
//
//        binding.editTextTimeStart.setRawInputType(InputType.TYPE_NULL);
        binding.editTextTimeStart.setFocusable(false);
//        binding.editTextTimeEnd.setRawInputType(InputType.TYPE_NULL);
        binding.editTextTimeEnd.setFocusable(false);
//        addClass = (Button) findViewById(R.id.buttonAddClassButton);
//
//        //Spinner Category Dropdown
//        Spinner spinner = (Spinner) findViewById(R.id.editTextAddFitnessClassCategory);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, R.layout.spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);
//        fitnessClassCategory = (Spinner) findViewById(R.id.editTextAddFitnessClassCategory);
//
//        //Event Bindings
////        editTimeStart.setOnFocusChangeListener(this);
////        editTimeEnd.setOnFocusChangeListener(this);
//        editTimeStart.setOnClickListener(this);
//        editTimeEnd.setOnClickListener(this);
//        addClass.setOnClickListener(this);
//        closeButton.setOnClickListener(this);

        rotateAnimation();
        binding.closeAddClassButton.setVisibility(View.VISIBLE);

//        if(fitness_intent != null){
//            editName.setText(fitness_intent.getClassName());
//            editSessionNumber.setText(fitness_intent.getSessionNo());
//            editTimeStart.setText(fitness_intent.getTimeStart());
//            editTimeEnd.setText(fitness_intent.getTimeEnd());
//            editDescription.setText(fitness_intent.getDescription());
//            editDuration.setText(fitness_intent.getDuration());
//            fitnessClassCategory.setSelection(fitness_intent.getCategory());
//            addClass.setText("Update Class");
//        }
    }

    private void rotateAnimation() {
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        binding.closeAddClassButton.setImageResource(R.drawable.ic_close_button);
        binding.closeAddClassButton.startAnimation(rotateAnimation);
    }
    @BindingAdapter({"fitnessClassValidationData"})
    public static void validateFitnessClassData(View view, HashMap<String, Object> validationData){
        Log.d("Binding adapter trgger", "Triggered");
        if (validationData != null) {
            Log.d("Validation Data", "Triggered");
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.buttonAddClassButton):
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

                    if(isInvalid)
                        Toast.makeText(this, "Some Input Fields Are Invalid, Please Try Again.", Toast.LENGTH_SHORT).show();
                    else{
                        binding.constraintLayoutFitnessClassProgressBar.setVisibility(View.VISIBLE);
                        MutableLiveData<FitnessClass> fitnessClassMutableLiveData = binding.getViewModel().insertFitnessClass();
                        fitnessClassMutableLiveData.observe(binding.getLifecycleOwner(), new Observer<FitnessClass>() {
                            @Override
                            public void onChanged(FitnessClass fitnessClass) {
                                if(fitnessClass!= null)
                                    Toast.makeText(AddFitnessClass.this, "Program Successfully Registered", Toast.LENGTH_SHORT).show();
                                binding.constraintLayoutFitnessClassProgressBar.setVisibility(View.GONE);
                                fitnessClassMutableLiveData.removeObserver(this::onChanged);
                                closeForm();
                            }
                        });
                    }
                }
                //addClass();
                break;
            case(R.id.relativeLayoutAddClassCloseButton):

                break;
            case (R.id.editTextTimeStart):
                showTimeDialog(binding.editTextTimeStart, 0);
                Toast.makeText(AddFitnessClass.this, "time start click", Toast.LENGTH_SHORT).show();
                break;
            case(R.id.editTextTimeEnd):
                Toast.makeText(AddFitnessClass.this, "time end click", Toast.LENGTH_SHORT).show();
                showTimeDialog(binding.editTextTimeEnd, 1);
                break;
        }

    }

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
//            String sessionNo = editSessionNumber.getText().toString();
//            String duration = editDuration.getText().toString();
//            String timeStart = editTimeStart.getText().toString();
//            String timeEnd = editTimeEnd.getText().toString();
//            int category = fitnessClassCategory.getSelectedItemPosition();
//            Date currentTime = calendar.getTime();
//            FitnessClass fitnessClass = new FitnessClass
//                    .Builder(name, description, category, timeStart, timeEnd, sessionNo, duration)
//                    .setDateCreated(currentTime.toString())
//                    .setClassTrainerID(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                    .build();
//            Toast.makeText(AddFitnessClass.this, "Class created", Toast.LENGTH_LONG).show();
//            if(fitness_intent != null){
//                fitnessClass.setClassID(fitness_intent.getClassID());
//                Log.d("Category", fitnessClass.getCategory()+"");
//                updateFitnessClass(fitnessClass);
//                Toast.makeText(this, "Fitness Class Successfully Updated", Toast.LENGTH_SHORT).show();
//                closeForm();
//            }
//            else{
//                fitnessClassViewModel.insertFitnessClass(fitnessClass);
//                Toast.makeText(this, "Fitness Class  Successfully Created", Toast.LENGTH_SHORT).show();
//                closeForm();
//            }
////            UserViewModel.registerUser(user).observe(this, new Observer<User>() {
////                @Override
////                public void onChanged(User user) {
////                    if(user != null)
////                        Toast.makeText(FitnexRegister.this, "Registration Successful, Welcome to Fitnex!", Toast.LENGTH_LONG).show();
////                }
////            });
//        }
//        else{
//            Toast.makeText(AddFitnessClass.this, "Failed to register, please check your inputs", Toast.LENGTH_LONG).show();
//        }
//    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch(view.getId()) {
//            case (R.id.editTextTimeStart):
//                showTimeDialog(editTimeStart, 0);
//                Toast.makeText(AddFitnessClass.this, "time start click", Toast.LENGTH_SHORT).show();
//                break;
//            case(R.id.editTextTimeEnd):
//                Toast.makeText(AddFitnessClass.this, "time end click", Toast.LENGTH_SHORT).show();
//                showTimeDialog(editTimeEnd, 1);
//                break;
        }
    }
//
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


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        fitnessClassCategory.setSelection(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
