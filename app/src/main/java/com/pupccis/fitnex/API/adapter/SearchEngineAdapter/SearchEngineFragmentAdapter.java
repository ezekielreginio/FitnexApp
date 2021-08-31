package com.pupccis.fitnex.API.adapter.SearchEngineAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.pupccis.fitnex.Activities.SearchEngine.Fragment.ProgramsFragment;
import com.pupccis.fitnex.Activities.SearchEngine.Fragment.TrainersFragment;
import com.pupccis.fitnex.Activities.SearchEngine.Fragment.VideosFragment;

public class SearchEngineFragmentAdapter extends FragmentStateAdapter {
    public SearchEngineFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 1:
                return new VideosFragment();
            case 2:
                return new ProgramsFragment();
        }
        return new TrainersFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
