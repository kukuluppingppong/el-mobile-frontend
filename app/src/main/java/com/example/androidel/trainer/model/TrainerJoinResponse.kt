package com.example.androidel.trainer.model

data class TrainerJoinResponse(
    val message: String,
    val path: String,
    val statusCode: Int,
    val success: Boolean,
    val timeStamp: String
)