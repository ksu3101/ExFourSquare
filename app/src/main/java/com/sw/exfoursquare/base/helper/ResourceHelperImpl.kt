package com.sw.exfoursquare.base.helper

import android.app.Application
import com.sw.model.base.helper.ResourceHelper

/**
 * @author burkd
 * @since 2019-11-05
 */
class ResourceHelperImpl(
        private val context: Application
): ResourceHelper {
    override fun getString(stringResId: Int): String = context.getString(stringResId)
}