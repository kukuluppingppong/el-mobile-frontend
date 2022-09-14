package com.example.androidel.signUp.model

import com.google.gson.annotations.SerializedName

data class SignUpBody(
    val id: String,
    val name: String,
    val password: String,
    @SerializedName("phone_number") val phoneNumber: String
)