package com.pupccis.fitnex.activities.login;

import static com.pupccis.fitnex.handlers.view.ViewHandler.errorHandler;
import static com.pupccis.fitnex.handlers.view.ViewHandler.uiErrorHandler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pupccis.fitnex.activities.main.Trainee.TraineeDashboard;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.activities.main.Trainer.TrainerDashboard;
import com.pupccis.fitnex.databinding.ActivityLoginFitnexBinding;
import com.pupccis.fitnex.model.User;
import com.pupccis.fitnex.utilities.VideoConferencingConstants;
import com.pupccis.fitnex.utilities.Preferences.UserPreferences;
import com.pupccis.fitnex.validation.ValidationResult;
import com.pupccis.fitnex.validation.validationFields.RegistrationFields;
import com.pupccis.fitnex.viewmodel.LoginViewModel;

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
//
//        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
//
//        setContentView(R.layout.activity_login_fitnex);
//
//        editEmail = (EditText) findViewById(R.id.editTextLoginEmail);
//        editPassword = (EditText) findViewById(R.id.editTextLoginPassword);
//        loginUser = (Button) findViewById(R.id.buttonLoginButton);
//        loginUser.setOnClickListener(this);
//        mAuth = FirebaseAuth.getInstance();
    }

    public void onLoginClick(View View){
        startActivity(new Intent(this, FitnexRegister.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);


    }

    @Override
    public void onClick(View view) {
        if(view == binding.buttonLogin){
            Log.d("Login", "clicked");
            boolean isInvalid = false;
            ArrayList<TextInputLayout> textInputLayouts = new ArrayList<>();
            textInputLayouts.add(binding.textInputLoginEmail);
            textInputLayouts.add(binding.textInputEmailPassword);
            isInvalid = uiErrorHandler(textInputLayouts);

            if(isInvalid)
                Toast.makeText(this, "Some Input Fields Are Invalid, Please Try Again.", Toast.LENGTH_SHORT).show();
            else{
                binding.getViewModel().loginUser().observe(binding.getLifecycleOwner(), new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if(user == null)
                            Toast.makeText(FitnexLogin.this, "Invalid Username and/or Password, Please Try Again.", Toast.LENGTH_SHORT).show();
                        else{
                            Toast.makeText(FitnexLogin.this, "User Type:"+user.getUserType(), Toast.LENGTH_SHORT).show();
                            userPreferences.putBoolean(VideoConferencingConstants.KEY_IS_SIGNED_ID, true);
                            userPreferences.putString(VideoConferencingConstants.KEY_FULLNAME, user.getName());
                            userPreferences.putString(VideoConferencingConstants.KEY_EMAIL, user.getEmail());
                            userPreferences.putString(VideoConferencingConstants.KEY_AGE, user.getAge()+"");
                            userPreferences.putString(VideoConferencingConstants.KEY_USER_ID, user.getUserID());
                            userPreferences.putString("usertype", user.getUserType());
                            if(user.getUserType().equals("trainer")){
                                startActivity(new Intent(getApplicationContext(), TrainerDashboard.class));
                            }
                            else if(user.getUserType().equals("trainee")){
                                startActivity(new Intent(getApplicationContext(), TraineeDashboard.class));
                            }
                        }
                    }
                });
            }
        }
//        switch(view.getId()){
//            case(R.id.buttonLoginButton):
//                Toast.makeText(FitnexLogin.this, "clcik", Toast.LENGTH_SHORT).show();
//                userLogin();
//                break;
//        }
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

//    private void userLogin() {
//        String email = editEmail.getText().toString();
//        String password = editPassword.getText().toString();
//
//        FirebaseDatabase db = FirebaseDatabase.getInstance();
//
//        if(email.isEmpty()){
//            editEmail.setError("Email address is required!");
//            editEmail.requestFocus();
//            return;
//        }
//        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            editEmail.setError("Please provide a valid email address");
//            editEmail.requestFocus();
//            return;
//        }
//        if(password.isEmpty()||password.length()<6){
//            editPassword.setError("Your password is invalid!");
//            editPassword.requestFocus();
//            return;
//        }
//
//
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful() && task.getResult() != null){
//                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(VideoConferencingConstants.Collections.KEY_PARENT);
//                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                            mDatabase.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DataSnapshot> task) {
//                                    String userType = task.getResult().child("userType").getValue().toString();
//                                    userPreferences.putBoolean(VideoConferencingConstants.KEY_IS_SIGNED_ID, true);
//                                    userPreferences.putString(VideoConferencingConstants.KEY_FULLNAME, task.getResult().child(VideoConferencingConstants.KEY_FULLNAME).getValue().toString()); //$_SESSION['fullname']
//                                    userPreferences.putString(VideoConferencingConstants.KEY_EMAIL, task.getResult().child(VideoConferencingConstants.KEY_EMAIL).getValue().toString());
//                                    userPreferences.putString(VideoConferencingConstants.KEY_AGE, task.getResult().child(VideoConferencingConstants.KEY_AGE).getValue().toString());
//                                    userPreferences.putString(VideoConferencingConstants.KEY_USER_ID, userId);
//                                    userPreferences.putString("usertype", userType);
//
//                                    Toast.makeText(FitnexLogin.this, "Login Successful! Welcome User", Toast.LENGTH_SHORT).show();
//                                    if(userType.equals("trainer")){
//                                        startActivity(new Intent(getApplicationContext(), TrainerDashboard.class));
//                                    }
//                                    else if(userType.equals("trainee")){
//                                        startActivity(new Intent(getApplicationContext(), TraineeDashboard.class));
//                                    }
//                                }
//                            });
//
//
//
//                        }
//                        else{
//                            Toast.makeText(FitnexLogin.this, "Invalid username or password! Please try again", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });
//    }
}