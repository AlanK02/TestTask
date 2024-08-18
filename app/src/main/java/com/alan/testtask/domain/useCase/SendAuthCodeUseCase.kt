package com.alan.testtask.domain.useCase

import com.alan.testtask.domain.repository.AuthRepository
import javax.inject.Inject

class SendAuthCodeUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(phone: String): Result<Unit> = authRepository.sendAuthCode(phone)
}