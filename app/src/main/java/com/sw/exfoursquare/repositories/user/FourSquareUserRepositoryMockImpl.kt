package com.sw.exfoursquare.repositories.user

import com.sw.model.domain.auth.dto.CheckIns
import com.sw.model.domain.auth.dto.Contact
import com.sw.model.domain.auth.dto.DUMMY_USER
import com.sw.model.domain.auth.dto.Friends
import com.sw.model.domain.auth.dto.Lists
import com.sw.model.domain.auth.dto.MayorShips
import com.sw.model.domain.auth.dto.Photos
import com.sw.model.domain.auth.dto.Requests
import com.sw.model.domain.auth.dto.Tips
import com.sw.model.domain.auth.dto.User
import com.sw.model.domain.auth.repos.FourSquareUserRepository
import com.sw.model.domain.common.dto.Photo
import io.reactivex.Single

/**
 * @author burkd
 * @since 2019-11-14
 */
class FourSquareUserRepositoryMockImpl : FourSquareUserRepository {

    override fun retrieveActingUserSelfDetails(): Single<User> {
        return Single.just(DUMMY_USER)
    }

}