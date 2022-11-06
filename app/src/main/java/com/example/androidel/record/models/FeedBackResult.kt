package com.example.androidel.record.models

data class FeedBackResult(
    val data: List<String>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)