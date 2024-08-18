package com.alan.testtask.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alan.testtask.R

@Composable
fun RegistrationScreen(viewModel: AuthViewModel, phone: String) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    val usernameRegex = "^[A-Za-z0-9-_]{1,20}$".toRegex()
    val context = LocalContext.current

    LaunchedEffect(viewModel.toastEvent) {
        viewModel.toastEvent.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = phone, style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.name)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = username,
            onValueChange = {
                if (usernameRegex.matches(it)) {
                    username = it
                }
            },
            label = { Text(stringResource(R.string.username)) },
            placeholder = { Text(stringResource(R.string.enter_a_valid_username)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.registerUser(name, username) }) {
            Text(stringResource(R.string.register))
        }
    }
}
