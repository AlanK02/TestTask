package com.alan.testtask.data.mapper

import com.alan.testtask.data.local.model.ProfileEntity
import com.alan.testtask.data.network.api.ApiFactory.Companion.BASE_URL
import com.alan.testtask.data.network.dto.AvatarsDto
import com.alan.testtask.data.network.dto.ProfileResponseDto
import com.alan.testtask.domain.entity.AvatarData
import com.alan.testtask.domain.entity.AvatarRequest
import com.alan.testtask.domain.entity.ProfileData
import com.alan.testtask.domain.entity.ProfileRequest

fun ProfileResponseDto.mapResponseToDomain(): ProfileData {
    return ProfileData(
        name = profileData?.name ?: "",
        username = profileData?.username ?: "",
        birthday = profileData?.birthday ?: "",
        city = profileData?.city ?: "",
        vk = profileData?.vk ?: "",
        instagram = profileData?.instagram ?: "",
        status = profileData?.status ?: "",
        avatar = profileData?.avatars?.mapAvatarsToDomain(),
        phone = profileData?.phone ?: ""
    )
}

fun AvatarsDto.mapAvatarsToDomain(): AvatarData? {
    return bigAvatar?.let {
        AvatarData(
            filename = "$BASE_URL/$it",
            base64 = ""
        )
    }
}

fun ProfileData.mapDomainToRequest(): ProfileRequest {
    return ProfileRequest(
        name = this.name,
        username = this.username,
        birthday = this.birthday,
        city = this.city,
        vk = this.vk,
        instagram = this.instagram,
        status = this.status,
        avatar = this.avatar?.let {
            AvatarRequest(
                filename = it.filename,
                base64 = it.base64
            )
        }
    )
}


fun AvatarData.mapDomainToAvatarRequest(): AvatarRequest {
    return AvatarRequest(
        filename = this.filename,
        base64 = this.base64
    )
}

fun ProfileEntity.mapToDomain(): ProfileData {
    return ProfileData(
        name = this.name,
        username = this.username,
        birthday = this.birthday,
        city = this.city,
        vk = this.vk,
        instagram = this.instagram,
        status = this.status,
        avatar = this.avatarFilename?.let {
            AvatarData(
                filename = it,
                base64 = "",
                url = "$BASE_URL/$it"
            )
        },
        phone = this.phone
    )
}


fun ProfileResponseDto.mapResponseToEntity(phone: String): ProfileEntity {
    return ProfileEntity(
        name = this.profileData?.name ?: "",
        username = this.profileData?.username ?: "",
        birthday = this.profileData?.birthday ?: "",
        city = this.profileData?.city ?: "",
        vk = this.profileData?.vk ?: "",
        instagram = this.profileData?.instagram ?: "",
        status = this.profileData?.status ?: "",
        avatarFilename = this.profileData?.avatars?.bigAvatar ?: "",
        phone = phone
    )
}


fun ProfileData.mapToEntity(): ProfileEntity? {
    return this.name?.let {
        this.username?.let { it1 ->
            this.phone?.let { it2 ->
                ProfileEntity(
                    name = it,
                    username = it1,
                    birthday = this.birthday,
                    city = this.city,
                    vk = this.vk,
                    instagram = this.instagram,
                    status = this.status,
                    avatarFilename = this.avatar?.filename,
                    phone = it2
                )
            }
        }
    }
}
