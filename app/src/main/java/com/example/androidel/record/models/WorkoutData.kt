package com.example.androidel.record.models

data class WorkoutData(
    val degree: String,
    val index: Int,
    val parts: List<String>,
    val reps: Int,
    val sets: Int,
    val time: String,
    val video: String
)