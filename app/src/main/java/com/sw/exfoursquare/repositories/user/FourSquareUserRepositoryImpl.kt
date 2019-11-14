package com.sw.exfoursquare.repositories.user

import com.sw.common.REQ_VERSIONING
import com.sw.model.base.exts.rx.loadWithProgressDialog
import com.sw.model.base.helper.SharedPreferenceHelper
import com.sw.model.domain.auth.dto.User
import com.sw.model.domain.auth.repos.FourSquareUserRepository
import io.reactivex.Single

/**
 *
 * @author burkd
 * @since 2019-11-14
 */
class FourSquareUserRepositoryImpl(
    private val pref: SharedPreferenceHelper,
    private val api: FourSquareUsersApi
) : FourSquareUserRepository {

    override fun retrieveActingUserSelfDetails(): Single<User> {
        return api.retrieveActingUserSelfDetails(
            pref.getAccessToken(),
            REQ_VERSIONING
        ).map {
            if (it.isAvailableResponse()) {
                it.response!!
            }
        }.loadWithProgressDialog()
    }

}