package com.sw.exfoursquare.repositories.auth

import com.sw.model.domain.auth.FourSquareAuthRepository
import com.sw.model.domain.auth.dto.AccessToken
import io.reactivex.Single

/**
 * @author burkd
 * @since 2019-11-13
 */
class FourSquareAuthRepositoryMockImpl: FourSquareAuthRepository {
    override fun requestAccessToken(authCode: String): Single<AccessToken> {
        return Single.just(AccessToken("asdf"))
    }
}