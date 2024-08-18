package com.alan.testtask.domain.repository

import com.alan.testtask.domain.entity.ProfileData

interface ProfileRepository {
    suspend fun getProfile(phone: String): Result<ProfileData>
    suspend fun updateProfile(profileData: ProfileData): Result<ProfileData>
}

