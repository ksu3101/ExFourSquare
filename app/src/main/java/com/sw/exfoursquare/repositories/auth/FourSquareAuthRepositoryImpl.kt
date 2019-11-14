package com.sw.exfoursquare.repositories.auth

import com.sw.exfoursquare.base.helper.FourSquareAuthCodeHelperImpl
import com.sw.model.base.exts.rx.loadWithProgressDialog
import com.sw.model.domain.auth.repos.FourSquareAuthRepository
import com.sw.model.domain.auth.dto.AccessToken
import io.reactivex.Single

/**
 * @author burkd
 * @since 2019-11-13
 */
class FourSquareAuthRepositoryImpl(
    private val api: FourSquareAuthApi
) : FourSquareAuthRepository {

    override fun requestAccessToken(authCode: String): Single<AccessToken> {
        return api.requestAccessToken(
            FourSquareAuthCodeHelperImpl.CLIENT_ID,
            "EFZADCIFXY2O3YEXX3LQLAW1Q34DSDIGZIDPTI2ZGG0ONAYX",
            "authorization_code",
            authCode
        ).loadWithProgressDialog()
    }

}