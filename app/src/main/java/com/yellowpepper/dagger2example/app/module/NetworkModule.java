package com.yellowpepper.dagger2example.app.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yellowpepper.dagger2example.common.RxErrorHandlingCallAdapterFactory;
import com.yellowpepper.dagger2example.data.repository.SessionRepository;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mickey35vn on 3/23/17.
 */

@Module
public class NetworkModule {

    private final long TIMEOUT = 60;

    @Provides
    @Singleton
    Retrofit provideRetrofit(SessionRepository sessionRepository) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

//        builder.interceptors().add(new AuthenticatedHeader(sessionRepository));

        builder.readTimeout(5, TimeUnit.SECONDS);
        builder.connectTimeout(5, TimeUnit.SECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.interceptors().add(logging);

        OkHttpClient client = builder.build();

        Gson gson = new GsonBuilder()
                .create();

        return new Retrofit.Builder()
                .baseUrl("http://google.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .client(client)
                .build();
    }

}
