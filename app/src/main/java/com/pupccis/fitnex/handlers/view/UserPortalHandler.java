package com.pupccis.fitnex.handlers.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.pupccis.fitnex.activities.healthassessment.HealthAssessment;
import com.pupccis.fitnex.activities.main.trainee.TraineeDashboard;
import com.pupccis.fitnex.viewmodel.UserViewModel;

public class UserPortalHandler {
    public static void checkHealthData(LifecycleOwner lifecycleOwner,Context context){
        UserViewModel userViewModel = new UserViewModel();

        userViewModel.checkHealthData().observe(lifecycleOwner, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSet) {
                if(isSet)
                    context.startActivity(new Intent(context, TraineeDashboard.class));
                else
                    context.startActivity(new Intent(context, HealthAssessment.class));
                ((Activity) context).finish();
            }
        });
    }
}
