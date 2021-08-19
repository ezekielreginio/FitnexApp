package com.pupccis.fitnex.video_conferencing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.login.FitnexLogin;
import com.pupccis.fitnex.utilities.Constants;
import com.pupccis.fitnex.utilities.PreferenceManager;

public class VideoActivityDemo extends AppCompatActivity implements View.OnClickListener{
    Button btnTest;
    TextView btnSignOut;
    private DatabaseReference mDatabase;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_demo);

        preferenceManager = new PreferenceManager(getApplicationContext());

        btnTest = (Button) findViewById(R.id.buttonTest);
        btnTest.setOnClickListener(this);

        btnSignOut =(TextView) findViewById(R.id.textSignOut);
        btnSignOut.setOnClickListener(this);

        Task<InstallationTokenResult> token = FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener(new OnCompleteListener<InstallationTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<InstallationTokenResult> task) {
                Toast.makeText(VideoActivityDemo.this, "Token: "+task.getResult().getToken(), Toast.LENGTH_SHORT).show();
                sendFCMTokenToDatabase(task.getResult().getToken());
            }
        });

        //token.
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.buttonTest):
                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Toast.makeText(VideoActivityDemo.this, "Test: "+id, Toast.LENGTH_SHORT).show();
                break;
            case(R.id.textSignOut):
                signOut();
                break;
        }
    }

    private void sendFCMTokenToDatabase(String token){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.KEY_COLLECTION_USERS);
        mDatabase.child(preferenceManager.getString(Constants.KEY_USER_ID)).child(Constants.KEY_FCM_TOKEN).setValue(token);
        //DocumentReference documentReference = mDatabase.collection
        //mDatabase.update
    }

    private void signOut(){
        Toast.makeText(VideoActivityDemo.this, "Signing Out", Toast.LENGTH_SHORT).show();
        mDatabase.child(preferenceManager.getString(Constants.KEY_USER_ID)).child(Constants.KEY_FCM_TOKEN).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(VideoActivityDemo.this, "Signing Out...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), FitnexLogin.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VideoActivityDemo.this, "Failed to Sign out. Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}