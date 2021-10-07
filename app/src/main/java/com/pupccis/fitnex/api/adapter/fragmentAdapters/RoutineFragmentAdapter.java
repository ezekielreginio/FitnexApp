package com.pupccis.fitnex.api.adapter.fragmentAdapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.pupccis.fitnex.activities.routine.RoutineFragment;
import com.pupccis.fitnex.model.Routine;

import java.util.List;

public class RoutineFragmentAdapter extends FragmentStateAdapter {
    private List<Routine> routineList;

    public RoutineFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public void setRoutineList(List<Routine> routineList) {
        this.routineList = routineList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d("CreateFragment Triggered", position+"");
        return new RoutineFragment(routineList.get(position));
    }

    @Override
    public int getItemCount() {
        return routineList.size();
    }
}
