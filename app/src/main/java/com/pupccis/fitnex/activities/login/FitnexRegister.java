package com.pupccis.fitnex.activities.login;


import static com.pupccis.fitnex.handlers.view.UserPortalHandler.checkHealthData;
import static com.pupccis.fitnex.handlers.view.ViewHandler.errorHandler;
import static com.pupccis.fitnex.handlers.view.ViewHandler.uiErrorHandler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.firestore.auth.User;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.activities.main.trainee.TraineeDashboard;
import com.pupccis.fitnex.activities.trainingdashboard.TrainerDashboard;
import com.pupccis.fitnex.model.User;
import com.pupccis.fitnex.utilities.Preferences.UserPreferences;
import com.pupccis.fitnex.databinding.ActivityFitnexRegisterBinding;
import com.pupccis.fitnex.validation.ValidationResult;
import com.pupccis.fitnex.validation.validationFields.RegistrationFields;
import com.pupccis.fitnex.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class FitnexRegister extends AppCompatActivity implements View.OnClickListener{
    private static ActivityFitnexRegisterBinding binding;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fitnex_register);
        binding.setViewModel(new UserViewModel());
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();
        binding.setPresenter(this);

        userPreferences = new UserPreferences(getApplicationContext());
    }

    public void onLoginClick (View view){
        startActivity(new Intent(this, FitnexLogin.class ));
        overridePendingTransition(R.anim.from_left, android.R.anim.slide_out_right);
    }

    @BindingAdapter({"validationData"})
    public static void setValidate(View view, HashMap<String, Object> validationData){
        if(validationData != null){
            Log.d("Validation Data", "Triggered");
            ValidationResult result =(ValidationResult) validationData.get("validationResult");
            RegistrationFields field = (RegistrationFields) validationData.get("field");
            switch(field){
                case NAME:
                    errorHandler(binding.textInputName, result);
                    break;
                case AGE:
                    errorHandler(binding.textInputAge, result);
                    break;
                case PASSWORD:
                    errorHandler(binding.textInputPassword, result);
                    break;
                case EMAIL:
                    errorHandler(binding.textInputRegisterEmail, result);
                    if(result.isValid()){
                        binding.getViewModel().emailCheckerLiveData().observe(binding.getLifecycleOwner(), new Observer<ValidationResult>() {
                            @Override
                            public void onChanged(ValidationResult validationResult) {
                                if(validationResult!=null){
                                    errorHandler(binding.textInputRegisterEmail, validationResult);
                                    binding.getViewModel().emailCheckerLiveData().removeObserver(this::onChanged);
                                }
                            }
                        });
                    }
                    break;
            }
        }
    }

    @BindingAdapter({"toastMessage"})
    public static void runMe(View view, String message) {
        if (message != null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
        binding.textInputName.isErrorEnabled();
    }

    @Override
    public void onClick(View view) {
        if(view == binding.buttonRegisterButton){
            ArrayList<TextInputLayout> textInputLayouts = new ArrayList<>() ;
            textInputLayouts.add(binding.textInputAge);
            textInputLayouts.add(binding.textInputName);
            textInputLayouts.add(binding.textInputRegisterEmail);
            textInputLayouts.add(binding.textInputPassword);
            boolean isInvalid = false;

            isInvalid = uiErrorHandler(textInputLayouts);

            if(isInvalid)
                Toast.makeText(this, "Some Input Fields Are Invalid, Please Try Again.", Toast.LENGTH_SHORT).show();
            else{
                binding.constraintLayoutRegisterProgressBar.setVisibility(View.VISIBLE);
                MutableLiveData<User> userMutableLiveData = binding.getViewModel().registerUser();
                userMutableLiveData.observe(binding.getLifecycleOwner(), new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if(user!= null){
                            Toast.makeText(FitnexRegister.this, "User Successfully Registered", Toast.LENGTH_SHORT).show();
                            userPreferences.setUserPreferences(user);
                            if(user.getUserType().equals("trainer")){
                                startActivity(new Intent(getApplicationContext(), TrainerDashboard.class));
                            }
                            else if(user.getUserType().equals("trainee")){
                                checkHealthData(binding.getLifecycleOwner(), FitnexRegister.this);
                            }
                        }
                        binding.constraintLayoutRegisterProgressBar.setVisibility(View.GONE);
                        userMutableLiveData.removeObserver(this::onChanged);
                    }
                });
            }
        }
    }
}