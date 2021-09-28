package com.pupccis.fitnex.activities.main.trainer.fragment;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.pupccis.fitnex.activities.main.trainer.AddFitnessClass;
import com.pupccis.fitnex.activities.main.trainer.AddProgram;
import com.pupccis.fitnex.adapters.FitnessClassAdapter;
import com.pupccis.fitnex.databinding.FragmentFitnessClassesBinding;
import com.pupccis.fitnex.handlers.view.WrapContentLinearLayoutManager;
import com.pupccis.fitnex.model.DAO.FitnessClassDAO;
import com.pupccis.fitnex.model.FitnessClass;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.repository.FitnessClassesRepository;
import com.pupccis.fitnex.utilities.Preferences.UserPreferences;
import com.pupccis.fitnex.viewmodel.FitnessClassViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FitnessClassAdapter adapter;
    private UserPreferences userPreferences;
    private FitnessClassDAO fitnessClassDAO = new FitnessClassDAO();

    private RecyclerView recyclerView;

    private List<FitnessClass> fitnessClasses = new ArrayList<>();
    private DatabaseReference mDatabase;
    private FitnessClassViewModel fitnessClassViewModel;
    private static FragmentFitnessClassesBinding binding;
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
//        userPreferences = new UserPreferences(getActivity().getApplicationContext());
//
//        Query query = FirebaseDatabase.getInstance().getReference(FitnessClassConstants.KEY_COLLECTION_FITNESS_CLASSES).orderByChild(FitnessClassConstants.KEY_FITNESS_CLASSES_TRAINER_ID).equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//        query.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                fitnessClasses.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    FitnessClass fitnessClass = new FitnessClass.Builder(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_NAME).getValue().toString()
//                            ,dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_DESCRIPTION).getValue().toString()
//                            ,(int)dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_CATEGORY).getValue()
//                            ,dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_START).getValue().toString()
//                            ,dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_END).getValue().toString()
//                            ,dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_SESSION_NUMBER).getValue().toString()
//                            ,dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_DURATION).getValue().toString()
//                            ,userPreferences.getString(VideoConferencingConstants.KEY_USER_ID)).build();
////                    fitnessClass.setClassName(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_NAME).getValue().toString());
////                    fitnessClass.setTimeStart(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_START).getValue().toString());
////                    fitnessClass.setTimeEnd(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_TIME_END).getValue().toString());
////                    fitnessClass.setSessionNo(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_SESSION_NUMBER).getValue().toString());
////                    fitnessClass.setDescription(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_DESCRIPTION).getValue().toString());
////                    fitnessClass.setDuration(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_DURATION).getValue().toString());
////                    fitnessClass.setCategory(Integer.parseInt(dataSnapshot.child(FitnessClassConstants.KEY_FITNESS_CLASSES_CATEGORY).getValue().toString()));
////                    fitnessClass.setClassID(dataSnapshot.getKey());
////                    fitnessClass.setClassTrainerID(userPreferences.getString(VideoConferencingConstants.KEY_USER_ID));
//                    fitnessClasses.add(fitnessClass);
//
//
//                }
//
//                fitnessClassAdapter = new FitnessClassAdapter(fitnessClasses, getContext(), GlobalConstants.KEY_ACCESS_TYPE_OWNER);
//                fitnessClassAdapter.notifyDataSetChanged();
//                fitnessClassRecyclerView.setAdapter(fitnessClassAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        //Recyclerview initialization
//        recyclerView = (RecyclerView) getView().findViewById(R.id.fitnessClassesRecyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        //ViewModel Instantiation
//        fitnessClassViewModel = new ViewModelProvider(this).get(FitnessClassViewModel.class);
//        fitnessClassViewModel.init(getActivity().getApplicationContext());

        //RecyclerView Adapter
//        fitnessClassAdapter = new FitnessClassAdapter(fitnessClassViewModel.getFitnessClasses().getValue(), getContext(),GlobalConstants.KEY_ACCESS_TYPE_OWNER);
//        fitnessClassRecyclerView.setAdapter(fitnessClassAdapter);

//        fitnessClassViewModel.getLiveDataFitnessClassesUpdate().observe(getActivity(), new Observer<HashMap<String, Object>>() {
//            @Override
//            public void onChanged(HashMap<String, Object> stringObjectHashMap) {
//                if(stringObjectHashMap.get(GlobalConstants.KEY_UPDATE_TYPE).equals(GlobalConstants.KEY_UPDATE_TYPE_LOADED))
//                    fitnessClassAdapter.notifyDataSetChanged();
//                else if(stringObjectHashMap.get(GlobalConstants.KEY_UPDATE_TYPE).equals(GlobalConstants.KEY_UPDATE_TYPE_INSERT))
//                    fitnessClassAdapter.notifyItemInserted((int) stringObjectHashMap.get("index"));
//                else if(stringObjectHashMap.get(GlobalConstants.KEY_UPDATE_TYPE).equals(GlobalConstants.KEY_UPDATE_TYPE_UPDATE))
//                    fitnessClassAdapter.notifyItemChanged((int) stringObjectHashMap.get("index"));
//                else if(stringObjectHashMap.get(GlobalConstants.KEY_UPDATE_TYPE).equals(GlobalConstants.KEY_UPDATE_TYPE_DELETE)){
//                    Log.d("Removed", "removed");
//                    fitnessClassAdapter.notifyItemRemoved((int) stringObjectHashMap.get("index"));
//                }
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fitness_classes, container, false);

        //Get FirestoreOptions From ViewModel
        FirestoreRecyclerOptions<FitnessClass> options = getFirebaseUIFitnessClassOptions();

        //Recycler view
        recyclerView = binding.fitnessClassesRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        recyclerView.setItemAnimator(null);

        adapter = new FitnessClassAdapter(options);
        recyclerView.setAdapter(adapter);

        binding.setLifecycleOwner(this);
        binding.setViewModel(adapter.getViewModel());

        binding.getViewModel().updateObserver().observe(binding.getLifecycleOwner(), new Observer<FitnessClass>() {
            @Override
            public void onChanged(FitnessClass fitnessClass) {

                Intent intent= new Intent(getContext(), AddFitnessClass.class);
                intent.putExtra("fitnessClass", fitnessClass);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_bottom,R.anim.stay);
            }
        });
        binding.getViewModel().deleteObserver().observe(binding.getLifecycleOwner(), new Observer<FitnessClass>() {
            @Override
            public void onChanged(FitnessClass fitnessClass) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Log.d("Delete", "Clicked");
                                binding.getViewModel().deleteFitnessClass(fitnessClass.getClassID());
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

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
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
}