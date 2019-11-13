package com.sw.model.domain.auth

import android.util.Log
import com.example.model.domain.common.MessageAction
import com.example.model.domain.common.ShowingErrorToast
import com.sw.model.base.exts.rx.actionTransformer
import com.sw.model.base.exts.rx.createActionProcessor
import com.sw.model.base.helper.FourSquareAuthCodeHelper
import com.sw.model.base.helper.ResourceHelper
import com.sw.model.base.helper.SharedPreferenceHelper
import com.sw.model.base.redux.Action
import com.sw.model.base.redux.ActionProcessor
import com.sw.model.base.redux.Store
import com.sw.model.domain.AppState
import com.sw.model.domain.AppStore
import io.reactivex.Observable

/**
 * @author burkd
 * @since 2019-11-13
 */
class FourSquareAuthActionProcessor(
    val repo: FourSquareAuthRepository,
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
            shared.ofType(RequestAccessTokenByAuthCodeAction::class.java).compose(requestAccessToken)
        )
    }

    private val requestAccessToken = actionTransformer<RequestAccessTokenByAuthCodeAction> {action ->
        repo.requestAccessToken(action.authCode)
            .map<Action> {
                val accessToken = it.accessToken
                Log.d("FSQ_AP","//// FSQ_TOKEN : ${accessToken} ")
                prefHelper.saveAccessToken(accessToken)
                AccessTokenReceiveSuccessfulAction(accessToken)
            }
            .onErrorReturn { handleError(it) }
            .toObservable()
    }

    private fun handleError(throwable: Throwable, action: Action? = null): MessageAction {
        return ShowingErrorToast(message = throwable.message)
    }

}