package com.pupccis.fitnex.Activities.Main.Trainer.Studio;

import static com.pupccis.fitnex.Utilities.Globals.RotateAnimation.rotateAnimation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.pupccis.fitnex.R;

public class AddVideo extends AppCompatActivity {
    //Private Layout Attributes
    ImageView btnClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);

        //Layout Binding
        btnClose = findViewById(R.id.closeAddClassButton);

        //Rotate Animation
        rotateAnimation(this, btnClose);
    }
}