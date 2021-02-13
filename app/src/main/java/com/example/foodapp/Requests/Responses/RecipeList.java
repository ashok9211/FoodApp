package com.example.foodapp.Requests.Responses;

import com.example.foodapp.Models.Recipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeList {

    @SerializedName("recipes")
    @Expose
    private List<Recipe> recipes ;

    public List<Recipe> getRecipes() {
        return recipes;
    }

    @Override
    public String toString() {
        return "RecipeList{" +
                "recipes=" + recipes +
                '}';
    }
}
