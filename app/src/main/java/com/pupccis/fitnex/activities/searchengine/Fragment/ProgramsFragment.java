package com.pupccis.fitnex.activities.searchengine.Fragment;

import static com.pupccis.fitnex.handlers.viewmodel.ViewModelHandler.getFirebaseUIFitnessClassOptions;
import static com.pupccis.fitnex.handlers.viewmodel.ViewModelHandler.getFirebaseUISearchProgramOptions;
import static com.pupccis.fitnex.utilities.Constants.PatronConstants.getPrivilegeHashMap;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pupccis.fitnex.activities.patron.TrainerPatronPage;
import com.pupccis.fitnex.activities.routine.RoutinePage;
import com.pupccis.fitnex.activities.searchengine.SearchEngine;
import com.pupccis.fitnex.adapters.ProgramAdapter;
import com.pupccis.fitnex.api.enums.AccessType;
import com.pupccis.fitnex.api.enums.Privilege;
import com.pupccis.fitnex.databinding.FragmentProgramsSeBinding;
import com.pupccis.fitnex.handlers.view.WrapContentLinearLayoutManager;
import com.pupccis.fitnex.model.FitnessClass;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.viewmodel.PatronViewModel;
import com.pupccis.fitnex.viewmodel.ProgramViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class ProgramsFragment extends Fragment {

    private EditText searchBox;
    private ProgramAdapter adapter;
    private RecyclerView recyclerView;
    private View view;
    private static FragmentProgramsSeBinding binding;
    private ProgramViewModel programViewModel = new ProgramViewModel();

    public ProgramsFragment(View view) {
        this.view = view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        binding.getViewModel().checkContainerClicked().observe(binding.getLifecycleOwner(), new Observer<Program>() {
            @Override
            public void onChanged(Program program) {
                if(program != null){
                    ((SearchEngine)getActivity()).showProgressBar();
                    Privilege privilege = Privilege.valueOf(program.getPrivilege());
                    if(privilege == Privilege.FREE){

                    }
                    else{
                        new PatronViewModel().checkPatronStatus(program.getTrainerID()).observe(binding.getLifecycleOwner(), new Observer<Privilege>() {
                            @Override
                            public void onChanged(Privilege privilege) {
                                if(privilege != null){
                                    ((SearchEngine)getActivity()).hideProgressBar();
                                    if(privilege == Privilege.NONE){
                                        ((SearchEngine)getActivity()).showSubscriptionModal(program);
                                    }
                                    else{
                                        HashMap<Privilege, Integer> privilegeMap = getPrivilegeHashMap();
                                        int currentSubscription = privilegeMap.get(privilege);
                                        int programPrivilege = privilegeMap.get(Privilege.valueOf(program.getPrivilege()));
                                        if(currentSubscription >= programPrivilege){
                                            Intent intent = new Intent(getActivity(), RoutinePage.class);
                                            intent.putExtra("program", program);
                                            startActivity(intent);
                                        }
                                        else
                                            ((SearchEngine)getActivity()).showInvalidSubscriptionModal(program);
                                    }
                                }
                            }
                        });
                    }

                    //Toast.makeText(getContext(), "Container Clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_programs_se, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(programViewModel);
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
                FirestoreRecyclerOptions<Program> options = getFirebaseUISearchProgramOptions(editable.toString());
                adapter = new ProgramAdapter(options);
                adapter.setAccessType(AccessType.VIEW);
                adapter.setProgramViewModel(programViewModel);
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