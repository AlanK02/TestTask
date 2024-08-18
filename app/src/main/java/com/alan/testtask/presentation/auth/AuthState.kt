package com.alan.testtask.presentation.auth

import com.alan.testtask.domain.entity.AuthInfo

sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data class AuthCodeSent(val phone: String) : AuthState()
    data class AuthSuccess(val authInfo: AuthInfo) : AuthState()
    data class UserNotRegistered(val phone: String, val authInfo: AuthInfo) : AuthState()
    data class RegistrationSuccess(val authInfo: AuthInfo) : AuthState()
    data class Error(val message: String) : AuthState()
}