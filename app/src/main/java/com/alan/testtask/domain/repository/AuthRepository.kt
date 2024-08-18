package com.alan.testtask.domain.repository

import com.alan.testtask.domain.entity.AuthInfo


interface AuthRepository {
    suspend fun sendAuthCode(phone: String): Result<Unit>
    suspend fun checkAuthCode(phone: String, code: String): Result<AuthInfo>
    suspend fun registerUser(phone: String, name: String, username: String): Result<AuthInfo>
}
