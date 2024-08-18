package com.alan.testtask.data.network.dto

import com.google.gson.annotations.SerializedName

data class AuthCodeDto(
    @SerializedName("is_success") val isSuccess: Boolean
)