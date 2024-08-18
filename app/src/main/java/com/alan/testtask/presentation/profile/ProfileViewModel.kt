package com.alan.testtask.presentation.profile


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alan.testtask.domain.entity.ProfileData
import com.alan.testtask.domain.useCase.GetProfileUseCase
import com.alan.testtask.domain.useCase.UpdateProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val profileState: StateFlow<ProfileState> get() = _profileState

    fun loadProfile(phoneNumber: String) {
        viewModelScope.launch {
            _profileState.value = ProfileState.Loading
            val result = getProfileUseCase(phoneNumber)
            result.onSuccess {
                _profileState.value = ProfileState.Success(it)
            }.onFailure {
                _profileState.value = ProfileState.Error(it.message ?: "Unknown error")
            }
        }
    }

    fun updateProfile(profileData: ProfileData) {
        viewModelScope.launch {
            _profileState.value = ProfileState.Loading
            val result = updateProfileUseCase(profileData)
            result.onSuccess {
                _profileState.value = ProfileState.Success(it)
            }.onFailure {
                _profileState.value = ProfileState.Error(it.message ?: "Unknown error")
            }
        }
    }

}
