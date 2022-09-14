package com.example.androidel.trainer.model

import com.google.gson.annotations.SerializedName

data class TrainerListModel (
    @SerializedName("data")
    val trainer: MutableList<TrainerItemModel>
)