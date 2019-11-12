package com.example.model.domain.auth.dto

/**
 * @author burkd
 * @since 2019-11-11
 */
data class AccessToken(
    val accessToken: String,
    val userId: Int
)