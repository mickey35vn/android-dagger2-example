package com.yellowpepper.dagger2example.main.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yellowpepper.dagger2example.R;
import com.yellowpepper.dagger2example.app.MyApplication;
import com.yellowpepper.dagger2example.main.viewmodel.MainViewModel;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    @Inject
    MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MyApplication) getApplication()).getAppComponent().inject(this);

        mMainViewModel.onAppConfigLoadedObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(appConfig -> {
                    Log.d("appConfig == ", appConfig.toString());
                });

        mMainViewModel.onLoadDataErrorObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(message -> {
                    Log.d("ErrorObservable", message);
                });

        mMainViewModel.getAppConfig();
    }
}
