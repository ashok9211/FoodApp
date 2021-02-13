package com.example.foodapp.Requests;

import com.example.foodapp.Models.Recipe;
import com.example.foodapp.Requests.Responses.RecipeList;
import com.example.foodapp.Requests.Responses.RecipeSearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeAPI {

    //Search Recipe
    @GET("recipes/complexSearch")
    Call<RecipeSearchResponse> getSearchResponse (
            @Query("apiKey") String Key,
            @Query("query") String query,
            @Query("offset") Integer skipresultby,
            @Query("fillIngredients") Boolean fillIngredient,
            @Query("addRecipeInformation") Boolean addRecipeInformationn,
            @Query("number") Integer number
    );

    //Single Item Call
    @GET("recipes/{id}/information")
    Call<Recipe> getRecipe(
            @Path("id") String rec_id ,
            @Query("apiKey") String Key
    );

    //Get Random List Of Recipes
    @GET("recipes/random")
    Call<RecipeList> getRecipeList(
            @Query("apiKey") String Key,
            @Query("vegetarian") Boolean vegetarian ,
            @Query("number") Integer number
    );
}
