package com.pupccis.fitnex.Activities.SearchEngine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.android.material.tabs.TabLayout;
import com.pupccis.fitnex.API.adapter.SearchEngineAdapter.SearchEngineFragmentAdapter;
import com.pupccis.fitnex.API.adapter.SearchEngineAdapter.TrainerSEAdapter;
import com.pupccis.fitnex.R;

public class SearchEngine extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private SearchEngineFragmentAdapter fragmentAdapter;
    private EditText searchBox;
    private TrainerSEAdapter trainerSEAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_engine);

        tabLayout = (TabLayout) findViewById(R.id.tabLayoutSearchEngine);
        viewPager2 = (ViewPager2) findViewById(R.id.viewPager2SearchEngine);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentAdapter = new SearchEngineFragmentAdapter(fragmentManager, getLifecycle(), this.findViewById(android.R.id.content).getRootView());
        viewPager2.setAdapter(fragmentAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Trainers"));
        tabLayout.addTab(tabLayout.newTab().setText("Videos"));
        tabLayout.addTab(tabLayout.newTab().setText("Programs"));

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



    }
}