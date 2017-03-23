package com.yellowpepper.dagger2example.app;

import com.yellowpepper.dagger2example.main.view.MainActivity;
import com.yellowpepper.dagger2example.modules.AppModule;
import com.yellowpepper.dagger2example.modules.NetworkModule;
import com.yellowpepper.dagger2example.modules.RepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mickey35vn on 3/23/17.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        RepositoryModule.class,
        NetworkModule.class
})

public interface AppComponent {

    void inject(MainActivity activity);

}