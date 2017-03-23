package com.yellowpepper.dagger2example.common;

import rx.Observable;
import rx.functions.Func0;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * Created by mickey35vn on 3/23/17.
 */

public class RxCommand<ResultType> {
    PublishSubject<Void> mCommand = PublishSubject.create();
    BehaviorSubject<Boolean> mOnExecuting = BehaviorSubject.create(false);
    PublishSubject<Throwable> mOnError = PublishSubject.create();
    Observable<ResultType> mOnExecuted;

    public RxCommand(Func0<Observable<ResultType>> func) {
        init(func, 1);
    }

    public RxCommand(Func0<Observable<ResultType>> func, int replayCount) {
        init(func, replayCount);
    }

    private void init(Func0<Observable<ResultType>> func, int replayCount) {
        mOnExecuted = mCommand.withLatestFrom(mOnExecuting, (v, executing) -> executing)
                .filter(executing -> !executing)
                .flatMap(v -> {
                    mOnExecuting.onNext(true);
                    return func.call()
                            .doOnError((v1) -> {
                                mOnExecuting.onNext(false);
                                mOnError.onNext(v1);
                            })
                            .onErrorResumeNext(error -> Observable.empty())
                            .doOnCompleted(() -> mOnExecuting.onNext(false));
                })
                .replay(replayCount).autoConnect();
    }

    public void execute() {
        mCommand.onNext(null);
    }

    public void executeOnce() {
        mCommand.onNext(null);
    }

    public Observable<Boolean> onExecuting() {
        return mOnExecuting;
    }

    public Observable<ResultType> onExecuted() {
        return mOnExecuted;
    }

    public Observable<Throwable> onError() {
        return mOnError;
    }
}