package com.example.foodapp.Extras;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application{

    private static  MyApplication instance = null;

    public MyApplication() {
        instance = this ;
    }

    public static MyApplication getInstance() {
        return instance;
    }
    public static Context getAppInstance(){
        return instance.getApplicationContext();
    }
}
