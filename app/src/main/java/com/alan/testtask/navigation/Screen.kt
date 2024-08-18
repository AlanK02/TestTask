package com.alan.testtask.navigation

import android.net.Uri
import com.alan.testtask.domain.entity.ChatInfo
import com.google.gson.Gson


sealed class Screen(
    val route: String
) {
    data object Profile : Screen(ROUTE_PROFILE)
    data object Home : Screen(ROUTE_HOME)
    data object Chat : Screen(ROUTE_CHAT) {

        private const val ROUTE_FOR_ARGS = "chat"

        fun getRouteWithArgs(chat: ChatInfo): String {
            val chatJson = Gson().toJson(chat)
            return "$ROUTE_FOR_ARGS/${chatJson.encode()}"
        }
    }

    data object EditProfile : Screen(ROUTE_EDIT_PROFILE)

    companion object {
        const val ROUTE_PROFILE = "profile"
        const val ROUTE_HOME = "home"
        const val KEY_CHAT = "chat_info"
        const val ROUTE_CHAT = "chat/{$KEY_CHAT}"
        const val ROUTE_EDIT_PROFILE = "edit_profile"
    }
}

fun String.encode(): String {
    return Uri.encode(this)
}
