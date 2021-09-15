package com.pupccis.fitnex.Activities.Routine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pupccis.fitnex.API.adapter.RoutineAdapter;
import com.pupccis.fitnex.Activities.Main.Trainer.Studio.AddVideo;
import com.pupccis.fitnex.Activities.Main.Trainer.Studio.TrainerStudio;
import com.pupccis.fitnex.Models.Program;
import com.pupccis.fitnex.Models.Routine;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.Utilities.Constants.RoutineConstants;

import java.util.ArrayList;
import java.util.List;

public class RoutinePage extends AppCompatActivity implements View.OnClickListener{
    private ImageView AddRoutineButton;
    private Program program;
    private TextView textViewRoutinePageProgramName;
    private DatabaseReference mDatabase;
    private List<Routine> routineList = new ArrayList<>();
    private RoutineAdapter routineAdapter;
    private RecyclerView routinePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_page);

        //Extra intents
        program = (Program) getIntent().getSerializableExtra("program");

        //Layout binding
        AddRoutineButton = findViewById(R.id.AddRoutineButton);
        textViewRoutinePageProgramName = findViewById(R.id.textViewRoutinePageProgramName);

        routinePage = (RecyclerView) findViewById(R.id.recyclerViewRoutinePage);
        routinePage.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //Event binding
        AddRoutineButton.setOnClickListener(this);


        textViewRoutinePageProgramName.setText(program.getName());

        //Query
        mDatabase = FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS).child(program.getProgramID()).child(RoutineConstants.KEY_COLLECTION_ROUTINE);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                routineList.clear();
                if (Integer.parseInt(program.getCategory())==2){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Routine routine = new Routine.RoutineBuilder(
                                dataSnapshot.child(RoutineConstants.KEY_ROUTINE_NAME).getValue().toString())
                                .reps(Integer.parseInt(dataSnapshot.child(RoutineConstants.KEY_ROUTINE_REPS).getValue().toString()))
                                .sets(Integer.parseInt(dataSnapshot.child(RoutineConstants.KEY_ROUTINE_SETS).getValue().toString()))
                                .weight(Double.parseDouble(dataSnapshot.child(RoutineConstants.KEY_ROUTINE_WEIGHT).getValue().toString()))
                                .build();

                        routineList.add(routine);
                    }
                }else{
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Routine routine = new Routine.RoutineBuilder(
                                dataSnapshot.child(RoutineConstants.KEY_ROUTINE_NAME).getValue().toString()
                        ).reps(Integer.parseInt(dataSnapshot.child(RoutineConstants.KEY_ROUTINE_REPS).getValue().toString()))
                                .duration(Integer.parseInt(dataSnapshot.child(RoutineConstants.KEY_ROUTINE_DURATION).getValue().toString()))
                                .build();

                        routineList.add(routine);
                    }

                }
                routineAdapter = new RoutineAdapter(routineList, "owner", program.getCategory());
                routinePage.setAdapter(routineAdapter);
                routineAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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