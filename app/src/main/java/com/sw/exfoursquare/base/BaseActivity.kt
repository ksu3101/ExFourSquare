package com.sw.exfoursquare.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.sw.common.KOIN_CURRENT_ACTIVITY
import com.example.model.domain.common.*
import com.sw.common.INTENT_REQUEST_AUTH_CODE
import com.sw.model.base.helper.MessageHelper
import com.sw.model.domain.AppStore
import com.sw.model.domain.auth.RequestAccessTokenByAuthCodeAction
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import com.sw.common.INTENT_RESULT_CODE
import com.sw.common.INTENT_RESULT_DENIED
import com.sw.common.INTENT_RESULT_ERROR
import com.sw.common.INTENT_RESULT_ERROR_MESSAGE
import java.lang.NullPointerException


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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("BaseActivity", "// onActivityResult : requestCode = $requestCode")

        when (requestCode) {
            INTENT_REQUEST_AUTH_CODE -> {
                handleResultOfAuthCodeRequest(resultCode, data)
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleResultOfAuthCodeRequest(resultCode: Int, data: Intent?) {
        if (data == null) throw NullPointerException("AuthCode Result data is Null.")
        when (resultCode) {
            Activity.RESULT_OK -> {
                val isDenied = data.getBooleanExtra(INTENT_RESULT_DENIED, false)
                val authCode = data.getStringExtra(INTENT_RESULT_CODE)

                Log.d("BaseActivity", "// onActivityResult : authCode = $authCode")
                Log.d("BaseActivity", "// onActivityResult : isDenied = $isDenied")

                if (isDenied) {
                    val errorCode = data.getStringExtra(INTENT_RESULT_ERROR)
                    val errorMessage = data.getStringExtra(INTENT_RESULT_ERROR_MESSAGE)
                    if (errorCode.isEmpty()) {
                        stateStore.dispatch(RequestAccessTokenByAuthCodeAction(authCode))
                    } else {
                        stateStore.dispatch(ShowingErrorToast(message = errorMessage))
                    }
                    Log.d("BaseActivity", "// onActivityResult : isDenied = $errorCode")
                    Log.d("BaseActivity", "// onActivityResult : isDenied = $errorMessage")
                } else {
                    stateStore.dispatch(RequestAccessTokenByAuthCodeAction(authCode))
                }
            }
            else -> {
                stateStore.dispatch(ShowingErrorToast(com.sw.exfoursquare.R.string.c_error_canceled_authcode))
            }
        }
    }

}