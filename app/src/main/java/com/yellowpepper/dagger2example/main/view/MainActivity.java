package com.yellowpepper.dagger2example.main.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yellowpepper.dagger2example.R;
import com.yellowpepper.dagger2example.main.viewmodel.MainViewModel;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
