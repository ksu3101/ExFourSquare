package com.sw.model.domain.auth

import com.sw.model.base.redux.Action
import com.sw.model.domain.auth.dto.User

/**
 * @author burkd
 * @since 2019-11-13
 */

sealed class FourSquareAuthAction : Action

object RequestAuthCodeAction : FourSquareAuthAction()

data class RequestAccessTokenByAuthCodeAction(
    val authCode: String
) : FourSquareAuthAction()

object RequestActingUserDetailsAction : FourSquareAuthAction()

data class ActingUserDetailReceiveSucessAction(
    val user: User
) : FourSquareAuthAction()