package com.example.foodapp.Requests;

import com.example.foodapp.BuildConfig;
import com.example.foodapp.Utils.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class RetrofitSingleton {

//    static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
//            .setLevel(HttpLoggingInterceptor.Level.BODY);

    static OkHttpClient httpClient  = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

    private static  Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build();

    public static RecipeAPI getRecipeAPI (){
        return retrofit.create(RecipeAPI.class);
    }
}
