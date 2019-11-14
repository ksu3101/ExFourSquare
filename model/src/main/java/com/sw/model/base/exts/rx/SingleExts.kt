package com.sw.model.base.exts.rx

import com.sw.common.response.FsqResponse
import com.sw.common.response.toResponseException
import com.sw.model.base.helper.ProgressDialogHelper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.context.GlobalContext

/**
 * @author burkd
 * @since 2019-11-14
 */

private fun <T> handleResponse(it: FsqResponse<T>): Single<T> {
    return if (it.isError() || !it.isAvailableResponse()) {
        Single.error(it.meta.toResponseException())
    } else {
        Single.just(it.response!!)
    }
}

fun <T> Single<T>.loadDataWithProgressDialog(): Single<T> {
    val progressDialog: ProgressDialogHelper = GlobalContext.get().koin.get()
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { progressDialog.show() }
        .doFinally { progressDialog.hide() }
}

fun <T> Single<FsqResponse<T>>.loadWithProgressDialog(): Single<T> {
    val progressDialog: ProgressDialogHelper = GlobalContext.get().koin.get()
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .flatMap { handleResponse(it) }
        .doOnSubscribe { progressDialog.show() }
        .doFinally { progressDialog.hide() }
}

fun <T> Single<FsqResponse<T>>.loadAsync(): Single<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .flatMap { handleResponse(it) }
}