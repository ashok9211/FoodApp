package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Acvities.Recipe_showData;
import com.example.foodapp.Adapters.RecipeClickListener;
import com.example.foodapp.Adapters.RecipeRecyclerAdapter;
import com.example.foodapp.Extras.Utils;
import com.example.foodapp.Models.Recipe;
import com.example.foodapp.Requests.RecipeAPI;
import com.example.foodapp.Requests.Responses.RecipeList;
import com.example.foodapp.Requests.RetrofitSingleton;
import com.example.foodapp.Utils.Constants;
import com.example.foodapp.Utils.VerticalSpacingItemDecorator;
import com.example.foodapp.ViewModels.RecipeListViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity implements RecipeClickListener {

    private static final String TAG = "soki";
    public RecipeListViewModel mRecipeListViewModel;
    public RecyclerView mRecyclerView;
    private RecipeRecyclerAdapter mRecipeAdapter;
    private Integer skipDishes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipe_list);

        //Livedata
        mRecipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);
        recipeLiveDataObserver();
        initRecycler();
        initSearchView();
        if (!mRecipeListViewModel.isViewingRecipes()) {
            mRecipeListViewModel.setViewingRecipes(false);
            mRecipeAdapter.displayCategories();
        }
    }

    private void initSearchView() {
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mRecipeAdapter.LoadingDisplay();
                mRecipeListViewModel.getSearchedRecipesList(query,skipDishes);
                searchView.clearFocus();
//                Utils.showProgresS(RecipeListActivity.this);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initRecycler() {

        mRecyclerView = findViewById(R.id.recipeRecyclerView);
        VerticalSpacingItemDecorator mVerticalSpacingItemDecorator = new VerticalSpacingItemDecorator(20);
        mRecyclerView.addItemDecoration(mVerticalSpacingItemDecorator);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecipeAdapter = new RecipeRecyclerAdapter(this);
        mRecyclerView.setAdapter(mRecipeAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                // skip data here code
                if (recyclerView.canScrollVertically(1)){
                    mRecipeListViewModel.getMoreData();
                }
            }
        });
    }

    private void recipeLiveDataObserver() {
        mRecipeListViewModel.getListLiveData().observe(this, recipes -> {
            if (recipes != null) {
                mRecipeListViewModel.setPerformingQuery(false);
                mRecipeAdapter.setmRecipeList(recipes);
            }
        });
    }

    private void getRandomList() {
        RecipeAPI recipeAPI = RetrofitSingleton.getRecipeAPI();
        Call<RecipeList> recipeListCall = recipeAPI.getRecipeList(Constants.API_KEY, Constants.VEGETARIAN, Constants.NUMBER);
        recipeListCall.enqueue(new Callback<RecipeList>() {
            @Override
            public void onResponse(Call<RecipeList> call, Response<RecipeList> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    List<Recipe> recipeList = response.body().getRecipes();
                    for (Recipe recipe : recipeList) {
                        Log.d(TAG, "onResponse: " + recipe.getTitle());
                    }
                } else {
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<RecipeList> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

//    private void getsingleitem() {
//        RecipeAPI recipeAPI = RetrofitSingleton.getRecipeAPI();
//        Call<Recipe> recipeCall = recipeAPI.getRecipe(654959, Constants.API_KEY);
//        recipeCall.enqueue(new Callback<Recipe>() {
//            @Override
//            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
//                if (response.isSuccessful() && response.code() == 200) {
//                    Recipe recipe = response.body();
//                    Log.d(TAG, "onResponse: " + recipe.getInstructions());
//                } else {
//                    Log.d(TAG, "onResponse: " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Recipe> call, Throwable t) {
//                Log.d(TAG, "onFailure: " + t.getMessage());
//            }
//        });
//    }

    @Override
    public void onRecipeSelected(Integer position) {
        Intent intent = new Intent(this, Recipe_showData.class);
        intent.putExtra("recipe_data",mRecipeAdapter.getSelectedRecipe(position));
        startActivity(intent);
    }

    @Override
    public void onCategorySelected(String type) {
        mRecipeAdapter.LoadingDisplay();
        mRecipeListViewModel.getSearchedRecipesList(type,skipDishes);
    }

    @Override
    public void onBackPressed() {
        if (mRecipeListViewModel.onBackPressed()) {
            super.onBackPressed();
        }
        else {
            mRecipeAdapter.displayCategories();
        }
    }
}