package com.sw.model.domain.auth.repos

import com.sw.model.domain.auth.dto.AccessToken
import io.reactivex.Single

/**
 * @author burkd
 * @since 2019-11-13
 */
interface FourSquareAuthRepository {

    fun requestAccessToken(authCode: String): Single<AccessToken>

}