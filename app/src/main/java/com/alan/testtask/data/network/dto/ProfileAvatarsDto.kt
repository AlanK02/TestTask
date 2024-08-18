package com.alan.testtask.data.network.dto

import com.google.gson.annotations.SerializedName

data class AvatarsUpdatedDto(
    @SerializedName("avatars") val avatars: AvatarsDto
)