package com.alan.testtask.presentation.profile

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.alan.testtask.R
import com.alan.testtask.data.util.getFileName
import com.alan.testtask.data.util.uriToBase64
import com.alan.testtask.domain.entity.AvatarData
import com.alan.testtask.domain.entity.ProfileData


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    profileViewModel: ProfileViewModel,
    onSaveClick: (ProfileData) -> Unit,
    onBackClick: () -> Unit
) {
    val profileState = profileViewModel.profileState.collectAsState().value

    if (profileState is ProfileState.Success) {
        val profileData = profileState.profileData

        var name by rememberSaveable { mutableStateOf(profileData.name) }
        var birthday by rememberSaveable { mutableStateOf(profileData.birthday ?: "") }
        var city by rememberSaveable { mutableStateOf(profileData.city ?: "") }
        var vk by rememberSaveable { mutableStateOf(profileData.vk ?: "") }
        var instagram by rememberSaveable { mutableStateOf(profileData.instagram ?: "") }
        var status by rememberSaveable { mutableStateOf(profileData.status ?: "") }
        var avatarUri by remember { mutableStateOf<Uri?>(null) }
        var avatarBase64 by remember { mutableStateOf("") }

        val context = LocalContext.current

        val launcher =
            rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    avatarUri = it
                    avatarBase64 = uriToBase64(it, context)
                }
            }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.edit_profile)) },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(
                                    R.string.back
                                )
                            )
                        }
                    }
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    IconButton(
                        onClick = { launcher.launch(context.getString(R.string.image)) },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        if (avatarUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(avatarUri),
                                contentDescription = stringResource(R.string.avatar),
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                            )
                        } else if (profileData.avatar?.filename?.isNotEmpty() == true) {
                            Image(
                                painter = rememberAsyncImagePainter(profileData.avatar.filename),
                                contentDescription = stringResource(R.string.avatar),
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(Color.Gray, CircleShape)
                            ) {
                                Text(
                                    text = stringResource(R.string.avatar),
                                    modifier = Modifier.align(Alignment.Center),
                                    color = Color.White
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    name?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = { name = it },
                            label = { Text(stringResource(R.string.name)) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = birthday,
                        onValueChange = { birthday = it },
                        label = { Text(stringResource(R.string.birthday)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = city,
                        onValueChange = { city = it },
                        label = { Text(stringResource(R.string.city)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = vk,
                        onValueChange = { vk = it },
                        label = { Text(stringResource(R.string.vk)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = instagram,
                        onValueChange = { instagram = it },
                        label = { Text(stringResource(R.string.instagram)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = status,
                        onValueChange = { status = it },
                        label = { Text(stringResource(R.string.about_me)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val formattedBirthday =
                                profileViewModel.convertDateToServerFormat(birthday, context)

                            val updatedProfileData = profileData.copy(
                                name = name,
                                birthday = formattedBirthday,
                                city = city,
                                vk = vk,
                                instagram = instagram,
                                status = status,
                                avatar = AvatarData(
                                    filename = avatarUri?.let { getFileName(it, context) }
                                        ?: profileData.avatar?.filename ?: "",
                                    base64 = avatarBase64
                                )
                            )
                            onSaveClick(updatedProfileData)
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(stringResource(R.string.save))
                    }
                }
            }
        )
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(R.string.no_profile_data_available), color = Color.Gray)
        }
    }
}
