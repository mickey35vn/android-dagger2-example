package com.yellowpepper.dagger2example.main.interactor.impl;

import com.yellowpepper.dagger2example.BuildConfig;
import com.yellowpepper.dagger2example.data.api.ConfigApi;
import com.yellowpepper.dagger2example.main.interactor.MainInteractor;
import com.yellowpepper.dagger2example.model.AppConfig;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by mickey35vn on 3/23/17.
 */

public class MainInteractorImpl implements MainInteractor {

    private ConfigApi mConfigApi;

    @Inject
    public MainInteractorImpl(ConfigApi configApi) {
        mConfigApi = configApi;
    }

    @Override
    public Observable<AppConfig> getAppConfig() {
        return mConfigApi.getAppConfig(BuildConfig.APP_CONFIG_URL);
    }
}
