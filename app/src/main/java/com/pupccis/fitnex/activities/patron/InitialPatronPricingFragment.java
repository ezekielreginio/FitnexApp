package com.pupccis.fitnex.activities.patron;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.FragmentInitialPatronPricingBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InitialPatronPricingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InitialPatronPricingFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentInitialPatronPricingBinding binding;


    public InitialPatronPricingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InitialPatronFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InitialPatronPricingFragment newInstance(String param1, String param2) {
        InitialPatronPricingFragment fragment = new InitialPatronPricingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_initial_patron_pricing, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(((PatronMainActivity) getActivity()).getPatronViewModel());
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.imageViewPrevStep.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        NavController navController = Navigation.findNavController(view);
        if(view == binding.imageViewPrevStep){
            navController.navigate(R.id.action_initialPatronPricingFragment_to_initialPatronPrivelegesFragment);
        }
    }
}