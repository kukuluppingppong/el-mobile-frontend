package com.example.androidel.model

import com.google.gson.annotations.SerializedName

data class TrainerItemModel(
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("award") val award: String
)
