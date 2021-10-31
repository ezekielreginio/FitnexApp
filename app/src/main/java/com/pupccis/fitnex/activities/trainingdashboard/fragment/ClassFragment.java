package com.pupccis.fitnex.activities.trainingdashboard.fragment;

import static com.pupccis.fitnex.handlers.viewmodel.ViewModelHandler.getFirebaseUIFitnessClassOptions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pupccis.fitnex.activities.trainingdashboard.AddFitnessClass;
import com.pupccis.fitnex.adapters.FitnessClassAdapter;
import com.pupccis.fitnex.api.enums.AccessType;
import com.pupccis.fitnex.databinding.FragmentFitnessClassesBinding;
import com.pupccis.fitnex.handlers.view.WrapContentLinearLayoutManager;
import com.pupccis.fitnex.model.FitnessClass;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.utilities.Preferences.UserPreferences;
import com.pupccis.fitnex.viewmodel.FitnessClassViewModel;

public class ClassFragment extends Fragment implements View.OnClickListener{
    //Fragment Variables
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    //Private Attributes
    private FitnessClassAdapter adapter;
    private UserPreferences userPreferences;
    private RecyclerView recyclerView;
    private FitnessClassViewModel fitnessClassViewModel = new FitnessClassViewModel();
    private static FragmentFitnessClassesBinding fragmentFitnessClassesBinding;
    public ClassFragment() {
        // Required empty public constructor
    }

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
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentFitnessClassesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fitness_classes, container, false);
        fragmentFitnessClassesBinding.setLifecycleOwner(this);
        fragmentFitnessClassesBinding.setViewModel(fitnessClassViewModel);
        fragmentFitnessClassesBinding.setPresenter(this);
        fragmentFitnessClassesBinding.executePendingBindings();
        //Get FirestoreOptions From ViewModel
        FirestoreRecyclerOptions<FitnessClass> options = getFirebaseUIFitnessClassOptions();
        //Recycler view
        recyclerView = fragmentFitnessClassesBinding.fitnessClassesRecyclerView;
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        adapter = new FitnessClassAdapter(options);
        adapter.setAccesstype(AccessType.OWNER);
        adapter.setFitnessClassViewModel(fitnessClassViewModel);
        recyclerView.setAdapter(adapter);



        fragmentFitnessClassesBinding.getViewModel().updateObserver().observe(fragmentFitnessClassesBinding.getLifecycleOwner(), new Observer<FitnessClass>() {
            @Override
            public void onChanged(FitnessClass fitnessClass) {
                Intent intent= new Intent();
                intent.putExtra("fitnessClass", fitnessClass);
                Log.e("Fitness Intent in fragment", fitnessClass.getClassID());
                fragmentFitnessClassesBinding.getViewModel().setIntent((FitnessClass) intent.getSerializableExtra("fitnessClass"));
                Navigation.findNavController(getView()).navigate(R.id.action_classFragment_to_addFitnessClass);
            }
        });
        fragmentFitnessClassesBinding.getViewModel().deleteObserver().observe(fragmentFitnessClassesBinding.getLifecycleOwner(), new Observer<FitnessClass>() {
            @Override
            public void onChanged(FitnessClass fitnessClass) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                fragmentFitnessClassesBinding.getViewModel().deleteFitnessClass(fitnessClass.getClassID());
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
        View view = fragmentFitnessClassesBinding.getRoot();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onClick(View view) {
        if(view == fragmentFitnessClassesBinding.imageViewAddFitnessClass){
            fragmentFitnessClassesBinding.getViewModel().clearIntent();
            Navigation.findNavController(getView()).navigate(R.id.action_classFragment_to_addFitnessClass);
        }
    }
    public static FitnessClassViewModel getFitnessClassViewModel(){
        return fragmentFitnessClassesBinding.getViewModel();
    }
}