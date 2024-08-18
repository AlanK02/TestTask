package com.alan.testtask.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.alan.testtask.domain.entity.ChatInfo


fun NavGraphBuilder.homeScreenNavGraph(
    navController: NavController,
    chatsScreenContent: @Composable (onChatSelected: (ChatInfo) -> Unit) -> Unit,
    chatScreenContent: @Composable (ChatInfo) -> Unit
) {
    navigation(
        startDestination = Screen.Home.route,
        route = "home_graph"
    ) {
        composable(Screen.Home.route) {
            chatsScreenContent { chatInfo ->
                val chatRoute = Screen.Chat.getRouteWithArgs(chatInfo)
                navController.navigate(chatRoute)
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
    }
}
