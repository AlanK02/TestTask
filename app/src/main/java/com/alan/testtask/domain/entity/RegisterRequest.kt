package com.alan.testtask.domain.entity

data class RegisterRequest(
    val phone: String,
    val name: String,
    val username: String
)