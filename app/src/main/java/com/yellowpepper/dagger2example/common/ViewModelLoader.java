package com.yellowpepper.dagger2example.common;

import android.content.Context;
import android.support.v4.content.Loader;

/**
 * Created by mickey35vn on 3/23/17.
 */

public class ViewModelLoader<ViewModel> extends Loader<ViewModel> {
    private ViewModelProvider<ViewModel> mProvider;
    private ViewModel mViewModel;

    public ViewModelLoader(Context context, ViewModelProvider<ViewModel> provider) {
        super(context);
        this.mProvider = provider;
    }

    @Override
    protected void onStartLoading() {
        if (mViewModel != null) {
            return;
        }

        onForceLoad();
    }

    @Override
    protected void onForceLoad() {
        deliverResult(getViewModel());
    }

    public ViewModel getViewModel() {
        if (mViewModel == null) {
            mViewModel = mProvider.provide();
        }
        return mViewModel;
    }
}