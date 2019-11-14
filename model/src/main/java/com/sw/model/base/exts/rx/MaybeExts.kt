package com.sw.model.base.exts.rx

import com.sw.model.base.helper.ProgressDialogHelper
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.context.GlobalContext

/**
 * @author burkd
 * @since 2019-11-14
 */

fun <T> Maybe<T>.loadWithProgressDialog(): Maybe<T> {
    val progressDialog: ProgressDialogHelper = GlobalContext.get().koin.get()
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { progressDialog.show() }
        .doFinally { progressDialog.hide() }
}

fun <T> Maybe<T>.loadAsync(): Maybe<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}