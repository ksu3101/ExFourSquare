package com.sw.model.base.exts.rx

import com.sw.model.base.helper.ProgressDialogHelper
import com.sw.model.base.redux.Action
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.context.GlobalContext

/**
 * @author burkd
 * @since 2019-11-05
 */

inline fun Completable.andActionObservable(next: () -> Action): Observable<Action> {
    return this.andThen(Observable.just(next()))
}

fun Completable.loadWithProgressDialog(): Completable {
    val progressDialog: ProgressDialogHelper = GlobalContext.get().koin.get()
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { progressDialog.show() }
        .doFinally { progressDialog.hide() }
}

fun Completable.loadAsync(): Completable {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}