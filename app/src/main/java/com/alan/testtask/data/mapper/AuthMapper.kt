package com.alan.testtask.data.mapper

import com.alan.testtask.data.network.dto.AuthResponseDto
import com.alan.testtask.domain.entity.AuthInfo

fun AuthResponseDto.mapAuthResponseDtoToEntity(): AuthInfo = AuthInfo(
    refreshToken = refreshToken,
    accessToken = accessToken,
    userId = userId,
    isUserExists = isUserExists
)
