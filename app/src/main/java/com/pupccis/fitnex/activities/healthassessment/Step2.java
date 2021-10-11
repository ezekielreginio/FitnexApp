package com.pupccis.fitnex.activities.healthassessment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.ActivityHealthAssessmentBinding;
import com.pupccis.fitnex.databinding.FragmentStep2Binding;
import com.pupccis.fitnex.viewmodel.HealthAssessmentViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Step2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Step2 extends Fragment implements View.OnClickListener{
    private FragmentStep2Binding binding;
    private ActivityHealthAssessmentBinding activityBinding;
    private HealthAssessmentViewModel viewModel;
    private ImageView backButton, nextButton;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Step2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Step2.
     */
    // TODO: Rename and change types and number of parameters
    public static Step2 newInstance(String param1, String param2) {
        Step2 fragment = new Step2();
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_step2, container, false);
        binding.setViewModel(((HealthAssessment)getActivity()).getViewModel());
        binding.setPresenter(this);
        binding.executePendingBindings();
        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        if(view == binding.imageViewTwoBack)
            Navigation.findNavController(view).navigate(R.id.action_step2_to_step1);
        if(view == binding.imageViewTwoNext)
            Navigation.findNavController(view).navigate(R.id.action_step2_to_step3);
        if(view == binding.checkBoxAllergy1){
            if(binding.checkBoxAllergy1.isChecked()){
                Log.e("CHECKED", "CHECKED");
                binding.checkBoxAllergy1.setChecked(true);
                binding.checkBoxAllergy1.setButtonDrawable(R.drawable.checkbox_checked);
                binding.getViewModel().addAllergy("Allergy 1");

            }
            else if(!binding.checkBoxAllergy1.isChecked()){
                Log.e("unCHECKED", "CHECKED");
                binding.checkBoxAllergy1.setChecked(false);
                binding.checkBoxAllergy1.setButtonDrawable(R.drawable.checkbox_unchecked);
                binding.getViewModel().removeAllergy("Allergy 1");
            }
            Log.e("Health assessment allergy", binding.getViewModel().getAllergies()+"");
            Log.e("Health assessment height", binding.getViewModel().getHeight());
            Log.e("Health assessment weight", binding.getViewModel().getWeight());
        }
        if(view == binding.checkBoxAllergy1){
            if(binding.checkBoxAllergy2.isChecked()){
                Log.e("CHECKED", "CHECKED");
                binding.checkBoxAllergy2.setChecked(true);
                binding.checkBoxAllergy2.setButtonDrawable(R.drawable.checkbox_checked);
                binding.getViewModel().addAllergy("Allergy 2");

            }
            else if(!binding.checkBoxAllergy2.isChecked()){
                Log.e("unCHECKED", "CHECKED");
                binding.checkBoxAllergy2.setChecked(false);
                binding.checkBoxAllergy2.setButtonDrawable(R.drawable.checkbox_unchecked);
                binding.getViewModel().removeAllergy("Allergy 2");
            }
            Log.e("Health assessment allergy", binding.getViewModel().getAllergies()+"");
            Log.e("Health assessment height", binding.getViewModel().getHeight());
            Log.e("Health assessment weight", binding.getViewModel().getWeight());
        }
        if(view == binding.buttonSavesad){
//            binding.getViewModel().submitHealthAssessment();
        }
    }

    //binding.checkBox.setButtonDrawable(R.drawable.checkbox_checked);
//    public void radioCheck(){
//        int radioID = binding.radioGroupAllergy.getCheckedRadioButtonId();
//        List<Integer> radioList = new ArrayList<>();
//
//    }
}