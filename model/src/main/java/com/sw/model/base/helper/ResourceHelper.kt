package com.sw.model.base.helper

import androidx.annotation.StringRes

/**
 * @author burkd
 * @since 2019-11-05
 */
interface ResourceHelper {

    fun getString(@StringRes stringResId: Int): String

}