package com.alan.testtask.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alan.testtask.domain.entity.ChatInfo

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    profileScreenContent: @Composable () -> Unit,
    editProfileScreenContent: @Composable () -> Unit,
    chatsScreenContent: @Composable (onChatSelected: (ChatInfo) -> Unit) -> Unit,
    chatScreenContent: @Composable (ChatInfo) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            chatsScreenContent { chatInfo ->
                val chatRoute = Screen.Chat.getRouteWithArgs(chatInfo)
                navHostController.navigate(chatRoute)
            }
        }

        composable(
            route = Screen.Chat.route,
            arguments = listOf(
                navArgument(Screen.KEY_CHAT) {
                    type = ChatInfo.NavigationType
                }
            )
        ) {
            val chatInfo = it.arguments?.getParcelable<ChatInfo>(Screen.KEY_CHAT)
                ?: throw RuntimeException("Args is null")
            chatScreenContent(chatInfo)
        }

        composable(Screen.Profile.route) {
            profileScreenContent()
        }

        composable(Screen.EditProfile.route) {
            editProfileScreenContent()
        }
    }
}
