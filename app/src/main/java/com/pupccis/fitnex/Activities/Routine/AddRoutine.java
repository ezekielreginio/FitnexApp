package com.pupccis.fitnex.Activities.Routine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.pupccis.fitnex.R;

public class AddRoutine extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private EditText editTextAddRoutineName, editTextAddRoutineReps, editTextAddPRoutineSets, editTextAddPRoutineWeights, editTextAddRoutineDuration;
    private Spinner editTextAddRoutineCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routine);
        editTextAddRoutineName = findViewById(R.id.editTextAddRoutineName);
        editTextAddRoutineCategory = findViewById(R.id.editTextAddRoutineCategory);
        editTextAddRoutineReps = findViewById(R.id.editTextAddRoutineReps);
        editTextAddPRoutineSets = findViewById(R.id.editTextAddPRoutineSets);
        editTextAddPRoutineWeights = findViewById(R.id.editTextAddPRoutineWeights);
        editTextAddRoutineDuration = findViewById(R.id.editTextAddRoutineDuration);

        //Spinner binding
        Spinner spinner=findViewById(R.id.editTextAddRoutineCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {

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