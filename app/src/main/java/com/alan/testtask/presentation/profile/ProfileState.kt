package com.alan.testtask.presentation.profile

import com.alan.testtask.domain.entity.ProfileData

sealed class ProfileState {
    data object Loading : ProfileState()
    data class Success(val profileData: ProfileData, val zodiacSign: String) : ProfileState()
    data class Error(val message: String) : ProfileState()
}