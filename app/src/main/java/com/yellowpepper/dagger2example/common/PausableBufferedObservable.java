package com.yellowpepper.dagger2example.common;

import rx.Observable;
import rx.subjects.ReplaySubject;

/**
 * Created by mickey35vn on 3/23/17.
 */

public class PausableBufferedObservable {
    public static <T> Observable<T> create(Observable<T> source, Observable<Boolean> pauser) {
        return source.publish(observable -> {
            ReplaySubjectHolder<T> replaySubjectHolder = new ReplaySubjectHolder<>(observable);
            return pauser.distinctUntilChanged()
                    .switchMap(paused -> {
                        if (paused) {
                            replaySubjectHolder.create(observable);
                            return Observable.empty();
                        } else {
                            return replaySubjectHolder.replaySubject.concatWith(source);
                        }
                    });
        });
    }

    private static class ReplaySubjectHolder<T> {
        public ReplaySubject<T> replaySubject;

        public ReplaySubjectHolder(Observable<T> source) {
            create(source);
        }

        public void create(Observable<T> source) {
            replaySubject = ReplaySubject.create();
            source.subscribe(replaySubject);
        }
    }
}