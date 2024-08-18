package com.alan.testtask.data.network.api

import com.alan.testtask.domain.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class AuthInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (originalRequest.url.encodedPath in listOf(
                "/api/v1/users/register/",
                "/api/v1/users/send-auth-code/",
                "/api/v1/users/check-auth-code/"
            )
        ) {
            return chain.proceed(originalRequest)
        }

        val accessToken = runBlocking { tokenRepository.obtainAccessToken() }
        val authenticatedRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        val response = chain.proceed(authenticatedRequest)

        if (response.code == 401) {
            val newToken = runBlocking { tokenRepository.obtainAccessToken(forceRefresh = true) }
            val newRequest = originalRequest.newBuilder()
                .removeHeader("Authorization")
                .addHeader("Authorization", "Bearer $newToken")
                .build()
            return chain.proceed(newRequest)
        }

        return response
    }
}
