package com.pupccis.fitnex.API.adapter.SearchEngineAdapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.pupccis.fitnex.Activities.SearchEngine.Fragment.ClassesFragment;
import com.pupccis.fitnex.Activities.SearchEngine.Fragment.ProgramsFragment;
import com.pupccis.fitnex.Activities.SearchEngine.Fragment.TrainersFragment;
import com.pupccis.fitnex.Activities.SearchEngine.Fragment.VideosFragment;

public class SearchEngineFragmentAdapter extends FragmentStateAdapter {
    private View view;
    public SearchEngineFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, View view) {
        super(fragmentManager, lifecycle);
        this.view = view;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 1:
                return new VideosFragment(view);
            case 2:
                return new ProgramsFragment(view);
            case 3:
                return new ClassesFragment(view);
        }
        return new TrainersFragment(view);
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
