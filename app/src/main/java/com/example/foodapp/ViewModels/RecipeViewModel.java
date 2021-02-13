package com.example.foodapp.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodapp.Models.Recipe;
import com.example.foodapp.Repository.RecipeRepository;

public class RecipeViewModel extends ViewModel {
    private RecipeRepository mRecipeRepository ;

    public RecipeViewModel() {
        mRecipeRepository = RecipeRepository.getRecipeRepositoryInstance();
    }

    public MutableLiveData<Recipe> getmRecipebyIdMutableLiveData() {
        //correct it
        return mRecipeRepository.getSingleRecipeMutableData();
    }

    public void querySingleRecipe(String id){
        mRecipeRepository.querySingleSelectedRecipe(id);
    }
}
