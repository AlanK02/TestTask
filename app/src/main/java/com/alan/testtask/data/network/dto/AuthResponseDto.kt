package com.alan.testtask.data.network.dto

import com.google.gson.annotations.SerializedName

data class AuthResponseDto(
    @SerializedName("refresh_token") val refreshToken: String?,
    @SerializedName("access_token") val accessToken: String?,
    @SerializedName("user_id") val userId: String?,
    @SerializedName("is_user_exists") val isUserExists: Boolean = false
)
