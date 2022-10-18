package com.example.androidel.record.models

data class RecordDietResult(
    val data: List<DietData>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)