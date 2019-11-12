package com.sw.model.base.helper

import androidx.annotation.StringRes
import com.sw.model.base.redux.Action
import io.reactivex.Completable
import io.reactivex.Maybe

/**
 * @author burkd
 * @since 2019-11-01
 */
interface MessageHelper {

    fun showingGeneralToast(@StringRes message: Int)

    fun showingErrorToast(
        @StringRes messageResId: Int,
        message: String? = null
    )

    fun createOneButtonDialog(
        title: CharSequence = "",
        message: CharSequence
    ): Completable

    fun createTwoButtonDialog(
        title: CharSequence = "",
        message: CharSequence
    ): Maybe<Boolean>

    fun createReTryActionDialog(
        title: CharSequence = "",
        message: CharSequence,
        action: Action
    ): Completable

}