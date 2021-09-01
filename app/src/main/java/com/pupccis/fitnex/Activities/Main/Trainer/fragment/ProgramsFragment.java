package com.pupccis.fitnex.Activities.Main.Trainer.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pupccis.fitnex.API.adapter.ProgramAdapter;
import com.pupccis.fitnex.API.adapter.VIewHolders.ProgramViewHolder;
import com.pupccis.fitnex.Models.DAO.ProgramDAO;
import com.pupccis.fitnex.Models.Program;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.Utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.Utilities.Preferences.UserPreferences;
import com.pupccis.fitnex.Utilities.VideoConferencingConstants;

import java.util.ArrayList;
import java.util.List;

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

    private ProgramAdapter programAdapter;
    private UserPreferences userPreferences;
    private ProgramDAO programDAO = new ProgramDAO();

    private RecyclerView programsRecyclerView;


    private List<Program> programs = new ArrayList<>();
    private DatabaseReference mDatabase;

    public ProgramsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProgramsFragment.
     */
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

        Query query = FirebaseDatabase.getInstance().getReference(ProgramConstants.KEY_COLLECTION_PROGRAMS).orderByChild(ProgramConstants.KEY_PROGRAM_TRAINER_ID).equalTo(userPreferences.getString(VideoConferencingConstants.KEY_USER_ID));

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                programs.clear();
                //DataSnapshot dataSnapshot : snapshot.getChildren()

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Program program = new Program();
                    program.setName(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_NAME).getValue().toString());
                    program.setDescription(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_DESCRIPTION).getValue().toString());
                    program.setCategory(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_CATEGORY).getValue().toString());
                    program.setSessionNumber(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_SESSION_NUMBER).getValue().toString());
                    program.setDuration(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_DURATION).getValue().toString());
                    program.setProgramID(dataSnapshot.getKey());
                    program.setTrainerID(userPreferences.getString(VideoConferencingConstants.KEY_USER_ID));
                    programs.add(program);


                }
                setAdapter();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            void setAdapter(){
                programAdapter = new ProgramAdapter(programs, getContext());
                programAdapter.notifyDataSetChanged();
                programsRecyclerView.setAdapter(programAdapter);
            }
        });

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
        programsRecyclerView = (RecyclerView) getView().findViewById(R.id.programsRecyclerView);
        programsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        //options = new FirebaseRecyclerOptions.Builder<Program>().setQuery(ref, Program.class)
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Started", "onStartExecuted");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_programs, container, false);
    }
}