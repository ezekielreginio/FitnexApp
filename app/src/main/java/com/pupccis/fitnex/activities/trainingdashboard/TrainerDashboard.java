package com.pupccis.fitnex.activities.trainingdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.pupccis.fitnex.activities.patron.PatronMainActivity;
import com.pupccis.fitnex.activities.trainingdashboard.studio.TrainerStudio;
import com.pupccis.fitnex.api.adapter.fragmentAdapters.TrainerDashboardFragmentAdapter;
import com.pupccis.fitnex.api.adapter.ProgramAdapter;
import com.pupccis.fitnex.model.DAO.ProgramDAO;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.activities.login.FitnexRegister;
import com.pupccis.fitnex.activities.videoconferencing.VideoActivityDemo;
import com.pupccis.fitnex.utilities.Preferences.UserPreferences;

import java.util.List;

public class TrainerDashboard extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout addButton;
    private ConstraintLayout trainerStudioButton, programPanel;
    private CardView cardViewViewPatronPage;

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private TrainerDashboardFragmentAdapter trainerDashboardFragmentAdapter;
    private Intent intent;
    private CardView cardViewCalls;
    private UserPreferences userPreferences;
    private ProgramDAO programDAO = new ProgramDAO();

    private List<Program> programs;
    private ProgramAdapter programAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_dashboard);

        userPreferences = new UserPreferences(getApplicationContext());
        tabLayout = findViewById(R.id.tabLayoutTrainerDashboard);
        viewPager2 = findViewById(R.id.viewPager2TrainerDashboard);

        addButton = (LinearLayout) findViewById(R.id.linearLayoutAddProgramButton);
        trainerStudioButton = (ConstraintLayout) findViewById(R.id.constraintLayoutTrainerStudioButton);
        cardViewViewPatronPage = findViewById(R.id.cardViewViewPatronPage);

        addButton.setOnClickListener(this);
        trainerStudioButton.setOnClickListener(this);
        cardViewViewPatronPage.setOnClickListener(this);

        programPanel = (ConstraintLayout) findViewById(R.id.constraintLayoutTrainerDashboardNavbar);

        FragmentManager fm = getSupportFragmentManager();
        trainerDashboardFragmentAdapter = new TrainerDashboardFragmentAdapter(fm, getLifecycle());
        viewPager2.setAdapter(trainerDashboardFragmentAdapter);
        intent = new Intent(TrainerDashboard.this, AddProgram.class);
        tabLayout.addTab(tabLayout.newTab().setText("Program"));
        tabLayout.addTab(tabLayout.newTab().setText("Classes"));
        tabLayout.addTab(tabLayout.newTab().setText("Schedule"));

        int page_intent = (int) getIntent().getIntExtra("page", -1);
        if(page_intent != -1){
            Log.d("Intent",page_intent+"");
            intent = new Intent(TrainerDashboard.this, AddFitnessClass.class);
            viewPager2.setCurrentItem(page_intent);
            TabLayout.Tab tab = tabLayout.getTabAt(page_intent);
            tab.select();
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
                int pos = tab.getPosition();
                if(pos==0){
                    intent = new Intent(TrainerDashboard.this, AddProgram.class);
                }
                else if(pos==1){
                    intent = new Intent(TrainerDashboard.this, AddFitnessClass.class);
                }
                else if(pos==2){
                    intent = new Intent(TrainerDashboard.this, FitnexRegister.class);
                }
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

        cardViewCalls = (CardView) findViewById(R.id.cardViewCalls);
        cardViewCalls.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.linearLayoutAddProgramButton:
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_bottom,R.anim.stay);
                break;
            case R.id.cardViewCalls:
                startActivity(new Intent(getApplicationContext(), VideoActivityDemo.class));
                break;
            case R.id.constraintLayoutTrainerStudioButton:
                Intent intent = new Intent(getApplicationContext(), TrainerStudio.class);
                intent.putExtra("access_type", "owner");
                startActivity(intent);
                Toast.makeText(TrainerDashboard.this, "Click working ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cardViewViewPatronPage:
                intent = new Intent(getApplicationContext(), PatronMainActivity.class);
                startActivity(intent);
                break;

        }
    }
}