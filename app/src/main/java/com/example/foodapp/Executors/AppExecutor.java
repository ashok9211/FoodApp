package com.example.foodapp.Executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutor {

    private static AppExecutor instance ;

    public static AppExecutor getInstance() {
        if (instance == null){
            instance = new AppExecutor();
        }
        return instance;
    }

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(6) ;

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }
}
