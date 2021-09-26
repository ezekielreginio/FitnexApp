package com.pupccis.fitnex.api.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.pupccis.fitnex.activities.main.trainer.fragment.ProgramsFragment;
import com.pupccis.fitnex.activities.main.trainer.fragment.ScheduleFragment;
import com.pupccis.fitnex.activities.main.trainer.fragment.ClassFragment;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new ClassFragment();
            case 2:
                return new ScheduleFragment();
        }
        return new ProgramsFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }


}


