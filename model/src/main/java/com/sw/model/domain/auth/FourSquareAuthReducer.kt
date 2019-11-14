package com.sw.model.domain.auth

import com.sw.model.base.redux.Action
import com.sw.model.base.redux.Reducer

/**
 * @author burkd
 * @since 2019-11-13
 */
class FourSquareAuthReducer: Reducer<FourSquareAuthState> {
    override val initializedState = InitializedState

    override fun reduce(oldState: FourSquareAuthState, resultAction: Action): FourSquareAuthState {
        when(resultAction) {
            is InitlaizeAction ->
                return InitializedState

            is ActingUserDetailReceiveSucessAction ->
                return ReceivedActingUserDetails(resultAction.user)
        }
        return oldState
    }
}