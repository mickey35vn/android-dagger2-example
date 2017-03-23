package com.yellowpepper.dagger2example.app;

import android.app.Application;

import com.yellowpepper.dagger2example.app.module.AppModule;

/**
 * Created by mickey35vn on 3/23/17.
 */

public class MyApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}