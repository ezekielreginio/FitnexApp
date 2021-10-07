package com.pupccis.fitnex.activities.searchengine.Fragment;

import static com.pupccis.fitnex.handlers.viewmodel.ViewModelHandler.getFirebaseUISearchFitnessClassOptions;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pupccis.fitnex.adapters.FitnessClassAdapter;
import com.pupccis.fitnex.adapters.ProgramAdapter;
import com.pupccis.fitnex.api.enums.AccessType;
import com.pupccis.fitnex.databinding.FragmentClassesSeBinding;
import com.pupccis.fitnex.handlers.view.WrapContentLinearLayoutManager;
import com.pupccis.fitnex.model.FitnessClass;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.utilities.Constants.FitnessClassConstants;

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
    private FitnessClassAdapter adapter;
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private View view;
    private List<FitnessClass> fitnessClassList = new ArrayList<>();
    private static FragmentClassesSeBinding binding;
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
       // mDatabase = FirebaseDatabase.getInstance().getReference(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_classes_se, container, false);
        binding.setLifecycleOwner(this);

        recyclerView = binding.recyclerViewSearchEngineClasses;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

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
                FirestoreRecyclerOptions<FitnessClass> options = getFirebaseUISearchFitnessClassOptions(editable.toString());
                adapter = new FitnessClassAdapter(options);
                adapter.setAccesstype(AccessType.VIEW);
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