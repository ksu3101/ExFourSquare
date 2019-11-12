package com.sw.exfoursquare.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.KOIN_CURRENT_ACTIVITY
import com.example.model.domain.common.*
import com.sw.model.base.helper.MessageHelper
import com.sw.model.domain.AppStore
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject

/**
 * @author burkd
 * @since 2019-11-01
 */
abstract class BaseActivity : AppCompatActivity() {

    protected val messageHelper: MessageHelper by inject()
    protected val stateStore: AppStore by inject()

    @LayoutRes
    abstract fun getLayoutId(): Int

    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getKoin().setProperty(KOIN_CURRENT_ACTIVITY, this)
        setContentView(getLayoutId())
    }

    override fun onResume() {
        super.onResume()
        handleAppState()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::compositeDisposable.isInitialized) {
            compositeDisposable.clear()
        }
    }

    private fun handleAppState() {
        checkCompositeDisposableInstanceAndCreator()
        compositeDisposable.add(
            stateStore.getStateListener()
                .flatMap { Observable.fromIterable(it.states.values) }
                .ofType(MessageState::class.java)
                .doOnNext {
                    stateStore.dispatch(HandledMessageAction)
                }
                .subscribe {
                    handleMessageState(it)
                }
        )
    }

    private fun handleMessageState(msgState: MessageState) {
        when (msgState) {
            is ShowingGeneralToastState -> {
                messageHelper.showingGeneralToast(msgState.messageResId)
            }
            is ShowingErrorToastState -> {
                messageHelper.showingErrorToast(msgState.messageResId, msgState.message)
            }
            is ShowingOneButtonDialogState -> {
                checkCompositeDisposableInstanceAndCreator()
                compositeDisposable.add( // todo : add error dialog flag and resources
                    messageHelper.createOneButtonDialog(
                        getString(msgState.title),
                        getString(msgState.messageResId)
                    )
                        .subscribe()
                )
            }
            is ShowingReTryActionDialogState -> {
                checkCompositeDisposableInstanceAndCreator()
                compositeDisposable.add(
                    messageHelper.createReTryActionDialog(
                        getString(msgState.title),
                        getString(msgState.messageResId),
                        msgState.retryAction
                    ).subscribe()
                )
            }
        }
    }

    private fun checkCompositeDisposableInstanceAndCreator() {
        if (!::compositeDisposable.isInitialized) {
            compositeDisposable = CompositeDisposable()
        }
    }

}