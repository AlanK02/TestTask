package com.alan.testtask.data.network.api

import com.alan.testtask.data.network.dto.AuthCodeDto
import com.alan.testtask.data.network.dto.AuthResponseDto
import com.alan.testtask.data.network.dto.AvatarsUpdatedDto
import com.alan.testtask.data.network.dto.ProfileResponseDto
import com.alan.testtask.domain.entity.AuthCodeRequest
import com.alan.testtask.domain.entity.PhoneRequest
import com.alan.testtask.domain.entity.ProfileRequest
import com.alan.testtask.domain.entity.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {

    @POST("/api/v1/users/send-auth-code/")
    suspend fun sendAuthCode(@Body request: PhoneRequest): Response<AuthCodeDto>

    @POST("/api/v1/users/check-auth-code/")
    suspend fun checkAuthCode(@Body request: AuthCodeRequest): Response<AuthResponseDto>

    @POST("/api/v1/users/register/")
    suspend fun registerUser(@Body request: RegisterRequest): Response<AuthResponseDto>

    @GET("/api/v1/users/me/")
    suspend fun getProfile(): Response<ProfileResponseDto>

    @PUT("/api/v1/users/me/")
    suspend fun updateProfile(@Body profileRequest: ProfileRequest): Response<AvatarsUpdatedDto>
}
