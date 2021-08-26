package com.pupccis.fitnex.Activities.Main.Trainer.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pupccis.fitnex.API.adapter.FitnessClassAdapter;
import com.pupccis.fitnex.Models.DAO.FitnessClassDAO;
import com.pupccis.fitnex.Models.FitnessClass;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.Constants.FitnessClassConstants;
import com.pupccis.fitnex.Utilities.PreferenceManager;
import com.pupccis.fitnex.Utilities.VideoConferencingConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FitnessClassAdapter fitnessClassAdapter;
    private PreferenceManager preferenceManager;
    private FitnessClassDAO fitnessClassDAO = new FitnessClassDAO();

    private RecyclerView fitnessClassRecyclerView;

    private List<FitnessClass> fitnessClasses = new ArrayList<>();
    private DatabaseReference mDatabase;
    public ClassFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassFragment newInstance(String param1, String param2) {
        ClassFragment fragment = new ClassFragment();
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
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());

        mDatabase = FirebaseDatabase.getInstance().getReference(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES);

        mDatabase.child(preferenceManager.getString(VideoConferencingConstants.KEY_USER_ID)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            fitnessClasses.clear();
            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                FitnessClass fitnessClass = new FitnessClass();
                fitnessClass.setClassName(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_NAME).getValue().toString());
                fitnessClass.setTimeStart(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_START).getValue().toString());
                fitnessClass.setTimeEnd(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_END).getValue().toString());
                fitnessClass.setSessionNo(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_SESSION_NUMBER).getValue().toString());
                fitnessClass.setDescription(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_DESCRIPTION).getValue().toString());
                fitnessClass.setClassID(dataSnapshot.getKey());
                fitnessClass.setClassTrainerID(preferenceManager.getString(VideoConferencingConstants.KEY_USER_ID));
                fitnessClasses.add(fitnessClass);
            }
            fitnessClassAdapter = new FitnessClassAdapter(fitnessClasses, getContext());
            fitnessClassAdapter.notifyDataSetChanged();
            fitnessClassRecyclerView.setAdapter(fitnessClassAdapter);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        fitnessClassRecyclerView = (RecyclerView) getView().findViewById(R.id.fitnessClassesRecyclerView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fitness_classes, container, false);
    }
}