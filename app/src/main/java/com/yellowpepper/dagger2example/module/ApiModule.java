package com.yellowpepper.dagger2example.module;

import com.yellowpepper.dagger2example.data.api.ConfigApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by mickey35vn on 3/23/17.
 */

@Module
public class ApiModule {

    @Provides
    @Singleton
    public ConfigApi providesConfigApi(Retrofit retrofit) {
        return retrofit.create(ConfigApi.class);
    }

}