package com.pupccis.fitnex.activities.healthassessment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.FragmentPrivacyTermsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrivacyTerms#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrivacyTerms extends Fragment implements View.OnClickListener{
    private static FragmentPrivacyTermsBinding binding;
    private boolean activated = false;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PrivacyTerms() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrivacyTerms.
     */
    // TODO: Rename and change types and number of parameters
    public static PrivacyTerms newInstance(String param1, String param2) {
        PrivacyTerms fragment = new PrivacyTerms();
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_privacy_terms, container, false);
        binding.setViewModel(((HealthAssessment)getActivity()).getViewModel());
        binding.setPresenter(this);
        binding.executePendingBindings();
        //binding.checkBoxAgree.setOnClickListener(null);
        //binding.textViewPrivacyTerms.setMovementMethod(new ScrollingMovementMethod());
        binding.scrolLViewPrivacyTerms.isSmoothScrollingEnabled();
        binding.scrolLViewPrivacyTerms.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if(binding.scrolLViewPrivacyTerms.getChildAt(0).getBottom()-100 <= (binding.scrolLViewPrivacyTerms.getHeight() + binding.scrolLViewPrivacyTerms.getScrollY())){
//                    binding.checkBoxAgree.setOnClickListener(PrivacyTerms.this);
                    setActivate();
                    Log.e("Activate Scroll", "Activated");

                }
            }

            private void setActivate() {
                Log.e("Triggered activate", "aouighfuigh");
                activated = true;
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if(view == binding.checkBoxAgree) {
            if (!activated) {
                    binding.checkBoxAgree.setChecked(false);
                    Log.e("Must read", "ASDAFFAFA");
                    Toast.makeText(getActivity(), "You must read the terms!", Toast.LENGTH_SHORT).show();
                }
            else{
                if(!binding.checkBoxAgree.isChecked()){
                    binding.checkBoxAgree.setChecked(false);
                    binding.checkBoxAgree.setButtonDrawable(R.drawable.checkbox_unchecked);
                }
                else if(binding.checkBoxAgree.isChecked()){
                    binding.checkBoxAgree.setChecked(true);
                    binding.checkBoxAgree.setButtonDrawable(R.drawable.checkbox_checked);
                }
            }
        }
        if(view == binding.imageViewPrivacyNext){
            if(binding.checkBoxAgree.isChecked()){
                Navigation.findNavController(view).navigate(R.id.action_privacyTerms_to_step1);
            }
            else if(!binding.checkBoxAgree.isChecked()){
                Toast.makeText(getActivity(), "You must consent to the privacy terms before proceeding!", Toast.LENGTH_SHORT).show();
            }
        }


    }
}