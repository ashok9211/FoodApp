package com.example.foodapp.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodapp.Models.Recipe;
import com.example.foodapp.Repository.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    private static final String TAG = "soki";
    private RecipeRepository recipeRepository;
    private LiveData<List<Recipe>> listLiveData;
    private boolean isViewingRecipes ;
    private boolean isPerformingQuery;

    public RecipeListViewModel() {
        isPerformingQuery = false ;
        recipeRepository = RecipeRepository.getRecipeRepositoryInstance();
        listLiveData = recipeRepository.getListMutableLiveData();
    }

    public LiveData<List<Recipe>> getListLiveData() {
        return listLiveData;
    }

    public void getSearchedRecipesList(String query,Integer skipDishes){
        isViewingRecipes = true ;
        isPerformingQuery = true ;
        recipeRepository.getSearchedRecipesList(query,skipDishes);
    }

    // getter and setters to check whether viewing contents or not
    public boolean isViewingRecipes() {
        return isViewingRecipes;
    }

    public void setViewingRecipes(boolean viewingContents) {
        isViewingRecipes = viewingContents;
    }

    // getter and setters for canceling the request
    public boolean isPerformingQuery() {
        return isPerformingQuery;
    }

    public void setPerformingQuery(boolean performingQuery) {
        isPerformingQuery = performingQuery;
    }

    //Load More dtaa in list when reached bottom of list call
    public void getMoreData(){
        if (!isPerformingQuery && isViewingRecipes ){
            recipeRepository.loadMoreDataInList();
        }
    }

    //custom bacpress check
    public boolean onBackPressed(){
        if (isPerformingQuery){
            recipeRepository.cancelSearchRequest();
            isPerformingQuery = false ;
        }
        if (isViewingRecipes){
            isViewingRecipes = false ;
        }
        return true ;
    }
}
