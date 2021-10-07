package com.pupccis.fitnex.activities.searchengine.Fragment;

import static com.pupccis.fitnex.handlers.viewmodel.ViewModelHandler.getFirebaseUIFitnessClassOptions;
import static com.pupccis.fitnex.handlers.viewmodel.ViewModelHandler.getFirebaseUISearchProgramOptions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pupccis.fitnex.adapters.ProgramAdapter;
import com.pupccis.fitnex.api.enums.AccessType;
import com.pupccis.fitnex.databinding.FragmentProgramsSeBinding;
import com.pupccis.fitnex.handlers.view.WrapContentLinearLayoutManager;
import com.pupccis.fitnex.model.FitnessClass;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class ProgramsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText searchBox;
    private ProgramAdapter adapter;
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private View view;
    private List<Program> programList = new ArrayList<>();
    private static FragmentProgramsSeBinding binding;
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
        //mDatabase = FirebaseDatabase.getInstance().getReference("programs");


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_programs_se, container, false);
        binding.setLifecycleOwner(this);

        recyclerView = binding.recyclerViewSearchEnginePrograms;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        // Inflate the layout for this fragment
        searchBox = view.findViewById(R.id.searchEngineSearchBox);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                ArrayList<String> searchInput = new ArrayList<>();
//                searchInput.add(editable.toString().toLowerCase());
                FirestoreRecyclerOptions<Program> options = getFirebaseUISearchProgramOptions(editable.toString());
                adapter = new ProgramAdapter(options);
                adapter.setAccessType(AccessType.VIEW);
                binding.setViewModel(adapter.getViewModel());
                recyclerView.setAdapter(adapter);
                adapter.startListening();
                if(editable.length()==0)
                    adapter.stopListening();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}