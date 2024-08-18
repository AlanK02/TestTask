package com.alan.testtask.data.repository

import com.alan.testtask.data.mapper.mapAuthResponseDtoToEntity
import com.alan.testtask.data.network.api.ApiService
import com.alan.testtask.data.storage.TokenStorage
import com.alan.testtask.domain.entity.AuthCodeRequest
import com.alan.testtask.domain.entity.AuthInfo
import com.alan.testtask.domain.entity.PhoneRequest
import com.alan.testtask.domain.entity.RegisterRequest
import com.alan.testtask.domain.repository.AuthRepository
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val tokenStorage: TokenStorage
) : AuthRepository {

    override suspend fun sendAuthCode(phone: String): Result<Unit> {
        return try {
            val response = apiService.sendAuthCode(PhoneRequest(phone))
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Throwable(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun checkAuthCode(phone: String, code: String): Result<AuthInfo> {
        return try {
            val response = apiService.checkAuthCode(AuthCodeRequest(phone, code))
            if (response.isSuccessful) {
                response.body()?.let { authResponse ->
                    val authInfo: AuthInfo = authResponse.mapAuthResponseDtoToEntity()
                    authInfo.accessToken?.let { accessToken ->
                        authInfo.refreshToken?.let { refreshToken ->
                            tokenStorage.saveTokens(accessToken, refreshToken)
                        }
                    }
                    Result.success(authInfo)
                } ?: Result.failure(Throwable("Empty response body"))
            } else {
                Result.failure(Throwable(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun registerUser(
        phone: String,
        name: String,
        username: String
    ): Result<AuthInfo> {
        return try {
            val response = apiService.registerUser(RegisterRequest(phone, name, username))
            if (response.isSuccessful) {
                response.body()?.let { authResponse ->
                    val authInfo: AuthInfo = authResponse.mapAuthResponseDtoToEntity()
                    authResponse.accessToken?.let { accessToken ->
                        authResponse.refreshToken?.let { refreshToken ->
                            tokenStorage.saveTokens(accessToken, refreshToken)
                        }
                    }
                    Result.success(authInfo)
                } ?: Result.failure(Throwable("Empty response body"))
            } else {
                Result.failure(Throwable(response.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
