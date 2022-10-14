package com.example.androidel.login.models

data class TrainerResponse(
    val data: List<Data>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)