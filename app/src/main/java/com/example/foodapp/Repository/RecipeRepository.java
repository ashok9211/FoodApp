package com.example.foodapp.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodapp.Models.Recipe;
import com.example.foodapp.Requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {

    private static final String TAG = "soki";
    private static RecipeRepository instance ;
    private RecipeApiClient mRecipeApiClient ;
    private Integer skipByNumberoffset = 0 ;
    private String query ;

    public static RecipeRepository getRecipeRepositoryInstance(){
        if (instance == null){
            instance = new RecipeRepository();
        }
        return instance ;
    }

    public RecipeRepository() {
        mRecipeApiClient = RecipeApiClient.getInstance();
    }

    // get data methods
    public LiveData<List<Recipe>> getListMutableLiveData() {
        return mRecipeApiClient.getListMutableLiveData();
    }
    public MutableLiveData<Recipe> getSingleRecipeMutableData(){
        return mRecipeApiClient.getmRecipeSingleData();
    }

    // Query methods
    public void getSearchedRecipesList(String query,Integer skipDishes){
        this.query = query ;
        mRecipeApiClient.seearchRecipesApi(query,skipDishes);
    }

    public void querySingleSelectedRecipe(String id){
        mRecipeApiClient.searchSingleRecipeApi(id);
    }

    // cancel request methods
    public void cancelSearchRequest(){
        mRecipeApiClient.cancelrequest();
    }

    // on scroal load more method
    public void loadMoreDataInList(){
        skipByNumberoffset += 30 ;
        mRecipeApiClient.seearchRecipesApi(query,skipByNumberoffset);
    }
}
