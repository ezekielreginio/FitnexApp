package com.pupccis.fitnex.activities.healthassessment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.FragmentStep3Binding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Step3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Step3 extends Fragment implements View.OnClickListener{
    private FragmentStep3Binding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Step3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Step3.
     */
    // TODO: Rename and change types and number of parameters
    public static Step3 newInstance(String param1, String param2) {
        Step3 fragment = new Step3();
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_step3, container, false);
        binding.setViewModel(((HealthAssessment)getActivity()).getViewModel());
        binding.setPresenter(this);
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if(view == binding.imageViewThreeBack)
            Navigation.findNavController(view).navigate(R.id.action_step3_to_step2);
        if(view == binding.checkBoxComorbidityAsthma){
            if(binding.checkBoxComorbidityAsthma.isChecked()){
                binding.checkBoxComorbidityAsthma.setChecked(true);
                binding.checkBoxComorbidityAsthma.setButtonDrawable(R.drawable.checkbox_checked);
                binding.getViewModel().addComorbidity("Asthma");

            }
            else if(!binding.checkBoxComorbidityAsthma.isChecked()){
                binding.checkBoxComorbidityAsthma.setChecked(false);
                binding.checkBoxComorbidityAsthma.setButtonDrawable(R.drawable.checkbox_unchecked);
                binding.getViewModel().removeComorbidity("Asthma");
            }

        }
        if(view == binding.checkBoxComorbidityDiabetes){
            if(binding.checkBoxComorbidityDiabetes.isChecked()){
                binding.checkBoxComorbidityDiabetes.setChecked(true);
                binding.checkBoxComorbidityDiabetes.setButtonDrawable(R.drawable.checkbox_checked);
                binding.getViewModel().addComorbidity("Diabetes");

            }
            else if(!binding.checkBoxComorbidityDiabetes.isChecked()){
                binding.checkBoxComorbidityDiabetes.setChecked(false);
                binding.checkBoxComorbidityDiabetes.setButtonDrawable(R.drawable.checkbox_unchecked);
                binding.getViewModel().removeComorbidity("Diabetes");
            }

        }
        if(view == binding.checkBoxComorbidityHypertension){
            if(binding.checkBoxComorbidityHypertension.isChecked()){
                binding.checkBoxComorbidityHypertension.setChecked(true);
                binding.checkBoxComorbidityHypertension.setButtonDrawable(R.drawable.checkbox_checked);
                binding.getViewModel().addComorbidity("Hypertension");

            }
            else if(!binding.checkBoxComorbidityHypertension.isChecked()){
                binding.checkBoxComorbidityHypertension.setChecked(false);
                binding.checkBoxComorbidityHypertension.setButtonDrawable(R.drawable.checkbox_unchecked);
                binding.getViewModel().removeComorbidity("Hypertension");
            }

        }
        if(view == binding.buttonSaveHealthAssessment){
            MutableLiveData<com.pupccis.fitnex.model.HealthAssessment> insertHealth = binding.getViewModel().insertHealthAssessment();
            insertHealth.observe(getViewLifecycleOwner(), new Observer<com.pupccis.fitnex.model.HealthAssessment>() {
                @Override
                public void onChanged(com.pupccis.fitnex.model.HealthAssessment healthAssessment) {
                    insertHealth.removeObserver(this::onChanged);
                }
            });
        }
    }
}