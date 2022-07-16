package com.example.androidel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrainerDataClass(
    val number: Int,
    val image: Int,
    val name: String,
    val day: String,
    val time: String,
    var isChecked: Boolean,
): Parcelable
