package com.alan.testtask.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey val phone: String,
    val name: String,
    val username: String,
    val birthday: String?,
    val city: String?,
    val vk: String?,
    val instagram: String?,
    val status: String?,
    val avatarFilename: String?
)