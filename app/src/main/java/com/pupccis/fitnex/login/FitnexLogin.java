package com.pupccis.fitnex.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.main.trainer.TrainerDashboard;
import com.pupccis.fitnex.utilities.Constants;
import com.pupccis.fitnex.utilities.PreferenceManager;
import com.pupccis.fitnex.video_conferencing.VideoActivityDemo;

public class FitnexLogin extends AppCompatActivity implements View.OnClickListener {
    private EditText editEmail, editPassword;
    private Button loginUser;
    private FirebaseAuth mAuth;
    private PreferenceManager preferenceManager;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        preferenceManager = new PreferenceManager(getApplicationContext());

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
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(Constants.KEY_COLLECTION_USERS);
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            Query data = mDatabase.child(userId);

                            data.addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_ID, true);
                                    preferenceManager.putString(Constants.KEY_FULLNAME, snapshot.child(Constants.KEY_FULLNAME).getValue().toString()); //$_SESSION['fullname']
                                    preferenceManager.putString(Constants.KEY_EMAIL, snapshot.child(Constants.KEY_EMAIL).getValue().toString());
                                    preferenceManager.putString(Constants.KEY_AGE, snapshot.child(Constants.KEY_AGE).getValue().toString());
                                    preferenceManager.putString(Constants.KEY_USER_ID, userId);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            Toast.makeText(FitnexLogin.this, "Login Successful! Welcome User", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), TrainerDashboard.class));


                        }
                        else{
                            Toast.makeText(FitnexLogin.this, "Invalid username or password! Please try again", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}