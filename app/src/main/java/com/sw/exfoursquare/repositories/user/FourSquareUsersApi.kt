package com.sw.exfoursquare.repositories.user

import com.sw.common.response.FsqResponse
import com.sw.model.domain.auth.dto.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author burkd
 * @since 2019-11-14
 */
interface FourSquareUsersApi {

    @GET("self")
    fun retrieveActingUserSelfDetails(
        @Query("oauth_token") accessToken: String,
        @Query("v") version: String
    ): Single<FsqResponse<User>>

}