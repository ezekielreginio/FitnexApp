package com.pupccis.fitnex.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.pupccis.fitnex.BR;
import com.pupccis.fitnex.api.enums.Privilege;
import com.pupccis.fitnex.model.Recipe;
import com.pupccis.fitnex.repository.RecipeRepository;

import java.util.HashMap;

public class RecipeViewModel extends BaseObservable {
    private RecipeRepository recipeRepository = RecipeRepository.getInstance();

    @Bindable
    private String addRecipeName = null;
    @Bindable
    private String addRecipeDescription = null;
    @Bindable
    private int addRecipeCategory = -1;
    @Bindable
    private Privilege addRecipePatronLevel = null;
    @Bindable
    private String addRecipePrepTime = null;
    @Bindable
    private HashMap<String, Object> recipeValidationData = null;

    public String getAddRecipeName() {
        return addRecipeName;
    }

    public String getAddRecipeDescription() {
        return addRecipeDescription;
    }

    public int getAddRecipeCategory() {
        return addRecipeCategory;
    }

    public Privilege getAddRecipePatronLevel() {
        return addRecipePatronLevel;
    }

    public String getAddRecipePrepTime() {
        return addRecipePrepTime;
    }

    public HashMap<String, Object> getRecipeValidationData() {
        return recipeValidationData;
    }

    public void setAddRecipeName(String addRecipeName) {
        this.addRecipeName = addRecipeName;
    }

    public void setAddRecipeDescription(String addRecipeDescription) {
        this.addRecipeDescription = addRecipeDescription;
    }

    public void setAddRecipeCategory(int addRecipeCategory) {
        this.addRecipeCategory = addRecipeCategory;
    }

    public void setAddRecipePatronLevel(Privilege addRecipePatronLevel) {
        this.addRecipePatronLevel = addRecipePatronLevel;
    }

    public void setAddRecipePrepTime(String addRecipePrepTime) {
        this.addRecipePrepTime = addRecipePrepTime;
    }

    public void setRecipeValidationData(HashMap<String, Object> recipeValidationData) {
        this.recipeValidationData = recipeValidationData;
        notifyPropertyChanged(BR.recipeValidationData);
    }

    public MutableLiveData<Recipe> insertRecipe(){
        Recipe recipe = recipeInstance();
        return recipeRepository.insertRecipe(recipe);
    }

    private Recipe recipeInstance(){
        Recipe recipe = new Recipe.Builder(getAddRecipeName()
                , getAddRecipeDescription()
                , getAddRecipeCategory()
                , getAddRecipePrepTime()
                , getAddRecipePatronLevel()).build();
        return recipe;
    }
}
