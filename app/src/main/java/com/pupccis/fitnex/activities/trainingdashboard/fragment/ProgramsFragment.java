package com.pupccis.fitnex.activities.trainingdashboard.fragment;

import static com.pupccis.fitnex.handlers.viewmodel.ViewModelHandler.getFirebaseUIProgramOptions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pupccis.fitnex.activities.trainingdashboard.AddProgram;
import com.pupccis.fitnex.activities.routine.RoutinePage;
import com.pupccis.fitnex.adapters.ProgramAdapter;
import com.pupccis.fitnex.api.enums.AccessType;
import com.pupccis.fitnex.databinding.FragmentProgramsBinding;
import com.pupccis.fitnex.handlers.view.WrapContentLinearLayoutManager;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.utilities.Preferences.UserPreferences;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgramsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgramsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private UserPreferences userPreferences;

    private static FragmentProgramsBinding fragmentProgramsBinding;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference programRef = db.collection(ProgramConstants.KEY_COLLECTION_PROGRAMS);
    private ProgramAdapter adapter;
    private RecyclerView recyclerView;
    public ProgramsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProgramsFragment newInstance(String param1, String param2) {
        ProgramsFragment fragment = new ProgramsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        userPreferences = new UserPreferences(getActivity().getApplicationContext());



//        Query query = FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS).orderByChild(ProgramConstants.KEY_PROGRAM_TRAINER_ID).equalTo(userPreferences.getString(VideoConferencingConstants.KEY_USER_ID));
//
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                programs.clear();
//                //DataSnapshot dataSnapshot : snapshot.getChildren()
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//
//                    Program program = new Program();
//                    program.setName(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_NAME).getValue().toString());
//                    program.setDescription(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_DESCRIPTION).getValue().toString());
//                    program.setCategory(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_CATEGORY).getValue().toString());
//                    program.setSessionNumber(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_SESSION_NUMBER).getValue().toString());
//                    program.setDuration(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_DURATION).getValue().toString());
//                    program.setProgramID(dataSnapshot.getKey());
//                    program.setTrainerID(userPreferences.getString(VideoConferencingConstants.KEY_USER_ID));
//                    programs.add(program);
//
//
//                }
//                setAdapter();
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//            void setAdapter(){
//                programAdapter = new ProgramAdapter(programs, getContext(), GlobalConstants.KEY_ACCESS_TYPE_OWNER);
//                programAdapter.notifyDataSetChanged();
//                programsRecyclerView.setAdapter(programAdapter);
//            }
//        });

//        mDatabase.child(preferenceManager.getString(VideoConferencingConstants.KEY_USER_ID)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                programs.clear();
//                for (DataSnapshot dataSnapshot : task.getResult().getChildren()){
//                    Program program = new Program();
//                    program.setName(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_NAME).getValue().toString());
//                    program.setDescription(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_DESCRIPTION).getValue().toString());
//                    program.setCategory(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_CATEGORY).getValue().toString());
//                    program.setSessionNumber(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_SESSION_NUMBER).getValue().toString());
//                    program.setDuration(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_DURATION).getValue().toString());
//                    programs.add(program);
//                    Log.d("Name: ",program.getName());
//                    Log.d("List: ",programs.toString());
//                }
//                programAdapter = new ProgramAdapter(programs);
//                programsRecyclerView.setAdapter(programAdapter);
//            }
//        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Log.d("ViewCreate", "onViewCreateExecuted");
        super.onViewCreated(view, savedInstanceState);

//
//        //RecyclerView Initialization
//        programsRecyclerView = (RecyclerView) getView().findViewById(R.id.programsRecyclerView);
//        programsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
//        //options = new FirebaseRecyclerOptions.Builder<Program>().setQuery(ref, Program.class)
//
//        //ViewModel Instantiation
////        programViewModel = new ViewModelProvider(this).get(ProgramViewModel.class);
////        programViewModel.init(getActivity().getApplicationContext());
//
//        //RecyclerView Adapter
//        programAdapter = new ProgramAdapter(programViewModel.getPrograms().getValue(), getContext(), programViewModel, GlobalConstants.KEY_ACCESS_TYPE_OWNER);
//        programsRecyclerView.setAdapter(programAdapter);
//
//        //Live Data Observers
//        programViewModel.getLiveDataProgramUpdate().observe(getActivity(), new Observer<HashMap<String, Object>>() {
//            @Override
//            public void onChanged(HashMap<String, Object> stringObjectHashMap) {
//                if(stringObjectHashMap.get(GlobalConstants.KEY_UPDATE_TYPE).equals(GlobalConstants.KEY_UPDATE_TYPE_LOADED))
//                    programAdapter.notifyDataSetChanged();
//                else if(stringObjectHashMap.get(GlobalConstants.KEY_UPDATE_TYPE).equals(GlobalConstants.KEY_UPDATE_TYPE_INSERT))
//                    programAdapter.notifyItemInserted((int) stringObjectHashMap.get("index"));
//                else if(stringObjectHashMap.get(GlobalConstants.KEY_UPDATE_TYPE).equals(GlobalConstants.KEY_UPDATE_TYPE_UPDATE))
//                    programAdapter.notifyItemChanged((int) stringObjectHashMap.get("index"));
//                else if(stringObjectHashMap.get(GlobalConstants.KEY_UPDATE_TYPE).equals(GlobalConstants.KEY_UPDATE_TYPE_DELETE)){
//                    Log.d("Removed", "removed");
//                    programAdapter.notifyItemRemoved((int) stringObjectHashMap.get("index"));
//                }
//
//            }
//        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Started", "onStartExecuted");
        adapter.startListening();;
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("OnCreateView", "executed");
        // Inflate the layout for this fragment
        fragmentProgramsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_programs, container, false);

        //Get FirestoreOptions From ViewModel
        FirestoreRecyclerOptions<Program> options = getFirebaseUIProgramOptions();

        //Instantiate and Set RecyclerView Settings
        recyclerView = fragmentProgramsBinding.programsRecyclerView;
        recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        //Instantiate Adapter and Bind to RecyclerView
        adapter = new ProgramAdapter(options);
        adapter.setAccessType(AccessType.OWNER);
        recyclerView.setAdapter(adapter);

        //Set Lifecycle and ViewModel of Binding
        fragmentProgramsBinding.setLifecycleOwner(this);
        fragmentProgramsBinding.setViewModel(adapter.getViewModel()); //Get the View Model from Adapter

        //ViewModel Observers
        fragmentProgramsBinding.getViewModel().routineObserver().observe(fragmentProgramsBinding.getLifecycleOwner(), new Observer<Program>() {
            @Override
            public void onChanged(Program program) {
                Intent intent = new Intent(getContext(), RoutinePage.class);
                intent.putExtra("program", program);
                Log.e("Program Fragment", program.getProgramID());
                startActivity(intent);
            }
        });
        fragmentProgramsBinding.getViewModel().updateObserver().observe(fragmentProgramsBinding.getLifecycleOwner(), new Observer<Program>() {
            @Override
            public void onChanged(Program program) {

                Intent intent= new Intent(getContext(), AddProgram.class);
                intent.putExtra("program", program);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_bottom,R.anim.stay);
            }
        });

        fragmentProgramsBinding.getViewModel().deleteObserver().observe(fragmentProgramsBinding.getLifecycleOwner(), new Observer<Program>() {
            @Override
            public void onChanged(Program program) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Log.d("Delete", "Clicked");
                                fragmentProgramsBinding.getViewModel().deleteProgram(program.getProgramID());
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you wish to delete this program?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        //Get the View from binding and return its value *REQUIRED
        View view = fragmentProgramsBinding.getRoot();
        return view;
    }

}