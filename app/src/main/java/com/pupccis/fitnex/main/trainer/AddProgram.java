package com.pupccis.fitnex.main.trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pupccis.fitnex.R;

public class AddProgram extends AppCompatActivity {
    Animation rotateAnimation;
    ImageView imageView;
    RelativeLayout closeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_program);
        imageView = (ImageView) findViewById(R.id.closeAddProgramButton);
        closeButton = (RelativeLayout) findViewById(R.id.relativeLayoutAddProgramCloseButton);

        rotateAnimation();
        closeButton.setVisibility(View.VISIBLE);
    }

    private void rotateAnimation() {
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        imageView.startAnimation(rotateAnimation);
    }

}