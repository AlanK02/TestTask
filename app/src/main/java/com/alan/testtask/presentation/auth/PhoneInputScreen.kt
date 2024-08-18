package com.alan.testtask.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.alan.testtask.R
import com.hbb20.CountryCodePicker


@Composable
fun PhoneInputScreen(viewModel: AuthViewModel) {
    var phoneNumber by remember { mutableStateOf("") }
    var selectedCountryCode by remember { mutableStateOf("") }

    val context = LocalContext.current
    val locale = remember {
        context.resources.configuration.locales[0]
    }

    val defaultCountryCode = remember {
        val countryPicker = CountryCodePicker(context)
        countryPicker.setDefaultCountryUsingNameCode(locale.country)
        countryPicker.resetToDefaultCountry()
        countryPicker.selectedCountryCodeWithPlus
    }

    if (selectedCountryCode.isEmpty()) {
        selectedCountryCode = defaultCountryCode
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AndroidView(
                factory = { context ->
                    CountryCodePicker(context).apply {
                        setOnCountryChangeListener {
                            selectedCountryCode = selectedCountryCodeWithPlus
                        }
                        setDefaultCountryUsingNameCode(locale.country)
                        resetToDefaultCountry()
                    }
                },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text(stringResource(R.string.phone_number)) },
                placeholder = { Text(stringResource(R.string.enter_your_phone_number)) },
                modifier = Modifier.weight(2f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.sendAuthCode("$selectedCountryCode$phoneNumber") }) {
            Text(stringResource(R.string.send_code))
        }
    }
}


