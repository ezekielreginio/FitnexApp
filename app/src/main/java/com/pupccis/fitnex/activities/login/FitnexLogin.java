package com.pupccis.fitnex.activities.login;

import static com.pupccis.fitnex.handlers.view.UserPortalHandler.checkHealthData;
import static com.pupccis.fitnex.handlers.view.ViewHandler.errorHandler;
import static com.pupccis.fitnex.handlers.view.ViewHandler.uiErrorHandler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.pupccis.fitnex.activities.healthassessment.HealthAssessment;
import com.pupccis.fitnex.activities.main.trainee.TraineeDashboard;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.activities.trainingdashboard.TempTrainerDashboard;
import com.pupccis.fitnex.activities.trainingdashboard.TrainerDashboard;
import com.pupccis.fitnex.databinding.ActivityLoginFitnexBinding;
import com.pupccis.fitnex.model.User;
import com.pupccis.fitnex.utilities.Constants.PatronConstants;
import com.pupccis.fitnex.utilities.Preferences.UserPreferences;
import com.pupccis.fitnex.validation.ValidationResult;
import com.pupccis.fitnex.validation.validationFields.RegistrationFields;
import com.pupccis.fitnex.viewmodel.LoginViewModel;
import com.pupccis.fitnex.viewmodel.PatronViewModel;
import com.pupccis.fitnex.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class FitnexLogin extends AppCompatActivity implements View.OnClickListener {
    private UserPreferences userPreferences;

    private static ActivityLoginFitnexBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_fitnex);
        binding.setViewModel(new LoginViewModel());
        binding.setLifecycleOwner(this);
        binding.setPresenter(this);
        binding.executePendingBindings();

        userPreferences = new UserPreferences(getApplicationContext());
    }

    public void onLoginClick(View View){
        startActivity(new Intent(this, FitnexRegister.class));
        overridePendingTransition(R.anim.from_right,R.anim.stay);
    }

    @Override
    public void onClick(View view) {
        if(view == binding.buttonLogin){
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            boolean isInvalid = false;
            ArrayList<TextInputLayout> textInputLayouts = new ArrayList<>();
            textInputLayouts.add(binding.textInputLoginEmail);
            textInputLayouts.add(binding.textInputEmailPassword);
            isInvalid = uiErrorHandler(textInputLayouts);

            if(isInvalid)
                Toast.makeText(this, "Some Input Fields Are Invalid, Please Try Again.", Toast.LENGTH_SHORT).show();
            else{
                binding.constraintLayoutLoginProgressBar.setVisibility(View.VISIBLE);
                binding.getViewModel().loginUser().observe(binding.getLifecycleOwner(), new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if(user == null)
                            Toast.makeText(FitnexLogin.this, "Invalid Username and/or Password, Please Try Again.", Toast.LENGTH_SHORT).show();
                        else{
                            userPreferences.setUserPreferences(user);

                            if(user.getUserType().equals("trainer")){
                                PatronViewModel patronViewModel = new PatronViewModel();
                                patronViewModel.checkPatronData().observe(binding.getLifecycleOwner(), isSet -> {
                                    if(isSet != null){
                                        Log.d("Patron is Set", isSet.toString());
                                        userPreferences.putBoolean(PatronConstants.KEY_PATRON_SET, isSet);
                                        startActivity(new Intent(getApplicationContext(), TrainerDashboard.class));
                                        finish();
                                    }
                                });
                            }
                            else if(user.getUserType().equals("trainee")){
                                checkHealthData(binding.getLifecycleOwner(), FitnexLogin.this);
                            }
                        }
                        binding.constraintLayoutLoginProgressBar.setVisibility(View.GONE);
                    }
                });
            }
        }
        if(view == binding.buttonQuickly){
            startActivity(new Intent(getApplicationContext(), TempTrainerDashboard.class));
        }
    }

    //Binding Adapters
    @BindingAdapter({"loginValidationData"})
    public static void getLoginValidationData(View view, HashMap<String, Object> validationData){
        if(validationData != null){
            ValidationResult result =(ValidationResult) validationData.get("validationResult");
            RegistrationFields field = (RegistrationFields) validationData.get("field");
            switch (field){
                case EMAIL:
                    errorHandler(binding.textInputLoginEmail, result);
                    break;
                case PASSWORD:
                    errorHandler(binding.textInputEmailPassword, result);
                    break;
            }
        }
    }
}