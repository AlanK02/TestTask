package com.alan.testtask.domain.repository

interface TokenRepository {
    suspend fun obtainAccessToken(forceRefresh: Boolean = false): String?
}