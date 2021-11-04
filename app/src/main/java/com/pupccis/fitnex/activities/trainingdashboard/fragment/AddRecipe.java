package com.pupccis.fitnex.activities.trainingdashboard.fragment;

import android.os.Bundle;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pupccis.fitnex.R;
import com.pupccis.fitnex.databinding.FragmentAddRecipeBinding;
import com.pupccis.fitnex.model.Recipe;
import com.pupccis.fitnex.viewmodel.RecipeViewModel;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRecipe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRecipe extends Fragment implements View.OnClickListener{

    private static FragmentAddRecipeBinding fragmentAddRecipeBinding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddRecipe() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRecipe.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRecipe newInstance(String param1, String param2) {
        AddRecipe fragment = new AddRecipe();
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
        fragmentAddRecipeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_recipe, container, false);
        fragmentAddRecipeBinding.setViewModel(new RecipeViewModel());
        fragmentAddRecipeBinding.setLifecycleOwner(this);
        fragmentAddRecipeBinding.setPresenter(this);
        fragmentAddRecipeBinding.executePendingBindings();
        // Inflate the layout for this fragment
        return fragmentAddRecipeBinding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if(view == fragmentAddRecipeBinding.buttonAddRecipeButton){
            MutableLiveData<Recipe> recipeMutableLiveData = fragmentAddRecipeBinding.getViewModel().insertRecipe();
            recipeMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Recipe>() {
                @Override
                public void onChanged(Recipe recipe) {
                    recipeMutableLiveData.removeObserver(this::onChanged);
                }
            });
        }
    }

    @BindingAdapter({"recipeValidationData"})
    public static void validateRecipeData(View view, HashMap<String, Object> validationData){

    }
}