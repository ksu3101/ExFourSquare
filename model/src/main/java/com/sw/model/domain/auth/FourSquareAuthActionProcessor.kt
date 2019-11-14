package com.sw.model.domain.auth

import android.util.Log
import com.example.model.domain.common.MessageAction
import com.example.model.domain.common.ShowingErrorToast
import com.sw.model.base.exts.rx.actionTransformer
import com.sw.model.base.exts.rx.createActionProcessor
import com.sw.model.base.helper.ResourceHelper
import com.sw.model.base.helper.SharedPreferenceHelper
import com.sw.model.base.redux.Action
import com.sw.model.base.redux.ActionProcessor
import com.sw.model.base.redux.Store
import com.sw.model.domain.AppState
import com.sw.model.domain.auth.repos.FourSquareAuthRepository
import com.sw.model.domain.auth.repos.FourSquareUserRepository
import io.reactivex.Observable

/**
 * @author burkd
 * @since 2019-11-13
 */
class FourSquareAuthActionProcessor(
    val authRepo: FourSquareAuthRepository,
    val userRepo: FourSquareUserRepository,
    val prefHelper: SharedPreferenceHelper,
    val resourceHelper: ResourceHelper
) : ActionProcessor<AppState> {

    override fun run(
        action: Observable<Action>,
        store: Store<AppState>
    ): Observable<out Action> {
        return action.compose(actionProcessor)
    }

    private val actionProcessor = createActionProcessor { shared ->
        arrayOf(
            shared.ofType(RequestAccessTokenByAuthCodeAction::class.java).compose(requestAccessToken),
            shared.ofType(RequestActingUserDetailsAction::class.java).compose(requestActingUserInfos)
        )
    }

    private val requestAccessToken = actionTransformer<RequestAccessTokenByAuthCodeAction> {action ->
        authRepo.requestAccessToken(action.authCode)
            .map<Action> {
                prefHelper.saveAccessToken(it.accessToken)
                RequestActingUserDetailsAction
            }
            .onErrorReturn { handleError(it) }
            .toObservable()
    }

    private val requestActingUserInfos = actionTransformer<RequestActingUserDetailsAction> {
        userRepo.retrieveActingUserSelfDetails()
            .map<Action> { ActingUserDetailReceiveSucessAction(it) }
            .onErrorReturn { handleError(it) }
            .toObservable()
    }

    private fun handleError(throwable: Throwable, action: Action? = null): MessageAction {
        return ShowingErrorToast(message = throwable.message)
    }

}