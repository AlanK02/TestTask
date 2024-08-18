package com.alan.testtask.presentation.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alan.testtask.presentation.auth.AuthState
import com.alan.testtask.presentation.auth.AuthViewModel
import com.alan.testtask.presentation.auth.CodeInputScreen
import com.alan.testtask.presentation.auth.ErrorScreen
import com.alan.testtask.presentation.auth.LoadingScreen
import com.alan.testtask.presentation.auth.PhoneInputScreen
import com.alan.testtask.presentation.auth.RegistrationScreen
import com.alan.testtask.presentation.getApplicationComponent
import com.alan.testtask.presentation.profile.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    val component = getApplicationComponent()
    val authViewModel: AuthViewModel = viewModel(factory = component.getViewModelFactory())
    val profileViewModel: ProfileViewModel = viewModel(factory = component.getViewModelFactory())

    val authState = authViewModel.authState.collectAsState()

    when (val state = authState.value) {
        is AuthState.Idle -> PhoneInputScreen(authViewModel)
        is AuthState.Loading -> LoadingScreen()
        is AuthState.AuthSuccess -> {
            NavScreen(profileViewModel, authViewModel.getPhoneNumber())
        }

        is AuthState.AuthCodeSent -> {
            CodeInputScreen(authViewModel, state.phone)
        }

        is AuthState.UserNotRegistered -> {
            RegistrationScreen(authViewModel, state.phone)
        }

        is AuthState.Error -> {
            ErrorScreen(state.message)
        }

        is AuthState.RegistrationSuccess -> {
            NavScreen(profileViewModel, authViewModel.getPhoneNumber())
        }
    }
}
