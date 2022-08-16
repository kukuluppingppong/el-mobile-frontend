package com.example.androidel

import android.graphics.Bitmap

data class SendDataClass(
    var video: Bitmap,
    val part: String,
    val set: Int,
    val number: Int,
    val strength: String,
    var score: Int,
)
