package com.pupccis.fitnex.Activities.Main.Trainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.pupccis.fitnex.Model.DAO.ProgramDAO;
import com.pupccis.fitnex.Model.Program;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.ViewModel.ProgramViewModel;

public class AddProgram extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private Animation rotateAnimation;
    private ImageView imageView;
    private RelativeLayout closeButton;
    private EditText editName, editDescription, editSessionNumber, editDuration;
    private Spinner editCategory;
    private ProgramDAO programDAO = new ProgramDAO();
    private Button addProgram;

    private Program program_intent;
    private ProgramViewModel programViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Extra Intents:
        program_intent = (Program) getIntent().getSerializableExtra("program");

        setContentView(R.layout.activity_add_program);

        Spinner spinner=findViewById(R.id.editTextAddProgramCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        imageView = (ImageView) findViewById(R.id.closeAddProgramButton);
        closeButton = (RelativeLayout) findViewById(R.id.relativeLayoutAddProgramCloseButton);

        //EditTexts
        editName = (EditText) findViewById(R.id.editTextAddProgramName);
        editDescription = (EditText) findViewById(R.id.editTextAddProgramDescription);
        editCategory = (Spinner) findViewById(R.id.editTextAddProgramCategory);
        editSessionNumber = (EditText) findViewById(R.id.editTextAddProgramSessionNumber);
        editDuration = (EditText) findViewById(R.id.editTextAddProgramDuration);
        addProgram = (Button) findViewById(R.id.buttonAddProgramButton);
        addProgram.setOnClickListener(this);
        closeButton.setOnClickListener(this);

        //ViewModel Instantiation
        programViewModel = new ViewModelProvider(this).get(ProgramViewModel .class);
        programViewModel.init(getApplicationContext());

        if(program_intent != null){
            editName.setText(program_intent.getName());
            editDescription.setText(program_intent.getDescription());
//            editCategory.setSelection();
//            editCategory.setText(program_intent.getCategory());
            editSessionNumber.setText(program_intent.getSessionNumber());
            editDuration.setText(program_intent.getDuration());
            editCategory.setSelection(Integer.parseInt(program_intent.getCategory()));
            addProgram.setText("Update Program");
        }

        rotateAnimation();
        closeButton.setVisibility(View.VISIBLE);

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
                String category = "" + editCategory.getSelectedItemPosition();
                String sessionNumber = editSessionNumber.getText().toString();
                String duration = editDuration.getText().toString();
                Program program = new Program.Builder(name, description, category, sessionNumber, duration, FirebaseAuth.getInstance().getCurrentUser().getUid()).build();

                if(program_intent != null){
                    //program.setTrainerID(program_intent.getTrainerID());
                    //program.setProgramID(program_intent.getProgramID());
                    //programDAO.updateProgram(program);
                    Toast.makeText(this, "Program Successfully Updated", Toast.LENGTH_SHORT).show();
                }

                else{
                    programViewModel.insertProgram(program);
                    //programDAO.createProgram(program);
                    Toast.makeText(this, "Program Successfully Created", Toast.LENGTH_SHORT).show();
                }

                break;

        }
        closeForm();
    }

    private void closeForm(){
        startActivity(new Intent(AddProgram.this, TrainerDashboard.class));
        overridePendingTransition(R.anim.slide_in_top,R.anim.stay);
    }

    private void rotateAnimation() {
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        imageView.setImageResource(R.drawable.ic_close_button);
        imageView.startAnimation(rotateAnimation);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        editCategory.setSelection(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
