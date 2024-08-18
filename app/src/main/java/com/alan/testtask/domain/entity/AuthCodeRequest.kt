package com.alan.testtask.domain.entity

data class AuthCodeRequest(
    val phone: String,
    val code: String
)