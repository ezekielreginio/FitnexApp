package com.pupccis.fitnex.activities.patron;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.activities.trainingdashboard.TrainerDashboard;
import com.pupccis.fitnex.api.BulletListUtil;
import com.pupccis.fitnex.api.enums.Privilege;
import com.pupccis.fitnex.databinding.ActivityTrainerPatronPageBinding;
import com.pupccis.fitnex.utilities.Constants.PatronConstants;
import com.pupccis.fitnex.viewmodel.PatronViewModel;

import java.util.HashMap;
import java.util.List;

public class TrainerPatronPage extends AppCompatActivity {
    private ActivityTrainerPatronPageBinding binding;
    private PatronViewModel patronViewModel = new PatronViewModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trainer_patron_page);
        binding.setViewModel(patronViewModel);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();
        //setContentView(R.layout.activity_trainer_patron_page);

        //Observers
        binding.getViewModel().getPrivilegeListLiveData().observe(binding.getLifecycleOwner(), new Observer<HashMap<Privilege, List<String>>>() {
            @Override
            public void onChanged(HashMap<Privilege, List<String>> privilegeListHashMap) {
                Log.d("Observer", "triggered");
                updatePrivilegeList();
            }
        });

        binding.getViewModel().getPatronData().observe(this, new Observer<HashMap<String, Object>>() {
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
        startActivity(new Intent(TrainerPatronPage.this, TrainerDashboard.class));
        finish();
    }
}