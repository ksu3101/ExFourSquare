package com.sw.model.domain.common.dto

/**
 * @author burkd
 * @since 2019-11-14
 */
typealias Size = Pair<Int, Int> // width x height

data class Photo(
    val prefix: String,
    val suffix: String
) {
    fun getImageUrlWithSize(size: Size = Pair(300, 300)) =
        "$prefix${size.first}x${size.second}${suffix}"
}

