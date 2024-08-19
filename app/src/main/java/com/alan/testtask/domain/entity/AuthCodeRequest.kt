package com.alan.testtask.domain.entity

import com.google.gson.annotations.SerializedName

data class AuthCodeRequest(
    @SerializedName("phone") val phone: String,
    @SerializedName("code") val code: String
)