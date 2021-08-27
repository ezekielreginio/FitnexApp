package com.pupccis.fitnex.Activities.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.auth.User;
import com.google.firebase.database.FirebaseDatabase;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Models.User;
import com.pupccis.fitnex.Utilities.VideoConferencingConstants;

public class FitnexRegister extends AppCompatActivity implements View.OnClickListener {
    private EditText editName, editAge, editEmail, editPassword;
    private TextView registerUser, registerTrainer;
    private FirebaseAuth mAuth;
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

        registerUser = (Button) findViewById(R.id.buttonApplyButton);
        registerUser.setOnClickListener(this);
        registerTrainer = (TextView) findViewById(R.id.textViewRegisterTrainerApplication);
        registerTrainer.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
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

    private void registerUser() {
        String email = editEmail.getText().toString();
        String name = editName.getText().toString();
        String password = editPassword.getText().toString();
        String age = editAge.getText().toString();

        if(name.isEmpty()){
            editName.setError("Fullname is Required");
            editName.requestFocus();
            return;
        }

        if(age.isEmpty()){
            editAge.setError("Age is Required");
            editAge.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please provide a valid email");
            editEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editPassword.setError("Password is Required");
            editPassword.requestFocus();
            return;
        }

        if(password.length() < 6) {
            editPassword.setError("Password must be longer than 6 characters");
            editPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()){
                            User user = new User(name, age, email);
                            FirebaseDatabase.getInstance().getReference(VideoConferencingConstants.KEY_COLLECTION_USERS)
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user);

                            Toast.makeText(FitnexRegister.this, "Registration Successful!", Toast.LENGTH_LONG).show();


                            startActivity(new Intent(FitnexRegister.this, FitnexLogin.class));


                        }
                        else{
                            Toast.makeText(FitnexRegister.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}