package com.yellowpepper.dagger2example.common;

import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mickey35vn on 3/23/17.
 */

public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {
    private final RxJavaCallAdapterFactory original;

    private RxErrorHandlingCallAdapterFactory() {
        original = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit));
    }

    private static class RxCallAdapterWrapper implements CallAdapter<Observable<?>> {
        private final Retrofit retrofit;
        private final CallAdapter<?> wrapped;

        public RxCallAdapterWrapper(Retrofit retrofit, CallAdapter<?> wrapped) {
            this.retrofit = retrofit;
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @SuppressWarnings("unchecked")
        @Override
        public <R> Observable<?> adapt(Call<R> call) {
            return ((Observable) wrapped.adapt(call))
                    .onErrorResumeNext(new Func1<Throwable, Observable>() {
                        @Override
                        public Observable call(Throwable throwable) {
                            return Observable.error(transformException(throwable));
                        }
                    });
        }

        private Exception transformException(Throwable throwable) {
            System.out.println(">>> RxCallAdapterWrapper -> transformException : " + throwable.getMessage());
            // We had non-200 http error
            if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) throwable;
                Response response = httpException.response();
                try {
                    ErrorResponse errorResponse = getErrorBodyAs(ErrorResponse.class, response);
                    String apiErrorType =  (errorResponse.status != null && errorResponse.status.length() > 0) ? errorResponse.status: errorResponse.errorType;
                    if(httpException.code() == 449){
                        apiErrorType = "449";
                    }
                    String detailMsg = (errorResponse.description != null && errorResponse.description.length() > 0) ? errorResponse.description : errorResponse.message;

                    if (httpException.code() == 401) {
                        Log.d("httpException", "401");
                    }

                    return new RuntimeException(detailMsg);

                } catch (IOException e) {
                    Log.getStackTraceString(e);
                    return new RuntimeException("500 - Server internal error");
                }
            }

            // A network error happened
            if (throwable instanceof UnknownHostException || throwable instanceof IOException) {
                System.out.println(throwable.getMessage());
                return new RuntimeException(throwable);
            }

            // We don't know what happened. We need to simply convert to an unknown error
            return new RuntimeException(throwable);
        }

        private  <T> T getErrorBodyAs(Class<T> type, Response response) throws IOException {
            if (response == null || response.errorBody() == null) {
                return null;
            }
            Converter<ResponseBody, T> converter =
                    retrofit.responseBodyConverter(type, new Annotation[0]);
            return converter.convert(response.errorBody());
        }
    }
}