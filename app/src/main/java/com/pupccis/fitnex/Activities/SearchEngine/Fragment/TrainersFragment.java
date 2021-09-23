package com.pupccis.fitnex.Activities.SearchEngine.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pupccis.fitnex.API.adapter.SearchEngineAdapter.TrainerSEAdapter;
import com.pupccis.fitnex.model.User;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.Preferences.UserPreferences;
import com.pupccis.fitnex.Utilities.VideoConferencingConstants;

import java.util.ArrayList;
import java.util.List;


public class TrainersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText searchBox;
    private UserPreferences userPreferences;
    private TrainerSEAdapter trainerSEAdapter;
    private RecyclerView TrainerSERecyclerView;
    private DatabaseReference mDatabase;
    private View view;
    private List<User> userList = new ArrayList<>();

    public TrainersFragment(View view) {
        // Required empty public constructor
        this.view = view;
    }


    // TODO: Rename and change types and number of parameters
//    public static TrainersFragment newInstance(String param1, String param2) {
//        TrainersFragment fragment = new TrainersFragment(context);
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
        userPreferences = new UserPreferences(getActivity().getApplicationContext());

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        //search

        searchBox = (EditText) view.findViewById(R.id.searchEngineSearchBox);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDatabase.orderByChild("userType").equalTo("trainer").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        userList.clear();
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            //User userListContainer = new User();

                            if(dataSnapshot.child(VideoConferencingConstants.KEY_FULLNAME).getValue().toString().toLowerCase().contains(charSequence.toString())){
                                //userListContainer.setName(dataSnapshot.child(VideoConferencingConstants.KEY_FULLNAME).getValue().toString());
                                //userListContainer.setEmail(dataSnapshot.child(VideoConferencingConstants.KEY_EMAIL).getValue().toString());
                                //userListContainer.setUserID(dataSnapshot.getKey());
                                //userList.add(userListContainer);
                            }

                        }
                        trainerSEAdapter = new TrainerSEAdapter(userList, getContext());
                        trainerSEAdapter.notifyDataSetChanged();
                        Log.d("Adapter", trainerSEAdapter+"");
                        TrainerSERecyclerView.setAdapter(trainerSEAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                Log.d("Text change", charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });








    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        TrainerSERecyclerView = getView().findViewById(R.id.recyclerViewSearchEngineTrainers);
        TrainerSERecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));




    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trainers_se, container, false);
    }


}