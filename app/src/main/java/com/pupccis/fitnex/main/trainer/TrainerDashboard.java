package com.pupccis.fitnex.main.trainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.pupccis.fitnex.API.adapter.FragmentAdapter;
import com.pupccis.fitnex.R;

public class TrainerDashboard extends AppCompatActivity implements View.OnClickListener{

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    FragmentAdapter fragmentAdapter;
    LinearLayout addButton, programPanel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_dashboard);

        tabLayout = findViewById(R.id.tabLayoutTrainerDashboard);
        viewPager2 = findViewById(R.id.viewPager2TrainerDashboard);
        addButton = (LinearLayout) findViewById(R.id.trainerDashboardAddProgramButton);
        programPanel = (LinearLayout) findViewById(R.id.linearLayoutTrainerDashboardNavbar);
        programPanel.setOnClickListener(this);
        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fm, getLifecycle());
        viewPager2.setAdapter(fragmentAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Program"));
        tabLayout.addTab(tabLayout.newTab().setText("Schedule"));
        tabLayout.addTab(tabLayout.newTab().setText("Trainees"));

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


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.linearLayoutTrainerDashboardNavbar:

        }
    }
}