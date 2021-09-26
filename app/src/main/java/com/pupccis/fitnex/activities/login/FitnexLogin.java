package com.pupccis.fitnex.activities.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pupccis.fitnex.activities.main.Trainee.TraineeDashboard;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.activities.main.trainer.TrainerDashboard;
import com.pupccis.fitnex.utilities.VideoConferencingConstants;
import com.pupccis.fitnex.utilities.Preferences.UserPreferences;

public class FitnexLogin extends AppCompatActivity implements View.OnClickListener {
    private EditText editEmail, editPassword;
    private Button loginUser, quickLogin;
    private FirebaseAuth mAuth;
    private UserPreferences userPreferences;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        userPreferences = new UserPreferences(getApplicationContext());

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setContentView(R.layout.activity_login_fitnex);

        editEmail = (EditText) findViewById(R.id.editTextLoginEmail);
        editPassword = (EditText) findViewById(R.id.editTextLoginPassword);
        loginUser = (Button) findViewById(R.id.buttonLoginButton);
        loginUser.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onLoginClick(View View){
        startActivity(new Intent(this, FitnexRegister.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.buttonLoginButton):
                Toast.makeText(FitnexLogin.this, "clcik", Toast.LENGTH_SHORT).show();
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        FirebaseDatabase db = FirebaseDatabase.getInstance();

        if(email.isEmpty()){
            editEmail.setError("Email address is required!");
            editEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please provide a valid email address");
            editEmail.requestFocus();
            return;
        }
        if(password.isEmpty()||password.length()<6){
            editPassword.setError("Your password is invalid!");
            editPassword.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful() && task.getResult() != null){
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(VideoConferencingConstants.Collections.KEY_PARENT);
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            mDatabase.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    String userType = task.getResult().child("userType").getValue().toString();
                                    userPreferences.putBoolean(VideoConferencingConstants.KEY_IS_SIGNED_ID, true);
                                    userPreferences.putString(VideoConferencingConstants.KEY_FULLNAME, task.getResult().child(VideoConferencingConstants.KEY_FULLNAME).getValue().toString()); //$_SESSION['fullname']
                                    userPreferences.putString(VideoConferencingConstants.KEY_EMAIL, task.getResult().child(VideoConferencingConstants.KEY_EMAIL).getValue().toString());
                                    userPreferences.putString(VideoConferencingConstants.KEY_AGE, task.getResult().child(VideoConferencingConstants.KEY_AGE).getValue().toString());
                                    userPreferences.putString(VideoConferencingConstants.KEY_USER_ID, userId);
                                    userPreferences.putString("usertype", userType);

                                    Toast.makeText(FitnexLogin.this, "Login Successful! Welcome User", Toast.LENGTH_SHORT).show();
                                    if(userType.equals("trainer")){
                                        startActivity(new Intent(getApplicationContext(), TrainerDashboard.class));
                                    }
                                    else if(userType.equals("trainee")){
                                        startActivity(new Intent(getApplicationContext(), TraineeDashboard.class));
                                    }
                                }
                            });



                        }
                        else{
                            Toast.makeText(FitnexLogin.this, "Invalid username or password! Please try again", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}