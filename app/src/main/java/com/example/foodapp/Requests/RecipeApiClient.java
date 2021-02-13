package com.example.foodapp.Requests;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.foodapp.Executors.AppExecutor;
import com.example.foodapp.Models.Recipe;
import com.example.foodapp.Requests.Responses.RecipeSearchResponse;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.foodapp.Utils.Constants.*;

public class RecipeApiClient {

    private static final String TAG = "soki";

    private static RecipeApiClient instance ;
    private MutableLiveData<List<Recipe>> listMutableLiveData ;
    private MutableLiveData<Recipe> mRecipeSingleData ;
    private RetrieveRecipeRunnable mRetrieveRecipeRunnable ;
    private RetrieveSingleRecipeRunnable mRetrieveSingleRecipeRunnable;

    public static RecipeApiClient getInstance(){
        if (instance == null){
            instance = new RecipeApiClient();
        }
        return instance ;
    }
    public RecipeApiClient(){
        listMutableLiveData = new MutableLiveData<>();
        mRecipeSingleData = new MutableLiveData<>() ;
    }

    public MutableLiveData<List<Recipe>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public MutableLiveData<Recipe> getmRecipeSingleData() {
        mRecipeSingleData = mRetrieveSingleRecipeRunnable.getGetsinglerecipeData();
        return mRecipeSingleData ;
    }

    public void seearchRecipesApi(String query, Integer skipDishes){
        if (mRetrieveRecipeRunnable != null){
            mRetrieveRecipeRunnable = null ;
        }
        mRetrieveRecipeRunnable = new RetrieveRecipeRunnable(query,skipDishes);

        final Future handler = AppExecutor.getInstance().getExecutorService().submit(mRetrieveRecipeRunnable);

        AppExecutor.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        },NETWORK_RUNOUT_TIME,TimeUnit.SECONDS);
    }

    public void searchSingleRecipeApi(String id){
        if (mRetrieveSingleRecipeRunnable != null){
            mRetrieveSingleRecipeRunnable = null ;
        }
        mRetrieveSingleRecipeRunnable = new RetrieveSingleRecipeRunnable(id);

        Future singleRecipeHandler = AppExecutor.getInstance().getExecutorService().submit(mRetrieveSingleRecipeRunnable);

        AppExecutor.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {
                singleRecipeHandler.cancel(true);
            }
        },NETWORK_RUNOUT_TIME,TimeUnit.SECONDS);
    }

    private class RetrieveRecipeRunnable implements Runnable {
        private String query ;
        private Integer skipDishes ;
        private Boolean cancel ;

        public RetrieveRecipeRunnable( String query,Integer skipDishes) {
            this.query = query;
            this.skipDishes = skipDishes ;
            cancel = false ;
        }

        @Override
        public void run() {
            try {
                Response<RecipeSearchResponse> response = listCall(query,skipDishes).execute();
                if (cancel){
                    return;
                }
                else if (response!= null && response.code() == 200){
                    Log.i(TAG, " In Recipe Api Client"+response.code());
                    List<Recipe> recipeList = response.body().getSearchRecipeResults();
                    for (Recipe recipe : recipeList){
                        Log.i(TAG, " In Recipe Api Client"+recipe.getTitle());
                    }
                    if (skipDishes == 0){
                        listMutableLiveData.postValue(recipeList);
                    }
                    else {
                        List<Recipe> nextListdata = listMutableLiveData.getValue();
                        nextListdata.addAll(recipeList);
                        listMutableLiveData.postValue(nextListdata);
                    }

                }
                else {
                    String error = response.errorBody().toString();
                    Log.i(TAG, "run: "+response.code() + error);
                    listMutableLiveData.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                String error = e.getMessage();
                Log.i(TAG, "run: "+error);
                listMutableLiveData.postValue(null);
            }
        }

        private Call<RecipeSearchResponse> listCall(String query,Integer skipdish){
            return RetrofitSingleton.getRecipeAPI().getSearchResponse(API_KEY,
                    query,
                    skipdish,
                    FILLINGREDIENTS,
                    ADDRECIPEINFORMATION,
                    NUMBER
            );
        }

        private void setCancel(){
            Log.d(TAG, "cancelled request: ");
            cancel = true ;
        }
    }
    public void cancelrequest(){
        if (mRetrieveRecipeRunnable != null){
            mRetrieveRecipeRunnable.setCancel();
        }
    }

    public void cancelRecipeRequest(){
        if (mRetrieveSingleRecipeRunnable != null){
            mRetrieveSingleRecipeRunnable.setCancel();
        }
    }
}
