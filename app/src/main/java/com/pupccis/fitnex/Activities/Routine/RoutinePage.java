package com.pupccis.fitnex.Activities.Routine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class RoutinePage extends AppCompatActivity implements View.OnClickListener, StartDragListener{
    private ImageView AddRoutineButton, RoutineDrag;
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

        RoutineDrag = findViewById(R.id.RoutineDrag);
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
                routineAdapter.setList(routineList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(routinePage);

    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN, 0) {
//        @Override
//        public boolean isLongPressDragEnabled() {
//            return false;
//        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(routineList, fromPosition, toPosition);
            routinePage.getAdapter().notifyItemMoved(fromPosition, toPosition);
            updateRoutineOrder();
            return false;
        }

        private void updateRoutineOrder() {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS).child(RoutineConstants.KEY_COLLECTION_ROUTINE);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("position", routineList);
            ref.updateChildren(hashMap);
            Log.d("Update routine", "updated");
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

    @Override
    public void requestDrag(RecyclerView.ViewHolder viewHolder) {

    }
}