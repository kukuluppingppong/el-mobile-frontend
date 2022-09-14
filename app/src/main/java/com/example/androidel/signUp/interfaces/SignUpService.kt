package com.example.androidel.signUp.interfaces

import com.example.androidel.signUp.model.SignUpBody
import com.example.androidel.signUp.model.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("api/users")
    fun signUp(@Body params: SignUpBody): Call<SignUpResponse>
}