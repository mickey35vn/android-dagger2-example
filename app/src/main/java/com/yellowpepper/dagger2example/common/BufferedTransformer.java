package com.yellowpepper.dagger2example.common;

import rx.Observable;

/**
 * Created by mickey35vn on 3/23/17.
 */

class BufferedTransformer<T> implements Observable.Transformer<T, T> {
    Observable<Boolean> mPauser;

    public BufferedTransformer(Observable<Boolean> pauser) {
        mPauser = pauser;
    }

    @Override
    public Observable<T> call(Observable<T> source) {
        return PausableBufferedObservable.create(source, mPauser);
    }
}