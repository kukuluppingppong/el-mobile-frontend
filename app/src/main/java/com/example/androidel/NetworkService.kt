package com.example.androidel

import com.example.androidel.model.LoginResponse
import com.example.androidel.model.TrainerListModel
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
    @GET("api/training")
    fun getTrainerList(): Call<TrainerListModel>

    @FormUrlEncoded
    @POST("api/users/login")
    fun login(
        @Field("id") id: String?,
        @Field("password") password: String?,
    ): Call<LoginResponse>
}