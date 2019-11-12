package com.sw.model.base.exts.rx

import com.sw.model.base.redux.Action
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * @author burkd
 * @since 2019-11-05
 */

inline fun Completable.andActionObservable(next: () -> Action): Observable<Action> {
    return this.andThen(Observable.just(next()))
}