package com.yellowpepper.dagger2example.modules;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yellowpepper.dagger2example.repository.SessionRepository;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache okHttpCache, SessionRepository sessionRepository) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.interceptors().add(new AuthenticatedHeader(sessionRepository));

        builder.cache(okHttpCache);

        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.interceptors().add(logging);

        OkHttpClient client = builder.build();

        return client;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    static class AuthenticatedHeader implements Interceptor {
        private SessionRepository mSessionRepository;

        public AuthenticatedHeader(SessionRepository sessionRepository) {
            mSessionRepository = sessionRepository;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request.Builder requestBuilder = chain.request()
                    .newBuilder();

            /*if(mSessionRepository.getSession() != null) {
                String accessToken = mSessionRepository.getSession().getAccessToken();
                if (accessToken != null && accessToken.length() > 0) {
                    requestBuilder.addHeader("Authorization", "Bearer " + accessToken);
                }
            }*/

            requestBuilder.addHeader("accept", "application/json");
            requestBuilder.addHeader("Content-Type", "application/json");

            Request newRequest = requestBuilder.build();

            return chain.proceed(newRequest);
        }
    }

}
