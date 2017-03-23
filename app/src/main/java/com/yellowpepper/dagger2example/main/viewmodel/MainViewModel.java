package com.yellowpepper.dagger2example.main.viewmodel;

import com.yellowpepper.dagger2example.common.RxCommand;
import com.yellowpepper.dagger2example.main.interactor.MainInteractor;
import com.yellowpepper.dagger2example.model.AppConfig;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by mickey35vn on 3/23/17.
 */

public class MainViewModel {

    private MainInteractor mMainInteractor;

    RxCommand<AppConfig> mRequestCommand;
    Observable<AppConfig> mOnSuccessObservable;

    @Inject
    public MainViewModel(MainInteractor interactor) {
        mMainInteractor = interactor;

        mRequestCommand = new RxCommand<>(() -> interactor.getAppConfig());
        mOnSuccessObservable = mRequestCommand.onExecuted();
    }

    public void getAppConfig() {
        mRequestCommand.execute();
    }

    public Observable<AppConfig> onAppConfigLoadedObservable() {
        return mOnSuccessObservable;
    }

    public Observable<String> onLoadDataErrorObservable() {
        return mRequestCommand.onError().map(error -> error.getLocalizedMessage());
    }

}
