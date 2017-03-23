package com.yellowpepper.dagger2example.main.interactor;

import com.yellowpepper.dagger2example.model.AppConfig;

import rx.Observable;

/**
 * Created by mickey35vn on 3/23/17.
 */

public interface MainInteractor {

    Observable<AppConfig> getAppConfig();

}
