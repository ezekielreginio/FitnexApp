package com.pupccis.fitnex.activities.Login;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.firestore.auth.User;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.validation.ValidationModel;
import com.pupccis.fitnex.databinding.ActivityFitnexRegisterBinding;
import com.pupccis.fitnex.validation.ValidationResult;
import com.pupccis.fitnex.viewmodel.UserViewModel;

import java.util.ArrayList;

public class FitnexRegister extends AppCompatActivity{
    private EditText editName, editAge, editEmail, editPassword;
    private TextView registerUser, registerTrainer, おっぱい;
    private ArrayList<ValidationModel> fields = new ArrayList<>();

    private TextInputLayout til_name, til_age, til_email, til_password;

    private Boolean isValid;

    private static ActivityFitnexRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fitnex_register);
        binding.setViewModel(new UserViewModel());
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();


        changeStatusBarColor();
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

    @BindingAdapter({"toastMessage"})
    public static void runMe(View view, String message) {
        if (message != null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @BindingAdapter({"validationResultName"})
    public static void validateName(View view, ValidationResult result) {
        Log.d("View Layer", result.isValid()+"");
        binding.textInputName.setErrorEnabled(!result.isValid());
        binding.textInputName.setError(result.getErrorMsg());
    }

    @BindingAdapter({"validationResultAge"})
    public static void validateAge(View view, ValidationResult result) {
        Log.d("View Layer", result.isValid()+"");
        binding.textInputAge.setErrorEnabled(!result.isValid());
        binding.textInputAge.setError(result.getErrorMsg());
    }

    @BindingAdapter({"validationResultPassword"})
    public static void validatePassword(View view, ValidationResult result) {
        Log.d("View Layer", result.isValid()+"");
        binding.textInputPassword.setErrorEnabled(!result.isValid());
        binding.textInputPassword.setError(result.getErrorMsg());
    }

}