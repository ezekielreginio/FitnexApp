package com.pupccis.fitnex.Activities.Main.Trainer.Studio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.pupccis.fitnex.Activities.Main.Trainer.AddProgram;
import com.pupccis.fitnex.Activities.Main.Trainer.TrainerDashboard;
import com.pupccis.fitnex.Activities.VideoConferencing.VideoActivityDemo;
import com.pupccis.fitnex.R;

public class TrainerStudio extends AppCompatActivity implements View.OnClickListener {
    //Private Layout Attributes
    private LinearLayout btnAddVideo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_studio);

        //Layout Binding:
        btnAddVideo = findViewById(R.id.linearLayoutAddVideoButton);

        //EventBinding:
        btnAddVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case (R.id.linearLayoutAddVideoButton):
                startActivity(new Intent(TrainerStudio.this, AddVideo.class));
                overridePendingTransition(R.anim.slide_in_bottom,R.anim.stay);
                break;
        }
    }
}