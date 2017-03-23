package com.yellowpepper.dagger2example.data.api;

import com.yellowpepper.dagger2example.model.AppConfig;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by mickey35vn on 3/23/17.
 */

public interface ConfigApi {

    @GET
    Observable<AppConfig> getAppConfig(@Url String url);

}