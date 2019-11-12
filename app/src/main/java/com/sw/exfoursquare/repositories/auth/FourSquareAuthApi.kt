package com.sw.exfoursquare.repositories.auth

import com.sw.model.domain.auth.dto.AccessToken
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author burkd
 * @since 2019-11-13
 */
interface FourSquareAuthApi {

    @GET("/access_token")
    fun requestAccessToken(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String
    ): Single<AccessToken>

}