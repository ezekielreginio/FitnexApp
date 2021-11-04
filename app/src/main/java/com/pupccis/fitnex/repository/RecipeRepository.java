package com.pupccis.fitnex.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pupccis.fitnex.model.Recipe;
import com.pupccis.fitnex.utilities.Constants.RecipeConstants;

public class RecipeRepository {
    private static RecipeRepository instance;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static RecipeRepository getInstance(){
        if(instance == null)
            instance = new RecipeRepository();
        return instance;
    }

    public MutableLiveData<Recipe> insertRecipe(Recipe recipe){
        MutableLiveData<Recipe> recipeMutableLiveData = new MutableLiveData<>();
        db.collection(RecipeConstants.KEY_COLLECTION_RECIPES).document().set(recipe.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    recipeMutableLiveData.postValue(recipe);
                else
                    recipeMutableLiveData.postValue(null);
            }
        });
        return recipeMutableLiveData;
    }
}
