package com.sw.model.domain.auth

import com.sw.model.base.redux.State
import com.sw.model.domain.auth.dto.User

/**
 * @author burkd
 * @since 2019-11-13
 */

sealed class FourSquareAuthState : State

object InitializedState : FourSquareAuthState()

object ReceiveActingUserDetailFailed : FourSquareAuthState()

data class ReceivedActingUserDetails(
    val user: User
) : FourSquareAuthState()