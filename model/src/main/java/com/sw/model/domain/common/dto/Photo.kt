package com.sw.model.domain.common.dto

/**
 * @author burkd
 * @since 2019-11-14
 */
data class Photo(
    val prefix: String,
    val suffix: String
) {
    fun getFullString() = "$prefix$suffix"
}