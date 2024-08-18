package com.alan.testtask.domain.useCase

import com.alan.testtask.domain.entity.ProfileData
import com.alan.testtask.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(phone: String): Result<ProfileData> {
        return profileRepository.getProfile(phone)
    }
}
