package com.alan.testtask.domain.useCase

import com.alan.testtask.domain.entity.AuthInfo
import com.alan.testtask.domain.repository.AuthRepository
import javax.inject.Inject

class CheckAuthCodeUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(phone: String, code: String): Result<AuthInfo> =
        authRepository.checkAuthCode(phone, code)

}