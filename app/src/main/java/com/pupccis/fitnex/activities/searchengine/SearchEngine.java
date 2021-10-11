package com.pupccis.fitnex.activities.searchengine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.pupccis.fitnex.activities.patron.TrainerPatronPage;
import com.pupccis.fitnex.api.adapter.fragmentAdapters.SearchEngineAdapter.SearchEngineFragmentAdapter;
import com.pupccis.fitnex.api.adapter.fragmentAdapters.SearchEngineAdapter.TrainerSEAdapter;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.utilities.Constants.PatronConstants;
import com.pupccis.fitnex.utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.utilities.Globals.HideStatusBar;

public class SearchEngine extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private SearchEngineFragmentAdapter fragmentAdapter;
    private ConstraintLayout constraintLayoutSearchEngineProgressBar, constraintLayoutUserNotSubscribedModal;
    private Program program;
    private Button buttonSubscribeToPatron;
    private TextView textViewSubscriptionModalTitle, textViewSubscriptionModalInfo;
    private EditText searchBox;
    private TrainerSEAdapter trainerSEAdapter;
    private HideStatusBar hideStatusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_engine);

        tabLayout = (TabLayout) findViewById(R.id.tabLayoutSearchEngine);
        viewPager2 = (ViewPager2) findViewById(R.id.viewPager2SearchEngine);
        constraintLayoutSearchEngineProgressBar = findViewById(R.id.constraintLayoutSearchEngineProgressBar);
        constraintLayoutUserNotSubscribedModal = findViewById(R.id.constraintLayoutUserNotSubscribedModal);
        buttonSubscribeToPatron = findViewById(R.id.buttonSubscribeToPatron);
        textViewSubscriptionModalTitle = findViewById(R.id.textViewSubscriptionModalTitle);
        textViewSubscriptionModalInfo = findViewById(R.id.textViewSubscriptionModalInfo);
        buttonSubscribeToPatron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSubscriptionModal();
                Intent intent = new Intent(SearchEngine.this, TrainerPatronPage.class);
                intent.putExtra(ProgramConstants.KEY_MODEL_PROGRAM, program);
                startActivity(intent);
            }
        });
        hideStatusBar = new HideStatusBar(getWindow());

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentAdapter = new SearchEngineFragmentAdapter(fragmentManager, getLifecycle(), this.findViewById(android.R.id.content).getRootView());
        viewPager2.setAdapter(fragmentAdapter);

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
    }

    public void showProgressBar(){
        constraintLayoutSearchEngineProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        constraintLayoutSearchEngineProgressBar.setVisibility(View.GONE);
    }

    public void showSubscriptionModal(Program program){
        this.program = program;
        textViewSubscriptionModalTitle.setText("User Not Subscribed");
        textViewSubscriptionModalInfo.setText("The program that you are trying to view is exclusive for patron subscribers");
        buttonSubscribeToPatron.setText("Subscribe to View");
        constraintLayoutUserNotSubscribedModal.setVisibility(View.VISIBLE);
    }

    public void showInvalidSubscriptionModal(Program program){
        this.program = program;
        textViewSubscriptionModalTitle.setText("Invalid Subscription");
        textViewSubscriptionModalInfo.setText("You current subscription is not valid to view this program");
        buttonSubscribeToPatron.setText("Upgrade Subscription");
        constraintLayoutUserNotSubscribedModal.setVisibility(View.VISIBLE);
    }

    public void hideSubscriptionModal(){
        constraintLayoutUserNotSubscribedModal.setVisibility(View.GONE);
    }
}