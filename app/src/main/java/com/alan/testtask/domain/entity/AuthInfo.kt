package com.alan.testtask.domain.entity

data class AuthInfo(
    val refreshToken: String?,
    val accessToken: String?,
    val userId: String?,
    val isUserExists: Boolean = false
)