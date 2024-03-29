package com.sw.exfoursquare.base.helper

import android.content.SharedPreferences
import com.sw.common.PREF_ACCESS_TOKEN_KEY
import com.sw.model.base.helper.SharedPreferenceHelper
import java.lang.NullPointerException

/**
 *
 * @author burkd
 * @since 2019-11-11
 */
class SharedPreferenceHelperImpl(
    val sharedPref: SharedPreferences
) : SharedPreferenceHelper {

    override fun saveAccessToken(token: String) {
        saveItem(PREF_ACCESS_TOKEN_KEY, token)
    }

    override fun getAccessToken(): String {
        return getString(PREF_ACCESS_TOKEN_KEY) ?: throw NullPointerException("유효한 인증 정보가 없습니다.")
    }

    override fun removeAccessToken() {
        remove(PREF_ACCESS_TOKEN_KEY)
    }

    private inline fun <reified T> saveItem(key: String, value: T) {
        val editor = sharedPref.edit()
        when (value) {
            is String -> editor.putString(key, value)
            is Long -> editor.putLong(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            else -> return
        }
        editor.apply()
    }

    private fun getString(key: String, defValue: String? = null): String? {
        return sharedPref.getString(key, defValue) ?: defValue
    }

    private fun getLong(key: String, defValue: Long = 0L): Long {
        return sharedPref.getLong(key, defValue)
    }

    private fun remove(key: String) {
        sharedPref.edit().remove(key).apply()
    }

}