package com.sw.model.domain.auth

import com.sw.model.base.redux.Action

/**
 * @author burkd
 * @since 2019-11-13
 */

sealed class FourSquareAuthAction : Action

object RequestAuthCodeAction : FourSquareAuthAction()

data class RequestAccessTokenByAuthCodeAction(
    val authCode: String
): FourSquareAuthAction()

data class AccessTokenReceiveSuccessfulAction(
    val accessToken: String
): FourSquareAuthAction()