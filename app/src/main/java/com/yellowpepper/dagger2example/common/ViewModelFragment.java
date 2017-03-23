package com.yellowpepper.dagger2example.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;

/**
 * Created by mickey35vn on 3/23/17.
 */

public abstract class ViewModelFragment<ViewModel, AppComponent> extends Fragment
        implements LoaderManager.LoaderCallbacks<ViewModel>,
        ViewModelProvider<ViewModel> {
}