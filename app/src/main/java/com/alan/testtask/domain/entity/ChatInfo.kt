package com.alan.testtask.domain.entity

import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.navigation.NavType
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.parcelize.Parcelize


@Parcelize
@Immutable
class ChatInfo(val str: String) : Parcelable {
    companion object {
        val NavigationType: NavType<ChatInfo> = object : NavType<ChatInfo>(false) {

            override fun get(bundle: Bundle, key: String): ChatInfo? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): ChatInfo {
                return try {
                    Gson().fromJson(value, ChatInfo::class.java)
                } catch (e: JsonSyntaxException) {
                    throw IllegalArgumentException("Failed to parse ChatInfo from value: $value", e)
                }
            }

            override fun put(bundle: Bundle, key: String, value: ChatInfo) {
                bundle.putParcelable(key, value)
            }
        }
    }
}
