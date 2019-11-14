package com.sw.common.response

import java.lang.RuntimeException

/**
 * @author burkd
 * @since 2019-11-14
 */

data class FsqResponseException(
    val errorCode: Int,
    val requestId: String,
    val errorType: String,
    val errorDetail: String
): RuntimeException()