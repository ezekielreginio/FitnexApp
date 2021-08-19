package com.pupccis.fitnex.video_conferencing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.utilities.Constants;
import com.pupccis.fitnex.utilities.PreferenceManager;

public class VideoActivityDemo extends AppCompatActivity implements View.OnClickListener{
    Button btnTest;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_demo);

        preferenceManager = new PreferenceManager(getApplicationContext());

        btnTest = (Button) findViewById(R.id.buttonTest);
        btnTest.setOnClickListener(this);

        Task<InstallationTokenResult> token = FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener(new OnCompleteListener<InstallationTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<InstallationTokenResult> task) {
                Toast.makeText(VideoActivityDemo.this, "Token: "+task.getResult(), Toast.LENGTH_SHORT).show();
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
        }
    }

    private void sendFCMTokenToDatabase(String token){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(Constants.KEY_COLLECTION_USERS);
        //DocumentReference documentReference = mDatabase.collection
        //mDatabase.update
    }
}