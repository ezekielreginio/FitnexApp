package com.pupccis.fitnex.Activities.Main.Trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
    private ProgramDAO programDAO;
    private Button addProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_program);
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

        rotateAnimation();
        closeButton.setVisibility(View.VISIBLE);
    }

    private void rotateAnimation() {
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        imageView.setImageResource(R.drawable.ic_close_button);
        imageView.startAnimation(rotateAnimation);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case(R.id.relativeLayoutAddProgramCloseButton):
                startActivity(new Intent(AddProgram.this, TrainerDashboard.class));
                overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
                break;
            case(R.id.buttonAddProgramButton):
                String name = editName.getText().toString();
                String description = editDescription.getText().toString();
                String category = editCategory.getText().toString();
                String sessionNumber = editSessionNumber.getText().toString();
                String duration = editDuration.getText().toString();
                Program program = new Program(name, description, category, sessionNumber, duration);
                FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(program);
                //programDAO.createProgram(program);
                break;
        }
    }
}