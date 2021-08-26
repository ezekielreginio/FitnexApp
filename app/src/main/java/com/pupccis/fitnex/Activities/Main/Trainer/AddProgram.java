package com.pupccis.fitnex.Activities.Main.Trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.pupccis.fitnex.R;

public class AddProgram extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Animation rotateAnimation;
    ImageView imageView;
    RelativeLayout closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_program);

        Spinner spinner=findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        imageView = (ImageView) findViewById(R.id.closeAddProgramButton);
        closeButton = (RelativeLayout) findViewById(R.id.relativeLayoutAddProgramCloseButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
                
                startActivity(new Intent(AddProgram.this, TrainerDashboard.class));
            }
        });

        rotateAnimation();
        closeButton.setVisibility(View.VISIBLE);
    }



    private void rotateAnimation() {
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        imageView.setImageResource(R.drawable.ic_close_button);
        imageView.startAnimation(rotateAnimation);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

