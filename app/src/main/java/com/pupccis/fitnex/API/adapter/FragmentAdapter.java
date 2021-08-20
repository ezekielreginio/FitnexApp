package com.pupccis.fitnex.API.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.pupccis.fitnex.main.trainer.fragment.ProgramsFragment;
import com.pupccis.fitnex.main.trainer.fragment.ScheduleFragment;
import com.pupccis.fitnex.main.trainer.fragment.TraineesFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new ScheduleFragment();
            case 2:
                return new TraineesFragment();
        }
        return new ProgramsFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}