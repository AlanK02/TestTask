package com.alan.testtask.domain.entity

import com.google.gson.annotations.SerializedName

data class ProfileRequest(
    val name: String?,
    val username: String?,
    val birthday: String?,
    val city: String?,
    val vk: String?,
    val instagram: String?,
    val status: String?,
    val avatar: AvatarRequest?
)

data class AvatarRequest(
    val filename: String?,
    @SerializedName("base_64")
    val base64: String?
)