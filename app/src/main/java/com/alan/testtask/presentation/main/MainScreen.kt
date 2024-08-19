package com.alan.testtask.presentation.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alan.testtask.R
import com.alan.testtask.presentation.auth.AuthState
import com.alan.testtask.presentation.auth.AuthViewModel
import com.alan.testtask.presentation.auth.CodeInputScreen
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
        is AuthState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
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
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "${stringResource(R.string.error)}: ${state.message}", color = MaterialTheme.colorScheme.error)
            }
        }
        is AuthState.RegistrationSuccess -> {
            NavScreen(profileViewModel, authViewModel.getPhoneNumber())
        }
    }
}
