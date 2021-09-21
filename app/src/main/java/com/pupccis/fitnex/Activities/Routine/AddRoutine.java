package com.pupccis.fitnex.Activities.Routine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.pupccis.fitnex.Model.Program;
import com.pupccis.fitnex.Model.Routine;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.Constants.GlobalConstants;

public class AddRoutine extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private EditText editTextAddRoutineName, editTextAddRoutineReps, editTextAddRoutineSets, editTextAddRoutineWeights, editTextAddRoutineDuration;
    private Spinner editTextAddRoutineCategory;
    private Program program;
    private Button buttonAddRoutineButton;
    private LinearLayout linearLayoutAddRoutineStrengthFields;
    private Routine routine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routine);

        //Extra intents
        program = (Program) getIntent().getSerializableExtra("program");
        //Layout binding
        editTextAddRoutineName = findViewById(R.id.editTextAddRoutineName);
        editTextAddRoutineCategory = findViewById(R.id.editTextAddRoutineCategory);
        editTextAddRoutineReps = findViewById(R.id.editTextAddRoutineReps);
        editTextAddRoutineSets = findViewById(R.id.editTextAddRoutineSets);
        editTextAddRoutineWeights = findViewById(R.id.editTextAddRoutineWeights);
        editTextAddRoutineDuration = findViewById(R.id.editTextAddRoutineDuration);
        linearLayoutAddRoutineStrengthFields = findViewById(R.id.linearLayoutAddRoutineStrengthFields);
        buttonAddRoutineButton = findViewById(R.id.buttonAddRoutineButton);
        buttonAddRoutineButton.setOnClickListener(this);
        //Spinner binding
        Spinner spinner=findViewById(R.id.editTextAddRoutineCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        if(Integer.parseInt(program.getCategory())==2){

        }else{
            linearLayoutAddRoutineStrengthFields.setVisibility(View.GONE);
        }


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.buttonAddRoutineButton):
                String routineName = editTextAddRoutineName.getText().toString();

                if(Integer.parseInt(program.getCategory())==2){
                    String reps = editTextAddRoutineReps.getText().toString();
                    String sets = editTextAddRoutineSets.getText().toString();
                    String weight = editTextAddRoutineWeights.getText().toString();
  //                  routine = new Routine.RoutineBuilder(routineName).reps(Integer.parseInt(reps)).sets(Integer.parseInt(sets)).weight(Double.parseDouble(weight)).build();
                }else{
                    String duration = editTextAddRoutineDuration.getText().toString();
            //        routine = new Routine.RoutineBuilder(routineName).duration(Integer.parseInt(duration)).build();
                    Log.d("Category", program.getCategory().toString());
                }
             //   routineDAO.createRoutine(routine, program);
                break;

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        editTextAddRoutineCategory.setSelection(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}