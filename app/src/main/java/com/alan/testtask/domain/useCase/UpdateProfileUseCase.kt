package com.alan.testtask.domain.useCase

import com.alan.testtask.domain.entity.ProfileData
import com.alan.testtask.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(profileData: ProfileData): Result<ProfileData> {
        return repository.updateProfile(profileData)
    }
}
