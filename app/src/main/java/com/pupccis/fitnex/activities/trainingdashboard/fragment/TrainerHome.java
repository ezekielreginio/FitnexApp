package com.pupccis.fitnex.activities.trainingdashboard.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.FragmentTrainerHomeBinding;
import com.pupccis.fitnex.utilities.Constants.UserConstants;
import com.pupccis.fitnex.utilities.Preferences.UserPreferences;

public class TrainerHome extends Fragment {

    private FragmentTrainerHomeBinding binding;
    private UserPreferences userPreferences;

    public TrainerHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trainer_home, container, false);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        userPreferences = new UserPreferences(getActivity().getApplicationContext());

        binding.textViewUserNameHeader.setText(userPreferences.getString(UserConstants.KEY_USER_NAME));


        return binding.getRoot();
    }
}