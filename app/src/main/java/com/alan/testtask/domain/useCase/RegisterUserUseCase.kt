package com.alan.testtask.domain.useCase

import com.alan.testtask.domain.entity.AuthInfo
import com.alan.testtask.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(phone: String, name: String, username: String): Result<AuthInfo> =
        authRepository.registerUser(phone, name, username)
}