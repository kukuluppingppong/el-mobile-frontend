package com.example.androidel.record.models

data class RecordWorkoutResult(
    val data: List<WorkoutData>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)