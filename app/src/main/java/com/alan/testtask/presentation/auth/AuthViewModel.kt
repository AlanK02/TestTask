package com.alan.testtask.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alan.testtask.domain.useCase.CheckAuthCodeUseCase
import com.alan.testtask.domain.useCase.RegisterUserUseCase
import com.alan.testtask.domain.useCase.SendAuthCodeUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val sendAuthCodeUseCase: SendAuthCodeUseCase,
    private val checkAuthCodeUseCase: CheckAuthCodeUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> get() = _authState

    private var phoneNumber: String = ""

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent: SharedFlow<String> get() = _toastEvent

    fun sendAuthCode(phone: String) {
        viewModelScope.launch {
            phoneNumber = phone
            _authState.value = AuthState.Loading
            val result = sendAuthCodeUseCase(phone)
            _authState.value = if (result.isSuccess) {
                AuthState.AuthCodeSent(phone)
            } else {
                val errorMessage = result.exceptionOrNull()?.message ?: "Unknown error"
                _toastEvent.emit("Error: $errorMessage")
                AuthState.Error(errorMessage)
            }
        }
    }

    fun checkAuthCode(phone: String, code: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = checkAuthCodeUseCase(phone, code)
            result.getOrNull()?.let { authInfo ->
                _authState.value = if (authInfo.isUserExists) {
                    AuthState.AuthSuccess(authInfo)
                } else {
                    AuthState.UserNotRegistered(phone, authInfo)
                }
            } ?: run {
                val errorMessage = result.exceptionOrNull()?.message ?: "Unknown error"
                _toastEvent.emit("Error: $errorMessage")
                _authState.value = AuthState.Error(errorMessage)
            }
        }
    }

    fun registerUser(name: String, username: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = registerUserUseCase(phoneNumber, name, username)
            result.getOrNull()?.let { authInfo ->
                _authState.value = AuthState.RegistrationSuccess(authInfo)
                _toastEvent.emit("Registration successful!")
            } ?: run {
                val errorMessage = result.exceptionOrNull()?.message ?: "Unknown error"
                _toastEvent.emit("Error: $errorMessage")
                _authState.value = AuthState.Error(errorMessage)
            }
        }
    }

    fun getPhoneNumber(): String {
        return phoneNumber
    }
}


