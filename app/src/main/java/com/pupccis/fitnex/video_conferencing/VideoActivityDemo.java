package com.pupccis.fitnex.video_conferencing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.pupccis.fitnex.R;

public class VideoActivityDemo extends AppCompatActivity implements View.OnClickListener{
    Button btnTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_demo);

        btnTest = (Button) findViewById(R.id.buttonTest);

        btnTest.setOnClickListener(this);
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
}