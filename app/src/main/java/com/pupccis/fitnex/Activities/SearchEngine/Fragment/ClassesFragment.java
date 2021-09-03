package com.pupccis.fitnex.Activities.SearchEngine.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pupccis.fitnex.API.adapter.FitnessClassAdapter;
import com.pupccis.fitnex.API.adapter.SearchEngineAdapter.fitnessClassSEAdapter;
import com.pupccis.fitnex.Models.FitnessClass;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.Constants.FitnessClassConstants;

import java.util.ArrayList;
import java.util.List;


public class ClassesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText searchBox;
    private FitnessClassAdapter fitnessClassAdapter;
    private RecyclerView fitnessClassSERecyclerView;
    private DatabaseReference mDatabase;
    private View view;
    private List<FitnessClass> fitnessClassList = new ArrayList<>();
    public ClassesFragment(View view) {
        // Required empty public constructor
        this.view = view;
    }

    // TODO: Rename and change types and number of parameters
//    public static ClassesFragment newInstance(String param1, String param2) {
//        ClassesFragment fragment = new ClassesFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mDatabase = FirebaseDatabase.getInstance().getReference(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES);

        searchBox = view.findViewById(R.id.searchEngineSearchBox);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        fitnessClassList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            FitnessClass fitnessClassListContainer = new FitnessClass();

                            if (dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_NAME).getValue().toString().toLowerCase().contains(charSequence.toString())){
                                fitnessClassListContainer.setClassName(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_NAME).getValue().toString());
                                fitnessClassListContainer.setCategory(Integer.parseInt(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_CATEGORY).getValue().toString()));
                                fitnessClassListContainer.setDescription(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_DESCRIPTION).getValue().toString());
                                fitnessClassListContainer.setSessionNo(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_SESSION_NUMBER).getValue().toString());
                                fitnessClassListContainer.setDuration(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_DURATION).getValue().toString());
                                fitnessClassList.add(fitnessClassListContainer);
                            }
                        }
                        fitnessClassAdapter = new FitnessClassAdapter(fitnessClassList, getContext());
                        fitnessClassAdapter.notifyDataSetChanged();
                        fitnessClassSERecyclerView.setAdapter(fitnessClassAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        fitnessClassSERecyclerView = getView().findViewById(R.id.recyclerViewSearchEngineClasses);
        fitnessClassSERecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_classes_se, container, false);
    }
}