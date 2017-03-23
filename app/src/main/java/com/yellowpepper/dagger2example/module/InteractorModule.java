package com.yellowpepper.dagger2example.module;

import com.yellowpepper.dagger2example.main.interactor.MainInteractor;
import com.yellowpepper.dagger2example.main.interactor.impl.MainInteractorImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mickey35vn on 3/23/17.
 */

@Module
public class InteractorModule {

    @Singleton
    @Provides
    MainInteractor providesMainInteractor(MainInteractorImpl interactor) {
        return interactor;
    }

}
