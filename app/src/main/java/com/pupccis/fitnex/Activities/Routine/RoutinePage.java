package com.pupccis.fitnex.Activities.Routine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.pupccis.fitnex.Activities.Main.Trainer.Studio.AddVideo;
import com.pupccis.fitnex.Activities.Main.Trainer.Studio.TrainerStudio;
import com.pupccis.fitnex.Models.Program;
import com.pupccis.fitnex.R;

public class RoutinePage extends AppCompatActivity implements View.OnClickListener{
    private ImageView AddRoutineButton;
    private Program program;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_page);

        //Extra intents
        program = (Program) getIntent().getSerializableExtra("program");

        //Layout binding
        AddRoutineButton = findViewById(R.id.AddRoutineButton);

        //Event binding
        AddRoutineButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.AddRoutineButton:
                Intent intent = new Intent(RoutinePage.this, AddRoutine.class);
                intent.putExtra("program", program);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_bottom,R.anim.stay);
                break;
        }
    }
}