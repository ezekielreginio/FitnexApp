package com.pupccis.fitnex.Activities.Login;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.auth.User;
import com.google.firebase.database.FirebaseDatabase;
import com.pupccis.fitnex.Activities.Main.Trainee.TraineeDashboard;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Model.User;
import com.pupccis.fitnex.Utilities.VideoConferencingConstants;
import com.pupccis.fitnex.Validation.InputType;
import com.pupccis.fitnex.Validation.Services.UserValidationService;
import com.pupccis.fitnex.Validation.Services.ValidationEventBinder;
import com.pupccis.fitnex.Validation.Services.ValidationService;
import com.pupccis.fitnex.Validation.ValidationModel;
import com.pupccis.fitnex.Validation.ValidationResult;
import com.pupccis.fitnex.ViewModel.UserViewModel;

import java.util.ArrayList;

public class FitnexRegister extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private EditText editName, editAge, editEmail, editPassword;
    private TextView registerUser, registerTrainer, おっぱい;
    private ArrayList<ValidationModel> fields = new ArrayList<>();

    private TextInputLayout til_name, til_age, til_email, til_password;

    private Boolean isValid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitnex_register);


        changeStatusBarColor();

        //Register Activity Object Casting
        editName = (EditText) findViewById(R.id.editTextRegisterName);
        editAge = (EditText) findViewById(R.id.editTextRegisterAge);
        editEmail = (EditText) findViewById(R.id.editTextApplicationEmail);
        editPassword = (EditText) findViewById(R.id.editTextRegisterPassword);

        //Text Input Layouts
        til_name = findViewById(R.id.textInputName);
        til_age = findViewById(R.id.textInputAge);
        til_email = findViewById(R.id.textInputRegisterEmail);
        til_password = findViewById(R.id.textInputPassword);

        fields.add(new ValidationModel(til_name, InputType.STRING));
        fields.add(new ValidationModel(til_age, InputType.INT));
        fields.add(new ValidationModel(til_email, InputType.NEW_EMAIL));
        fields.add(new ValidationModel(til_password, InputType.PASSWORD));

        ValidationEventBinder validationEventBinder = new ValidationEventBinder(this);
        validationEventBinder.bindEvents(fields, new UserValidationService());

        validationEventBinder.getIsValid().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                isValid = aBoolean;
            }
        });

        registerUser = (Button) findViewById(R.id.buttonApplyButton);
        registerUser.setOnClickListener(this);
        registerTrainer = (TextView) findViewById(R.id.textViewRegisterTrainerApplication);
        registerTrainer.setOnClickListener(this);

    }
    public void changeStatusBarColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));

        }
    }

    public void onLoginClick (View view){
        startActivity(new Intent(this, FitnexLogin.class ));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.buttonApplyButton):
                registerUser();
                break;
            case(R.id.textViewRegisterTrainerApplication):
                startActivity(new Intent(this, FitnexTrainerApplication.class));
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if(!hasFocus){
            switch (view.getId()){
                case R.id.editTextRegisterName:
                    String name = editName.getText().toString();
                    if(name.isEmpty()){
                        editName.setError("Minimum of 4 Characters");
                        return;
                    }
                    break;
            }
        }
    }

    private void registerUser() {
        String email = editEmail.getText().toString();
        String name = editName.getText().toString();
        String password = editPassword.getText().toString();
        int age = Integer.parseInt(editAge.getText().toString());
        boolean isValid = true;

        for(ValidationModel field: fields){
            String input = field.getTextInputLayout().getEditText().getText().toString();
            if(input == null || input.equals("")){
                TextInputLayout textInputLayout = field.getTextInputLayout();
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError("This Field is required");
                isValid = false;
            }
        }

        if(isValid && this.isValid){
            User user = new User.Builder(name, email)
                    .setPassword(password)
                    .setAge(age)
                    .build();
            UserViewModel.registerUser(user).observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if(user != null)
                        Toast.makeText(FitnexRegister.this, "Registration Successful, Welcome to Fitnex!", Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            Toast.makeText(FitnexRegister.this, "Failed to register, please check your inputs", Toast.LENGTH_LONG).show();
        }
    }


}