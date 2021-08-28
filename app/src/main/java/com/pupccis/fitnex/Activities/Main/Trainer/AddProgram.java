package com.pupccis.fitnex.Activities.Main.Trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.pupccis.fitnex.Models.DAO.ProgramDAO;
import com.pupccis.fitnex.Models.Program;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;

public class AddProgram extends AppCompatActivity implements View.OnClickListener{
    private Animation rotateAnimation;
    private ImageView imageView;
    private RelativeLayout closeButton;
    private EditText editName, editDescription, editCategory, editSessionNumber, editDuration;
    private ProgramDAO programDAO = new ProgramDAO();
    private Button addProgram;

    private Program program_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Extra Intents:
        program_intent = (Program) getIntent().getSerializableExtra("program");

        setContentView(R.layout.activity_add_program);

        Spinner spinner=findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        imageView = (ImageView) findViewById(R.id.closeAddProgramButton);
        closeButton = (RelativeLayout) findViewById(R.id.relativeLayoutAddProgramCloseButton);

        //EditTexts
        editName = (EditText) findViewById(R.id.editTextAddProgramName);
        editDescription = (EditText) findViewById(R.id.editTextAddProgramDescription);
        editCategory = (EditText) findViewById(R.id.editTextAddProgramCategory);
        editSessionNumber = (EditText) findViewById(R.id.editTextAddProgramSessionNumber);
        editDuration = (EditText) findViewById(R.id.editTextAddProgramDuration);
        addProgram = (Button) findViewById(R.id.buttonAddProgramButton);
        addProgram.setOnClickListener(this);
        closeButton.setOnClickListener(this);

        if(program_intent != null){
            editName.setText(program_intent.getName());
            editDescription.setText(program_intent.getDescription());
            editCategory.setText(program_intent.getCategory());
            editSessionNumber.setText(program_intent.getSessionNumber());
            editDuration.setText(program_intent.getDuration());
            addProgram.setText("Update Program");
        }

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case(R.id.relativeLayoutAddProgramCloseButton):
                closeForm();
                break;
            case(R.id.buttonAddProgramButton):
                String name = editName.getText().toString();
                String description = editDescription.getText().toString();
                String category = editCategory.getText().toString();
                String sessionNumber = editSessionNumber.getText().toString();
                String duration = editDuration.getText().toString();
                Program program = new Program(name, description, category, sessionNumber, duration);
                if(program_intent != null){
                    program.setProgramTrainerID(program_intent.getProgramTrainerID());
                    program.setProgramID(program_intent.getProgramID());
                    programDAO.updateProgram(program);
                    Toast.makeText(this, "Program Successfully Updated", Toast.LENGTH_SHORT).show();
                    closeForm();
                }

                else{
                    programDAO.createProgram(program);
                    Toast.makeText(this, "Program Successfully Created", Toast.LENGTH_SHORT).show();
                    closeForm();
                }

                break;
        }
    }

    private void closeForm(){
        startActivity(new Intent(AddProgram.this, TrainerDashboard.class));
        overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
    }
}
