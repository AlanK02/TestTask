package com.alan.testtask.data.network.api

import com.alan.testtask.data.network.dto.AuthResponseDto
import com.alan.testtask.domain.entity.RefreshTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshApiService {

    @POST("/api/v1/users/refresh-token/")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<AuthResponseDto>
}
