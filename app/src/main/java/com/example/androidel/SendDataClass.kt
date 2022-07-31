package com.example.androidel

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SendDataClass(
    var video: Bitmap,
    val part: String,
    val set: Int,
    val number: Int,
    val strength: String,
    var score: Int,
): Parcelable
