package com.pupccis.fitnex.Activities.Routine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.pupccis.fitnex.Model.Program;
import com.pupccis.fitnex.Model.Routine;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.Utilities.Constants.RoutineConstants;
import com.pupccis.fitnex.ViewModel.RoutineViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class RoutinePage extends AppCompatActivity implements View.OnClickListener{
    private ImageView AddRoutineButton;
    private Program program;
    private TextView textViewRoutinePageProgramName;
    private DatabaseReference mDatabase;
    private List<Routine> routineList = new ArrayList<>();
    private RoutineAdapter routineAdapter;
    private RecyclerView routinePage;
    private RoutineViewModel routineViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_page);

        //Extra intents
        program = (Program) getIntent().getSerializableExtra("program");
        Log.d("+=========================", program.getProgramID());
        //ViewModel instantiation
        routineViewModel = new ViewModelProvider(this).get(RoutineViewModel.class);
        routineViewModel.init(getApplicationContext(), program.getProgramID());
        Log.d("Query Routine", routineViewModel.getRoutines().getValue().toString());
        //Layout binding
        AddRoutineButton = findViewById(R.id.AddRoutineButton);
        textViewRoutinePageProgramName = findViewById(R.id.textViewRoutinePageProgramName);

        routinePage = (RecyclerView) findViewById(R.id.recyclerViewRoutinePage);
        routinePage.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //Event binding
        AddRoutineButton.setOnClickListener(this);


        textViewRoutinePageProgramName.setText(program.getName());

        //Routine Adapter
        routineAdapter = new RoutineAdapter(routineViewModel.getRoutines().getValue(), "owner", program.getCategory());
        routinePage.setAdapter(routineAdapter);

        routineViewModel.getRoutines().observe(this, new Observer<ArrayList<Object>>() {
            @Override
            public void onChanged(ArrayList<Object> objects) {
                routineAdapter.notifyDataSetChanged();
                routineViewModel.getRoutines().removeObserver(this::onChanged);
                initObserver();
            }


        });
        private void initObserver() {
            routineViewModel.getLiveDataRoutines(program.getProgramID()).observe(this, stringObjectHashMap -> {
                if(stringObjectHashMap.get(GlobalConstants.KEY_UPDATE_TYPE).equals(GlobalConstants.KEY_UPDATE_TYPE_INSERT))
                    routineAdapter.notifyItemInserted((int) stringObjectHashMap.get("index"));
                else if(stringObjectHashMap.get(GlobalConstants.KEY_UPDATE_TYPE).equals(GlobalConstants.KEY_UPDATE_TYPE_UPDATE))
                    routineAdapter.notifyItemChanged((int) stringObjectHashMap.get("index"));
                else if(stringObjectHashMap.get(GlobalConstants.KEY_UPDATE_TYPE).equals(GlobalConstants.KEY_UPDATE_TYPE_DELETE)){
                    Log.d("Removed", "removed");
                    routineAdapter.notifyItemRemoved((int) stringObjectHashMap.get("index"));
                }
            });
        };



        //Query
//        mDatabase = FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS).child(program.getProgramID()).child(RoutineConstants.KEY_COLLECTION_ROUTINE);
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                routineList.clear();
//                if (Integer.parseInt(program.getCategory())==2){
//                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                        Routine routine = new Routine.RoutineBuilder(
//                                dataSnapshot.child(RoutineConstants.KEY_ROUTINE_NAME).getValue().toString())
//                                .reps(Integer.parseInt(dataSnapshot.child(RoutineConstants.KEY_ROUTINE_REPS).getValue().toString()))
//                                .sets(Integer.parseInt(dataSnapshot.child(RoutineConstants.KEY_ROUTINE_SETS).getValue().toString()))
//                                .weight(Double.parseDouble(dataSnapshot.child(RoutineConstants.KEY_ROUTINE_WEIGHT).getValue().toString()))
//                                .build();
//
//                        routineList.add(routine);
//                    }
//                }else{
//                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                        Routine routine = new Routine.RoutineBuilder(
//                                dataSnapshot.child(RoutineConstants.KEY_ROUTINE_NAME).getValue().toString()
//                        ).reps(Integer.parseInt(dataSnapshot.child(RoutineConstants.KEY_ROUTINE_REPS).getValue().toString()))
//                                .duration(Integer.parseInt(dataSnapshot.child(RoutineConstants.KEY_ROUTINE_DURATION).getValue().toString()))
//                                .build();
//
//                        routineList.add(routine);
//                    }
//
//                }
//                routineAdapter = new RoutineAdapter(routineList, "owner", program.getCategory());
//                routinePage.setAdapter(routineAdapter);
//                routineAdapter.notifyDataSetChanged();
//                routineAdapter.setList(routineList);
//
//            }
//
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(routinePage);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(routineList, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            //routineAdapter.notifyDataSetChanged();
            //updateRoutineOrder(routineList, program.getProgramID());
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

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