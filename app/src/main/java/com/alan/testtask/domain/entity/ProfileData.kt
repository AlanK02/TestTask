package com.alan.testtask.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileData(
    val name: String?,
    val username: String?,
    val birthday: String?,
    val city: String?,
    val vk: String?,
    val instagram: String?,
    val status: String?,
    val avatar: AvatarData?,
    val phone: String?
) : Parcelable

@Parcelize
data class AvatarData(
    val filename: String,
    val base64: String,
    val url: String? = null
) : Parcelable
