package com.yellowpepper.dagger2example.app.module;

import android.content.Context;

import com.yellowpepper.dagger2example.data.repository.PreferenceRepository;
import com.yellowpepper.dagger2example.data.repository.SessionRepository;
import com.yellowpepper.dagger2example.data.repository.impl.PreferenceRepositoryImpl;
import com.yellowpepper.dagger2example.data.repository.impl.SessionRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mickey35vn on 3/23/17.
 */

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    PreferenceRepository providesPreferenceRepository(Context applicationContext) {
        return new PreferenceRepositoryImpl(applicationContext);
    }

    @Singleton
    @Provides
    SessionRepository providesSessionRepository(PreferenceRepository preferenceRepository) {
        return new SessionRepositoryImpl(preferenceRepository);
    }

}
