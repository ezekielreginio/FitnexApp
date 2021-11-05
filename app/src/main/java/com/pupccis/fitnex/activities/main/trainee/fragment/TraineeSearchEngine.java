package com.pupccis.fitnex.activities.main.trainee.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.api.adapter.fragmentAdapters.SearchEngineAdapter.SearchEngineFragmentAdapter;
import com.pupccis.fitnex.databinding.FragmentTraineeSearchEngineBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TraineeSearchEngine#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TraineeSearchEngine extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static FragmentTraineeSearchEngineBinding binding;
    public TraineeSearchEngine() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TraineeSearchEngine newInstance(String param1, String param2) {
        TraineeSearchEngine fragment = new TraineeSearchEngine();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trainee_search_engine, container, false);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        SearchEngineFragmentAdapter fragmentAdapter = new SearchEngineFragmentAdapter(fragmentManager, getLifecycle(), getActivity().findViewById(android.R.id.content).getRootView());
        binding.viewPager2TraineeSearchEngine.setAdapter(fragmentAdapter);

        TabLayout tabLayout = binding.tabLayoutTraineeSearchEngine;
        ViewPager2 viewPager2 = binding.viewPager2TraineeSearchEngine;
        tabLayout.addTab(tabLayout.newTab().setText("Trainers"));
        tabLayout.addTab(tabLayout.newTab().setText("Videos"));
        tabLayout.addTab(tabLayout.newTab().setText("Programs"));
        tabLayout.addTab(tabLayout.newTab().setText("Classes"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}