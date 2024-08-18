package com.alan.testtask.data.storage

import com.alan.testtask.data.network.api.RefreshApiService
import com.alan.testtask.di.ApplicationScope
import com.alan.testtask.domain.entity.RefreshTokenRequest
import com.alan.testtask.domain.repository.TokenRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@ApplicationScope
class TokenRepositoryImpl @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val refreshApiService: RefreshApiService
) : TokenRepository {

    private val mutex = Mutex()

    override suspend fun obtainAccessToken(forceRefresh: Boolean): String? {
        return mutex.withLock {
            val accessToken = tokenStorage.getAccessToken()
            val refreshToken = tokenStorage.getRefreshToken()

            if (accessToken.isNullOrEmpty() || forceRefresh) {
                if (refreshToken.isNullOrEmpty()) {
                    return null
                }

                val response = refreshApiService.refreshToken(RefreshTokenRequest(refreshToken))
                if (response.isSuccessful) {
                    response.body()?.let {
                        it.accessToken?.let { it1 ->
                            it.refreshToken?.let { it2 ->
                                tokenStorage.saveTokens(
                                    it1,
                                    it2
                                )
                            }
                        }
                        return@withLock it.accessToken
                    }
                }

                return null
            }

            return@withLock accessToken
        }
    }
}