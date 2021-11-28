package com.pupccis.fitnex.activities.main.trainee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.ActivityTopupCoinsBinding;
import com.pupccis.fitnex.model.TopUpRequest;
import com.pupccis.fitnex.utilities.Constants.UserConstants;
import com.pupccis.fitnex.utilities.Preferences.UserPreferences;
import com.pupccis.fitnex.viewmodel.TopUpViewModel;

public class TopupCoins extends AppCompatActivity implements View.OnClickListener{
    private static ActivityTopupCoinsBinding binding;
    private UserPreferences userPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_topup_coins);
        binding.setViewModel(new TopUpViewModel());
        binding.setLifecycleOwner(this);
        binding.setPresenter(this);
        binding.executePendingBindings();
        userPreferences = new UserPreferences(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
    if(view == binding.buttonTopUpButton ){
        binding.constraintLayoutTopUpProgressBar.setVisibility(View.VISIBLE);
        MutableLiveData<TopUpRequest> topUpRequestMutableLiveData = binding.getViewModel().insertTopUpRequest(userPreferences.getString(UserConstants.KEY_USER_NAME));
        topUpRequestMutableLiveData.observe(binding.getLifecycleOwner(), new Observer<TopUpRequest>() {
            @Override
            public void onChanged(TopUpRequest topUpRequest) {
                if(topUpRequest != null){
                    topUpRequestMutableLiveData.removeObserver(this::onChanged);
                }
            }
        });
        finish();
    }
    }
}