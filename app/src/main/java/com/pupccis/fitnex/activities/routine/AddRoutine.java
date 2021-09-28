package com.pupccis.fitnex.activities.routine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;


import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.model.Routine;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.viewmodel.RoutineViewModel;

public class AddRoutine extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private EditText editTextAddRoutineName, editTextAddRoutineReps, editTextAddRoutineSets, editTextAddRoutineWeights, editTextAddRoutineDuration;
    private Spinner editTextAddRoutineCategory;
    private Program program;
    private Button buttonAddRoutineButton;
    private LinearLayout linearLayoutAddRoutineStrengthFields;
    private Routine routine;
    private RoutineViewModel routineViewModel;
    private Routine routine_intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routine);

        //Extra intent
         routine_intent = (Routine) getIntent().getSerializableExtra("routine");

        //ViewModel Instantiation
        routineViewModel = new ViewModelProvider(this).get(RoutineViewModel.class);

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


        if(routine_intent != null){
            editTextAddRoutineName.setText(routine_intent.getName());
            editTextAddRoutineDuration.setText(routine_intent.getDuration()+"");
            editTextAddRoutineReps.setText(routine_intent.getReps()+"");
            editTextAddRoutineSets.setText(routine_intent.getSets()+"");
            editTextAddRoutineWeights.setText(routine_intent.getWeight()+"");
            buttonAddRoutineButton.setText("Update Routine");
        }

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.buttonAddRoutineButton):
                String routineName = editTextAddRoutineName.getText().toString();


             //   routineDAO.createRoutine(routine, program);
                if(routine_intent != null){
                    routine.setRoutineID(routine_intent.getId());
                    routineViewModel.updateRoutine(routine);
                }

                else
                    routineViewModel.insertRoutine(routine);

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