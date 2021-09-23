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
import com.pupccis.fitnex.API.adapter.TrainerStudioVideosAdapter;
import com.pupccis.fitnex.model.PostVideo;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Utilities.Constants.GlobalConstants;
import com.pupccis.fitnex.Utilities.Constants.PostVideoConstants;
import com.pupccis.fitnex.Utilities.Preferences.UserPreferences;

import java.util.ArrayList;


public class VideosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText searchBox;
    private UserPreferences userPreferences;
    private TrainerStudioVideosAdapter trainerStudioVideosAdapter;
    private RecyclerView VideoSERecyclerView;
    private DatabaseReference mDatabase;
    private View view;
    private ArrayList<PostVideo> videoList = new ArrayList<>();

    public VideosFragment(View view) {
        // Required empty public constructor
        this.view = view;
    }


    // TODO: Rename and change types and number of parameters
//    public static VideosFragment newInstance(String param1, String param2) {
//        VideosFragment fragment = new VideosFragment();
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

        mDatabase = FirebaseDatabase.getInstance().getReference(PostVideoConstants.KEY_COLLECTION_POST_VIDEO);

        searchBox = (EditText) view.findViewById(R.id.searchEngineSearchBox);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        videoList.clear();
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){


                            if(dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_TITLE).getValue().toString().toLowerCase().contains(charSequence.toString())){

                                PostVideo postVideo = new PostVideo.PostVideoBuilder(
                                        dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_TITLE).getValue().toString(),
                                        dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_CATEGORY).getValue().toString(),
                                        dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_DESCRIPTION).getValue().toString(),
                                        dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_TRAINER_ID).getValue().toString(),
                                        (long) dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_DATE_POSTED).getValue()
                                )
                                        .videoURL(dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_URL).getValue().toString())
                                        .thumbnailURL(dataSnapshot.child(PostVideoConstants.KEY_POST_VIDEO_THUMBNAIL_URL).getValue().toString())
                                        .postVideoID(dataSnapshot.getKey())
                                        .build();
                                videoList.add(postVideo);
                            }

                        }

                        trainerStudioVideosAdapter = new TrainerStudioVideosAdapter(videoList, getContext(), GlobalConstants.KEY_ACCESS_TYPE_VIEW);
                        trainerStudioVideosAdapter.notifyDataSetChanged();
                        VideoSERecyclerView.setAdapter(trainerStudioVideosAdapter);
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
        VideoSERecyclerView = getView().findViewById(R.id.recyclerViewSearchEngineVideos);
        VideoSERecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_videos_se, container, false);
    }
}