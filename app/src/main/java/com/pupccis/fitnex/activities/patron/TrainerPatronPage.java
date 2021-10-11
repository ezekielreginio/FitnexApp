package com.pupccis.fitnex.activities.patron;

import static com.pupccis.fitnex.utilities.Constants.PatronConstants.getPrivilegeHashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.pupccis.fitnex.R;
import com.pupccis.fitnex.activities.routine.RoutinePage;
import com.pupccis.fitnex.activities.searchengine.SearchEngine;
import com.pupccis.fitnex.activities.trainingdashboard.TrainerDashboard;
import com.pupccis.fitnex.api.BulletListUtil;
import com.pupccis.fitnex.api.enums.Privilege;
import com.pupccis.fitnex.databinding.ActivityTrainerPatronPageBinding;
import com.pupccis.fitnex.model.Program;
import com.pupccis.fitnex.utilities.Constants.PatronConstants;
import com.pupccis.fitnex.utilities.Constants.ProgramConstants;
import com.pupccis.fitnex.viewmodel.PatronViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrainerPatronPage extends AppCompatActivity implements View.OnClickListener{
    private ActivityTrainerPatronPageBinding binding;
    private PatronViewModel patronViewModel = new PatronViewModel();
    private String userID;
    private Program program;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trainer_patron_page);
        binding.setViewModel(patronViewModel);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        //Intents
        program = (Program) getIntent().getSerializableExtra(ProgramConstants.KEY_MODEL_PROGRAM);
        if(program != null)
            userID = program.getTrainerID();
        else
            userID = FirebaseAuth.getInstance().getUid();

        //Observers
        binding.getViewModel().getPrivilegeListLiveData().observe(binding.getLifecycleOwner(), new Observer<HashMap<Privilege, List<String>>>() {
            @Override
            public void onChanged(HashMap<Privilege, List<String>> privilegeListHashMap) {
                Log.d("Observer", "triggered");
                updatePrivilegeList();
            }
        });

        binding.getViewModel().getPatronData(userID).observe(this, new Observer<HashMap<String, Object>>() {
            @Override
            public void onChanged(HashMap<String, Object> dataMap) {
                if(dataMap!=null){
                    Log.d("Hashmap Data", dataMap.toString());
                    HashMap<Privilege, Object> hashMapData = (HashMap<Privilege, Object>) dataMap.get(PatronConstants.KEY_COLLECTION_PRIVILEGE);
                    if(hashMapData != null)
                        if(hashMapData.size() == 3)
                            patronViewModel.setPatronData(dataMap);
                }
            }
        });




    }

    private void updatePrivilegeList() {
        binding.textViewBronzePrivilages.setText(BulletListUtil.makeBulletList(binding.getViewModel().getPrivilegeList().get(Privilege.BRONZE)));
        binding.textViewSilverPrivilages.setText(BulletListUtil.makeBulletList(binding.getViewModel().getPrivilegeList().get(Privilege.SILVER)));
        binding.textViewGoldPrivilages.setText(BulletListUtil.makeBulletList(binding.getViewModel().getPrivilegeList().get(Privilege.GOLD)));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(program == null)
            startActivity(new Intent(TrainerPatronPage.this, TrainerDashboard.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view == binding.cardViewBronzePrivilege){
            binding.getViewModel().setCurrentPrivilege(Privilege.BRONZE);
            setCardSelected(binding.cardViewBronzePrivilege, Privilege.BRONZE);
        }
        else if(view == binding.cardViewSilverPrivilege){
            binding.getViewModel().setCurrentPrivilege(Privilege.SILVER);
            setCardSelected(binding.cardViewSilverPrivilege, Privilege.SILVER);
        }
        else if(view == binding.cardViewGoldPrivilege){
            binding.getViewModel().setCurrentPrivilege(Privilege.GOLD);
            setCardSelected(binding.cardViewGoldPrivilege, Privilege.GOLD);
        }

        else if(view == binding.buttonSubscribeToPatron){
            binding.getViewModel().subscribe(userID).observe(binding.getLifecycleOwner(), new Observer<Privilege>() {
                @Override
                public void onChanged(Privilege privilege) {
                    int subscribedPrivilege = getPrivilegeHashMap().get(privilege);
                    int programPrivilege = getPrivilegeHashMap().get(Privilege.valueOf(program.getPrivilege()));
                    if(subscribedPrivilege >= programPrivilege){
                        Intent intent = new Intent(TrainerPatronPage.this, RoutinePage.class);
                        intent.putExtra("program", program);
                        startActivity(intent);
                    }
                    else
                        Log.d("Subscription","Invalid");
                }
            });
        }
    }

    public void setCardSelected(MaterialCardView cardSelected, Privilege privilege){
        resetCardsSelected();
        cardSelected.setStrokeColor(getResources().getColor(R.color.blueTextColor));
        cardSelected.setStrokeWidth(13);
        cardSelected.setCardForegroundColor(ColorStateList.valueOf(getResources().getColor(R.color.selectedTintColor)));
        setButton(privilege);
    }

    private void setButton(Privilege privilege) {
        binding.buttonSubscribeToPatron.setVisibility(View.VISIBLE);
        binding.buttonSubscribeToPatron.setText("Subscribe to "+privilege.toString());
//        switch (privilege){
//            case BRONZE:
//                binding.buttonSubscribeToPatron.setBackgroundColor(getResources().getColor(R.color.bronzeColor));
//                break;
//            case SILVER:
//                binding.buttonSubscribeToPatron.setBackgroundColor(getResources().getColor(R.color.silverColor));
//                break;
//            case GOLD:
//                binding.buttonSubscribeToPatron.setBackgroundColor(getResources().getColor(R.color.goldColor));
//                break;
//        }
    }

    public void resetCardsSelected(){
        List<MaterialCardView> cards = new ArrayList<>();
        cards.add(binding.cardViewBronzePrivilege);
        cards.add(binding.cardViewSilverPrivilege);
        cards.add(binding.cardViewGoldPrivilege);

        for(MaterialCardView card : cards){
            card.setStrokeColor(null);
            card.setStrokeWidth(0);
            card.setCardForegroundColor(null);
        }
    }
}