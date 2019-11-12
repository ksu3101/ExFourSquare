package com.sw.exfoursquare.base.helper

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.sw.common.extensions.isNotNullOrEmpty
import com.sw.exfoursquare.R
import com.sw.model.base.helper.MessageHelper
import com.sw.model.base.redux.Action
import com.sw.model.domain.AppStore
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.disposables.Disposables
import org.koin.core.KoinComponent

/**
 * @author burkd
 * @since 2019-11-01
 */
class MessageHelperImpl(
    val context: Context
) : MessageHelper, KoinComponent {

    override fun showingGeneralToast(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showingErrorToast(messageResId: Int, message: String?) {
        // todo : need inflating background color
        val msg = if (messageResId == 0 && message.isNullOrEmpty()) {
            throw IllegalArgumentException("message parameter has not available.")
        } else {
            if (message.isNotNullOrEmpty()) message
            else context.getString(messageResId)
        }
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    override fun createOneButtonDialog(
        title: CharSequence,
        message: CharSequence
    ): Completable =
        createDialogSource(
            title,
            message
        ).flatMapCompletable { Completable.complete() }

    override fun createTwoButtonDialog(
        title: CharSequence,
        message: CharSequence
    ): Maybe<Boolean> =
        createDialogSource(
            title,
            message
        )

    override fun createReTryActionDialog(
        title: CharSequence,
        message: CharSequence, action: Action
    ): Completable =
        createDialogSource(
            title,
            message,
            positiveReceiver = {
                getKoin().get<AppStore>().dispatch(action)
            }
        ).flatMapCompletable { Completable.complete() }

    private fun createDialogSource(
        title: CharSequence,
        message: CharSequence,
        @StringRes positiveBtnResId: Int = R.string.c_yes,
        positiveReceiver: (() -> Unit)? = null,
        @StringRes negativeBtnResId: Int = R.string.c_no,
        negativeReceiver: (() -> Unit)? = null,
        isCancelable: Boolean = true,
        isTwoBtn: Boolean = false
    ): Maybe<Boolean> =
        Maybe.create { emitter ->
            val builder = AlertDialog.Builder(context)
            if (title.isNotEmpty()) builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(context.getString(positiveBtnResId)) { _, _ ->
                positiveReceiver.let { it }
                emitter.onSuccess(true)
                emitter.onComplete()
            }
            if (isTwoBtn) {
                builder.setNegativeButton(context.getString(negativeBtnResId)) { _, _ ->
                    negativeReceiver.let { it }
                    emitter.onSuccess(false)
                    emitter.onComplete()
                }
            }
            if (isCancelable) {
                builder.setOnCancelListener {
                    if (isTwoBtn) emitter.onSuccess(false)
                    emitter.onComplete()
                }
            }
            val dialog = builder.create()
            emitter.setDisposable(Disposables.fromRunnable {
                dialog.dismiss()
            })
            dialog.show()
        }

}