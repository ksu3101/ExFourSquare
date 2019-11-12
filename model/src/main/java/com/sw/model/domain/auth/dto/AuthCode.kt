package com.sw.model.domain.auth.dto

/**
 * @author burkd
 * @since 2019-11-13
 */
data class AuthCode(
    val code: String,
    val exception: Exception
)