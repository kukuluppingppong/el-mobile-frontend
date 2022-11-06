package com.example.androidel.trainer.model

import com.google.gson.annotations.SerializedName

data class TrainerItemModel(
    val name: String,
    val award: String,
    val career: String,
    @SerializedName("trainer_id") val trainerId: Int,
    @SerializedName("profile_image") val image: String
)
