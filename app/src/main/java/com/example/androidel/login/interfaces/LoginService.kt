package com.example.androidel.login.interfaces

import com.example.androidel.login.models.LoginResponse
import com.example.androidel.login.models.LoginResult
import retrofit2.Call
import retrofit2.http.*

interface LoginService {
    @POST("api/users/login")
    fun login(@Body params: LoginResponse): Call<LoginResult>
}