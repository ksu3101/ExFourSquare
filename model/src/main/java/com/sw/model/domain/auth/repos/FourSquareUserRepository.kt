package com.sw.model.domain.auth.repos

import com.sw.model.domain.auth.dto.User
import io.reactivex.Single

/**
 * @author burkd
 * @since 2019-11-14
 */
interface FourSquareUserRepository {

    fun retrieveActingUserSelfDetails(): Single<User>

}