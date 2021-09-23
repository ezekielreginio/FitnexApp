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
import com.pupccis.fitnex.API.adapter.ProgramAdapter;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.R;

import java.util.ArrayList;
import java.util.List;


public class ProgramsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText searchBox;
    private ProgramAdapter programAdapter;
    private RecyclerView ProgramSERecyclerView;
    private DatabaseReference mDatabase;
    private View view;
    private List<Program> programList = new ArrayList<>();
    public ProgramsFragment(View view) {
        this.view = view;
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
//    public static ProgramsFragment newInstance(String param1, String param2) {
//        ProgramsFragment fragment = new ProgramsFragment();
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
        mDatabase = FirebaseDatabase.getInstance().getReference("programs");

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
                        programList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            //Program programListContainer = new Program();

//                            if (dataSnapshot.child(ProgramConstants.KEY_PROGRAM_NAME).getValue().toString().toLowerCase().contains(charSequence.toString())){
//                                programListContainer.setName(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_NAME).getValue().toString());
//                                programListContainer.setCategory(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_CATEGORY).getValue().toString());
//                                programListContainer.setDescription(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_DESCRIPTION).getValue().toString());
//                                programListContainer.setSessionNumber(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_SESSION_NUMBER).getValue().toString());
//                                programListContainer.setDuration(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_DURATION).getValue().toString());
//                                programList.add(programListContainer);
//                            }
                        }
//                        programAdapter = new ProgramAdapter(programList, getContext(), GlobalConstants.KEY_ACCESS_TYPE_VIEW);
//                        programAdapter.notifyDataSetChanged();
//                        ProgramSERecyclerView.setAdapter(programAdapter);
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
        ProgramSERecyclerView = getView().findViewById(R.id.recyclerViewSearchEnginePrograms);
        ProgramSERecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_programs_se, container, false);
    }
}