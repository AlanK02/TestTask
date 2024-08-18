package com.alan.testtask.presentation.main


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alan.testtask.R
import com.alan.testtask.navigation.AppNavGraph
import com.alan.testtask.navigation.Screen
import com.alan.testtask.navigation.rememberNavigationState
import com.alan.testtask.presentation.chat.ChatScreen
import com.alan.testtask.presentation.chat.ChatsScreen
import com.alan.testtask.presentation.profile.EditProfileScreen
import com.alan.testtask.presentation.profile.ProfileScreen
import com.alan.testtask.presentation.profile.ProfileState
import com.alan.testtask.presentation.profile.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavScreen(profileViewModel: ProfileViewModel, phoneNumber: String) {
    val navigationState = rememberNavigationState()

    LaunchedEffect(Unit) {
        profileViewModel.loadProfile(phoneNumber)
    }

    val profileState by profileViewModel.profileState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
                    val currentScreen = navBackStackEntry?.destination?.route
                    Text(
                        text = when (currentScreen) {
                            Screen.Home.route -> stringResource(R.string.chats)
                            Screen.Profile.route -> stringResource(R.string.profile)
                            else -> stringResource(R.string.chat)
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Profile
                )
                items.forEach { item ->
                    val selected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navigationState.navigateTo(item.screen.route)
                            }
                        },
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            chatsScreenContent = {
                ChatsScreen(
                    onChatSelected = { chatInfo ->
                        navigationState.navigateTo(Screen.Chat.getRouteWithArgs(chatInfo))
                    },
                    modifier = Modifier.padding(paddingValues)
                )
            },
            chatScreenContent = { chatInfo ->
                ChatScreen(
                    chatInfo = chatInfo,
                    modifier = Modifier.padding(paddingValues)
                )
            },
            profileScreenContent = {
                when (profileState) {
                    is ProfileState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is ProfileState.Success -> {
                        ProfileScreen(
                            profileData = (profileState as ProfileState.Success).profileData,
                            onEditProfileClick = {
                                navigationState.navigateTo(Screen.EditProfile.route)
                            }
                        )
                    }

                    is ProfileState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (profileState as ProfileState.Error).message,
                                color = Color.Red
                            )
                        }
                    }
                }
            },
            editProfileScreenContent = {
                EditProfileScreen(
                    profileViewModel = profileViewModel,
                    onSaveClick = { updatedProfileData ->
                        profileViewModel.updateProfile(updatedProfileData)
                        profileViewModel.loadProfile(phoneNumber)
                        navigationState.navHostController.popBackStack()
                    },
                    onBackClick = {
                        navigationState.navHostController.popBackStack()
                    }
                )
            }
        )
    }
}
