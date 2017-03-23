package com.yellowpepper.dagger2example.common;

/**
 * Created by mickey35vn on 3/23/17.
 */

public interface ViewModelProvider<ViewModel> {
    ViewModel provide();
}