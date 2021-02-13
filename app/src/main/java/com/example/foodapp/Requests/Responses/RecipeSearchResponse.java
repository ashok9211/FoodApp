package com.example.foodapp.Requests.Responses;

import com.example.foodapp.Models.Recipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeSearchResponse {

    @SerializedName("results")
    @Expose
    private List<Recipe> searchRecipeResults ;

    @SerializedName("offset")
    @Expose
    private Integer skipNumberofdishes ;

    @SerializedName("number")
    @Expose
    private Integer searchNumber ;

    @SerializedName("totalResults")
    @Expose
    private Integer totalResults ;

    public List<Recipe> getSearchRecipeResults() {
        return searchRecipeResults;
    }
    public Integer getSkipNumberofdishes() {
        return skipNumberofdishes;
    }
    public Integer getSearchNumber() {
        return searchNumber;
    }
    public Integer getTotalResults() {
        return totalResults;
    }


    @Override
    public String toString() {
        return "RecipeSearchResponse{" +
                "searchRecipeResults=" + searchRecipeResults +
                ", skipNumberofdishes=" + skipNumberofdishes +
                ", searchNumber=" + searchNumber +
                ", totalResults=" + totalResults +
                '}';
    }
}
