package com.yellowpepper.dagger2example.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mickey35vn on 3/23/17.
 */

@Module
public class AppModule {

    private Context mApplicationContext;

    public AppModule(Context applicationContext) {
        mApplicationContext = applicationContext;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return mApplicationContext;
    }

}
