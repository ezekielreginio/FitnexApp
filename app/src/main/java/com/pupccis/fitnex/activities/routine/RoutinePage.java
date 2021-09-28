package com.pupccis.fitnex.activities.routine;

import static com.pupccis.fitnex.handlers.viewmodel.ViewModelHandler.getFirebaseUIRoutineOptions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.pupccis.fitnex.adapters.RoutineAdapter;
import com.pupccis.fitnex.databinding.ActivityRoutinePageBinding;
import com.pupccis.fitnex.handlers.view.WrapContentLinearLayoutManager;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.model.Routine;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.viewmodel.RoutineViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoutinePage extends AppCompatActivity implements View.OnClickListener{
    private ImageView AddRoutineButton;
    private Program program;
    private TextView textViewRoutinePageProgramName;
    private DatabaseReference mDatabase;
    private List<Routine> routineList = new ArrayList<>();
    private RoutineAdapter adapter;
    private RecyclerView recyclerView;
    private RoutineViewModel routineViewModel;
    private static ActivityRoutinePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Extra intents
        program = (Program) getIntent().getSerializableExtra("program");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_routine_page);

        //Get FirestoreOptions From ViewModel
        FirestoreRecyclerOptions<Routine> options = getFirebaseUIRoutineOptions(program.getProgramID());

        //RecyclerView
        recyclerView = binding.recyclerViewRoutinePage;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //Adapter
        adapter = new RoutineAdapter(options);
        recyclerView.setAdapter(adapter);

        binding.setLifecycleOwner(this);
        binding.setViewModel(adapter.getViewModel());


//        //ViewModel instantiation
//        routineViewModel = new ViewModelProvider(this).get(RoutineViewModel.class);
//        routineViewModel.init(getApplicationContext(), program.getProgramID());
//
//        //Layout binding
//        AddRoutineButton = findViewById(R.id.AddRoutineButton);
//        textViewRoutinePageProgramName = findViewById(R.id.textViewRoutinePageProgramName);
//
//        recyclerViewRoutinePage = (RecyclerView) findViewById(R.id.recyclerViewRoutinePage);
//        recyclerViewRoutinePage.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//
//
//        //Event binding
//        AddRoutineButton.setOnClickListener(this);
//
//
//        textViewRoutinePageProgramName.setText(program.getName());

        //Routine Adapter
//        routineAdapter = new RoutineAdapter( routineViewModel.getRoutines().getValue(), "owner", program, getApplicationContext());
//        recyclerViewRoutinePage.setAdapter(routineAdapter);
//        routineViewModel.getRoutines().observe(this, new Observer<ArrayList<Object>>() {
//            @Override
//            public void onChanged(ArrayList<Object> objects) {
//                routineAdapter.notifyDataSetChanged();
//                routineViewModel.getRoutines().removeObserver(this::onChanged);
//            }
//        });


//        routineViewModel.getLiveDataRoutines(program.getProgramID()).observe(this, stringObjectHashMap -> {
//            if(stringObjectHashMap.get(GlobalConstants.KEY_UPDATE_TYPE).equals(GlobalConstants.KEY_UPDATE_TYPE_LOADED))
//                routineAdapter.notifyDataSetChanged();
//
//
//
////            else if(stringObjectHashMap.get(GlobalConstants.KEY_UPDATE_TYPE).equals(GlobalConstants.KEY_UPDATE_TYPE_INSERT))
////                routineAdapter.notifyItemInserted((int) stringObjectHashMap.get("index"));
////            else if(stringObjectHashMap.get(GlobalConstants.KEY_UPDATE_TYPE).equals(GlobalConstants.KEY_UPDATE_TYPE_UPDATE))
////                routineAdapter.notifyItemChanged((int) stringObjectHashMap.get("index"));
////            else if(stringObjectHashMap.get(GlobalConstants.KEY_UPDATE_TYPE).equals(GlobalConstants.KEY_UPDATE_TYPE_DELETE)){
////                Log.d("Removed", "removed");
////                routineAdapter.notifyItemRemoved((int) stringObjectHashMap.get("index"));
//            //}
//        });

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
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(routineViewModel.getRoutines().getValue(), fromPosition, toPosition);
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