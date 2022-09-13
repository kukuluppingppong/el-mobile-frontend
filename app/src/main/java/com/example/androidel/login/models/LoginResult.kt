package com.example.androidel.login.models

data class LoginResult(
    val success: Boolean,
    val statusCode: Int,
    val message: String,
    val data: Token
)