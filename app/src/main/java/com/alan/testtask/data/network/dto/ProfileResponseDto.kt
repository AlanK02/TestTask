package com.alan.testtask.data.network.dto

import com.google.gson.annotations.SerializedName

data class ProfileResponseDto(
    @SerializedName("profile_data")
    val profileData: ProfileDataDto?
)

data class ProfileDataDto(
    val name: String?,
    val username: String?,
    val birthday: String?,
    val city: String?,
    val vk: String?,
    val instagram: String?,
    val status: String?,
    val avatar: String?,
    val id: Int?,
    val last: String?,
    val online: Boolean?,
    val created: String?,
    val phone: String?,
    @SerializedName("completed_task")
    val completedTask: Int?,
    val avatars: AvatarsDto?
)

data class AvatarsDto(
    val avatar: String?,
    @SerializedName("bigAvatar")
    val bigAvatar: String?,
    @SerializedName("miniAvatar")
    val miniAvatar: String?
)