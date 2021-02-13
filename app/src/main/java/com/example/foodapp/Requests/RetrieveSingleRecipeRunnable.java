package com.example.foodapp.Requests;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.foodapp.Models.Recipe;
import com.example.foodapp.Utils.Constants;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class RetrieveSingleRecipeRunnable implements Runnable{

    private static final String TAG ="soki" ;
    private String id ;
    private Boolean cancel ;
    private MutableLiveData<Recipe> getsinglerecipeData = null;

    public MutableLiveData<Recipe> getGetsinglerecipeData() {
        return getsinglerecipeData;
    }

    public RetrieveSingleRecipeRunnable(String id) {
        getsinglerecipeData = new MutableLiveData<>();
        this.id = id ;
        cancel = false ;
    }

    @Override
    public void run() {

        try {
            Response<Recipe> singleRecipeResponse = singleRecipeCall(id).execute();
            if (cancel){
                return;
            }
            else if (singleRecipeResponse != null && singleRecipeResponse.code() == 200){
                Recipe SingleRecipe = singleRecipeResponse.body();
                getsinglerecipeData.postValue(SingleRecipe);
                Log.i(TAG, "run: "+SingleRecipe.getSpoonacularScore());
            }
            else {
                String elseMessage = singleRecipeResponse.message();
                Log.i(TAG, "else Message "+elseMessage);
                getsinglerecipeData.postValue(null);
            }
        } catch (IOException e) {
            String singleRecipeCatchMessage = e.getMessage();
            Log.i(TAG, "Catch Message "+singleRecipeCatchMessage);
            getsinglerecipeData.postValue(null);
        }

    }

    private Call<Recipe> singleRecipeCall(String id){
        return RetrofitSingleton.getRecipeAPI().getRecipe(id, Constants.API_KEY);
    }

    public void setCancel(){
        Log.d(TAG, "cancelled request: ");
        cancel = true ;
    }
}
