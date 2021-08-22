package com.pupccis.fitnex.Activities.Main.Trainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.pupccis.fitnex.API.adapter.FragmentAdapter;
import com.pupccis.fitnex.Models.DAO.ProgramDAO;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.Activities.Login.FitnexRegister;
import com.pupccis.fitnex.Activities.VideoConferencing.VideoActivityDemo;
import com.pupccis.fitnex.Utilities.PreferenceManager;
import com.pupccis.fitnex.Utilities.VideoConferencingConstants;

public class TrainerDashboard extends AppCompatActivity implements View.OnClickListener{

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private FragmentAdapter fragmentAdapter;
    private LinearLayout programPanel, addButton;
    private Intent intent;
    private CardView cardViewCalls;
    private PreferenceManager preferenceManager;
    private ProgramDAO programDAO = new ProgramDAO();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_dashboard);

        preferenceManager = new PreferenceManager(getApplicationContext());
        tabLayout = findViewById(R.id.tabLayoutTrainerDashboard);
        viewPager2 = findViewById(R.id.viewPager2TrainerDashboard);
        addButton = (LinearLayout) findViewById(R.id.linearLayoutAddProgramButton);
        programPanel = (LinearLayout) findViewById(R.id.linearLayoutTrainerDashboardNavbar);
        addButton.setOnClickListener(this);
        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fm, getLifecycle());
        viewPager2.setAdapter(fragmentAdapter);
        intent = new Intent(TrainerDashboard.this, AddProgram.class);
        tabLayout.addTab(tabLayout.newTab().setText("Program"));
        tabLayout.addTab(tabLayout.newTab().setText("Schedule"));
        tabLayout.addTab(tabLayout.newTab().setText("Trainees"));
        programDAO.readPrograms(preferenceManager.getString(VideoConferencingConstants.KEY_USER_ID));
        /*

        private DatabaseReference mDatabase =FirebaseDatabase.getInstance().getReference(VideoConferencingConstants.KEY_COLLECTION_PROGRAMS)
        private Program program = new Program();
        private Task<Program> programsList = new Task<Program>;
        Program prog1;
        Program prog2;



        programsList.add(prog1);

        public Task<Program> readAllPrograms(String userID){
            Task<Program> programsList = new Task<Program>;

            mDatabase.child(userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>(){
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    programs.clear();
                    for(DataSnapshot dataSnapshot : task.getResult().getChildren()){
                        programs.setCategory(dataSnapshot.child(ProgramConstants.KEY_PROGRAM_CATEGORY).getValue().toString());
                        //
                        //
                        //
                        //
                        programsList.add(programs);

                    }
                }

                return programsList;
            });
        }



         */


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
                int pos = tab.getPosition();
                if(pos==0){
                      intent = new Intent(TrainerDashboard.this, AddProgram.class);
                }
                else if(pos==1){
                      intent = new Intent(TrainerDashboard.this, FitnexRegister.class);
                }
                else{
                    intent = new Intent(TrainerDashboard.this, AddProgram.class);
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
//                Intent intent = new Intent(TrainerDashboard.this, AddProgram.class);
//                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(TrainerDashboard.this, programPanel, ViewCompat.getTransitionName(programPanel));
//                startActivity(intent, optionsCompat.toBundle());
//                addButton.setVisibility(View.GONE);

                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_bottom,R.anim.stay);
                Toast.makeText(TrainerDashboard.this, "Click working ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cardViewCalls:
                startActivity(new Intent(getApplicationContext(), VideoActivityDemo.class));
                break;

        }
    }
}