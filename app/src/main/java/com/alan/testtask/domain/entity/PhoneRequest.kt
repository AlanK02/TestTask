package com.alan.testtask.domain.entity

import com.google.gson.annotations.SerializedName

data class PhoneRequest(
    @SerializedName("phone") val phone: String
)