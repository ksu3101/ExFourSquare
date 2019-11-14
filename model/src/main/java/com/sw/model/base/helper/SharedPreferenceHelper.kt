package com.sw.model.base.helper

/**
 * @author burkd
 * @since 2019-11-11
 */
interface SharedPreferenceHelper {

    fun saveAccessToken(token: String)

    fun getAccessToken(): String

    fun removeAccessToken()

}