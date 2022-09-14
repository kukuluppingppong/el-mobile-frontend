package com.example.androidel.signUp.model

data class SignUpResponse(
    val message: String,
    val path: String,
    val statusCode: Int,
    val success: Boolean,
    val timeStamp: String
)