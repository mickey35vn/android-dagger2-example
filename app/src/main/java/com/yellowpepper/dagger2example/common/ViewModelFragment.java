package com.yellowpepper.dagger2example.common;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.internal.util.SubscriptionList;
import rx.subjects.BehaviorSubject;

/**
 * Created by mickey35vn on 3/23/17.
 */

public abstract class ViewModelFragment<ViewModel, AppComponent> extends Fragment
        implements LoaderManager.LoaderCallbacks<ViewModel>,
        ViewModelProvider<ViewModel> {

    private final BehaviorSubject<Boolean> mPauser = BehaviorSubject.create(Boolean.FALSE);

    @Inject
    protected ViewModel mViewModel;
    protected SubscriptionList mSubscriptions;

    protected abstract void bindViewModel();
    protected abstract int getFragmentLayoutResId();
    protected abstract void inject(AppComponent appComponent);
    protected abstract AppComponent getAppComponent();

    protected int getViewModelLoaderId() {
        return Integer.MAX_VALUE - 1;
    }

    protected void addSubscriptions(Subscription... subscriptions) {
        for (Subscription subscription : subscriptions) {
            mSubscriptions.add(subscription);
        }
    }

    public void setViewModel(ViewModel viewModel) {
        mViewModel = viewModel;
    }

    public ViewModel provide() {
        return mViewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayoutResId(), null);
    }

    @Override
    public Loader<ViewModel> onCreateLoader(int id, Bundle args) {
        if (id == getViewModelLoaderId()) {
            return new ViewModelLoader<>(getContext(), this);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<ViewModel> loader, ViewModel data) {
        mViewModel = data;
    }

    @Override
    public void onLoaderReset(Loader<ViewModel> loader) {
    }

    @NonNull
    @CheckResult
    public final <T> Observable.Transformer<T, T> bindToLifecycle() {
        return new BufferedTransformer<>(mPauser);
    }

    @Override
    @CallSuper
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mViewModel == null && savedInstanceState == null) {
            inject(getAppComponent());
        }

        getLoaderManager().initLoader(getViewModelLoaderId(), null, this);

        // on configuration changed, onLoadFinished won't be called before onStart() with initLoader()
        // then we need to manually recover our ViewModel from the loader
        if (mViewModel == null) {
            ViewModelLoader<ViewModel> loader = (ViewModelLoader<ViewModel>)
                    getLoaderManager().getLoader(getViewModelLoaderId());
            mViewModel = loader.getViewModel();
        }

        if(mViewModel == null) {
            inject(getAppComponent());
        }

        mSubscriptions = new SubscriptionList();
        if(mViewModel != null){
            bindViewModel();
        }
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        mPauser.onNext(Boolean.FALSE);
    }

    @Override
    @CallSuper
    public void onPause() {
        mPauser.onNext(Boolean.TRUE);
        super.onPause();
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        mSubscriptions.unsubscribe();
        super.onDestroyView();
    }

}