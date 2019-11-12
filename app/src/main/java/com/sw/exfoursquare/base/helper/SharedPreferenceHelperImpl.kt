package com.sw.exfoursquare.base.helper

import android.content.SharedPreferences
import com.sw.model.base.helper.SharedPreferenceHelper

/**
 *
 * @author burkd
 * @since 2019-11-11
 */
class SharedPreferenceHelperImpl(
    val sharedPref: SharedPreferences
) : SharedPreferenceHelper {

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