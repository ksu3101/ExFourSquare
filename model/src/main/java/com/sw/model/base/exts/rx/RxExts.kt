package com.sw.model.base.exts.rx

import com.sw.model.base.RxDisposer
import com.sw.model.base.redux.Action
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author burkd
 * @since 2019-11-04
 */

private typealias OnNext<T> = ((T) -> (Unit))

private typealias OnNextAnd<T> = ((T) -> (Any?))
private typealias OnError = ((Throwable) -> Unit)?
private typealias OnComplete = (() -> Unit)?

fun <T> Observable<T>.subscribeWith(
    rxDisposer: RxDisposer,
    onNext: OnNext<T>,
    onError: OnError = null,
    onComplete: OnComplete = null
) {
    rxDisposer.addDisposer(
        this.subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onNext(it) },
                { onError?.let { errorHandler -> errorHandler(it) } },
                { onComplete?.let { onComplete } }
            )
    )
}

fun <T> Flowable<T>.subscribeWith(
    rxDisposer: RxDisposer,
    onNext: OnNext<T>,
    onError: OnError = null,
    onComplete: OnComplete = null
) {
    rxDisposer.addDisposer(
        this.subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onNext(it) },
                { onError?.let { errorHandler -> errorHandler(it) } },
                { onComplete?.let { onComplete } }
            )
    )
}

fun <T> Maybe<T>.subscribeWith(
    rxDisposer: RxDisposer,
    onNext: OnNext<T>,
    onError: OnError = null,
    onComplete: OnComplete = null
) {
    rxDisposer.addDisposer(
        this.subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onNext(it) },
                { onError?.let { errorHandler -> errorHandler(it) } },
                { onComplete?.let { onComplete } }
            )
    )
}

fun <T> Single<T>.subscribeWith(
    rxDisposer: RxDisposer,
    onNext: OnNext<T>,
    onError: OnError = null
) {
    rxDisposer.addDisposer(
        this.subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onNext(it) },
                { onError?.let { errorHandler -> errorHandler(it) } }
            )
    )
}

fun Completable.subscribeWith(
    rxDisposer: RxDisposer,
    onComplete: OnComplete = null,
    onError: OnError = null
) {
    rxDisposer.addDisposer(
        this.subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onComplete?.let { it } },
                { onError?.let { errorHandler -> errorHandler(it) } }
            )
    )
}

inline fun createActionProcessor(crossinline merger: (Observable<Action>) -> Array<Observable<Action>>): ObservableTransformer<Action, Action> =
    ObservableTransformer {
        it.publish { shared ->
            Observable.mergeArray<Action>(*merger(shared))
        }
    }

inline fun <T : Action> actionTransformer(crossinline body: (T) -> Observable<Action>): ObservableTransformer<T, Action> {
    return ObservableTransformer { actionObservable ->
        actionObservable.flatMap {
            body(it)
        }
    }
}